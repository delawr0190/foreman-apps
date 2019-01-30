package mn.foreman.avalon.response;

import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * A {@link SummaryAndStatsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#STATS}
 * response from an Avalon controller.
 *
 * <p>All of the metrics returned by a single avalon controller are aggregated
 * together.</p>
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
     * Extracts the fans.
     *
     * @param stats       The stats.
     * @param asicBuilder The builder.
     */
    private static void addFans(
            final List<String> stats,
            final Asic.Builder asicBuilder) {
        final FanInfo.Builder fanBuilder =
                new FanInfo.Builder();
        final List<String> speeds =
                stats
                        .stream()
                        .map(s -> getValue(s, "FanR"))
                        .map(s -> s.replace("%", ""))
                        .collect(Collectors.toList());
        fanBuilder
                .setCount(speeds.size())
                .setSpeedUnits("%");
        speeds.forEach(fanBuilder::addSpeed);
        asicBuilder.setFanInfo(fanBuilder.build());
    }

    /**
     * Extracts the temps.
     *
     * @param stats       The stats.
     * @param asicBuilder The builder.
     */
    private static void addTemps(
            final List<String> stats,
            final Asic.Builder asicBuilder) {
        for (final String stat : stats) {
            asicBuilder.addTemp(
                    getValue(
                            stat,
                            "Temp"));
            asicBuilder.addTemp(
                    getValue(
                            stat,
                            "TMax"));
        }
    }

    /**
     * Extracts a value from an encoded key[value] string.
     *
     * @param toExtract The string to examine.
     * @param key       The key to extract.
     *
     * @return The value.
     */
    private static String getValue(
            final String toExtract,
            final String key) {
        final int start = toExtract.indexOf(key);
        return toExtract.substring(
                // Start after KEY[
                start + key.length() + 1,
                // End after ]
                toExtract.indexOf("]", start));
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
                .filter(entry -> "SUMMARY".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .anyMatch(map -> map.containsKey("MHS 5s"));
    }

    /**
     * Utility method to convert the provided values, which contain per-ASIC
     * metrics, to a {@link Asic} and adds it to the provided builder.
     *
     * @param values The asic values.
     */
    private void addAsicStats(
            final List<Map<String, String>> values) {
        final List<String> stats =
                values
                        .stream()
                        .map(Map::entrySet)
                        .flatMap(Collection::stream)
                        .filter(e -> e.getKey().startsWith("MM ID"))
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());

        final Asic.Builder asicBuilder =
                this.builderReference.get();
        addFans(
                stats,
                asicBuilder);
        addTemps(
                stats,
                asicBuilder);
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

        final List<Map<String, String>> asics = values
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith("STATS"))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(value -> value.containsKey("STATS"))
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
                .filter(map -> map.containsKey("MHS 5s"))
                .forEach(map -> {
                    final Asic.Builder builder =
                            this.builderReference.get();
                    builder
                            .setHashRate(
                                    new BigDecimal(map.get("MHS 5s"))
                                            .multiply(
                                                    new BigDecimal(
                                                            Math.pow(1000, 2))));
                });

        this.sawSummary.set(true);
    }
}