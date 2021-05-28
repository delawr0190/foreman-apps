package mn.foreman.goldshell;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Runs an integration tests using {@link Goldshell} against a fake API. */
@RunWith(Parameterized.class)
public class GoldshellStatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     */
    public GoldshellStatsITest(
            final Map<String, ServerHandler> handlers,
            final MinerStats expected) {
        super(
                new GoldshellFactory(
                        new ApplicationConfiguration()).create(
                        ImmutableMap.<String, Object>builder()
                                .put(
                                        "apiIp",
                                        "127.0.0.1")
                                .put(
                                        "apiPort",
                                        "8080")
                                .build()),
                new FakeHttpMinerServer(
                        8080,
                        handlers),
                expected);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // CK5
                                ImmutableMap.of(
                                        "/mcb/cgminer",
                                        new HttpHandler(
                                                "cgminercmd=devs",
                                                "{\n" +
                                                        "    \"status\": 2,\n" +
                                                        "    \"data\": [\n" +
                                                        "        {\n" +
                                                        "            \"valid\": 15,\n" +
                                                        "            \"av_hashrate\": 3051310.1080000001,\n" +
                                                        "            \"rejected\": 4434,\n" +
                                                        "            \"time\": 1293,\n" +
                                                        "            \"id\": 0,\n" +
                                                        "            \"powerplan\": 0,\n" +
                                                        "            \"hwerrors\": 497,\n" +
                                                        "            \"fanspeed\": \"2940 rpm / 2940 rpm / 2940 rpm / 2940 rpm\",\n" +
                                                        "            \"accepted\": 222312,\n" +
                                                        "            \"hwerr_ration\": 0.0021679999999999997,\n" +
                                                        "            \"nonces\": 227243,\n" +
                                                        "            \"hashrate\": 2992548.7789999998,\n" +
                                                        "            \"temp\": \"83.0 Â°C / 73.8 Â°C\",\n" +
                                                        "            \"minerstatus\": 0\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "            \"valid\": 15,\n" +
                                                        "            \"av_hashrate\": 3058388.8669999999,\n" +
                                                        "            \"rejected\": 4353,\n" +
                                                        "            \"time\": 1293,\n" +
                                                        "            \"id\": 1,\n" +
                                                        "            \"powerplan\": 0,\n" +
                                                        "            \"hwerrors\": 224,\n" +
                                                        "            \"fanspeed\": \"2940 rpm / 2940 rpm / 2940 rpm / 2940 rpm\",\n" +
                                                        "            \"accepted\": 222918,\n" +
                                                        "            \"hwerr_ration\": 0.000983,\n" +
                                                        "            \"nonces\": 227496,\n" +
                                                        "            \"hashrate\": 3240137.4839999998,\n" +
                                                        "            \"temp\": \"83.0 Â°C / 73.9 Â°C\",\n" +
                                                        "            \"minerstatus\": 0\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "            \"valid\": 15,\n" +
                                                        "            \"av_hashrate\": 3048403.4069999999,\n" +
                                                        "            \"rejected\": 4528,\n" +
                                                        "            \"time\": 1293,\n" +
                                                        "            \"id\": 2,\n" +
                                                        "            \"powerplan\": 0,\n" +
                                                        "            \"hwerrors\": 189,\n" +
                                                        "            \"fanspeed\": \"2940 rpm / 2940 rpm / 2940 rpm / 2940 rpm\",\n" +
                                                        "            \"accepted\": 222004,\n" +
                                                        "            \"hwerr_ration\": 0.000837,\n" +
                                                        "            \"nonces\": 226722,\n" +
                                                        "            \"hashrate\": 3205471.7319999999,\n" +
                                                        "            \"temp\": \"84.0 Â°C / 73.5 Â°C\",\n" +
                                                        "            \"minerstatus\": 0\n" +
                                                        "        },\n" +
                                                        "        {\n" +
                                                        "            \"valid\": 15,\n" +
                                                        "            \"av_hashrate\": 3061376.3110000002,\n" +
                                                        "            \"rejected\": 4445,\n" +
                                                        "            \"time\": 1293,\n" +
                                                        "            \"id\": 3,\n" +
                                                        "            \"powerplan\": 0,\n" +
                                                        "            \"hwerrors\": 151,\n" +
                                                        "            \"fanspeed\": \"2940 rpm / 2940 rpm / 2940 rpm / 2940 rpm\",\n" +
                                                        "            \"accepted\": 223048,\n" +
                                                        "            \"hwerr_ration\": 0.000675,\n" +
                                                        "            \"nonces\": 227645,\n" +
                                                        "            \"hashrate\": 3013904.3689999998,\n" +
                                                        "            \"temp\": \"84.0 Â°C / 73.9 Â°C\",\n" +
                                                        "            \"minerstatus\": 0\n" +
                                                        "        }\n" +
                                                        "    ]\n" +
                                                        "}"),
                                        "/mcb/pools",
                                        new HttpHandler(
                                                "",
                                                "[\n" +
                                                        "    {\n" +
                                                        "        \"url\": \"stratum+tcp://us-ckb.2miners.com:6464\",\n" +
                                                        "        \"legal\": true,\n" +
                                                        "        \"active\": true,\n" +
                                                        "        \"dragid\": 0,\n" +
                                                        "        \"user\": \"[REDACTED]\",\n" +
                                                        "        \"pool-priority\": 0,\n" +
                                                        "        \"pass\": \"[REDACTED]\"\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "        \"url\": \"stratum+tcp://ckb.f2pool.com:4300\",\n" +
                                                        "        \"legal\": true,\n" +
                                                        "        \"active\": false,\n" +
                                                        "        \"dragid\": 1,\n" +
                                                        "        \"user\": \"[REDACTED]\",\n" +
                                                        "        \"pool-priority\": 1,\n" +
                                                        "        \"pass\": \"[REDACTED]\"\n" +
                                                        "    }\n" +
                                                        "]")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(8080)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us-ckb.2miners.com:6464")
                                                        .setWorker("[REDACTED]")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                890282,
                                                                17760,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("ckb.f2pool.com:4300")
                                                        .setWorker("[REDACTED]")
                                                        .setPriority(1)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("12452062363999.9993000000"))
                                                        .setBoards(4)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(4)
                                                                        .addSpeed(2940)
                                                                        .addSpeed(2940)
                                                                        .addSpeed(2940)
                                                                        .addSpeed(2940)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(83)
                                                        .addTemp(73)
                                                        .addTemp(83)
                                                        .addTemp(73)
                                                        .addTemp(84)
                                                        .addTemp(73)
                                                        .addTemp(84)
                                                        .addTemp(73)
                                                        .build())
                                        .build()
                        },
                        {
                                // LT5
                                ImmutableMap.of(
                                        "/mcb/cgminer",
                                        new HttpHandler(
                                                "cgminercmd=devs",
                                                "{\n" +
                                                        "  \"status\": 2,\n" +
                                                        "  \"data\": [\n" +
                                                        "    {\n" +
                                                        "      \"r_temp\": \"73.8 °C\",\n" +
                                                        "      \"valid\": 0,\n" +
                                                        "      \"av_hashrate\": 493.025,\n" +
                                                        "      \"rejected\": 1,\n" +
                                                        "      \"id\": 0,\n" +
                                                        "      \"time\": 11769,\n" +
                                                        "      \"l_temp\": \"0.0 °C\",\n" +
                                                        "      \"powerplan\": 0,\n" +
                                                        "      \"hwerrors\": 7,\n" +
                                                        "      \"t_temp\": \"0.0 °C\",\n" +
                                                        "      \"accepted\": 26,\n" +
                                                        "      \"hwerr_ration\": 0.007192,\n" +
                                                        "      \"hashrate\": 546.357,\n" +
                                                        "      \"m_temp\": \"0.0 °C\",\n" +
                                                        "      \"nonces\": 887,\n" +
                                                        "      \"fanspeed\": \"6060 rpm / 6120 rpm\",\n" +
                                                        "      \"minerstatus\": 0,\n" +
                                                        "      \"adjustpower\": 0\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"r_temp\": \"77.8 °C\",\n" +
                                                        "      \"valid\": 0,\n" +
                                                        "      \"av_hashrate\": 453.76,\n" +
                                                        "      \"rejected\": 0,\n" +
                                                        "      \"id\": 1,\n" +
                                                        "      \"time\": 11769,\n" +
                                                        "      \"l_temp\": \"0.0 °C\",\n" +
                                                        "      \"powerplan\": 0,\n" +
                                                        "      \"hwerrors\": 2,\n" +
                                                        "      \"t_temp\": \"0.0 °C\",\n" +
                                                        "      \"accepted\": 28,\n" +
                                                        "      \"hwerr_ration\": 0.001767,\n" +
                                                        "      \"hashrate\": 453.2,\n" +
                                                        "      \"m_temp\": \"0.0 °C\",\n" +
                                                        "      \"nonces\": 812,\n" +
                                                        "      \"fanspeed\": \"6060 rpm / 6120 rpm\",\n" +
                                                        "      \"minerstatus\": 0,\n" +
                                                        "      \"adjustpower\": 0\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"r_temp\": \"80.2 °C\",\n" +
                                                        "      \"valid\": 0,\n" +
                                                        "      \"av_hashrate\": 454.925,\n" +
                                                        "      \"rejected\": 2,\n" +
                                                        "      \"id\": 2,\n" +
                                                        "      \"time\": 11769,\n" +
                                                        "      \"l_temp\": \"0.0 °C\",\n" +
                                                        "      \"powerplan\": 0,\n" +
                                                        "      \"hwerrors\": 4,\n" +
                                                        "      \"t_temp\": \"0.0 °C\",\n" +
                                                        "      \"accepted\": 13,\n" +
                                                        "      \"hwerr_ration\": 0.004492,\n" +
                                                        "      \"hashrate\": 534.744,\n" +
                                                        "      \"m_temp\": \"0.0 °C\",\n" +
                                                        "      \"nonces\": 814,\n" +
                                                        "      \"fanspeed\": \"6060 rpm / 6120 rpm\",\n" +
                                                        "      \"minerstatus\": 0,\n" +
                                                        "      \"adjustpower\": 0\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"r_temp\": \"78.1 °C\",\n" +
                                                        "      \"valid\": 0,\n" +
                                                        "      \"av_hashrate\": 512.104,\n" +
                                                        "      \"rejected\": 0,\n" +
                                                        "      \"id\": 3,\n" +
                                                        "      \"time\": 11769,\n" +
                                                        "      \"l_temp\": \"0.0 °C\",\n" +
                                                        "      \"powerplan\": 0,\n" +
                                                        "      \"hwerrors\": 1,\n" +
                                                        "      \"t_temp\": \"0.0 °C\",\n" +
                                                        "      \"accepted\": 16,\n" +
                                                        "      \"hwerr_ration\": 0.0007099999999999999,\n" +
                                                        "      \"hashrate\": 581.2519999999998,\n" +
                                                        "      \"m_temp\": \"0.0 °C\",\n" +
                                                        "      \"nonces\": 913,\n" +
                                                        "      \"fanspeed\": \"6060 rpm / 6120 rpm\",\n" +
                                                        "      \"minerstatus\": 0,\n" +
                                                        "      \"adjustpower\": 0\n" +
                                                        "    }\n" +
                                                        "  ]\n" +
                                                        "}"),
                                        "/mcb/pools",
                                        new HttpHandler(
                                                "",
                                                "[\n" +
                                                        "    {\n" +
                                                        "        \"url\": \"stratum+tcp://us-ckb.2miners.com:6464\",\n" +
                                                        "        \"legal\": true,\n" +
                                                        "        \"active\": true,\n" +
                                                        "        \"dragid\": 0,\n" +
                                                        "        \"user\": \"[REDACTED]\",\n" +
                                                        "        \"pool-priority\": 0,\n" +
                                                        "        \"pass\": \"[REDACTED]\"\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "        \"url\": \"stratum+tcp://ckb.f2pool.com:4300\",\n" +
                                                        "        \"legal\": true,\n" +
                                                        "        \"active\": false,\n" +
                                                        "        \"dragid\": 1,\n" +
                                                        "        \"user\": \"[REDACTED]\",\n" +
                                                        "        \"pool-priority\": 1,\n" +
                                                        "        \"pass\": \"[REDACTED]\"\n" +
                                                        "    }\n" +
                                                        "]")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(8080)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us-ckb.2miners.com:6464")
                                                        .setWorker("[REDACTED]")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                83,
                                                                3,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("ckb.f2pool.com:4300")
                                                        .setWorker("[REDACTED]")
                                                        .setPriority(1)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("2115552999.9999998000000"))
                                                        .setBoards(4)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(2)
                                                                        .addSpeed(6060)
                                                                        .addSpeed(6120)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(73)
                                                        .addTemp(77)
                                                        .addTemp(80)
                                                        .addTemp(78)
                                                        .build())
                                        .build()
                        },
                        {
                                // HS5
                                ImmutableMap.of(
                                        "/mcb/cgminer",
                                        new HttpHandler(
                                                "cgminercmd=devs",
                                                "{\n" +
                                                        "  \"status\": 2,\n" +
                                                        "  \"data\": [\n" +
                                                        "    {\n" +
                                                        "      \"valid\": 10,\n" +
                                                        "      \"av_hashrate\": 1392080.759,\n" +
                                                        "      \"rejected\": 0,\n" +
                                                        "      \"time\": 20237,\n" +
                                                        "      \"id\": 0,\n" +
                                                        "      \"powerplan\": 0,\n" +
                                                        "      \"hwerrors\": 1,\n" +
                                                        "      \"fanspeed\": \"2700 rpm / 2700 rpm / 2700 rpm / 2700 rpm\",\n" +
                                                        "      \"accepted\": 631,\n" +
                                                        "      \"hwerr_ration\": 0.00018700000000000002,\n" +
                                                        "      \"nonces\": 4830,\n" +
                                                        "      \"hashrate\": 1310382.019,\n" +
                                                        "      \"temp\": \"82.0 °C / 79.7 °C\",\n" +
                                                        "      \"minerstatus\": 0\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"valid\": 10,\n" +
                                                        "      \"av_hashrate\": 1354028.437,\n" +
                                                        "      \"rejected\": 1,\n" +
                                                        "      \"time\": 20237,\n" +
                                                        "      \"id\": 1,\n" +
                                                        "      \"powerplan\": 0,\n" +
                                                        "      \"hwerrors\": 5,\n" +
                                                        "      \"fanspeed\": \"2700 rpm / 2700 rpm / 2700 rpm / 2700 rpm\",\n" +
                                                        "      \"accepted\": 596,\n" +
                                                        "      \"hwerr_ration\": 0.00107,\n" +
                                                        "      \"nonces\": 4702,\n" +
                                                        "      \"hashrate\": 1405026.7640000002,\n" +
                                                        "      \"temp\": \"81.0 °C / 82.1 °C\",\n" +
                                                        "      \"minerstatus\": 0\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"valid\": 10,\n" +
                                                        "      \"av_hashrate\": 1350856.82,\n" +
                                                        "      \"rejected\": 0,\n" +
                                                        "      \"time\": 20237,\n" +
                                                        "      \"id\": 2,\n" +
                                                        "      \"powerplan\": 0,\n" +
                                                        "      \"hwerrors\": 6,\n" +
                                                        "      \"fanspeed\": \"2700 rpm / 2700 rpm / 2700 rpm / 2700 rpm\",\n" +
                                                        "      \"accepted\": 624,\n" +
                                                        "      \"hwerr_ration\": 0.001391,\n" +
                                                        "      \"nonces\": 4692,\n" +
                                                        "      \"hashrate\": 1225742.98,\n" +
                                                        "      \"temp\": \"83.0 °C / 85.3 °C\",\n" +
                                                        "      \"minerstatus\": 0\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"valid\": 10,\n" +
                                                        "      \"av_hashrate\": 1347685.795,\n" +
                                                        "      \"rejected\": 1,\n" +
                                                        "      \"time\": 20237,\n" +
                                                        "      \"id\": 3,\n" +
                                                        "      \"powerplan\": 0,\n" +
                                                        "      \"hwerrors\": 23,\n" +
                                                        "      \"fanspeed\": \"2700 rpm / 2700 rpm / 2700 rpm / 2700 rpm\",\n" +
                                                        "      \"accepted\": 626,\n" +
                                                        "      \"hwerr_ration\": 0.0052049999999999996,\n" +
                                                        "      \"nonces\": 4698,\n" +
                                                        "      \"hashrate\": 1426696.874,\n" +
                                                        "      \"temp\": \"84.0 °C / 86.7 °C\",\n" +
                                                        "      \"minerstatus\": 0\n" +
                                                        "    }\n" +
                                                        "  ]\n" +
                                                        "}"),
                                        "/mcb/pools",
                                        new HttpHandler(
                                                "",
                                                "[\n" +
                                                        "    {\n" +
                                                        "        \"url\": \"stratum+tcp://us-ckb.2miners.com:6464\",\n" +
                                                        "        \"legal\": true,\n" +
                                                        "        \"active\": true,\n" +
                                                        "        \"dragid\": 0,\n" +
                                                        "        \"user\": \"[REDACTED]\",\n" +
                                                        "        \"pool-priority\": 0,\n" +
                                                        "        \"pass\": \"[REDACTED]\"\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "        \"url\": \"stratum+tcp://ckb.f2pool.com:4300\",\n" +
                                                        "        \"legal\": true,\n" +
                                                        "        \"active\": false,\n" +
                                                        "        \"dragid\": 1,\n" +
                                                        "        \"user\": \"[REDACTED]\",\n" +
                                                        "        \"pool-priority\": 1,\n" +
                                                        "        \"pass\": \"[REDACTED]\"\n" +
                                                        "    }\n" +
                                                        "]")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(8080)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("us-ckb.2miners.com:6464")
                                                        .setWorker("[REDACTED]")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                2477,
                                                                2,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("ckb.f2pool.com:4300")
                                                        .setWorker("[REDACTED]")
                                                        .setPriority(1)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("5367848637000.0002000000"))
                                                        .setBoards(4)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(4)
                                                                        .addSpeed(2700)
                                                                        .addSpeed(2700)
                                                                        .addSpeed(2700)
                                                                        .addSpeed(2700)
                                                                        .setSpeedUnits("RPM")
                                                                        .build())
                                                        .addTemp(82)
                                                        .addTemp(79)
                                                        .addTemp(81)
                                                        .addTemp(82)
                                                        .addTemp(83)
                                                        .addTemp(85)
                                                        .addTemp(84)
                                                        .addTemp(86)
                                                        .build())
                                        .build()
                        }
                });
    }
}