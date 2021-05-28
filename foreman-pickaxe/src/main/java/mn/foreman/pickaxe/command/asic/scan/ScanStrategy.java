package mn.foreman.pickaxe.command.asic.scan;

import mn.foreman.api.ForemanApi;
import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.CommandUpdate;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.model.ApplicationConfiguration;
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
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

import static mn.foreman.pickaxe.command.util.CommandUtils.safeGet;

/** Scans the provided subnet and start/stop for miners. */
public class ScanStrategy
        implements CommandStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(ScanStrategy.class);

    /** Never use more than 5 threads to scan. */
    private static final int SCAN_THREADS =
            Math.max(
                    6,
                    Runtime.getRuntime().availableProcessors() * 4);

    /** The thread pool to use for scanning. */
    private static final ExecutorService THREAD_POOL =
            Executors.newCachedThreadPool();

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /** The filtering strategy. */
    private final FilteringStrategy filteringStrategy;

    /**
     * Constructor.
     *
     * @param configuration The configuration.
     */
    public ScanStrategy(final ApplicationConfiguration configuration) {
        this(
                new NullFilteringStrategy(),
                configuration);
    }

    /**
     * Constructor.
     *
     * @param filteringStrategy The strategy for filtering.
     * @param configuration     The configuration.
     */
    public ScanStrategy(
            final FilteringStrategy filteringStrategy,
            final ApplicationConfiguration configuration) {
        this.filteringStrategy = filteringStrategy;
        this.configuration = configuration;
    }

    @SuppressWarnings("unchecked")
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
        final List<String> targetMacs =
                (List<String>) args.getOrDefault(
                        "targetMacs",
                        new LinkedList<String>());

        switch (type) {
            case "asic":
                runAsicScan(
                        command,
                        foremanApi,
                        safeGet(args, "manufacturer"),
                        start,
                        stop,
                        port,
                        targetMacs,
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
     * Checks to see if all of the scans are done.
     *
     * @param futures The scans.
     *
     * @return Whether or not all of the scans are done.
     */
    private boolean areScansDone(final List<Future<?>> futures) {
        return futures
                .stream()
                .allMatch(Future::isDone);
    }

    /**
     * Processes the scan results.
     *
     * @param scanResults The results.
     * @param scanned     The scan counter.
     * @param remaining   The remaining counter.
     * @param miners      All of the miners that were found.
     * @param foremanApi  The API handler.
     * @param id          The scan command ID.
     * @param targetMacs  The target mac.
     */
    private void processResults(
            final BlockingQueue<ScanResult> scanResults,
            final AtomicInteger scanned,
            final AtomicInteger remaining,
            final BlockingQueue<Object> miners,
            final ForemanApi foremanApi,
            final String id,
            final List<String> targetMacs) {
        final List<ScanResult> lastResults = new LinkedList<>();
        scanResults.drainTo(lastResults);

        if (!lastResults.isEmpty()) {
            for (final ScanResult scanResult : lastResults) {
                scanned.incrementAndGet();
                remaining.decrementAndGet();
                final Detection detection = scanResult.result;
                if (detection != null &&
                        this.filteringStrategy.matches(
                                detection,
                                targetMacs)) {
                    miners.add(
                            toMiner(
                                    detection));
                }
            }

            final Map<String, Object> update = new HashMap<>();
            update.put("found", miners.size());
            update.put("scanned", scanned.get());
            update.put("remaining", remaining.get());

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
     * @param targetMacs   The target MACs.
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
            final List<String> targetMacs,
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
                        targetMacs,
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
     * @param targetMacs   The target macs.
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
            final List<String> targetMacs,
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
                    targetMacs,
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
     * @param targetMacs   The target macs.
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
            final List<String> targetMacs,
            final Map<String, Object> args,
            final Manufacturer manufacturer,
            final CommandDone.CommandDoneBuilder builder,
            final Callback callback) {
        final BlockingQueue<Long> ipsToScan = new LinkedBlockingDeque<>();
        LongStream
                .rangeClosed(start, stop)
                .forEach(ipsToScan::add);

        final AtomicInteger scanned = new AtomicInteger(0);
        final AtomicInteger remaining = new AtomicInteger(ipsToScan.size());

        final BlockingQueue<ScanResult> scanResults =
                new LinkedBlockingDeque<>();
        final BlockingQueue<Object> foundMiners =
                new LinkedBlockingDeque<>();

        final List<Future<?>> scanFutures = new ArrayList<>(SCAN_THREADS);
        for (int i = 0; i < SCAN_THREADS; i++) {
            scanFutures.add(
                    THREAD_POOL.submit(
                            new Scanner(
                                    this.configuration,
                                    args,
                                    ipsToScan,
                                    manufacturer,
                                    port,
                                    scanResults)));
        }

        while (!areScansDone(scanFutures)) {
            processResults(
                    scanResults,
                    scanned,
                    remaining,
                    foundMiners,
                    foremanApi,
                    id,
                    targetMacs);
        }

        // Once more for the race
        processResults(
                scanResults,
                scanned,
                remaining,
                foundMiners,
                foremanApi,
                id,
                targetMacs);

        final List<Object> miners = new LinkedList<>();
        foundMiners.drainTo(miners);

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

    /** A {@link ScanResult} represents the result of a sigle IP scan. */
    private static class ScanResult {

        /** The result, if something was found. */
        private final Detection result;

        /**
         * Constructor.
         *
         * @param result The result, if something was found.
         */
        ScanResult(final Detection result) {
            this.result = result;
        }
    }

    /**
     * A {@link Scanner} provides a {@link Runnable} implementation that will
     * continuously scan miners until there are no more IPs that need to be
     * evaluated.
     */
    private static class Scanner
            implements Runnable {

        /** The arguments. */
        private final Map<String, Object> args;

        /** The configuration. */
        private final ApplicationConfiguration configuration;

        /** The IPs to scan. */
        private final BlockingQueue<Long> ipsToScan;

        /** The manufacturer. */
        private final Manufacturer manufacturer;

        /** The miner API port. */
        private final int port;

        /** Where the result will be stored. */
        private final BlockingQueue<ScanResult> scanResults;

        /**
         * Constructor.
         *
         * @param configuration The configuration.
         * @param args          The arguments.
         * @param ipsToScan     The IPs to scan.
         * @param manufacturer  The manufacturer.
         * @param port          The port.
         * @param scanResults   The results.
         */
        private Scanner(
                final ApplicationConfiguration configuration,
                final Map<String, Object> args,
                final BlockingQueue<Long> ipsToScan,
                final Manufacturer manufacturer,
                final int port,
                final BlockingQueue<ScanResult> scanResults) {
            this.configuration = configuration;
            this.args = args;
            this.ipsToScan = ipsToScan;
            this.manufacturer = manufacturer;
            this.port = port;
            this.scanResults = scanResults;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    final Long ipLong = this.ipsToScan.poll(5, TimeUnit.SECONDS);
                    if (ipLong != null) {
                        final String ip = ipFromLong(ipLong);

                        final DetectionStrategy detectionStrategy =
                                this.manufacturer.getDetectionStrategy(
                                        this.args,
                                        ip,
                                        this.configuration);

                        LOG.debug("Scanning {}:{}", ip, this.port);

                        Optional<Detection> detectionOpt = Optional.empty();
                        try {
                            detectionOpt =
                                    detectionStrategy.detect(
                                            ip,
                                            this.port,
                                            this.args);
                        } catch (final Exception e) {
                            LOG.warn("Exception occurred while querying", e);
                        }

                        this.scanResults.add(
                                new ScanResult(
                                        detectionOpt.orElse(null)));
                    } else {
                        // Done
                        LOG.info("Scanner has finished");
                        break;
                    }
                }
            } catch (final Exception e) {
                LOG.warn("Scanner was interrupted", e);
            }
        }
    }
}
