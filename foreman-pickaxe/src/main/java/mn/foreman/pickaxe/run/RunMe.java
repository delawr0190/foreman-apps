package mn.foreman.pickaxe.run;

import mn.foreman.antminer.AntminerFactory;
import mn.foreman.bminer.BminerFactory;
import mn.foreman.ccminer.CcminerFactory;
import mn.foreman.model.MetricsReport;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.process.HttpPostMetricsProcessingStrategy;
import mn.foreman.pickaxe.process.MetricsProcessingStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * {@link RunMe} provides the application context for PICKAXE.
 *
 * <p>This approach was selected to keep application complexity down instead of
 * leveraging some big ol' dependency injection framework.  After all, it's a
 * small Java application!</p>
 */
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
                        this.configuration.getBminerConfigs(),
                        new BminerFactory()));
        minerList.addAll(
                createMiners(
                        this.configuration.getCcminerConfigs(),
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