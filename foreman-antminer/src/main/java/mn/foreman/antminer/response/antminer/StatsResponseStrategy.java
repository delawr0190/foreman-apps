package mn.foreman.antminer.response.antminer;

import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.cgminer.PoolsResponseStrategy;
import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A {@link PoolsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#STATS}
 * response from an antminer.
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
                    .filter(value -> value.containsKey("GHS 5s"))
                    .forEach(value ->
                            addAsicStats(
                                    builder,
                                    value));
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
            final Map<String, String> values) {
        final BigDecimal hashRate =
                new BigDecimal(values.get("GHS 5s"))
                        .multiply(new BigDecimal(Math.pow(1000, 3)));

        final Asic.Builder asicBuilder =
                new Asic.Builder();

        // Boards
        if (values.containsKey("miner_count")) {
            asicBuilder.setBoards(Integer.parseInt(values.get("miner_count")));
        }

        // Fans
        final FanInfo.Builder fanBuilder =
                new FanInfo.Builder()
                        .setCount(values.get("fan_num"))
                        .setSpeedUnits("RPM");
        for (int i = 1; i <= 8; i++) {
            fanBuilder.addSpeed(values.get("fan" + i));
        }
        asicBuilder.setFanInfo(fanBuilder.build());

        // Take chip and PCB temps before defaults
        final List<String> tempPrefixes;
        if (values.containsKey("temp_pcb1")) {
            tempPrefixes =
                    Arrays.asList(
                            "temp_pcb",
                            "temp_chip");
        } else {
            tempPrefixes =
                    Arrays.asList(
                            "temp",
                            "temp2_",
                            "temp3_",
                            "temp4_");
        }
        for (final String prefix : tempPrefixes) {
            for (int i = 1; i <= 32; i++) {
                asicBuilder.addTemp(values.get(prefix + i));
            }
        }

        // Errors
        boolean hasErrors = false;
        boolean hasFunctioningChips = false;
        for (int i = 1; i <= 16; i++) {
            final String chain = values.get("chain_acs" + i);
            if (chain != null) {
                hasErrors = (hasErrors || chain.contains("x"));
                hasFunctioningChips |=
                        chain
                                .trim()
                                .replace(
                                        "x",
                                        "")
                                .trim()
                                .length() > 0;
            }
        }

        final boolean hasPools =
                builder
                        .getPools()
                        .stream()
                        .map(Pool::getName)
                        .filter(s -> !"undefined".equals(s))
                        .anyMatch(s -> !s.isEmpty());
        final boolean hasSubmittedShares =
                this.context
                        .getMulti(ContextKey.LAST_SHARE_TIME)
                        .map(shares ->
                                shares
                                        .values()
                                        .stream()
                                        .map(Object::toString)
                                        .filter(String::isEmpty)
                                        .mapToInt(Integer::parseInt)
                                        .sum() > 0)
                        .orElse(false);

        asicBuilder
                .hasErrors(hasFunctioningChips && hasErrors)
                .setPowerMode(
                        !hasFunctioningChips && hasErrors && hasPools && !hasSubmittedShares
                                ? Asic.PowerMode.SLEEPING
                                : Asic.PowerMode.NORMAL);

        // Context data
        this.context.getSimple(ContextKey.MRR_RIG_ID)
                .ifPresent(asicBuilder::setMrrRigId);
        this.context.getMulti(ContextKey.RAW_STATS)
                .ifPresent(asicBuilder::addRawStats);
        this.context.getSimple(ContextKey.MINER_TYPE)
                .ifPresent(asicBuilder::setMinerType);
        this.context.getSimple(ContextKey.COMPILE_TIME)
                .ifPresent(asicBuilder::setCompileTime);

        builder.addAsic(
                asicBuilder
                        .setHashRate(hashRate)
                        .build());

        this.context.clear();
    }
}