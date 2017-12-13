package mn.foreman.pickaxe.run;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.connection.NettyConnectionFactory;
import mn.foreman.model.MetricsReport;
import mn.foreman.model.Miner;
import mn.foreman.pickaxe.configuration.CgMinerConfig;
import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.process.HttpPostMetricsProcessingStrategy;
import mn.foreman.pickaxe.process.MetricsProcessingStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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
        final List<Miner> minerList =
                createCgminers(
                        this.configuration.getCgminerConfigs());

        final int sleepInSeconds =
                this.configuration.getPollFrequencyInSeconds();

        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                final MetricsReport.Builder metricsReportBuilder =
                        new MetricsReport.Builder();
                minerList
                        .stream()
                        .map(Miner::getStats)
                        .forEach(metricsReportBuilder::addMinerStats);

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
     * Creates a new {@link Miner} from the provided config.
     *
     * @param config The config.
     *
     * @return The new {@link Miner}.
     */
    private static Miner createCgminer(
            final CgMinerConfig config) {
        return new CgMiner(
                config.getName(),
                config.getApiIp(),
                config.getApiPort(),
                new NettyConnectionFactory());
    }

    /**
     * Creates new {@link Miner miners} from the provided configs.
     *
     * @param configs The configs.
     *
     * @return The new {@link Miner miners}.
     */
    private static List<Miner> createCgminers(
            final List<CgMinerConfig> configs) {
        return configs
                .stream()
                .map(RunMe::createCgminer)
                .collect(Collectors.toList());
    }
}