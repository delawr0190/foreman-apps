package mn.foreman.obelisk;

import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Collections;

/** Runs an integration tests using {@link Obelisk} against a fake API. */
public class ObeliskSc1Gen1StatsITest
        extends AbstractApiITest {

    /** Constructor. */
    public ObeliskSc1Gen1StatsITest() {
        super(
                new Obelisk(
                        "127.0.0.1",
                        8080,
                        "username",
                        "password"),
                new FakeHttpMinerServer(
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
                                        Collections.emptyMap()))),
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
                                        .build())
                        .build());
    }
}