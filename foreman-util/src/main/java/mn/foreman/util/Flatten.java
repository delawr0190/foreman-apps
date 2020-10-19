package mn.foreman.util;

import com.github.wnameless.json.flattener.FlattenMode;
import com.github.wnameless.json.flattener.JsonFlattener;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Utilities for flattening json responses. */
public class Flatten {

    /**
     * Flattens and filters the provided json by whitelisted keys.
     *
     * @param json      The raw json.
     * @param whitelist The whitelisted keys.
     *
     * @return The flat and filtered stats.
     */
    public static Map<String, Object> flattenAndFilter(
            final String json,
            final List<String> whitelist) {
        return new JsonFlattener(json)
                .withFlattenMode(FlattenMode.MONGODB)
                .flattenAsMap()
                .entrySet()
                .stream()
                .filter(entry -> isWhitelisted(entry, whitelist))
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (o, o2) -> o,
                                LinkedHashMap::new));
    }

    /**
     * Checks to see if the provided entry contains a key that's whitelisted.
     *
     * @param entry     The entry.
     * @param whitelist The whitelist.
     *
     * @return Whether or not the key is allowed.
     */
    private static boolean isWhitelisted(
            final Map.Entry<String, Object> entry,
            final List<String> whitelist) {
        final String key = entry.getKey();
        return whitelist.contains(key) ||
                whitelist.contains(key.toLowerCase().replace(" ", "_")) ||
                whitelist.contains("all");
    }
}
