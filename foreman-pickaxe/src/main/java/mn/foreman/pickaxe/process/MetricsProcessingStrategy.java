package mn.foreman.pickaxe.process;

import mn.foreman.model.MetricsReport;

import java.util.List;

public interface MetricsProcessingStrategy {

    /**
     * Processes the provided {@link MetricsReport}.
     *
     * @param metricsReport The report to process.
     */
    void process(MetricsReport metricsReport);

    /**
     * Processes all of the provided {@link MetricsReport reports}.
     *
     * @param metricsReports The reports to process.
     */
    void processAll(List<MetricsReport> metricsReports);
}
