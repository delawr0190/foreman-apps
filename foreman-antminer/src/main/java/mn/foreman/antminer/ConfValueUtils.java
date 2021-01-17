package mn.foreman.antminer;

import mn.foreman.api.model.Pool;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/** Utilities for manipulating configuration values. */
public class ConfValueUtils {

    /**
     * Creates a parameter.
     *
     * @param sourceKey    The source key.
     * @param destKey      The dest key.
     * @param conf         The configuration.
     * @param defaultValue The default value.
     * @param dest         The destination.
     */
    public static void addField(
            final String sourceKey,
            final Object destKey,
            final Map<String, Object> conf,
            final Object defaultValue,
            final List<Map<String, Object>> dest) {
        final Object value = conf.getOrDefault(sourceKey, defaultValue).toString();
        dest.add(
                ImmutableMap.of(
                        "key",
                        destKey,
                        "value",
                        value));
    }

    /**
     * Creates a parameter.
     *
     * @param sourceKey The source key.
     * @param destKey   The dest key.
     * @param conf      The configuration.
     * @param dest      The destination.
     */
    public static void addField(
            final String sourceKey,
            final Object destKey,
            final Map<String, Object> conf,
            final List<Map<String, Object>> dest) {
        addField(
                sourceKey,
                destKey,
                conf,
                "",
                dest);
    }

    /**
     * Adds a pool field to the destination.
     *
     * @param dest        The destination.
     * @param keyPattern  The key pattern.
     * @param pools       The pools.
     * @param sourceIndex The source pool index.
     * @param getter      The getter.
     * @param destIndex   The destination index.
     */
    public static void addPoolField(
            final List<Map<String, Object>> dest,
            final String keyPattern,
            final List<Pool> pools,
            final int sourceIndex,
            final Function<Pool, String> getter,
            final int destIndex) {
        dest.add(
                ImmutableMap.of(
                        "key",
                        String.format(
                                keyPattern,
                                destIndex),
                        "value",
                        getter.apply(pools.get(sourceIndex))));
    }
}
