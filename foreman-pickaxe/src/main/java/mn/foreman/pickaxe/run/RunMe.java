package mn.foreman.pickaxe.run;

import mn.foreman.api.ForemanApi;
import mn.foreman.api.ForemanApiImpl;
import mn.foreman.api.JdkWebUtil;
import mn.foreman.api.endpoints.miners.Miners;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.Commands;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.cache.SelfExpiringStatsCache;
import mn.foreman.model.cache.StatsCache;
import mn.foreman.pickaxe.command.CommandProcessor;
import mn.foreman.pickaxe.command.CommandProcessorImpl;
import mn.foreman.pickaxe.command.asic.AsicStrategyFactory;
import mn.foreman.pickaxe.command.asic.NullPostProcessor;
import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.miners.MinerConfiguration;
import mn.foreman.pickaxe.miners.remote.RemoteConfiguration;
import mn.foreman.pickaxe.process.HttpPostMetricsProcessingStrategy;
import mn.foreman.pickaxe.process.MetricsProcessingStrategy;
import mn.foreman.util.VersionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/** {@link RunMe} provides the application context for PICKAXE. */
public class RunMe {

    /** The Foreman base URL. */
    private static final String FOREMAN_BASE_URL;

    /** The logger for this class. */
    private final static Logger LOG =
            LoggerFactory.getLogger(RunMe.class);

    static {
        FOREMAN_BASE_URL =
                System.getProperty(
                        "FOREMAN_BASE_URL",
                        "https://api.foreman.mn");
    }

    /** The blacklisted miners. */
    private final Set<MinerID> blacklistedMiners =
            Sets.newConcurrentHashSet();

