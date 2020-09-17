package mn.foreman.aixin.response;

import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.cgminer.PoolsResponseStrategy;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@link PoolsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#STATS}
 * response from an aixin.
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
            final List<Map<String, String>> stats =
                    response.getValues()
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().equals("STATS"))
                            .map(Map.Entry::getValue)
                            .flatMap(List::stream)
                            .filter(value -> value.containsKey("Fan duty"))
                            .collect(Collectors.toList());
            addAsicStats(
                    builder,
                    stats);
        } else {
            LOG.debug("No ACICs founds");
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
            final List<Map<String, String>> values) {
        if (values.size() >= 1) {
            final BigDecimal hashRate =
                    values
                            .stream()
                            .map(Map::entrySet)
                            .flatMap(Set::stream)
                            .filter(entry -> entry.getKey().equals("MHS av"))
                            .map(Map.Entry::getValue)
                            .map(BigDecimal::new)
                            .map(rate -> rate.multiply(new BigDecimal(Math.pow(1000, 2))))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

            final Asic.Builder asicBuilder =
                    new Asic.Builder()
                            .setHashRate(hashRate);

            final Map<String, String> firstStats =
                    values.get(0);

            // Fans
            asicBuilder.setFanInfo(
                    new FanInfo.Builder()
                            .setCount(2)
                            .addSpeed(firstStats.get("Fan0 rpm"))
                            .addSpeed(firstStats.get("Fan1 rpm"))
                            .setSpeedUnits("RPM")
                            .build());

            // Temps
            values
                    .stream()
                    .map(Map::entrySet)
                    .flatMap(Set::stream)
                    .filter(entry -> entry.getKey().equals("Temp"))
                    .map(Map.Entry::getValue)
                    .forEach(asicBuilder::addTemp);

            // Errors
            asicBuilder.hasErrors(
                    values
                            .stream()
                            .anyMatch(map ->
                                    Integer.parseInt(map.get("Num chips")) !=
                                            Integer.parseInt(map.get("Num active " +
                                                    "chips"))));

            // MRR rig id
            this.context.get(ContextKey.MRR_RIG_ID)
                    .ifPresent(asicBuilder::setMrrRigId);

            builder.addAsic(asicBuilder.build());
        } else {
            LOG.warn("No stats found");
        }
    }
}