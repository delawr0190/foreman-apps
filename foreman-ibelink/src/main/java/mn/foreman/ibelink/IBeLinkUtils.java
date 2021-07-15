package mn.foreman.ibelink;

import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;

import java.util.List;
import java.util.Map;

/** Utility methods for parsing Blackminer miner response values. */
class IBeLinkUtils {

    /**
     * Updates the builder with devs.
     *
     * @param values The response values.
     */
    static void updateDevs(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        builder.setBoards(
                values.entrySet()
                        .stream()
                        .filter(entry -> "DEVS".equals(entry.getKey()))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("PGA"))
                        .filter(map -> map.getOrDefault("Enabled", "").equals("Y"))
                        .filter(map -> map.getOrDefault("Status", "").equals("Alive"))
                        .count());
    }

    /**
     * Updates the builder with summary info.
     *
     * @param context The context.
     * @param values  The response values.
     * @param builder The builder.
     */
    static void updateSummary(
            final Context context,
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        values.entrySet()
                .stream()
                .filter(entry -> "SUMMARY".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("MHS 5s"))
                .forEach(map -> {
                    builder
                            .setHashRate(Double.parseDouble(map.get("MHS 5s")) * Math.pow(1000, 2))
                            .setFanInfo(
                                    new FanInfo.Builder()
                                            .setCount(0)
                                            .setSpeedUnits("RPM")
                                            .build());
                    for (int i = 1; i <= 8; i++) {
                        builder.addTemp(map.getOrDefault("Temp" + i, "0"));
                    }

                    context.addSimple(
                            ContextKey.MAC,
                            map.getOrDefault("macaddress", "")
                                    .replaceAll(
                                            ".{2}(?=.)",
                                            "$0:"));
                });
    }
}
