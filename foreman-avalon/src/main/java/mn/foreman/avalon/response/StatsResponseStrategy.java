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
import java.util.stream.Collectors;

/**
 * A {@link StatsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#STATS}
 * response from an Avalon controller.
 *
 * <p>All of the metrics returned by a single avalon controller are aggregated
 * together.</p>
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
                            .filter(value -> value.containsKey("STATS"))
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
        final List<String> stats =
                values
                        .stream()
                        .map(Map::entrySet)
                        .flatMap(Collection::stream)
                        .filter(e -> e.getKey().startsWith("MM ID"))
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());

        final Asic.Builder asicBuilder =
                new Asic.Builder()
                        .setHashRate(toRate(stats));
        addFans(
                stats,
                asicBuilder);
        addTemps(
                stats,
                asicBuilder);

        builder.addAsic(asicBuilder.build());
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
     * Extracts the hash rate.
     *
     * @param stats The stats.
     *
     * @return The rate.
     */
    private static BigDecimal toRate(
            final List<String> stats) {
        return
                stats
                        .stream()
                        .map(s -> getValue(s, "GHSmm"))
                        .map(BigDecimal::new)
                        .map(rate ->
                                rate.multiply(
                                        new BigDecimal(
                                                Math.pow(1000, 3))))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}