package mn.foreman.spondoolies;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.spondoolies.response.JsonStats;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/** Utility methods for parsing Spondoolies miner response values. */
class SpondooliesUtils {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(SpondooliesUtils.class);

    /**
     * Updates the builder with stats.
     *
     * @param values The response values.
     */
    static void updateStats(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        values.entrySet()
                .stream()
                .filter(entry -> "STATS".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("json-stats"))
                .forEach(map -> {
                    try {
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
                                        new BigDecimal(map.get("MHS av"))
                                                .multiply(
                                                        new BigDecimal(
                                                                Math.pow(
                                                                        1000,
                                                                        2)))));
    }
}
