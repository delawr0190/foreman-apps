package mn.foreman.pickaxe.run;

import mn.foreman.model.MetricsReport;
import mn.foreman.model.Miner;
import mn.foreman.pickaxe.cache.MinerCache;
import mn.foreman.pickaxe.cache.MinerCacheImpl;
import mn.foreman.pickaxe.cache.SelfExpiringStatsCache;
import mn.foreman.pickaxe.cache.StatsCache;
import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.miners.MinerConfiguration;
import mn.foreman.pickaxe.miners.remote.RemoteConfiguration;
import mn.foreman.pickaxe.process.HttpPostMetricsProcessingStrategy;
import mn.foreman.pickaxe.process.MetricsProcessingStrategy;
import mn.foreman.util.VersionUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** {@link RunMe} provides the application context for PICKAXE. */
public class RunMe {

    /** The logger for this class. */
    private final static Logger LOG =
            LoggerFactory.getLogger(RunMe.class);

    /** A cache of all of the miners. */
    private final MinerCache allCache = new MinerCacheImpl();

    /** The {@link Configuration}. */
    private final Configuration configuration;

    /** The factory for creating all of the {@link Miner miners}. */
    private final MinerConfiguration minerConfiguration;

    /** An in-memory cache for holding all of the active stats. */
    private final StatsCache statsCache =
            new SelfExpiringStatsCache(
                    90,
                    TimeUnit.SECONDS);

    /** The thread pool for running tasks. */
    private final ScheduledExecutorService threadPool =
            Executors.newScheduledThreadPool(
                    Runtime.getRuntime().availableProcessors());

    /**
     * Constructor.
     *
     * @param configuration The {@link Configuration}.
     */
    public RunMe(final Configuration configuration) {
        this.configuration = configuration;
        this.minerConfiguration =
                new RemoteConfiguration(
                        String.format(
                                "%s/%s/%s",
                                configuration.getForemanConfigUrl(),
                                configuration.getPickaxeId(),
                                VersionUtils.getVersion()),
                        configuration.getApiKey());
    }

    /**
     * Runs the application.
     *
     * <p>Note: this is the main processing function for PICKAXE.</p>
     */
    public void run() {
        final MetricsProcessingStrategy metricsProcessingStrategy =
                new HttpPostMetricsProcessingStrategy(
                        this.configuration.getForemanApiUrl() + "/" +
                                this.configuration.getPickaxeId(),
                        this.configuration.getApiKey());

        // Query manually once - this will prevent the first round of stats
        // querying from evaluating no miners
        queryForConfigs();

        // Now schedule our tasks
        startConfigQuerying();
        startUpdateMiners();

        //noinspection InfiniteLoopStatement
        while (true) {
            final MetricsReport.Builder metricsReportBuilder =
                    new MetricsReport.Builder();
            this.statsCache
                    .getMetrics()
                    .forEach(metricsReportBuilder::addMinerStats);

            try {
                // Metrics could be empty if everything was down
                final MetricsReport metricsReport =
                        metricsReportBuilder.build();

                LOG.debug("Generated report: {}", metricsReport);

                metricsProcessingStrategy.process(metricsReport);
            } catch (final Exception e) {
                LOG.warn("Exception occurred while generating report - " +
                                "possibly no reachable miners",
                        e);
            }

            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (final InterruptedException ie) {
                // Ignore
            }
        }
    }

    /** Queries for a configuration. */
    private void queryForConfigs() {
        final List<Miner> currentMiners =
                this.allCache.getMiners();
        final List<Miner> newMiners =
                this.minerConfiguration.load();
        if (!CollectionUtils.isEqualCollection(
                currentMiners,
                newMiners)) {
            LOG.debug("A new configuration has been obtained");
            this.allCache.setMiners(newMiners);
        } else {
            LOG.debug("No configuration changes were observed");
        }
    }

    /**
     * Starts the thread that will continuously query for new configurations.
     */
    private void startConfigQuerying() {
        this.threadPool.scheduleWithFixedDelay(
                this::queryForConfigs,
                0,
                2,
                TimeUnit.MINUTES);
    }

    /** Schedules the job to begin automatically updating miner stats. */
    private void startUpdateMiners() {
        this.threadPool.scheduleWithFixedDelay(
                () -> {
                    LOG.debug("Updating miner stats cache");
                    this.allCache
                            .getMiners()
                            .forEach(this::updateMiner);
                },
                0,
                1,
                TimeUnit.MINUTES);
    }

    /**
     * Fires a job for each miner that will query and update the cached stats
     * for it.
     *
     * @param miner The miner.
     */
    private void updateMiner(final Miner miner) {
        this.threadPool.execute(() -> {
            try {
                this.statsCache.add(
                        miner.getMinerID(),
                        miner.getStats());
                LOG.debug("Cached metrics for {}", miner);
            } catch (final Exception e) {
                LOG.warn("Failed to obtain metrics for {}",
                        miner,
                        e);
            }
        });
    }
}