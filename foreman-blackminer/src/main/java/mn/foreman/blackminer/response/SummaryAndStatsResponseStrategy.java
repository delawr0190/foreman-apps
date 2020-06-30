package mn.foreman.blackminer.response;

import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * A {@link SummaryAndStatsResponseStrategy} provides a {@link ResponseStrategy}
 * that will create and add an {@link Asic} to the provided {@link
 * MinerStats.Builder} as metrics are observed from Blackminer miners.
 *
 * <p>Implementation note: both the summary and stats responses must be
 * provided to the same instance of this class; both of those responses must be
 * paired together to create an {@link Asic}.</p>
 */
public class SummaryAndStatsResponseStrategy
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(SummaryAndStatsResponseStrategy.class);

    /** The active builder. */
    private final AtomicReference<Asic.Builder> builderReference =
            new AtomicReference<>(new Asic.Builder());

    /** Whether or not the stats were seen. */
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
            LOG.warn("Failed to obtain either stats or summary");
        }
    }

    private static boolean areTempsValid(final List<Integer> temps) {
        return temps
                .stream()
                .allMatch(temp -> temp > 10 && temp < 150);
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
                .anyMatch(map -> map.containsKey("GHS 5s"));
    }

    /** Resets the observed state. */
    private void reset() {
        this.sawSummary.set(false);
        this.sawStats.set(false);
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

        values.entrySet()
                .stream()
                .filter(entry -> "STATS".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("STATS"))
                .forEach(map -> {
                    final Asic.Builder builder =
                            this.builderReference.get();

                    // Fans
                    final int numFans = Integer.parseInt(map.get("fan_num"));
                    final FanInfo.Builder fanBuilder =
                            new FanInfo.Builder()
                                    .setCount(numFans)
                                    .setSpeedUnits("RPM");
                    for (int i = 1; i <= numFans; i++) {
                        fanBuilder.addSpeed(map.get("fan" + i));
                    }
                    builder.setFanInfo(fanBuilder.build());

                    // Temps
                    final int numTemps = Integer.parseInt(map.get("temp_num"));

                    // Add sensors first
                    for (int i = 1; i <= numTemps; i++) {
                        builder.addTemp(map.get("temp" + i));
                    }
                    // Add chip temps last
                    for (int i = 0; i <= numTemps; i++) {
                        final List<Integer> temps =
                                Arrays.stream(map
                                        .getOrDefault("chipTemp" + i, "")
                                        .split(" "))
                                        .map(temp -> temp.replace("[", ""))
                                        .map(temp -> temp.replace("]", ""))
                                        .map(temp -> temp.replace(",", ""))
                                        .map(String::trim)
                                        .filter(temp -> !temp.isEmpty())
                                        .map(Double::valueOf)
                                        .map(Double::intValue)
                                        .collect(Collectors.toList());
                        if (areTempsValid(temps)) {
                            temps.forEach(builder::addTemp);
                        }
                    }
                });

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
                .filter(map -> map.containsKey("GHS 5s"))
                .forEach(map -> {
                    final Asic.Builder builder =
                            this.builderReference.get();
                    builder
                            .setHashRate(
                                    new BigDecimal(map.get("GHS 5s"))
                                            .multiply(
                                                    new BigDecimal(
                                                            Math.pow(
                                                                    1000,
                                                                    3))));
                });

        this.sawSummary.set(true);
    }
}