    /** The thread pool for running commands. */
    private final Executor commandThreadPool =
            Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors() * 4);

    /** The {@link Configuration}. */
    private final Configuration configuration;

    /** The {@link ForemanApi}. */
    private final ForemanApi foremanApi;

    /** Cache MAC addresses for 6 hours. */
    private final Cache<MinerID, String> macCache =
            CacheBuilder
                    .newBuilder()
                    .expireAfterWrite(6, TimeUnit.HOURS)
                    .build();

    /** The thread pool for sending metrics. */
    private final Executor metricsThreadPool =
            Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors() * 4);

    /** The factory for creating all of the {@link Miner miners}. */
    private final MinerConfiguration minerConfiguration;

    /** A cache of all of the miners. */
    private final AtomicReference<List<Miner>> miners =
            new AtomicReference<>(new LinkedList<>());

    /** An in-memory cache for holding all of the active stats. */
    private final StatsCache statsCache =
            new SelfExpiringStatsCache(
                    120,
                    TimeUnit.SECONDS);

    /** The thread pool for running tasks. */
    private final Executor statsThreadPool =
            Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors() * 8);

    /** The thread pool for running tasks. */
    private final ScheduledExecutorService threadPool =
            Executors.newScheduledThreadPool(
                    Runtime.getRuntime().availableProcessors() * 4);

    /**
     * Constructor.
     *
     * @param configuration The {@link Configuration}.
     */
    public RunMe(final Configuration configuration) {
        LOG.debug("Base url: {}", FOREMAN_BASE_URL);

        this.configuration = configuration;
        this.minerConfiguration =
                new RemoteConfiguration(
                        String.format(
                                "%s/api/config/%s/%s/%s/",
                                FOREMAN_BASE_URL,
                                configuration.getClientId(),
                                configuration.getPickaxeId(),
                                VersionUtils.getVersion()),
                        toFullUrl(
                                "api/nicehashv2"),
                        toFullUrl(
                                "api/autominer"),
                        toFullUrl(
                                "api/claymore"),
                        configuration.getApiKey());
        this.foremanApi =
                new ForemanApiImpl(
                        configuration.getClientId(),
                        configuration.getPickaxeId(),
                        new ObjectMapper(),
                        new JdkWebUtil(
                                FOREMAN_BASE_URL,
                                configuration.getApiKey()));
    }

    /**
     * Runs the application.
     *
     * <p>Note: this is the main processing function for PICKAXE.</p>
     */
    public void run() {
        final MetricsProcessingStrategy metricsProcessingStrategy =
                new HttpPostMetricsProcessingStrategy(
                        String.format(
                                "%s/%s/%s/%s",
                                FOREMAN_BASE_URL,
                                "api/metrics",
                                this.configuration.getClientId(),
                                this.configuration.getPickaxeId()),
                        this.configuration.getApiKey());

        final MetricsSender metricsSender =
                new MetricsSenderImpl(
                        metricsProcessingStrategy);

        startConfigQuerying();
        startUpdateMiners();
        startBlacklistFlush();
        startMacQuerying();

        // Only query for commands if pickaxe is running for command and control
        if (this.configuration.isControl()) {
            startCommandQuerying();
        }

        //noinspection InfiniteLoopStatement
        while (true) {
            final long deadline =
                    System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1);
            final List<StatsBatch> batches =
                    StatsBatch.toBatches(
                            this.statsCache.getMetrics(),
                            200);

            final CountDownLatch doneLatch = new CountDownLatch(batches.size());
            batches.forEach(
                    statsBatch ->
                            this.metricsThreadPool.execute(() -> {
                                try {
                                    metricsSender.sendMetrics(
                                            statsBatch.getBatchTime(),
                                            statsBatch.getBatch());
                                } catch (final Exception e) {
                                    LOG.warn("Exception while sending", e);
                                }
                                doneLatch.countDown();
                            }));

            try {
                doneLatch.await(1, TimeUnit.MINUTES);
            } catch (final InterruptedException e) {
                // Ignore
            }

            final long now = System.currentTimeMillis();
            if (now < deadline) {
                try {
                    TimeUnit.MILLISECONDS.sleep(deadline - now);
                } catch (final InterruptedException ie) {
                    // Ignore
                }
            } else {
                LOG.info("Took too long to send metrics - going again");
            }
        }
    }

    /**
     * Creates a full Foreman URL.
     *
     * @param uri The URI.
     *
     * @return The full URL.
     */
    private static String toFullUrl(
            final String uri) {
        return String.format(
                "%s/%s",
                FOREMAN_BASE_URL,
                uri);
    }

    /** Clears the {@link #blacklistedMiners} periodically. */
    private void startBlacklistFlush() {
        this.threadPool.scheduleWithFixedDelay(
                () -> {
                    LOG.info("Flushing blacklist");
                    this.blacklistedMiners.clear();
                },
                0,
                2,
                TimeUnit.MINUTES);
    }

    /** Schedules command and control querying. */
    private void startCommandQuerying() {
        final CommandProcessor commandProcessor =
                new CommandProcessorImpl(
                        this.foremanApi,
                        new AsicStrategyFactory(
                                new NullPostProcessor(),
                                this.threadPool,
                                this.blacklistedMiners,
                                this.statsCache));
        this.threadPool.scheduleAtFixedRate(
                () -> {
                    try {
                        final Optional<Commands> commands =
                                this.foremanApi
                                        .pickaxe()
                                        .getCommands();
                        if (commands.isPresent()) {
                            commands.get()
                                    .commands
                                    .forEach(command ->
                                            submitCommand(
                                                    command,
                                                    commandProcessor));
                        } else {
                            LOG.warn("Failed to obtain commands");
                        }
                    } catch (final Exception e) {
                        LOG.warn("Exception occurred while updating", e);
                    }
                },
                0,
                30,
                TimeUnit.SECONDS);
    }

    /**
     * Starts the thread that will continuously query for new configurations.
     */
    private void startConfigQuerying() {
        this.threadPool.scheduleWithFixedDelay(
                () -> {
                    try {
                        final List<Miner> currentMiners =
                                this.miners.get();
                        final List<Miner> newMiners =
                                this.minerConfiguration.load();
                        if (!CollectionUtils.isEqualCollection(
                                currentMiners,
                                newMiners)) {
                            LOG.debug("A new configuration has been obtained");
                            this.miners.set(newMiners);
                            this.blacklistedMiners.clear();
                        } else {
                            LOG.debug("No configuration changes were observed");
                        }
                    } catch (final Exception e) {
                        LOG.warn("Exception occurred while querying", e);
                    }
                },
                0,
                2,
                TimeUnit.MINUTES);
    }

    /** Starts the thread that will periodically query for MAC addresses. */
    private void startMacQuerying() {
        this.threadPool.scheduleWithFixedDelay(
                () -> {
                    LOG.info("Starting MAC querying...");
                    try {
                        final Map<Miners.Miner, String> newMacs = new HashMap<>();
                        this.miners
                                .get()
                                .stream()
                                .filter(miner -> !this.blacklistedMiners.contains(miner.getMinerID()))
                                .filter(miner -> this.macCache.getIfPresent(miner.getMinerID()) == null)
                                .forEach(miner -> {
                                    try {
                                        LOG.info("Attempting to obtain MAC for {}", miner);
                                        final MinerID minerID = miner.getMinerID();
                                        miner
                                                .getMacAddress()
                                                .map(String::toLowerCase)
                                                .ifPresent(mac -> {
                                                    final Miners.Miner apiMiner =
                                                            new Miners.Miner();
                                                    apiMiner.apiIp = miner.getIp();
                                                    apiMiner.apiPort = miner.getApiPort();
                                                    newMacs.put(
                                                            apiMiner,
                                                            mac);
                                                    this.macCache.put(
                                                            minerID,
                                                            mac);
                                                });
                                    } catch (final Exception e) {
                                        LOG.warn("Exception occurred while querying for MAC: {}",
                                                miner,
                                                e);
                                    }
                                });
                        this.foremanApi
                                .pickaxe()
                                .updateMacs(newMacs);
                    } catch (final Exception e) {
                        LOG.warn("Exception occurred while querying MACs", e);
                    }
                },
                1,
                5,
                TimeUnit.MINUTES);
    }

    /** Schedules the job to begin automatically updating miner stats. */
    private void startUpdateMiners() {
        this.threadPool.scheduleWithFixedDelay(
                () -> {
                    try {
                        LOG.debug("Updating miner stats cache");
                        final List<Miner> miners = this.miners.get();
                        final List<List<Miner>> partitionedMiners =
                                Lists.partition(
                                        miners,
                                        5);
                        final CountDownLatch doneLatch =
                                new CountDownLatch(partitionedMiners.size());
                        partitionedMiners.forEach(toScan ->
                                this.statsThreadPool.execute(() -> {
                                    try {
                                        updateMiners(toScan);
                                    } catch (final Exception e) {
                                        // Ignore
                                    } finally {
                                        doneLatch.countDown();
                                    }
                                }));
                        doneLatch.await();
                    } catch (final Exception e) {
                        LOG.warn("Exception occurred while updating", e);
                    }
                },
                0,
                30,
                TimeUnit.SECONDS);
    }

    /**
     * Submits the provided {@link CommandStart} to the {@link
     * #commandThreadPool} for processing.
     *
     * @param commandStart     The command to start.
     * @param commandProcessor The processor.
     */
    private void submitCommand(
            final CommandStart commandStart,
            final CommandProcessor commandProcessor) {
        this.commandThreadPool.execute(() -> {
            try {
                commandProcessor.runCommand(commandStart);
            } catch (final Exception e) {
                LOG.warn("Exception while running command", e);
            }
        });
    }

    /**
     * Fires a job for each miner that will query and update the cached stats
     * for it.
     *
     * @param miners The miners.
     */
    private void updateMiners(
            final List<Miner> miners) {
        miners
                .stream()
                .filter(miner -> !this.blacklistedMiners.contains(miner.getMinerID()))
                .forEach(miner -> {
                    final MinerID minerID = miner.getMinerID();
                    try {
                        this.statsCache.add(
                                minerID,
                                miner.getStats());
                        LOG.debug("Cached metrics for {}", miner);
                    } catch (final Exception e) {
                        LOG.info("Failed to obtain metrics for {}",
                                miner,
                                e);
                        this.blacklistedMiners.add(minerID);
                        this.statsCache.invalidate(minerID);
                    }
                });
    }
}