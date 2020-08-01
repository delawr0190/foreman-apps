package mn.foreman.antminer.util;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

/** Test utilities for Antminer testing. */
public class AntminerTestUtils {

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
