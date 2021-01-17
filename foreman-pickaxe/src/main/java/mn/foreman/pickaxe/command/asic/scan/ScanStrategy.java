package mn.foreman.pickaxe.command.asic.scan;

import mn.foreman.api.ForemanApi;
import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.CommandUpdate;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MinerType;
import mn.foreman.pickaxe.command.CommandStrategy;
import mn.foreman.pickaxe.command.asic.Manufacturer;

import com.google.common.collect.ImmutableMap;
import com.google.common.net.InetAddresses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.*;

import static mn.foreman.pickaxe.command.util.CommandUtils.safeGet;

/** Scans the provided subnet and start/stop for miners. */
public class ScanStrategy
        implements CommandStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(ScanStrategy.class);

    @Override
    public void runCommand(
            final CommandStart command,
            final ForemanApi foremanApi,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        final Map<String, Object> args = command.args;

        final String type = safeGet(args, "type");
        final long start = ipToLong(safeGet(args, "start"));
        final long stop = ipToLong(safeGet(args, "stop"));
        final int port = Integer.parseInt(safeGet(args, "port"));

        switch (type) {
            case "asic":
                runAsicScan(
                        command,
                        foremanApi,
                        safeGet(args, "manufacturer"),
                        start,
                        stop,
                        port,
                        args,
                        builder,
                        callback);
                break;
            default:
                break;
        }
    }

    /**
     * Converts the IP as a long to an IP address.
     *
     * @param ip IP to convert.
     *
     * @return The ip.
     */
    private static String ipFromLong(final long ip) {
        return String.format("%d.%d.%d.%d",
                (ip >>> 24) & 0xff,
                (ip >>> 16) & 0xff,
                (ip >>> 8) & 0xff,
                (ip) & 0xff);
    }

    /**
     * Converts the IP as a string to a long.
     *
     * @param ip IP to convert.
     *
     * @return The ip.
     */
    @SuppressWarnings("UnstableApiUsage")
    private static long ipToLong(final String ip) {
        final InetAddress inetAddress = InetAddresses.forString(ip);
        final int ipInt = InetAddresses.coerceToInteger(inetAddress);
        return ipInt & 0xFFFFFFFFL;
    }

    /**
     * Converts the provided {@link Detection} to a miner.
     *
     * @param detection The {@link Detection}.
     *
     * @return The miner.
     */
    private static Object toMiner(final Detection detection) {
        final MinerType minerType = detection.getMinerType();
        return ImmutableMap.of(
                "apiPort",
                detection.getPort(),
                "parameters",
                detection.getParameters(),
                "key",
                minerType.getSlug(),
                "category",
                minerType.getCategory().getName(),
                "ipAddress",
                detection.getIpAddress());
    }

    /**
     * Scans for an ASIC.
     *
     * @param command      The command.
     * @param foremanApi   The Foreman API handle.
     * @param manufacturer The manufacturer.
     * @param start        Where to start.
     * @param stop         Where to stop.
     * @param port         The port to inspect.
     * @param args         The arguments.
     * @param builder      The builder to use for creating the final result.
     * @param callback     The callback.
     */
    private void runAsicScan(
            final CommandStart command,
            final ForemanApi foremanApi,
            final String manufacturer,
            final long start,
            final long stop,
            final int port,
            final Map<String, Object> args,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        Manufacturer
                .fromName(manufacturer)
                .ifPresent(value -> runScan(
                        foremanApi,
                        command.id,
                        start,
                        stop,
                        port,
                        args,
                        value,
                        builder,
                        callback));
    }

    /**
     * Scans for miners.
     *
     * @param foremanApi   The Foreman API handle.
     * @param id           The command ID.
     * @param start        Where to start.
     * @param stop         Where to stop.
     * @param port         The port to inspect.
     * @param args         The arguments.
     * @param builder      The builder to use for creating the final result.
     * @param manufacturer The manufacturer.
     * @param callback     The callback.
     */
    private void runScan(
            final ForemanApi foremanApi,
            final String id,
            final long start,
            final long stop,
            final int port,
            final Map<String, Object> args,
            final Manufacturer manufacturer,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        if (stop - start <= 65_536) {
            scanInRange(
                    foremanApi,
                    id,
                    start,
                    stop,
                    port,
                    args,
                    manufacturer,
                    builder,
                    callback);
        } else {
            callback.done(
                    builder.status(
                            CommandDone.Status
                                    .builder()
                                    .type(DoneStatus.FAILED)
                                    .message("Subnet range is too large")
                                    .build())
                            .build());
        }
    }

    /**
     * Scans a valid range.
     *
     * @param foremanApi   The Foreman API handle.
     * @param id           The command ID.
     * @param start        Where to start.
     * @param stop         Where to stop.
     * @param port         The port to inspect.
     * @param args         The arguments.
     * @param builder      The builder to use for creating the final result.
     * @param manufacturer The manufacturer.
     * @param callback     The callback.
     */
    private void scanInRange(
            final ForemanApi foremanApi,
            final String id,
            final long start,
            final long stop,
            final int port,
            final Map<String, Object> args,
            final Manufacturer manufacturer,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        final List<Object> miners = new LinkedList<>();
        for (long i = start; i <= stop; i++) {
            final String ip = ipFromLong(i);
            final DetectionStrategy detectionStrategy =
                    manufacturer.getDetectionStrategy(
                            args,
                            ip);

            Optional<Detection> detectionOpt = Optional.empty();
            try {
                detectionOpt =
                        detectionStrategy.detect(
                                ip,
                                port,
                                args);
            } catch (final Exception e) {
                LOG.warn("Exception occurred while querying", e);
            }

            LOG.debug("Scanning {}:{}", ip, port);

            final Map<String, Object> update = new HashMap<>();
            update.put("found", miners.size());
            update.put("scanned", (i - start) + 1);
            update.put("remaining", stop - i);

            if (detectionOpt.isPresent()) {
                final Object miner = toMiner(detectionOpt.get());
                miners.add(miner);
                update.put("miner", miner);
            }

            foremanApi
                    .pickaxe()
                    .commandUpdate(
                            CommandUpdate
                                    .builder()
                                    .command("scan")
                                    .update(update)
                                    .build(),
                            id);
        }
        callback.done(
                builder
                        .result(
                                ImmutableMap.of(
                                        "miners",
                                        miners))
                        .status(
                                CommandDone.Status
                                        .builder()
                                        .type(DoneStatus.SUCCESS)
                                        .build())
                        .build());
    }
}
