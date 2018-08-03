package mn.foreman.util;

/** Pool utilities. */
public class PoolUtils {

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

            // Trim the protocol
            if (sanitized.contains("://")) {
                sanitized = sanitized.split("://")[1];
            }

            // Trim user
            if (sanitized.contains("@")) {
                sanitized = sanitized.split("@")[1];
            }

            // Remove slashes
            sanitized = sanitized.replace("/", "");

            // Trim padding
            sanitized = sanitized.trim();
        }
        return sanitized;
    }
}
