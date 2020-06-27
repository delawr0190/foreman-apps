package mn.foreman.dragonmint;

import mn.foreman.util.AbstractChangePoolsITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.google.common.collect.ImmutableMap;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Test changing pools on a Dragonmint. */
@RunWith(Parameterized.class)
public class DragonmintChangePoolsITest
        extends AbstractChangePoolsITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     */
    public DragonmintChangePoolsITest(
            final Map<String, ServerHandler> handlers) {
        super(
                new DragonmintChangePoolsStrategy(),
                () -> new FakeHttpMinerServer(
                        8080,
                        handlers),
                true);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Dragonmint T1
                                ImmutableMap.of(
                                        "/api/updatePools",
                                        new HttpHandler(
                                                "Pool1=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&Username1=my-test-username1&Password1=my-test-password1&Pool2=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&Username2=my-test-username2&Password2=my-test-password2&Pool3=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&Username3=my-test-username3&Password3=my-test-password3",
                                                "{\"success\": true}",
                                                DragonmintChangePoolsITest::validateBasicAuth))
                        }
                });
    }

    /**
     * Validates the basic authentication.
     *
     * @param exchange The exchange to validate.
     *
     * @return Whether or not the auth was valid.
     */
    private static boolean validateBasicAuth(final HttpExchange exchange) {
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
