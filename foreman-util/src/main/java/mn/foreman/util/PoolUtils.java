package mn.foreman.util;

/** Pool utilities. */
public class PoolUtils {

    /**
     * Constructor.
     *
     * Note: intentionally hidden.
     */
    private PoolUtils() {
        // Do nothing
    }

    /**
     * Sanitizes a URL by extracting the host and post.
     *
     * @param url The URL.
     *
     * @return The pool.
     */
    public static String sanitizeUrl(final String url) {
        String sanitized = "";
        if ((url != null) && !url.isEmpty()) {
            sanitized = url;

            // Trim padding
            sanitized = sanitized.trim();

            // Remove anything in the front
            if (sanitized.contains(" ")) {
                sanitized = sanitized.split(" ")[1];
            }

            // Trim the protocol
            if (sanitized.contains("://")) {
                sanitized = sanitized.split("://")[1];
            }

            // Trim user
            if (sanitized.contains("@")) {
                sanitized = sanitized.split("@")[1];
            }

            // Lowercase
            sanitized = sanitized.toLowerCase();

            // Remove slashes
            sanitized = sanitized.replace("/", "");

            // Remove >
            sanitized = sanitized.replace(">", "");

            // Trim padding
            sanitized = sanitized.trim();
        }
        return sanitized;
    }
}
