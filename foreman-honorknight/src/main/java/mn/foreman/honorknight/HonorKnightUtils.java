package mn.foreman.honorknight;

import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/** HonorKnight utilities. */
public class HonorKnightUtils {

    /**
     * Update the provided builder with the stats in the edevs response.
     *
     * @param fullValues  The raw values.
     * @param asicBuilder The builder to update.
     */
    public static void updateEDevs(
            final Map<String, List<Map<String, String>>> fullValues,
            final Asic.Builder asicBuilder) {
        final List<Map<String, String>> devs =
                fullValues.entrySet()
                        .stream()
                        .filter(entry -> "DEVS".equals(entry.getKey()))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("MHS av"))
                        .collect(Collectors.toList());

        // Hash rate
        asicBuilder.setHashRate(
                devs
                        .stream()
                        .map(Map::entrySet)
                        .flatMap(Set::stream)
                        .filter(entry -> entry.getKey().equals("MHS av"))
                        .map(Map.Entry::getValue)
                        .map(BigDecimal::new)
                        .map(rate -> rate.multiply(BigDecimal.valueOf(Math.pow(1000, 2))))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        // Boards
        asicBuilder.setBoards(
                devs
                        .stream()
                        .filter(map -> "Alive".equals(map.get("Status")) && "Y".equals(map.get("Enabled")))
                        .count());
    }

    /**
     * Update the provided builder with the stats in the stats response.
     *
     * @param fullValues  The raw values.
     * @param asicBuilder The builder to update.
     */
    public static void updateStats(
            final Context context,
            final Map<String, List<Map<String, String>>> fullValues,
            final Asic.Builder asicBuilder) {
        final List<Map<String, String>> values =
                fullValues.entrySet()
                        .stream()
                        .filter(entry -> "STATS".equals(entry.getKey()))
                        .map(Map.Entry::getValue)
                        .flatMap(List::stream)
                        .filter(map -> map.containsKey("Num chips"))
                        .collect(Collectors.toList());

        final Map<String, String> firstStats =
                values.get(0);

        // Fans
        asicBuilder.setFanInfo(
                new FanInfo.Builder()
                        .setCount(2)
                        .addSpeed(firstStats.get("Fan0 rpm"))
                        .addSpeed(firstStats.get("Fan1 rpm"))
                        .setSpeedUnits("RPM")
                        .build());

        // Temps
        values
                .stream()
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .filter(entry -> entry.getKey().equals("Temp"))
                .map(Map.Entry::getValue)
                .forEach(asicBuilder::addTemp);

        // Errors
        asicBuilder.hasErrors(
                values
                        .stream()
                        .anyMatch(map ->
                                Integer.parseInt(map.get("Num chips")) !=
                                        Integer.parseInt(map.get("Num active chips"))));

        // Context data
        context.getSimple(ContextKey.MRR_RIG_ID)
                .ifPresent(asicBuilder::setMrrRigId);
        context.getMulti(ContextKey.RAW_STATS)
                .ifPresent(asicBuilder::addRawStats);

    }
}
