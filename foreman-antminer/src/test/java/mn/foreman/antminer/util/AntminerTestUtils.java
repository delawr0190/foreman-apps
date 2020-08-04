package mn.foreman.antminer.util;

import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.google.common.collect.ImmutableMap;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.apache.http.HttpStatus;

import java.util.Map;

/** Test utilities for Antminer testing. */
public class AntminerTestUtils {

    /**
     * Factory method that creates the typical reboot handlers.
     *
     * @param realm The realm.
     *
     * @return The handlers.
     */
    public static Map<String, ServerHandler> toRebootHandlers(final String realm) {
        return ImmutableMap.of(
                "/cgi-bin/reboot.cgi",
                new HttpHandler(
                        "",
                        "",
                        exchange -> AntminerTestUtils.validateDigest(
                                exchange,
                                realm),
                        HttpStatus.SC_INTERNAL_SERVER_ERROR));
    }

    /**
     * Validates the digest authentication.
     *
     * @param exchange The exchange to validate.
     * @param realm    The realm.
     *
     * @return Whether or not the auth was valid.
     */
    public static boolean validateDigest(
            final HttpExchange exchange,
            final String realm) {
        final Headers headers = exchange.getRequestHeaders();
        return headers
                .entrySet()
                .stream()
                .filter(entry -> "Authorization".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .anyMatch(header -> {
                    final String headerString = header.get(0);
                    return headerString.contains(
                            "realm=\"" + realm + "\"") &&
                            headerString.contains("nonce");
                });
    }
}
