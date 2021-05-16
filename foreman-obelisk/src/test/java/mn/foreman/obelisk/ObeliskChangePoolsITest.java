package mn.foreman.obelisk;

import mn.foreman.util.AbstractAsyncActionITest;
import mn.foreman.util.TestUtils;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.http.SkipFirstHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/** Test changing pools on an Obelisk. */
@RunWith(Parameterized.class)
public class ObeliskChangePoolsITest
        extends AbstractAsyncActionITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     */
    public ObeliskChangePoolsITest(
            final Map<String, ServerHandler> handlers) {
        super(
                8080,
                8080,
                new ObeliskChangePoolsAction(
                        1,
                        TimeUnit.SECONDS,
                        new ObjectMapper()),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                handlers)),
                new ObeliskFactory(),
                TestUtils.toPoolJson(),
                true);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Obelisk SC1
                                ImmutableMap.of(
                                        "/api/login",
                                        new HttpHandler(
                                                "{\"username\":\"my-auth-username\",\"password\":\"my-auth-password\"}",
                                                Collections.emptyMap(),
                                                "",
                                                ImmutableMap.of(
                                                        "Set-Cookie",
                                                        "sessionid=foreman")),
                                        "/api/config/pools",
                                        new HttpHandler(
                                                "[{\"url\":\"stratum+tcp://my-test-pool1.com:5588\",\"worker\":\"my-test-username1\",\"password\":\"my-test-password1\"},{\"url\":\"stratum+tcp://my-test-pool2.com:5588\",\"worker\":\"my-test-username2\",\"password\":\"my-test-password2\"},{\"url\":\"stratum+tcp://my-test-pool3.com:5588\",\"worker\":\"my-test-username3\",\"password\":\"my-test-password3\"}]",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sessionid=foreman"),
                                                "",
                                                Collections.emptyMap()),
                                        "/api/info",
                                        new SkipFirstHandler(
                                                new HttpHandler(
                                                        "",
                                                        "{\n" +
                                                                "    \"macAddress\": \"80:00:27:2e:bc:69\",\n" +
                                                                "    \"ipAddress\": \"192.168.1.123\",\n" +
                                                                "    \"model\": \"SC1\",\n" +
                                                                "    \"vendor\": \"Obelisk\"\n" +
                                                                "}\n")),
                                        "/api/status/dashboard",
                                        new HttpHandler(
                                                "",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sessionid=foreman"),
                                                // Hand-generated based off of obelisk API code
                                                "{\n" +
                                                        "  \"hashboardStatus\": [\n" +
                                                        "    {\n" +
                                                        "      \"boardTemp\": 71,\n" +
                                                        "      \"chipTemp\": 81,\n" +
                                                        "      \"mhsAvg\": 3.5\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"boardTemp\": 72,\n" +
                                                        "      \"chipTemp\": 82,\n" +
                                                        "      \"mhsAvg\": 3.5\n" +
                                                        "    }\n" +
                                                        "  ],\n" +
                                                        "  \"poolStatus\": [\n" +
                                                        "    {\n" +
                                                        "      \"url\": \"myurl.com:3333\",\n" +
                                                        "      \"status\": \"Alive\",\n" +
                                                        "      \"accepted\": 1234,\n" +
                                                        "      \"rejected\": 5678\n" +
                                                        "    }\n" +
                                                        "  ],\n" +
                                                        "  \"systemInfo\": [\n" +
                                                        "    {\n" +
                                                        "      \"name\": \"Fan 1 Speed\",\n" +
                                                        "      \"value\": 6000\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"name\": \"Fan 2 Speed\",\n" +
                                                        "      \"value\": 7000\n" +
                                                        "    }\n" +
                                                        "  ]\n" +
                                                        "}",
                                                Collections.emptyMap()))
                        }
                });
    }
}
