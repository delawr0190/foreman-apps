package mn.foreman.strongu;

import mn.foreman.antminer.AntminerChangePoolsStrategy;
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

/** Test changing pools on a StrongU. */
@RunWith(Parameterized.class)
public class StrongUChangePoolsITest
        extends AbstractChangePoolsITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     */
    public StrongUChangePoolsITest(
            final Map<String, ServerHandler> handlers) {
        super(
                new AntminerChangePoolsStrategy(
                        "stuMiner Configuration",
                        Arrays.asList(
                                StrongUConfValue.POOL_1_URL,
                                StrongUConfValue.POOL_1_USER,
                                StrongUConfValue.POOL_1_PASS,
                                StrongUConfValue.POOL_2_URL,
                                StrongUConfValue.POOL_2_USER,
                                StrongUConfValue.POOL_2_PASS,
                                StrongUConfValue.POOL_3_URL,
                                StrongUConfValue.POOL_3_USER,
                                StrongUConfValue.POOL_3_PASS,
                                StrongUConfValue.NO_BEEPER,
                                StrongUConfValue.NO_TEMP_OVER_CTRL,
                                StrongUConfValue.FAN_PWM,
                                StrongUConfValue.FREQ_1,
                                StrongUConfValue.FREQ_2,
                                StrongUConfValue.FREQ_3,
                                StrongUConfValue.FREQ_4,
                                StrongUConfValue.WORK_VOLT,
                                StrongUConfValue.START_VOLT,
                                StrongUConfValue.PLL_START,
                                StrongUConfValue.PLL_STEP)),
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
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // STU-U6
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
                                                        "\"failover-only\" : true,\n" +
                                                        "\"no-submit-stale\" : true,\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-port\" : \"4028\",\n" +
                                                        "\"api-allow\" : \"W:0/0\",\n" +
                                                        "\"pllstart\" : \"160\",\n" +
                                                        "\"pllstep\" : \"10\",\n" +
                                                        "\"pll0\" : \"550\",\n" +
                                                        "\"pll1\" : \"50\",\n" +
                                                        "\"pll2\" : \"50\",\n" +
                                                        "\"pll3\" : \"50\",\n" +
                                                        "\"upperpll\" : \"580\",\n" +
                                                        "\"lowerpll\" : \"520\",\n" +
                                                        "\"gt_upstep\" : \"5\",\n" +
                                                        "\"gt_downstep\" : \"10\",\n" +
                                                        "\"lt_upstep\" : \"5\",\n" +
                                                        "\"lt_downstep\" : \"10\",\n" +
                                                        "\"gt_uppercent\" : \"0.05\",\n" +
                                                        "\"gt_lowpercent\" : \"0.20\",\n" +
                                                        "\"lt_uppercent\" : \"0.05\",\n" +
                                                        "\"lt_lowpercent\" : \"0.20\",\n" +
                                                        "\"fan0\" : \"100\",\n" +
                                                        "\"fan1\" : \"100\",\n" +
                                                        "\"workvolt\" : \"13.3\",\n" +
                                                        "\"startvol\" : \"13.3\",\n" +
                                                        "\"multi-version\" : \"1\"\n" +
                                                        "}",
                                                StrongUChangePoolsITest::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "_stu_pool1url=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&_stu_pool1user=my-test-username1&_stu_pool1pw=my-test-password1&_stu_pool2url=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&_stu_pool2user=my-test-username2&_stu_pool2pw=my-test-password2&_stu_pool3url=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&_stu_pool3user=my-test-username3&_stu_pool3pw=my-test-password3&_stu_nobeeper=false&_stu_notempoverctrl=false&_stu_fan_customize_value=100&_stu_freq1=550&_stu_freq2=50&_stu_freq3=50&_stu_freq4=50&_stu_workvolt=13.3&_stu_startvol=13.3&_stu_pllstart=160&_stu_pllstep=10",
                                                "ok",
                                                StrongUChangePoolsITest::validateDigest))
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
                            "realm=\"stuMiner Configuration\"") &&
                            headerString.contains("nonce");
                });
    }
}