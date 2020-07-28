package mn.foreman.dragonmint;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

/** Dragonmint test utilities. */
public class DragonmintTestUtils {

    /**
     * Validates the basic authentication.
     *
     * @param exchange The exchange to validate.
     *
     * @return Whether or not the auth was valid.
     */
    public static boolean validateBasicAuth(final HttpExchange exchange) {
        final Headers headers = exchange.getRequestHeaders();
        return headers
                .entrySet()
                .stream()
                .filter(entry -> "Authorization".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .allMatch(header -> {
                    final String headerString = header.get(0);
                    return ("Basic bXktYXV0aC11c2VybmFtZTpteS1hdXRoLXBhc3N3b3Jk")
                            .equals(headerString);
                });
    }
}
