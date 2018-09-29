package mn.foreman.pickaxe.run;

import mn.foreman.model.MetricsReport;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.miners.MinerStats;
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

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/** {@link RunMe} provides the application context for PICKAXE. */
public class RunMe {

    /** The logger for this class. */
    private final static Logger LOG =
            LoggerFactory.getLogger(RunMe.class);

    /** The {@link Configuration}. */
    private final Configuration configuration;

    /** The factory for creating all of the {@link Miner miners}. */
    private final MinerConfiguration minerConfiguration;

    /** A cache of all of the miners. */
    private final AtomicReference<List<Miner>> miners =
            new AtomicReference<>(new LinkedList<>());

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
                                "%s/%s/%s/",
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
                        String.format(
                                "%s/%s",
                                this.configuration.getForemanApiUrl(),
                                this.configuration.getPickaxeId()),
                        this.configuration.getApiKey());

        startConfigQuerying();
        startUpdateMiners();

        //noinspection InfiniteLoopStatement
        while (true) {
            final MetricsReport.Builder metricsReportBuilder =
                    new MetricsReport.Builder();
            final List<MinerStats> stats =
                    this.statsCache.getMetrics();
            if (!stats.isEmpty()) {
                stats.forEach(
                        metricsReportBuilder::addMinerStats);

                try {
                    // Metrics could be empty if everything was down
                    final MetricsReport metricsReport =
                            metricsReportBuilder.build();

                    LOG.debug("Generated report: {}", metricsReport);

                    metricsProcessingStrategy.process(metricsReport);
                } catch (final Exception e) {
                    LOG.warn("Exception occurred while generating report", e);
                }
            } else {
                LOG.debug("No miner stats to report");
            }

            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (final InterruptedException ie) {
                // Ignore
            }
        }
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

    /** Schedules the job to begin automatically updating miner stats. */
    private void startUpdateMiners() {
        this.threadPool.scheduleWithFixedDelay(
                () -> {
                    try {
                        LOG.debug("Updating miner stats cache");
                        this.miners
                                .get()
                                .forEach(this::updateMiner);
                    } catch (final Exception e) {
                        LOG.warn("Exception occurred while updating", e);
                    }
                },
                0,
                30,
                TimeUnit.SECONDS);
    }

    /**
     * Fires a job for each miner that will query and update the cached stats
     * for it.
     *
     * @param miner The miner.
     */
    private void updateMiner(final Miner miner) {
        this.threadPool.execute(() -> {
            final MinerID minerID = miner.getMinerID();
            try {
                this.statsCache.add(
                        minerID,
                        miner.getStats());
                LOG.debug("Cached metrics for {}", miner);
            } catch (final Exception e) {
                LOG.warn("Failed to obtain metrics for {}",
                        miner,
                        e);
                this.statsCache.invalidate(minerID);
            }
        });
    }
}