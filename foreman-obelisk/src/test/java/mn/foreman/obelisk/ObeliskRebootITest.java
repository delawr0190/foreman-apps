package mn.foreman.obelisk;

import mn.foreman.util.AbstractRebootITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.SkipFirstHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/** Test rebooting an Obelisk. */
public class ObeliskRebootITest
        extends AbstractRebootITest {

    /** Constructor. */
    public ObeliskRebootITest() {
        super(
                8080,
                8080,
                new ObeliskRebootStrategy(
                        1,
                        1,
                        TimeUnit.SECONDS,
                        THREAD_POOL,
                        new ObeliskFactory()),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
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
                                        "/api/login",
                                        new HttpHandler(
                                                "{\"username\":\"my-auth-username\",\"password\":\"my-auth-password\"}",
                                                Collections.emptyMap(),
                                                "",
                                                ImmutableMap.of(
                                                        "Set-Cookie",
                                                        "sessionid=foreman")),
                                        "/api/action/reboot",
                                        new HttpHandler(
                                                "",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sessionid=foreman"),
                                                "",
                                                Collections.emptyMap()),
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
                                                Collections.emptyMap())))),
                true);
    }
}
