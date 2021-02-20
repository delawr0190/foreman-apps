package mn.foreman.minerva;

import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/** Utility methods for parsing Miner-Va miner response values. */
class MinerVaUtils {

    /**
     * Updates the builder with stats.
     *
     * @param values The response values.
     */
    static void updateStats(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        final AtomicInteger activeBoards = new AtomicInteger(0);
        values.entrySet()
                .stream()
                .filter(entry -> "STATS".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("STATS"))
                .forEach(map -> {
                    final BigDecimal boardRate =
                            new BigDecimal(
                                    map.getOrDefault(
                                            "MHS av",
                                            "0"));
                    if (boardRate.compareTo(BigDecimal.ZERO) > 0) {
                        activeBoards.incrementAndGet();
                    }

                    if (!builder.hasFans()) {
                        builder.setFanInfo(
                                new FanInfo.Builder()
                                        .setCount(2)
                                        .addSpeed(
                                                map.getOrDefault(
                                                        "Fan0 Speed",
                                                        "0"))
                                        .addSpeed(
                                                map.getOrDefault(
                                                        "Fan1 Speed",
                                                        "0"))
                                        .setSpeedUnits("RPM")
                                        .build());
                    }

                    builder.addTemp(
                            map.getOrDefault(
                                    "Temp Avg",
                                    "0"));
                });

        builder.setBoards(activeBoards.get());
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
                .filter(map -> map.containsKey("MHS av"))
                .forEach(map -> {
                    context.addSimple(
                            ContextKey.MAC,
                            map.get("Netid").replaceAll(".{2}(?=.)", "$0:"));
                    builder.setHashRate(
                            new BigDecimal(map.get("MHS av"))
                                    .multiply(BigDecimal.valueOf(Math.pow(1000, 2))));
                });
    }
}
