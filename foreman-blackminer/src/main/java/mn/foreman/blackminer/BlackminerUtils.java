package mn.foreman.blackminer;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Utility methods for parsing Blackminer miner response values. */
class BlackminerUtils {

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
                .filter(map -> map.containsKey("STATS"))
                .forEach(map -> {
                    // Fans
                    final int numFans = Integer.parseInt(map.get("fan_num"));
                    final FanInfo.Builder fanBuilder =
                            new FanInfo.Builder()
                                    .setCount(numFans)
                                    .setSpeedUnits("RPM");
                    for (int i = 1; i <= numFans; i++) {
                        fanBuilder.addSpeed(map.get("fan" + i));
                    }
                    builder.setFanInfo(fanBuilder.build());

                    // Temps
                    final int numTemps = Integer.parseInt(map.get("temp_num"));

                    // Add sensors first
                    for (int i = 1; i <= numTemps; i++) {
                        builder.addTemp(map.get("temp" + i));
                    }
                    // Add chip temps last
                    for (int i = 0; i <= numTemps; i++) {
                        final List<Integer> temps =
                                Arrays.stream(map
                                        .getOrDefault("chipTemp" + i, "")
                                        .split(" "))
                                        .map(temp -> temp.replace("[", ""))
                                        .map(temp -> temp.replace("]", ""))
                                        .map(temp -> temp.replace(",", ""))
                                        .map(String::trim)
                                        .filter(temp -> !temp.isEmpty())
                                        .map(Double::valueOf)
                                        .map(Double::intValue)
                                        .collect(Collectors.toList());
                        if (areTempsValid(temps)) {
                            temps.forEach(builder::addTemp);
                        }
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
                .filter(map -> map.containsKey("GHS 5s"))
                .forEach(map ->
                        builder
                                .setHashRate(
                                        new BigDecimal(map.get("GHS 5s"))
                                                .multiply(
                                                        new BigDecimal(
                                                                Math.pow(
                                                                        1000,
                                                                        3)))));
    }

    /**
     * Checks to see if temps are valid.
     *
     * @param temps The temps.
     *
     * @return Whether or not the temps are valid.
     */
    private static boolean areTempsValid(final List<Integer> temps) {
        return temps
                .stream()
                .allMatch(temp -> temp > 10 && temp < 150);
    }
}
