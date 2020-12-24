package mn.foreman.util;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/** Utilities for manipulating params. */
public class ParamUtils {

    /**
     * Adds the provided params.
     * @param key The key.
     * @param value The value.
     * @param dest The dest.
     */
    public static void addParam(
            final String key,
            final Object value,
            final List<Map<String, Object>> dest) {
        dest.add(
                ImmutableMap.of(
                        "key",
                        key,
                        "value",
                        value));
    }
}
