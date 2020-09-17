package mn.foreman.util;

/** Utilities for iteracting with MRR. */
public class MrrUtils {

    /**
     * Gets the rig id.
     *
     * @param pool The pool.
     * @param user The user.
     *
     * @return The rig ID, if present.
     */
    public static String getRigId(
            final String pool,
            final String user) {
        if (pool.contains("miningrigrentals")) {
            final String[] tokens = user.split("\\.");
            if (tokens.length > 1) {
                return tokens[tokens.length - 1];
            }
        }
        return null;
    }
}
