package mn.foreman.pickaxe.run;

import mn.foreman.antminer.AntminerFactory;
import mn.foreman.baikal.BaikalFactory;
import mn.foreman.bminer.BminerFactory;
import mn.foreman.castxmr.CastxmrFactory;
import mn.foreman.ccminer.CcminerFactory;
import mn.foreman.claymore.ClaymoreFactory;
import mn.foreman.dstm.DstmFactory;
import mn.foreman.ethminer.EthminerFactory;
import mn.foreman.ewbf.EwbfFactory;
import mn.foreman.excavator.ExcavatorFactory;
import mn.foreman.jceminer.JceminerFactory;
import mn.foreman.lolminer.LolminerFactory;
import mn.foreman.model.MetricsReport;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.model.error.MinerException;
import mn.foreman.pickaxe.cache.MinerCache;
import mn.foreman.pickaxe.cache.MinerCacheImpl;
import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.process.HttpPostMetricsProcessingStrategy;
import mn.foreman.pickaxe.process.MetricsProcessingStrategy;
import mn.foreman.sgminer.SgminerFactory;
import mn.foreman.srbminer.SrbminerFactory;
import mn.foreman.trex.TrexFactory;
import mn.foreman.xmrig.XmrigFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/** {@link RunMe} provides the application context for PICKAXE. */
public class RunMe {

    /** The logger for this class. */
    private final static Logger LOG =
            LoggerFactory.getLogger(RunMe.class);

    /** A cache of all of the active miners. */
    private final MinerCache activeCache = new MinerCacheImpl();

    /** A cache of all of the blacklisted miners. */
    private final MinerCache blacklistCache = new MinerCacheImpl();

    /** The {@link Configuration}. */
    private final Configuration configuration;

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
        createMiners(
                this.configuration.getAntminerConfigs(),
                new AntminerFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getBaikalConfigs(),
                new BaikalFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getBminerConfigs(),
                new BminerFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getCastxmrConfigs(),
                new CastxmrFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getCcminerConfigs(),
                new CcminerFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getClaymoreConfigs(),
                new ClaymoreFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getCryptoDredgeConfigs(),
                new CcminerFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getDstmConfigs(),
                new DstmFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getEthminerConfigs(),
                new EthminerFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getEwbfConfigs(),
                new EwbfFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getExcavatorConfigs(),
                new ExcavatorFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getJceminerConfigs(),
                new JceminerFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getLolminerConfigs(),
                new LolminerFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getPhoenixConfigs(),
                new ClaymoreFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getSgminerConfigs(),
                new SgminerFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getSrbminerConfigs(),
                new SrbminerFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getTrexConfigs(),
                new TrexFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getWildrigConfigs(),
                new XmrigFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getXmrigConfigs(),
                new XmrigFactory()).forEach(this.activeCache::add);
        createMiners(
                this.configuration.getZenemyConfigs(),
                new CcminerFactory()).forEach(this.activeCache::add);

        final int sleepInSeconds =
                this.configuration.getPollFrequencyInSeconds();

        startBlacklistValidation();

        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                final MetricsReport.Builder metricsReportBuilder =
                        new MetricsReport.Builder();
                for (final Miner miner : this.activeCache.getMiners()) {
                    try {
                        metricsReportBuilder.addMinerStats(
                                miner.getStats());
                    } catch (final Exception e) {
                        LOG.warn("Failed to obtain metrics for {} - temporarily blacklisting",
                                miner,
                                e);
                        this.activeCache.remove(miner);
                        this.blacklistCache.add(miner);
                    }
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

                TimeUnit.SECONDS.sleep(sleepInSeconds);
            }
        } catch (final InterruptedException ie) {
            LOG.debug("Interrupted while sleeping - terminating", ie);
        }
    }

    /**
     * Creates new {@link Miner miners} from the provided configs.
     *
     * @param configs The configs.
     * @param factory The factory for creating {@link Miner miners}.
     *
     * @return The new {@link Miner miners}.
     */
    private static List<Miner> createMiners(
            final List<Map<String, String>> configs,
            final MinerFactory factory) {
        return configs
                .stream()
                .map(factory::create)
                .collect(Collectors.toList());
    }

    /** Starts {@link #blacklistCache} evaluation. */
    private void startBlacklistValidation() {
        this.threadPool.scheduleAtFixedRate(
                () -> {
                    final List<Miner> toValidate = this.blacklistCache.getMiners();

                    LOG.debug("Evaluating {} miners for blacklist eviction",
                            toValidate.size());

                    for (final Miner miner : toValidate) {
                        try {
                            miner.getStats();

                            LOG.debug("{} is reachable - restoring", miner);

                            this.blacklistCache.remove(miner);
                            this.activeCache.add(miner);
                        } catch (final MinerException me) {
                            LOG.warn("Couldn't reach miner - keeping it blacklisted");
                        }
                    }
                },
                60,
                60,
                TimeUnit.SECONDS);
    }
}