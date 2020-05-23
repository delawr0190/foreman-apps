package mn.foreman.pickaxe.command.scan;

import mn.foreman.api.ForemanApi;
import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MinerType;
import mn.foreman.model.command.CommandDone;
import mn.foreman.model.command.CommandStart;
import mn.foreman.model.command.CommandUpdate;
import mn.foreman.pickaxe.command.CommandStrategy;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            final CommandDone.CommandDoneBuilder builder) {
        final Map<String, String> args = command.args;

        final String type = safeGet(args, "type");
        final String subnet = safeGet(args, "subnet");
        final int start = Integer.parseInt(safeGet(args, "start"));
        final int stop = Integer.parseInt(safeGet(args, "stop"));
        final int port = Integer.parseInt(safeGet(args, "port"));

        switch (type) {
            case "asic":
                runAsicScan(
                        command,
                        foremanApi,
                        safeGet(args, "manufacturer"),
                        subnet,
                        start,
                        stop,
                        port,
                        args,
                        builder);
                break;
            default:
                break;
        }
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
     * @param subnet       The subnet.
     * @param start        Where to start.
     * @param stop         Where to stop.
     * @param port         The port to inspect.
     * @param args         The arguments.
     * @param builder      The builder to use for creating the final result.
     */
    private void runAsicScan(
            final CommandStart command,
            final ForemanApi foremanApi,
            final String manufacturer,
            final String subnet,
            final int start,
            final int stop,
            final int port,
            final Map<String, String> args,
            final CommandDone.CommandDoneBuilder builder) {
        final Optional<Manufacturer> type =
                Manufacturer.fromName(manufacturer);
        if (type.isPresent()) {
            final Manufacturer manufacturerType =
                    type.get();
            runScan(
                    foremanApi,
                    command.id,
                    subnet,
                    start,
                    stop,
                    port,
                    args,
                    manufacturerType.getDetectionStrategy(),
                    builder);
        }
    }

    /**
     * Scans for miners.
     *
     * @param foremanApi        The Foreman API handle.
     * @param id                The command ID.
     * @param subnet            The subnet.
     * @param start             Where to start.
     * @param stop              Where to stop.
     * @param port              The port to inspect.
     * @param args              The arguments.
     * @param builder           The builder to use for creating the final
     *                          result.
     * @param detectionStrategy The strategy to use for detecting miners.
     */
    private void runScan(
            final ForemanApi foremanApi,
            final String id,
            final String subnet,
            final int start,
            final int stop,
            final int port,
            final Map<String, String> args,
            final DetectionStrategy detectionStrategy,
            final CommandDone.CommandDoneBuilder builder) {
        final List<Object> miners = new LinkedList<>();

        for (int i = start; i <= stop; i++) {
            final String ip = String.format("%s.%d", subnet, i);
            final Optional<Detection> detectionOpt =
                    detectionStrategy.detect(
                            ip,
                            port,
                            args);
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

        builder.result(
                ImmutableMap.of(
                        "miners",
                        miners));
    }
}
