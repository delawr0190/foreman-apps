package mn.foreman.innosilicon;

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
import java.util.stream.Collectors;

/**
 * A {@link StatsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#STATS}
 * response from an Innosilicon.
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
            final List<Map<String, String>> asics =
                    response.getValues()
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().startsWith("STATS"))
                            .map(Map.Entry::getValue)
                            .flatMap(List::stream)
                            .filter(value -> value.containsKey("Num chips"))
                            .collect(Collectors.toList());
            if (!asics.isEmpty()) {
                addAsicStats(
                        builder,
                        asics);
            }
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
            final List<Map<String, String>> values) {
        final Asic.Builder asicBuilder =
                new Asic.Builder()
                        .setHashRate(toRate(values));

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
                        .filter(StatsResponseStrategy::hasErrors)
                        .anyMatch(StatsResponseStrategy::hasErrors));

        builder.addAsic(asicBuilder.build());
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
                .map((map) -> new BigDecimal(map.get("MHS av")))
                .map((value) ->
                        value.multiply(
                                new BigDecimal(1000 * 1000)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}