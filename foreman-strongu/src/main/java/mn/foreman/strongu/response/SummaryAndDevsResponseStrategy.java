package mn.foreman.strongu.response;

import mn.foreman.cgminer.ResponseStrategy;
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

/**
 * A {@link SummaryAndDevsResponseStrategy} provides a {@link ResponseStrategy}
 * that will create and add an {@link Asic} to the provided {@link
 * MinerStats.Builder} as metrics are observed from StrongU miners.
 *
 * <p>Implementation note: both the summary and devs responses must be
 * provided to the same instance of this class; both of those responses must be
 * paired together to create an {@link Asic}.</p>
 */
public class SummaryAndDevsResponseStrategy
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(SummaryAndDevsResponseStrategy.class);

    /** The active builder. */
    private final AtomicReference<Asic.Builder> builderReference =
            new AtomicReference<>(new Asic.Builder());

    /** Whether or not the devs were seen. */
    private final AtomicBoolean sawDevs = new AtomicBoolean(false);

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
                updateDevs(values);
            }

            if (this.sawDevs.get() && this.sawSummary.get()) {
                builder.addAsic(
                        this.builderReference.get().build());

                reset();
            }
        } else {
            LOG.warn("Failed to obtain either stats or summary");
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
                .anyMatch(map -> map.containsKey("MHS 5s"));
    }

    /** Resets the observed state. */
    private void reset() {
        this.sawSummary.set(false);
        this.sawDevs.set(false);
        this.builderReference.set(new Asic.Builder());
    }

    /**
     * Updates the {@link #builderReference builder} with devs.
     *
     * @param values The response values.
     */
    private void updateDevs(
            final Map<String, List<Map<String, String>>> values) {
        // Verify that we aren't out of sync
        if (this.sawDevs.get()) {
            reset();
        }

        final Asic.Builder builder =
                this.builderReference.get();
        values.entrySet()
                .stream()
                .filter(entry -> "DEVS".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("ASC"))
                .forEach(map -> builder.addTemp(map.get("Temperature")));

        this.sawDevs.set(true);
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

        final Asic.Builder builder =
                this.builderReference.get();
        values.entrySet()
                .stream()
                .filter(entry -> "SUMMARY".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("MHS 5s"))
                .forEach(map -> {
                    // Fans
                    final int numFans = Integer.parseInt(map.get("Fan Num"));
                    final FanInfo.Builder fanBuilder =
                            new FanInfo.Builder()
                                    .setCount(numFans)
                                    .setSpeedUnits("RPM");
                    for (int i = 1; i <= numFans; i++) {
                        fanBuilder.addSpeed(map.get("Fan" + i));
                    }

                    builder
                            .setHashRate(
                                    new BigDecimal(map.get("MHS 5s"))
                                            .multiply(
                                                    new BigDecimal(
                                                            Math.pow(
                                                                    1000,
                                                                    2))))
                            .setFanInfo(fanBuilder.build());
                });

        this.sawSummary.set(true);
    }
}
