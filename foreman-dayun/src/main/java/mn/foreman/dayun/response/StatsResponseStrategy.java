package mn.foreman.dayun.response;

import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
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

/**
 * A {@link StatsResponseStrategy} provides a {@link ResponseStrategy} that will
 * process a Z1 "stats" response.
 */
public class StatsResponseStrategy
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(StatsResponseStrategy.class);

    /** The context. */
    private final Context context;

    /**
     * Constructor.
     *
     * @param context The context.
     */
    public StatsResponseStrategy(final Context context) {
        this.context = context;
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        if (response.hasValues()) {
            response.getValues()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equals("STATS"))
                    .map(Map.Entry::getValue)
                    .flatMap(List::stream)
                    .filter(value -> value.containsKey("MHS 30S"))
                    .forEach(value ->
                            addAsicStats(
                                    builder,
                                    value));
        } else {
            LOG.debug("No ASICs found");
        }
    }

    /**
     * Utility method to convert the provided values, which contain per-ASIC
     * metrics, to a {@link Asic} and adds it to the provided builder.
     *
     * @param builder The builder to update.
     * @param values  The asic values.
     */
    private void addAsicStats(
            final MinerStats.Builder builder,
            final Map<String, String> values) {
        final BigDecimal hashRate =
                new BigDecimal(values.get("MHS 30S"))
                        .multiply(new BigDecimal(Math.pow(1000, 2)));
        final Asic.Builder asicBuilder =
                new Asic.Builder()
                        .setHashRate(hashRate)
                        .setFanInfo(
                                new FanInfo.Builder()
                                        .setCount(values.get("Fan Nunber"))
                                        .addSpeed(values.get("Fan In"))
                                        .addSpeed(values.get("Fan Out"))
                                        .setSpeedUnits("RPM")
                                        .build())
                        .addTemp(values.get("Temperature Core"))
                        .addTemp(values.get("CH1 Temp"))
                        .addTemp(values.get("CH2 Temp"))
                        .addTemp(values.get("CH3 Temp"))
                        .addTemp(values.get("CH4 Temp"))
                        // API doesn't report errors
                        .hasErrors(false);
        this.context.getSimple(ContextKey.MRR_RIG_ID)
                .ifPresent(asicBuilder::setMrrRigId);
        builder.addAsic(asicBuilder.build());
    }
}