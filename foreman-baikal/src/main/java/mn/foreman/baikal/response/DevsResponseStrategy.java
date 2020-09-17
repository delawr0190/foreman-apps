package mn.foreman.baikal.response;

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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * A {@link PoolsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#DEVS}
 * response from a baikal.
 */
public class DevsResponseStrategy
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(DevsResponseStrategy.class);

    /** The context. */
    private final Context context;

    /** A map containing the algos for each pool. */
    private final ConcurrentMap<String, String> poolAlgoMap;

    /** The temperature key. */
    private final String temperatureKey;

    /**
     * Constructor.
     *
     * @param temperatureKey The temperature key.
     * @param context        The context.
     */
    public DevsResponseStrategy(
            final String temperatureKey,
            final Context context) {
        this(
                temperatureKey,
                new ConcurrentHashMap<>(),
                context);
    }

    /**
     * Constructor.
     *
     * @param temperatureKey The temperature key.
     * @param poolAlgoMap    The algorithms for each pool.
     * @param context        The context.
     */
    public DevsResponseStrategy(
            final String temperatureKey,
            final ConcurrentMap<String, String> poolAlgoMap,
            final Context context) {
        this.temperatureKey = temperatureKey;
        this.poolAlgoMap = poolAlgoMap;
        this.context = context;
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        if (response.hasValues()) {
            final List<Map<String, String>> values =
                    response.getValues()
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().equals("DEVS"))
                            .map(Map.Entry::getValue)
                            .flatMap(List::stream)
                            .collect(Collectors.toList());
            if (hasAsics(values)) {
                addAsic(
                        values,
                        builder);
            }
        } else {
            LOG.debug("Received an empty response");
        }
    }

    /**
     * Checks to see if the values have ASICs.
     *
     * @param values The values.
     *
     * @return Whether or not ASICs exist.
     */
    private static boolean hasAsics(
            final List<Map<String, String>> values) {
        return values
                .stream()
                .anyMatch((map) -> map.containsKey("ASC"));
    }

    /**
     * Extracts the hash rate.
     *
     * @param values The values.
     *
     * @return The rate.
     */
    private static BigDecimal toRate(
            final List<Map<String, String>> values) {
        return values
                .stream()
                .map((map) -> new BigDecimal(map.get("MHS 5s")))
                .map((value) ->
                        value.multiply(
                                new BigDecimal(1000 * 1000)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Adds all of the ASICs from the provided values.
     *
     * @param values       The values.
     * @param statsBuilder The builder.
     */
    private void addAsic(
            final List<Map<String, String>> values,
            final MinerStats.Builder statsBuilder) {
        final List<Map<String, String>> asicValues =
                values
                        .stream()
                        .filter((map) -> map.containsKey("ASC"))
                        .collect(Collectors.toList());
        final Asic.Builder asicBuilder =
                new Asic.Builder()
                        .setHashRate(toRate(asicValues))
                        .setFanInfo(
                                new FanInfo.Builder()
                                        .setCount(0)
                                        .setSpeedUnits("%")
                                        .build());
        asicValues
                .stream()
                .map(map -> map.get(this.temperatureKey))
                .map(Double::parseDouble)
                .map(Double::intValue)
                .forEach(asicBuilder::addTemp);
        asicValues
                .stream()
                .filter(map -> map.containsKey("Last Share Pool"))
                .map(map -> map.get("Last Share Pool"))
                .findFirst()
                .ifPresent(poolIndex -> {
                    if (this.poolAlgoMap.containsKey(poolIndex)) {
                        asicBuilder.setPowerState(
                                this.poolAlgoMap.get(poolIndex));
                    }
                });
        this.context.get(ContextKey.MRR_RIG_ID)
                .ifPresent(asicBuilder::setMrrRigId);
        statsBuilder.addAsic(asicBuilder.build());
    }
}