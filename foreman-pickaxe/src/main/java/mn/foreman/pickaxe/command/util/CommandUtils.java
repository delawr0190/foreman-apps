package mn.foreman.pickaxe.command.util;

import java.util.Map;

/** Utilities for command processing. */
public class CommandUtils {

    /**
     * Gets a key from the provided map, returning an empty string if not
     * found.
     *
     * @param args The map to inspect.
     * @param key  The key.
     *
     * @return The value.
     */
    public static String safeGet(
            final Map<String, Object> args,
            final String key) {
        String result = "";
        final Object object = args.get(key);
        if (object != null) {
            result = object.toString();
        }
        return result.toLowerCase();
    }
}
