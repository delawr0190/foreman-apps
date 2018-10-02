package mn.foreman.antminer.response;

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
    private static void addAsicStats(
            final MinerStats.Builder builder,
            final Map<String, String> values) {
        final BigDecimal hashRate =
                new BigDecimal(values.get("GHS 5s"))
                        .multiply(new BigDecimal(Math.pow(1000, 3)));

        final Asic.Builder asicBuilder =
                new Asic.Builder()
                        .setHashRate(hashRate);

        // Fangs
        final FanInfo.Builder fanBuilder =
                new FanInfo.Builder()
                        .setCount(values.get("fan_num"))
                        .setSpeedUnits("RPM");
        for (int i = 1; i <= 8; i++) {
            fanBuilder.addSpeed(values.get("fan" + i));
        }
        asicBuilder.setFanInfo(fanBuilder.build());

        // Temps
        final List<String> tempPrefixes =
                Arrays.asList(
                        "temp",
                        "temp2_",
                        "temp3_",
                        "temp4_");
        for (final String prefix : tempPrefixes) {
            for (int i = 1; i <= 32; i++) {
                asicBuilder.addTemp(values.get(prefix + i));
            }
        }

        // Errors
        boolean hasErrors = false;
        for (int i = 1; i <= 16; i++) {
            final String chain = values.get("chain_acs" + i);
            if (chain != null) {
                hasErrors = (hasErrors || chain.contains("x"));
            }
        }
        asicBuilder.hasErrors(hasErrors);

        builder.addAsic(asicBuilder.build());
    }
}