package mn.foreman.avalon;

import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/** Utility methods for parsing Avalon miner response values. */
class AvalonUtils {

    /** The hash board key. */
    private static final String HASH_BOARD_KEY = "Hash Board: ";

    /**
     * Queries an Avalon.
     *
     * @param ip        The ip.
     * @param port      The port.
     * @param message   The message.
     * @param validator The response validator.
     *
     * @return The result.
     */
    public static boolean query(
            final String ip,
            final int port,
            final String message,
            final BiFunction<String, ApiRequest, Boolean> validator) {
        boolean success = false;

        final ApiRequest request =
                new ApiRequestImpl(
                        ip,
                        (port == 8081 || port == 4029 ? 4029 : 4028),
                        message);

        final Connection connection =
                ConnectionFactory.createRawConnection(
                        request,
                        1,
                        TimeUnit.SECONDS);
        connection.query();

        if (request.waitForCompletion(
                1,
                TimeUnit.SECONDS)) {
            final String response = request.getResponse();
            success =
                    validator.apply(
                            response,
                            request);
        }

        return success;
    }

    /**
     * Updates the builder with stats.
     *
     * @param values The response values.
     */
    static void updateStats(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        final List<Map<String, String>> asics = values
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith("STATS"))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(value -> value.containsKey("STATS"))
                .collect(Collectors.toList());
        if (!asics.isEmpty()) {
            addAsicStats(
                    asics,
                    builder);
        }
    }

    /**
     * Updates the builder with summary info.
     *
     * @param values  The response values.
     * @param builder The builder.
     */
    static void updateSummary(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        values.entrySet()
                .stream()
                .filter(entry -> "SUMMARY".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("MHS av"))
                .forEach(map ->
                        builder
                                .setHashRate(
                                        toHashRate(map).multiply(
                                                BigDecimal.valueOf(Math.pow(1000, 2)))));
    }

    /**
     * Utility method to convert the provided values, which contain per-ASIC
     * metrics, to a {@link Asic} and adds it to the provided builder.
     *
     * @param values  The asic values.
     * @param builder The builder.
     */
    private static void addAsicStats(
            final List<Map<String, String>> values,
            final Asic.Builder builder) {
        final List<String> stats =
                values
                        .stream()
                        .map(Map::entrySet)
                        .flatMap(Collection::stream)
                        .filter(e -> e.getKey().startsWith("MM ID"))
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());
        addFans(
                stats,
                builder);
        addTemps(
                stats,
                builder);
        addBoards(
                stats,
                builder);
    }

    /**
     * Adds the hash boards, if they can be determined from this model.
     *
     * @param stats       The stats.
     * @param asicBuilder The builder.
     */
    private static void addBoards(
            final List<String> stats,
            final Asic.Builder asicBuilder) {
        if (stats.stream().anyMatch(stat -> stat.contains(HASH_BOARD_KEY))) {
            asicBuilder.setBoards(
                    stats
                            .stream()
                            .filter(stat -> stat.contains(HASH_BOARD_KEY))
                            .mapToInt(stat -> {
                                final int start = stat.indexOf(HASH_BOARD_KEY);
                                if (start >= 0) {
                                    return Integer.parseInt(
                                            stat.substring(
                                                    start + HASH_BOARD_KEY.length(),
                                                    stat.indexOf("]", start))
                                                    .trim());
                                }
                                return 0;
                            })
                            .sum());
        }
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
     * Gets the hash rate from the provided stats.
     *
     * @param values The values.
     *
     * @return The hash rate.
     */
    private static BigDecimal toHashRate(final Map<String, String> values) {
        return new BigDecimal(values.getOrDefault("MHS av", "0"));
    }
}
