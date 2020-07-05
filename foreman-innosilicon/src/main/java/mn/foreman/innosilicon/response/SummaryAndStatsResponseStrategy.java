package mn.foreman.innosilicon.response;

import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * A {@link SummaryAndStatsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#STATS}
 * response from an Innosilicon.
 */
public class SummaryAndStatsResponseStrategy
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(SummaryAndStatsResponseStrategy.class);

    /** The active builder. */
    private final AtomicReference<Asic.Builder> builderReference =
            new AtomicReference<>(new Asic.Builder());

    /** Whether or not the status were seen. */
    private final AtomicBoolean sawStats = new AtomicBoolean(false);

    /** Whether or not the summary was seen. */
    private final AtomicBoolean sawSummary = new AtomicBoolean(false);

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        if (response.hasValues()) {
            final Map<String, List<Map<String, String>>> values =
                    response.getValues();
            if (isSummary(values)) {
                updateSummary(values);
            } else {
                updateStats(values);
            }

            if (this.sawStats.get() && this.sawSummary.get()) {
                builder.addAsic(
                        this.builderReference.get().build());
                reset();
            }
        } else {
            LOG.debug("No ACICs founds");
        }
    }

    /**
     * Determines whether or not the ASIC has errors.
     *
     * @param values The values to examine.
     *
     * @return Whether or not there are errors.
     */
    private static boolean hasErrors(final Map<String, String> values) {
        return Integer.parseInt(values.get("Num chips")) >
                Integer.parseInt(values.get("Num active chips"));
    }

    /**
     * Checks to see if the response contains summary.
     *
     * @param values The response values.
     *
     * @return Whether or not the values are a summary response.
     */
    private static boolean isSummary(
            final Map<String, List<Map<String, String>>> values) {
        return values
                .entrySet()
                .stream()
                .anyMatch(entry -> "SUMMARY".equals(entry.getKey()));
    }

    /**
     * Utility method to convert the provided values, which contain per-ASIC
     * metrics, to a {@link Asic} and adds it to the provided builder.
     *
     * @param values The asic values.
     */
    private void addAsicStats(
            final List<Map<String, String>> values) {
        final Asic.Builder asicBuilder = this.builderReference.get();

        // Fans
        final List<Integer> fans =
                values.stream()
                        .map(map -> map.get("Fan duty"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
        final FanInfo.Builder fanInfo =
                new FanInfo.Builder()
                        .setCount(fans.size())
                        .setSpeedUnits("%");
        fans.forEach(fanInfo::addSpeed);
        asicBuilder.setFanInfo(fanInfo.build());

        // Temps
        values.stream()
                .map(map -> map.get("Temp"))
                .forEach(asicBuilder::addTemp);

        // HW errors
        asicBuilder.hasErrors(
                values.stream()
                        .filter(SummaryAndStatsResponseStrategy::hasErrors)
                        .anyMatch(SummaryAndStatsResponseStrategy::hasErrors));
    }

    /** Resets the flags indicating the responses that were observed. */
    private void reset() {
        this.sawStats.set(false);
        this.sawSummary.set(false);
        this.builderReference.set(new Asic.Builder());
    }

    /**
     * Updates the {@link #builderReference builder} with stats.
     *
     * @param values The response values.
     */
    private void updateStats(
            final Map<String, List<Map<String, String>>> values) {
        // Verify that we aren't out of sync
        if (this.sawStats.get()) {
            reset();
        }

        final List<Map<String, String>> asics =
                values
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().startsWith("STATS"))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(value -> value.containsKey("Num chips"))
                        .collect(Collectors.toList());
        if (!asics.isEmpty()) {
            addAsicStats(asics);
        }

        this.sawStats.set(true);
    }

    /**
     * Updates the {@link #builderReference builder} with summary info.
     *
     * @param values The response values.
     */
    private void updateSummary(
            final Map<String, List<Map<String, String>>> values) {
        // Verify that we aren't out of sync
        if (this.sawSummary.get()) {
            reset();
        }

        values.entrySet()
                .stream()
                .filter(entry -> "SUMMARY".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("MHS av"))
                .map(map -> new BigDecimal(map.get("MHS av")))
                .map(value ->
                        value.multiply(
                                new BigDecimal(1000 * 1000)))
                .forEach(value ->
                        this.builderReference.get().setHashRate(value));

        this.sawSummary.set(true);
    }
}