package mn.foreman.whatsminer;

import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/** Utility methods for parsing Whatsminer miner response values. */
class WhatsminerUtils {

    /**
     * Updates the builder with devs.
     *
     * @param values The response values.
     */
    static void updateDevs(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        values.entrySet()
                .stream()
                .filter(entry -> "DEVS".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .forEach(map -> {
                    builder.addTemp(map.get("Temperature"));
                    if (!"Alive".equals(map.getOrDefault("Status", "Alive"))) {
                        builder.hasErrors(true);
                    }
                });
    }

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
     * @param context The context.
     */
    static void updateSummary(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder,
            final Context context) {
        values.entrySet()
                .stream()
                .filter(entry -> "SUMMARY".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("MHS 5s"))
                .forEach(map -> {
                    context.addSimple(ContextKey.MAC, map.get("MAC"));
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
                                            .multiply(BigDecimal.valueOf(Math.pow(1000, 2))));
                });
    }
}
