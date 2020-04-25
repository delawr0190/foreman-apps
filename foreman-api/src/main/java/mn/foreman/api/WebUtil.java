package mn.foreman.api;

import java.util.Optional;

/**
 * {@link WebUtil} provides utility functions for performing HTTP operations
 * against the Foreman API.
 */
public interface WebUtil {

    /**
     * Performs a <code>GET</code> operation against the provided URI.
     *
     * @param uri The URI.
     *
     * @return The response content.
     */
    Optional<String> get(String uri);

    /**
     * Performs a <code>POST</code> operation against the provided URI.
     *
     * @param uri The URI.
     *
     * @return The response content.
     */
    Optional<String> post(String uri);

    /**
     * Performs a <code>POST</code> operation against the provided URI with
     * content.
     *
     * @param uri  The URI.
     * @param body The content.
     *
     * @return The response content.
     */
    Optional<String> post(
            String uri,
            String body);
}
