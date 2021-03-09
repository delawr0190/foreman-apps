package mn.foreman.whatsminer;

import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.asic.Asic;

import org.apache.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/** Utility methods for parsing Whatsminer miner response values. */
class WhatsminerUtils {

    /**
     * Creates a token query.
     *
     * @param uri   The uri.
     * @param token The token to set.
     *
     * @return The query.
     */
    static WhatsminerQuery.Query queryToken(
            final String uri,
            final AtomicReference<String> token) {
        return WhatsminerQuery.Query
                .builder()
                .uri(uri)
                .isGet(true)
                .isMultipartForm(false)
                .urlParams(Collections.emptyList())
                .callback((code, data) -> {
                    if (code == HttpStatus.SC_OK) {
                        final Document document =
                                Jsoup.parse(data);
                        final Element form =
                                document.select("form").first();
                        if (form != null) {
                            final Element input =
                                    document.select("input[name=token]").first();
                            token.set(input.attr("value"));
                        } else {
                            // No form - attempt to extract from js
                            final int start = data.indexOf("token");
                            final int tokenStart = data.indexOf("'", start);
                            final int tokenEnd = data.indexOf("'", tokenStart + 1);
                            token.set(
                                    data.substring(
                                            tokenStart,
                                            tokenEnd).replace("'", ""));
                        }
                    }
                })
                .build();
    }

    /**
     * Updates the builder with devs.
     *
     * @param values The response values.
     */
    static void updateDevs(
            final Map<String, List<Map<String, String>>> values,
            final Asic.Builder builder) {
        final AtomicInteger activeBoards = new AtomicInteger(0);
        values.entrySet()
                .stream()
                .filter(entry -> "DEVS".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .forEach(map -> {
                    builder.addTemp(map.get("Temperature"));
                    builder.addTemp(map.get("Chip Temp Avg"));

                    final BigDecimal boardHashRate =
                            new BigDecimal(
                                    map.getOrDefault(
                                            "MHS av",
                                            "0"));
                    if (boardHashRate.compareTo(BigDecimal.ZERO) > 0) {
                        activeBoards.incrementAndGet();
                    }

                    if (!"Alive".equals(map.getOrDefault("Status", "Alive"))) {
                        builder.hasErrors(true);
                    }
                });

        builder.setBoards(activeBoards.get());
    }

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
                    if (map.containsKey("slot")) {
                        activeBoards.incrementAndGet();
                    }

                    for (int i = 1; i <= 8; i++) {
                        builder.addTemp(map.get("temp_" + i));
                    }
                    if (!"0".equals(map.getOrDefault("err_chips", "0"))) {
                        builder.hasErrors(true);
                    }
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
                    context.addSimple(ContextKey.MAC, map.get("MAC"));

                    final List<String> fans = new LinkedList<>();
                    addFan("Fan Speed In", map, fans);
                    addFan("Fan Speed Out", map, fans);
                    addFan("Power Fanspeed", map, fans);

                    builder
                            .setPowerMode(toPowerMode(map.get("Power Mode")))
                            .setFanInfo(
                                    new FanInfo.Builder()
                                            .setCount(fans.size())
                                            .addSpeeds(fans)
                                            .setSpeedUnits("RPM")
                                            .build())
                            .addTemp(map.get("Env Temp"))
                            .setHashRate(
                                    new BigDecimal(map.get("MHS av"))
                                            .multiply(BigDecimal.valueOf(Math.pow(1000, 2))));
                });
    }

    /**
     * Adds the provided fans.
     *
     * @param key   The key.
     * @param stats The stats.
     * @param fans  The dest.
     */
    private static void addFan(
            final String key,
            final Map<String, String> stats,
            final List<String> fans) {
        final String fan = stats.get(key);
        if (fan != null && !fan.isEmpty() && !"0".equals(fan)) {
            fans.add(fan);
        }
    }

    /**
     * Converts the provided mode to a string.
     *
     * @param powerMode The mode.
     *
     * @return The power mode.
     */
    private static Asic.PowerMode toPowerMode(final String powerMode) {
        if ("High".equalsIgnoreCase(powerMode)) {
            return Asic.PowerMode.HIGH;
        } else if ("Low".equalsIgnoreCase(powerMode)) {
            return Asic.PowerMode.LOW;
        } else {
            return Asic.PowerMode.NORMAL;
        }
    }
}
