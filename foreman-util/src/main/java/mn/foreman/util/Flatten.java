package mn.foreman.util;

import com.github.wnameless.json.flattener.FlattenMode;
import com.github.wnameless.json.flattener.JsonFlattener;

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
                .filter(entry -> whitelist.contains(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
    }
}
