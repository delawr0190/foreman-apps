package mn.foreman.pickaxe.run;

import mn.foreman.model.MetricsReport;
import mn.foreman.model.Miner;
import mn.foreman.model.error.MinerException;
import mn.foreman.pickaxe.cache.MinerCache;
import mn.foreman.pickaxe.cache.MinerCacheImpl;
import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.miners.MinerConfiguration;
import mn.foreman.pickaxe.miners.remote.RemoteConfiguration;
import mn.foreman.pickaxe.process.HttpPostMetricsProcessingStrategy;
import mn.foreman.pickaxe.process.MetricsProcessingStrategy;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * {@link RunMe} provides the application context for PICKAXE.
 *
 * <h2>Threading</h2>
 *
 * <p>A {@link ReentrantReadWriteLock} is leveraged between the 3 threads that
 * run pickaxe (main thread and 2 threads in the {@link #threadPool} that handle
 * blacklist management and metrics querying).  This allows for the metrics
 * querying and blacklisting to be processed in parallel ({@link
 * ReentrantReadWriteLock#readLock() read-locked}), and then a {@link
 * ReentrantReadWriteLock#writeLock() write lock} is acquired to prevent {@link
 * Miner miners} from possibly being preserved instead of pruned when a new
 * configuration is downloaded.</p>
 */
public class RunMe {

    /** The logger for this class. */
    private final static Logger LOG =
            LoggerFactory.getLogger(RunMe.class);

    /** A cache of all of the active miners. */
    private final MinerCache activeCache = new MinerCacheImpl();

    /** A cache of all of the miners. */
    private final MinerCache allCache = new MinerCacheImpl();

    /** A cache of all of the blacklisted miners. */
    private final MinerCache blacklistCache = new MinerCacheImpl();

    /** The {@link Configuration}. */
    private final Configuration configuration;

    /** The lock to enable multi-threaded cache accesses. */
    private final ReentrantReadWriteLock lock =
            new ReentrantReadWriteLock();

    /** The factory for creating all of the {@link Miner miners}. */
    private final MinerConfiguration minerConfiguration;

    /** The thread pool for running tasks. */
    private final ScheduledExecutorService threadPool =
            Executors.newScheduledThreadPool(1);

    /**
     * Constructor.
     *
     * @param configuration The {@link Configuration}.
     */
    public RunMe(final Configuration configuration) {
        this.configuration = configuration;
        this.minerConfiguration =
                new RemoteConfiguration(
                        configuration.getForemanConfigUrl(),
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
                        this.configuration.getForemanApiUrl(),
                        this.configuration.getApiKey());

        startConfigQuerying();
        startBlacklistValidation();

        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                final MetricsReport.Builder metricsReportBuilder =
                        new MetricsReport.Builder();
                this.lock.readLock().lock();
                try {
                    for (final Miner miner : this.activeCache.getMiners()) {
                        try {
                            metricsReportBuilder.addMinerStats(
                                    miner.getStats());
                        } catch (final Exception e) {
                            LOG.warn("Failed to obtain metrics for {} - " +
                                            "temporarily blacklisting",
                                    miner,
                                    e);
                            this.activeCache.remove(miner);
                            this.blacklistCache.add(miner);
                        }
                    }
                } finally {
                    this.lock.readLock().unlock();
                }

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

                TimeUnit.SECONDS.sleep(60);
            }
        } catch (final InterruptedException ie) {
            LOG.debug("Interrupted while sleeping - terminating", ie);
        }
    }

    /** Starts {@link #blacklistCache} evaluation. */
    private void startBlacklistValidation() {
        this.threadPool.scheduleAtFixedRate(
                () -> {
                    this.lock.readLock().lock();
                    try {
                        final List<Miner> toValidate =
                                this.blacklistCache.getMiners();

                        LOG.debug("Evaluating {} miners for blacklist eviction",
                                toValidate.size());

                        for (final Miner miner : toValidate) {
                            try {
                                miner.getStats();

                                LOG.debug("{} is reachable - restoring", miner);

                                this.blacklistCache.remove(miner);
                                this.activeCache.add(miner);
                            } catch (final MinerException me) {
                                LOG.warn("Couldn't reach miner - keeping it " +
                                        "blacklisted");
                            }
                        }
                    } finally {
                        this.lock.readLock().unlock();
                    }
                },
                60,
                60,
                TimeUnit.SECONDS);
    }

    /**
     * Starts the thread that will continuously query for new configurations.
     */
    private void startConfigQuerying() {
        this.threadPool.scheduleAtFixedRate(
                () -> {
                    this.lock.writeLock().lock();
                    try {
                        final List<Miner> currentMiners =
                                this.allCache.getMiners();
                        final List<Miner> newMiners =
                                this.minerConfiguration.load();
                        if (!CollectionUtils.isEqualCollection(
                                currentMiners,
                                newMiners)) {
                            this.blacklistCache.invalidate();
                            this.activeCache.invalidate();
                            this.allCache.invalidate();
                            newMiners.forEach(this.allCache::add);
                            newMiners.forEach(this.activeCache::add);
                        }
                    } finally {
                        this.lock.writeLock().unlock();
                    }
                },
                0,
                5,
                TimeUnit.MINUTES);
    }
}