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
import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.process.HttpPostMetricsProcessingStrategy;
import mn.foreman.pickaxe.process.MetricsProcessingStrategy;
import mn.foreman.sgminer.SgminerFactory;
import mn.foreman.srbminer.SrbminerFactory;
import mn.foreman.trex.TrexFactory;
import mn.foreman.xmrig.XmrigFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/** {@link RunMe} provides the application context for PICKAXE. */
public class RunMe {

    /** The logger for this class. */
    private final static Logger LOG =
            LoggerFactory.getLogger(RunMe.class);

    /** The {@link Configuration}. */
    private final Configuration configuration;

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
        final List<Miner> minerList = new LinkedList<>();
        minerList.addAll(
                createMiners(
                        this.configuration.getAntminerConfigs(),
                        new AntminerFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getBaikalConfigs(),
                        new BaikalFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getBminerConfigs(),
                        new BminerFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getCastxmrConfigs(),
                        new CastxmrFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getCcminerConfigs(),
                        new CcminerFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getClaymoreConfigs(),
                        new ClaymoreFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getCryptoDredgeConfigs(),
                        new CcminerFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getDstmConfigs(),
                        new DstmFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getEthminerConfigs(),
                        new EthminerFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getEwbfConfigs(),
                        new EwbfFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getExcavatorConfigs(),
                        new ExcavatorFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getJceminerConfigs(),
                        new JceminerFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getLolminerConfigs(),
                        new LolminerFactory()));
        // PhoenixMiner uses the same API as Claymore
        minerList.addAll(
                createMiners(
                        this.configuration.getPhoenixConfigs(),
                        new ClaymoreFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getSgminerConfigs(),
                        new SgminerFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getSrbminerConfigs(),
                        new SrbminerFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getTrexConfigs(),
                        new TrexFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getXmrigConfigs(),
                        new XmrigFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getZenemyConfigs(),
                        new CcminerFactory()));

        final int sleepInSeconds =
                this.configuration.getPollFrequencyInSeconds();

        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                final MetricsReport.Builder metricsReportBuilder =
                        new MetricsReport.Builder();
                for (final Miner miner : minerList) {
                    try {
                        metricsReportBuilder.addMinerStats(
                                miner.getStats());
                    } catch (final Exception e) {
                        LOG.warn("Failed to obtain metrics for {}",
                                miner,
                                e);
                    }
                }

                final MetricsReport metricsReport = metricsReportBuilder.build();
                LOG.debug("Generated report: {}", metricsReport);

                metricsProcessingStrategy.process(metricsReportBuilder.build());

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
}