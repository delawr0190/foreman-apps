package mn.foreman.util;

import java.util.Map;

/** Utilities for parsing arguments. */
public class ArgUtils {

    /**
     * Checks to see if hostname is preferred based on the provided args.
     *
     * @param args The args.
     *
     * @return Whether or not hostname is preferred.
     */
    public static boolean isHostnamePreferred(final Map<String, Object> args) {
        return safeGet("hostnamePreferred", args);
    }

    /**
     * Checks to see if workername is preferred based on the provided args.
     *
     * @param args The args.
     *
     * @return Whether or not workername is preferred.
     */
    public static boolean isWorkerPreferred(final Map<String, Object> args) {
        return safeGet("workerPreferred", args);
    }

    /**
     * Safely obtains a boolean value from the provided map.
     *
     * @param key  The key.
     * @param args The args.
     *
     * @return The value.
     */
    private static boolean safeGet(
            final String key,
            final Map<String, Object> args) {
        return Boolean.parseBoolean(
                args.getOrDefault(
                        key,
                        "false").toString());
    }
}
