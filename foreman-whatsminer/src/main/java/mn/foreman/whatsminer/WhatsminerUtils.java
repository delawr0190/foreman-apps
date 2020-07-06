package mn.foreman.whatsminer;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/** Utility methods for parsing Whatsminer miner response values. */
class WhatsminerUtils {

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
                    for (int i = 1; i <= 8; i++) {
                        builder.addTemp(map.get("temp_" + i));
                    }
                    if (!"0".equals(map.getOrDefault("err_chips", "0"))) {
                        builder.hasErrors(true);
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
                .filter(map -> map.containsKey("MHS 5s"))
                .forEach(map ->
                        builder
                                .setFanInfo(
                                        new FanInfo.Builder()
                                                .setCount(2)
                                                .addSpeed(map.get("Fan Speed In"))
                                                .addSpeed(map.get("Fan Speed Out"))
                                                .setSpeedUnits("RPM")
                                                .build())
                                .setHashRate(
                                        new BigDecimal(map.get("MHS 5s"))
                                                .multiply(new BigDecimal(
                                                        Math.pow(1000, 2)))));
    }
}
