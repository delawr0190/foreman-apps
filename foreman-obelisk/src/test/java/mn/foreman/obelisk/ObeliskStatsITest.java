package mn.foreman.obelisk;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Tests obelisk stats obtaining. */
@RunWith(Parameterized.class)
public class ObeliskStatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @param port          The port.
     * @param handlers      The handlers.
     * @param expectedStats The expected stats.
     */
    public ObeliskStatsITest(
            final int port,
            final Map<String, ServerHandler> handlers,
            final MinerStats expectedStats) {
        super(
                new ObeliskFactory()
                        .create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        Integer.toString(port),
                                        "username",
                                        "username",
                                        "password",
                                        "password")),
                new FakeHttpMinerServer(
                        port,
                        handlers),
                expectedStats);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     *
     * @throws IOException never.
     */
    @Parameterized.Parameters
    public static Collection parameters()
            throws IOException {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Obelisk SC1 (gen 1)
                                8080,
                                ImmutableMap.of(
                                        "/api/info",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "    \"macAddress\": \"80:00:27:2e:bc:69\",\n" +
                                                        "    \"ipAddress\": \"192.168.1.123\",\n" +
                                                        "    \"model\": \"SC1\",\n" +
                                                        "    \"vendor\": \"Obelisk\"\n" +
                                                        "}\n"),
                                        "/api/login",
                                        new HttpHandler(
                                                "{\"username\":\"username\",\"password\":\"password\"}",
                                                Collections.emptyMap(),
                                                "",
                                                ImmutableMap.of(
                                                        "Set-Cookie",
                                                        "sessionid=foreman")),
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
                                                Collections.emptyMap()),
                                        "/api/logout",
                                        new HttpHandler(
                                                "",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sessionid=foreman"),
                                                "",
                                                Collections.emptyMap())),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(8080)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("myurl.com:3333")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                1234,
                                                                5678,
                                                                0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("7000000.0"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(6000)
                                                                        .addSpeed(7000)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(71)
                                                        .addTemp(81)
                                                        .addTemp(72)
                                                        .addTemp(82)
                                                        .addFlatResponse(
                                                                new ObjectMapper()
                                                                        .readValue(
                                                                                ObeliskStatsITest.class.getResourceAsStream("/sc1_gen1.json"),
                                                                                new TypeReference<Map<String, Object>>() {
                                                                                }))
                                                        .build())
                                        .build()
                        },
                        {
                                // Obelisk SC1 (gen 2)
                                8081,
                                ImmutableMap.of(
                                        "/api/info",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "    \"macAddress\": \"80:00:27:2e:bc:69\",\n" +
                                                        "    \"ipAddress\": \"192.168.1.123\",\n" +
                                                        "    \"model\": \"SC1 Slim\",\n" +
                                                        "    \"vendor\": \"Obelisk\"\n" +
                                                        "}\n"),
                                        "/api/login",
                                        new HttpHandler(
                                                "{\"username\":\"username\",\"password\":\"password\"}",
                                                Collections.emptyMap(),
                                                "",
                                                ImmutableMap.of(
                                                        "Set-Cookie",
                                                        "sessionid=foreman")),
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
                                                        "      \"intakeTemp\": 71,\n" +
                                                        "      \"exhaustTemp\": 81,\n" +
                                                        "      \"hashrate5min\": 3.5\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"intakeTemp\": 72,\n" +
                                                        "      \"exhaustTemp\": 82,\n" +
                                                        "      \"hashrate5min\": 3.5\n" +
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
                                                        "      \"name\": \"Fan Speed\",\n" +
                                                        "      \"value\": 99\n" +
                                                        "    }\n" +
                                                        "  ]\n" +
                                                        "}",
                                                Collections.emptyMap()),
                                        "/api/logout",
                                        new HttpHandler(
                                                "",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sessionid=foreman"),
                                                "",
                                                Collections.emptyMap())),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(8081)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("myurl.com:3333")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                1234,
                                                                5678,
                                                                0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("7000000.0"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(99)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .addTemp(71)
                                                        .addTemp(81)
                                                        .addTemp(72)
                                                        .addTemp(82)
                                                        .addFlatResponse(
                                                                new ObjectMapper()
                                                                        .readValue(
                                                                                ObeliskStatsITest.class.getResourceAsStream("/sc1_gen2.json"),
                                                                                new TypeReference<Map<String, Object>>() {
                                                                                }))
                                                        .build())
                                        .build()
                        }
                });
    }
}
