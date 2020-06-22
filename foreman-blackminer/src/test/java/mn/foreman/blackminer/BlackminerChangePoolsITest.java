package mn.foreman.blackminer;

import mn.foreman.antminer.AntminerChangePoolsStrategy;
import mn.foreman.util.AbstractChangePoolsITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Test changing pools on a Blackminer. */
@RunWith(Parameterized.class)
public class BlackminerChangePoolsITest
        extends AbstractChangePoolsITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     */
    public BlackminerChangePoolsITest(
            final Map<String, HttpHandler> handlers) {
        super(
                new AntminerChangePoolsStrategy(
                        "blackMiner Configuration",
                        Arrays.asList(
                                BlackminerConfValue.POOL_1_URL,
                                BlackminerConfValue.POOL_1_USER,
                                BlackminerConfValue.POOL_1_PASS,
                                BlackminerConfValue.POOL_2_URL,
                                BlackminerConfValue.POOL_2_USER,
                                BlackminerConfValue.POOL_2_PASS,
                                BlackminerConfValue.POOL_3_URL,
                                BlackminerConfValue.POOL_3_USER,
                                BlackminerConfValue.POOL_3_PASS,
                                BlackminerConfValue.NO_BEEPER,
                                BlackminerConfValue.NO_TEMP_OVER_CTRL,
                                BlackminerConfValue.FAN_CTRL,
                                BlackminerConfValue.FAN_PWM,
                                BlackminerConfValue.FREQ,
                                BlackminerConfValue.COIN_TYPE)),
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
                                // Blackminer F1-Ultra
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"X\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"X\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://dash.f2pool.com:5588\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"X\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\": true,\n" +
                                                        "\"api-network\": true,\n" +
                                                        "\"freq\": \"400\",\n" +
                                                        "\"coin-type\": \"tellor\",\n" +
                                                        "\"api-groups\": \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\": \"R:0/0,W:127.0.0.1\"\n" +
                                                        "}"),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "_bb_pool1url=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&_bb_pool1user=my-test-username1&_bb_pool1pw=my-test-password1&_bb_pool2url=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&_bb_pool2user=my-test-username2&_bb_pool2pw=my-test-password2&_bb_pool3url=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&_bb_pool3user=my-test-username3&_bb_pool3pw=my-test-password3&_bb_nobeeper=false&_bb_notempoverctrl=false&_bb_fan_customize_switch=false&_bb_fan_customize_value=&_bb_freq=400&_bb_coin_type=tellor",
                                                "ok"))
                        }
                });
    }
}