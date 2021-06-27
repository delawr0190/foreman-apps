package mn.foreman.pickaxe.command.asic.scan;

import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.CommandUpdate;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MinerType;
import mn.foreman.pickaxe.command.CommandCompletionCallback;
import mn.foreman.pickaxe.command.CommandStrategy;
import mn.foreman.pickaxe.command.asic.Manufacturer;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    /** The source strategy. */
    private final IpSourceStrategy ipSourceStrategy;

    /**
     * Constructor.
     *
     * @param configuration    The configuration.
     * @param ipSourceStrategy The source strategy.
     */
    public ScanStrategy(
            final ApplicationConfiguration configuration,
            final IpSourceStrategy ipSourceStrategy) {
        this(
                new NullFilteringStrategy(),
                configuration,
                ipSourceStrategy);
    }

    /**
     * Constructor.
     *
     * @param filteringStrategy The strategy for filtering.
     * @param configuration     The configuration.
     * @param ipSourceStrategy  The strategy for generating the IPs to scan.
     */
    public ScanStrategy(
            final FilteringStrategy filteringStrategy,
            final ApplicationConfiguration configuration,
            final IpSourceStrategy ipSourceStrategy) {
        this.filteringStrategy = filteringStrategy;
        this.configuration = configuration;
        this.ipSourceStrategy = ipSourceStrategy;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void runCommand(
            final CommandStart command,
            final CommandCompletionCallback commandCompletionCallback,
            final CommandDone.CommandDoneBuilder builder) {
        final Map<String, Object> args = command.args;

        final String type = safeGet(args, "type");
        final int port = Integer.parseInt(safeGet(args, "port"));
        final List<String> targetMacs =
                (List<String>) args.getOrDefault(
                        "targetMacs",
                        new LinkedList<String>());

        switch (type) {
            case "asic":
                runAsicScan(
                        command,
                        commandCompletionCallback,
                        safeGet(args, "manufacturer"),
                        port,
                        targetMacs,
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
     * @param scanResults               The results.
     * @param scanned                   The scan counter.
     * @param remaining                 The remaining counter.
     * @param miners                    All of the miners that were found.
     * @param commandCompletionCallback The API handler.
     * @param id                        The scan command ID.
     * @param targetMacs                The target mac.
     */
    private void processResults(
            final BlockingQueue<ScanResult> scanResults,
            final AtomicInteger scanned,
            final AtomicInteger remaining,
            final BlockingQueue<Object> miners,
            final CommandCompletionCallback commandCompletionCallback,
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

            commandCompletionCallback.update(
                    id,
                    CommandUpdate
                            .builder()
                            .command("scan")
                            .update(update)
                            .build());
        }
    }

    /**
     * Scans for an ASIC.
     *
     * @param command                   The command.
     * @param commandCompletionCallback The Foreman API handle.
     * @param manufacturer              The manufacturer.
     * @param port                      The port to inspect.
     * @param targetMacs                The target MACs.
     * @param args                      The arguments.
     * @param builder                   The builder to use for creating the
     *                                  final result.
     */
    private void runAsicScan(
            final CommandStart command,
            final CommandCompletionCallback commandCompletionCallback,
            final String manufacturer,
            final int port,
            final List<String> targetMacs,
            final Map<String, Object> args,
            final CommandDone.CommandDoneBuilder builder) {
        Manufacturer
                .fromName(manufacturer)
                .ifPresent(value -> scanInRange(
                        commandCompletionCallback,
                        command.id,
                        port,
                        targetMacs,
                        args,
                        value,
                        builder));
    }

    /**
     * Scans a valid range.
     *
     * @param commandCompletionCallback The Foreman API handle.
     * @param id                        The command ID.
     * @param port                      The port to inspect.
     * @param targetMacs                The target macs.
     * @param args                      The arguments.
     * @param builder                   The builder to use for creating the
     *                                  final result.
     * @param manufacturer              The manufacturer.
     */
    private void scanInRange(
            final CommandCompletionCallback commandCompletionCallback,
            final String id,
            final int port,
            final List<String> targetMacs,
            final Map<String, Object> args,
            final Manufacturer manufacturer,
            final CommandDone.CommandDoneBuilder builder) {
        final BlockingQueue<String> ipsToScan =
                this.ipSourceStrategy.toIps(
                        args);
        if (ipsToScan.size() < 65_536) {
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
                        commandCompletionCallback,
                        id,
                        targetMacs);
            }

            // Once more for the race
            processResults(
                    scanResults,
                    scanned,
                    remaining,
                    foundMiners,
                    commandCompletionCallback,
                    id,
                    targetMacs);

            final List<Object> miners = new LinkedList<>();
            foundMiners.drainTo(miners);

            commandCompletionCallback.done(
                    id,
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
        } else {
            commandCompletionCallback.done(
                    id,
                    builder.status(
                            CommandDone.Status
                                    .builder()
                                    .type(DoneStatus.FAILED)
                                    .message("Subnet range is too large")
                                    .build())
                            .build());
        }
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
        private final BlockingQueue<String> ipsToScan;

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
                final BlockingQueue<String> ipsToScan,
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
                    final String ip = this.ipsToScan.poll(5, TimeUnit.SECONDS);
                    if (ip != null) {
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
