package mn.foreman.spondoolies.response;

import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A {@link SummaryAndStatsResponseStrategy} provides a {@link ResponseStrategy}
 * that will create and add an {@link Asic} to the provided {@link
 * MinerStats.Builder} as metrics are observed from Spondoolies miners.
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
            LOG.warn("Failed to obtain either stats or summary from SP36");
        }
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
                .anyMatch(map -> map.containsKey("MHS av"));
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
                .filter(map -> map.containsKey("json-stats"))
                .forEach(map -> {
                    try {
                        final Asic.Builder builder =
                                this.builderReference.get();

                        final JsonStats jsonStats =
                                new ObjectMapper().readValue(
                                        map.get("json-stats"),
                                        JsonStats.class);

                        // Fans
                        final List<Integer> fanSpeeds = jsonStats.fanSpeeds;
                        final FanInfo.Builder fanBuilder =
                                new FanInfo.Builder()
                                        .setCount(fanSpeeds.size())
                                        .setSpeedUnits("RPM");
                        fanSpeeds.forEach(fanBuilder::addSpeed);
                        builder.setFanInfo(fanBuilder.build());

                        // Temps
                        builder
                                .addTemp(jsonStats.topBoard.frontTemp)
                                .addTemp(jsonStats.topBoard.rearTemp)
                                .addTemp(jsonStats.topBoard.psuTemp)
                                .addTemp(jsonStats.bottomBoard.frontTemp)
                                .addTemp(jsonStats.bottomBoard.rearTemp)
                                .addTemp(jsonStats.bottomBoard.psuTemp);

                        // Errors based on non-working chips
                        builder.hasErrors(
                                jsonStats.topBoard.workingAsics +
                                        jsonStats.bottomBoard.workingAsics < 150);
                    } catch (final Exception e) {
                        LOG.warn("Exception occurred while parsing response", e);
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
                .filter(map -> map.containsKey("MHS av"))
                .forEach(map -> {
                    final Asic.Builder builder =
                            this.builderReference.get();
                    builder
                            .setHashRate(
                                    new BigDecimal(map.get("MHS av"))
                                            .multiply(
                                                    new BigDecimal(
                                                            Math.pow(
                                                                    1000,
                                                                    2))));
                });

        this.sawSummary.set(true);
    }
}