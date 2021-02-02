package mn.foreman.strongu;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Utility methods for parsing StrongU miner response values. */
class StrongUUtils {

    /**
     * Updates the builder with devs.
     *
     * @param values The response values.
     */
    static void updateDevs(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        final List<Map<String, String>> asics =
                values.entrySet()
                        .stream()
                        .filter(entry -> "DEVS".equals(entry.getKey()))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("ASC"))
                        .collect(Collectors.toList());
        asics.forEach(map -> builder.addTemp(map.get("Temperature")));

        // Boards
        builder.setBoards(
                asics
                        .stream()
                        .map(map -> new BigDecimal(map.getOrDefault("MHS av", "0")))
                        .filter(value -> value.compareTo(BigDecimal.ZERO) > 0)
                        .count());
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
                .forEach(map -> {
                    // Fans
                    final int numFans = Integer.parseInt(map.get("Fan Num"));
                    final FanInfo.Builder fanBuilder =
                            new FanInfo.Builder()
                                    .setCount(numFans)
                                    .setSpeedUnits("RPM");
                    for (int i = 1; i <= numFans; i++) {
                        fanBuilder.addSpeed(map.get("Fan" + i));
                    }

                    builder
                            .setHashRate(
                                    new BigDecimal(map.get("MHS 5s"))
                                            .multiply(
                                                    new BigDecimal(
                                                            Math.pow(
                                                                    1000,
                                                                    2))))
                            .setFanInfo(fanBuilder.build());
                });
    }
}
