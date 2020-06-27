package mn.foreman.antminer;

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

/** Test changing pools on an Antminer. */
@RunWith(Parameterized.class)
public class AntminerChangePoolsITest
        extends AbstractChangePoolsITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     */
    public AntminerChangePoolsITest(
            final Map<String, ServerHandler> handlers) {
        super(
                new AntminerChangePoolsStrategy(
                        "antMiner Configuration",
                        Arrays.asList(
                                AntminerConfValue.POOL_1_URL,
                                AntminerConfValue.POOL_1_USER,
                                AntminerConfValue.POOL_1_PASS,
                                AntminerConfValue.POOL_2_URL,
                                AntminerConfValue.POOL_2_USER,
                                AntminerConfValue.POOL_2_PASS,
                                AntminerConfValue.POOL_3_URL,
                                AntminerConfValue.POOL_3_USER,
                                AntminerConfValue.POOL_3_PASS,
                                AntminerConfValue.NO_BEEPER,
                                AntminerConfValue.NO_TEMP_OVER_CTRL,
                                AntminerConfValue.FAN_CTRL,
                                AntminerConfValue.FAN_PWM,
                                AntminerConfValue.FREQ)),
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
                                // Antminer L3
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum-ltc.antpool.com:8888\",\n" +
                                                        "\"user\" : \"antminer_1\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum-ltc.antpool.com:443\",\n" +
                                                        "\"user\" : \"antminer_1\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum.f2pool.com:8888\",\n" +
                                                        "\"user\" : \"ant.123\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-freq\" : \"384\"\n" +
                                                        "}",
                                                AntminerChangePoolsITest::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "_ant_pool1url=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&_ant_pool1user=my-test-username1&_ant_pool1pw=my-test-password1&_ant_pool2url=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&_ant_pool2user=my-test-username2&_ant_pool2pw=my-test-password2&_ant_pool3url=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&_ant_pool3user=my-test-username3&_ant_pool3pw=my-test-password3&_ant_nobeeper=false&_ant_notempoverctrl=false&_ant_fan_customize_switch=false&_ant_fan_customize_value=&_ant_freq=384",
                                                "ok",
                                                AntminerChangePoolsITest::validateDigest))
                        },
                        {
                                // Antminer L3 (custom fan)
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum-ltc.antpool.com:8888\",\n" +
                                                        "\"user\" : \"antminer_1\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum-ltc.antpool.com:443\",\n" +
                                                        "\"user\" : \"antminer_1\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum.f2pool.com:8888\",\n" +
                                                        "\"user\" : \"ant.123\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-fan-ctrl\" : true,\n" +
                                                        "\"bitmain-fan-pwm\" : \"100\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-freq\" : \"384\"\n" +
                                                        "}",
                                                AntminerChangePoolsITest::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "_ant_pool1url=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&_ant_pool1user=my-test-username1&_ant_pool1pw=my-test-password1&_ant_pool2url=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&_ant_pool2user=my-test-username2&_ant_pool2pw=my-test-password2&_ant_pool3url=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&_ant_pool3user=my-test-username3&_ant_pool3pw=my-test-password3&_ant_nobeeper=false&_ant_notempoverctrl=false&_ant_fan_customize_switch=true&_ant_fan_customize_value=100&_ant_freq=384",
                                                "ok",
                                                AntminerChangePoolsITest::validateDigest))
                        },
                        {
                                // Antminer S9
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"solo.antpool.com:3333\",\n" +
                                                        "\"user\" : \"antminer_1\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum.antpool.com:3333\",\n" +
                                                        "\"user\" : \"antminer_1\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://cn.ss.btc.com:3333\",\n" +
                                                        "\"user\" : \"antminer.1\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version:noncenum\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-freq\" : \"550\",\n" +
                                                        "\"multi-version\" : \"1\"\n" +
                                                        "}",
                                                AntminerChangePoolsITest::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "_ant_pool1url=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&_ant_pool1user=my-test-username1&_ant_pool1pw=my-test-password1&_ant_pool2url=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&_ant_pool2user=my-test-username2&_ant_pool2pw=my-test-password2&_ant_pool3url=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&_ant_pool3user=my-test-username3&_ant_pool3pw=my-test-password3&_ant_nobeeper=false&_ant_notempoverctrl=false&_ant_fan_customize_switch=false&_ant_fan_customize_value=&_ant_freq=550",
                                                "ok",
                                                AntminerChangePoolsITest::validateDigest))
                        }
                });
    }

    /**
     * Validates the digest authentication.
     *
     * @param exchange The exchange to validate.
     *
     * @return Whether or not the auth was valid.
     */
    private static boolean validateDigest(final HttpExchange exchange) {
        final Headers headers = exchange.getRequestHeaders();
        return headers
                .entrySet()
                .stream()
                .filter(entry -> "Authorization".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .anyMatch(header -> {
                    final String headerString = header.get(0);
                    return headerString.contains(
                            "realm=\"antMiner Configuration\"") &&
                            headerString.contains("nonce");
                });
    }
}