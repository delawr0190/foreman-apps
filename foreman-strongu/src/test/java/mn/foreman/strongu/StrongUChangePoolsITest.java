package mn.foreman.strongu;

import mn.foreman.antminer.StockChangePoolsAction;
import mn.foreman.antminer.util.AntminerAsyncActionITest;
import mn.foreman.util.TestUtils;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

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
        extends AntminerAsyncActionITest {

    /**
     * Constructor.
     *
     * @param httpHandlers The HTTP handlers.
     * @param rpcHandlers  The RPC handlers.
     */
    public StrongUChangePoolsITest(
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, HandlerInterface> rpcHandlers) {
        super(
                TestUtils.toPoolJson(),
                new StrongUFactory(),
                new StockChangePoolsAction(
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
                httpHandlers,
                rpcHandlers);
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
                                                StrongUChangePoolsITest::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 4.11.1\"}],\"SUMMARY\":[{\"Elapsed\":2416,\"MHS av\":387186.77,\"MHS 5s\":397759.08,\"MHS 1m\":405927.53,\"MHS 5m\":410826.48,\"MHS 15m\":378651.35,\"Miner type\":\"dash\",\"Fan Num\":2,\"Fan1\":7800,\"Fan2\":7800,\"Found Blocks\":4,\"Getworks\":85,\"Accepted\":162,\"Rejected\":4,\"Hardware Errors\":8061,\"Utility\":4.02,\"Discarded\":33856,\"Stale\":1,\"Get Failures\":0,\"Local Work\":66696,\"Remote Failures\":0,\"Network Blocks\":37,\"Total MH\":935262073.0000,\"Work Utility\":676.15,\"Difficulty Accepted\":192509.06250000,\"Difficulty Rejected\":4863.92578125,\"Difficulty Stale\":1023.98437500,\"Best Share\":8811756,\"Device Hardware%\":22.8473,\"Device Rejected%\":17.8683,\"Pool Rejected%\":2.4516,\"Pool Stale%\":0.5161,\"Last getwork\":1592273806}],\"id\":1}"),
                                        "{\"command\":\"devs\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":9,\"Msg\":\"2 ASC(s)\",\"Description\":\"cgminer 4.11.1\"}],\"DEVS\":[{\"ASC\":0,\"Name\":\"u6\",\"ID\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":54.56,\"volt\":10.500,\"ChipNum\":70,\"Frequency\":550,\"MHS av\":190748.48,\"MHS 5s\":191920.49,\"MHS 1m\":194433.37,\"MHS 5m\":201864.17,\"MHS 15m\":186301.66,\"Accepted\":77,\"Rejected\":2,\"Hardware Errors\":4552,\"Utility\":1.91,\"Last Share Pool\":0,\"Last Share Time\":1592273789,\"Total MH\":460759065.0000,\"Diff1 Work\":13410,\"Difficulty Accepted\":91134.60937500,\"Difficulty Rejected\":2303.96484375,\"Last Share Difficulty\":2047.96875000,\"Last Valid Work\":1592273807,\"Device Hardware%\":25.3424,\"Device Rejected%\":17.1809,\"Device Elapsed\":2416,\"Reboot Count\":0},{\"ASC\":2,\"Name\":\"u6\",\"ID\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":55.62,\"volt\":12.700,\"ChipNum\":70,\"Frequency\":550,\"MHS av\":196438.30,\"MHS 5s\":208240.11,\"MHS 1m\":211732.23,\"MHS 5m\":208990.13,\"MHS 15m\":192341.76,\"Accepted\":85,\"Rejected\":2,\"Hardware Errors\":3509,\"Utility\":2.11,\"Last Share Pool\":0,\"Last Share Time\":1592273802,\"Total MH\":474503008.0000,\"Diff1 Work\":13811,\"Difficulty Accepted\":101374.45312500,\"Difficulty Rejected\":2559.96093750,\"Last Share Difficulty\":2047.96875000,\"Last Valid Work\":1592273807,\"Device Hardware%\":20.2598,\"Device Rejected%\":18.5357,\"Device Elapsed\":2416,\"Reboot Count\":0}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1592273807,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.11.1\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://img.f2pool.com:4400\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":82,\"Accepted\":162,\"Rejected\":4,\"Works\":32793,\"Discarded\":33856,\"Stale\":1,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Tue Jun 16 10:16:42 2020\n\",\"Diff1 Shares\":27220,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":192509.06250000,\"Difficulty Rejected\":4863.92578125,\"Difficulty Stale\":1023.98437500,\"Last Share Difficulty\":2047.96875000,\"Work Difficulty\":2047.96875000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"img.f2pool.com\",\"Stratum Difficulty\":2047.96875000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":8811756,\"Pool Rejected%\":2.4516,\"Pool Stale%\":0.5161,\"Bad Work\":79,\"Current Block Height\":797103,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"stratum+tcp://img.f2pool.com:4400\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Thu Jan  1 08:00:00 1970\n\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":1,\"Current Block Height\":0,\"Current Block Version\":536870912},{\"POOL\":2,\"URL\":\"stratum+tcp://img.f2pool.com:4400\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx\",\"Last Share Time\":\"Thu Jan  1 08:00:00 1970\n\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":255.99609375,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":2,\"Current Block Height\":797068,\"Current Block Version\":536870912}],\"id\":1}"))
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
                .filter(entry -> "Authorization" .equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .anyMatch(header -> {
                    final String headerString = header.get(0);
                    return headerString.contains(
                            "realm=\"stuMiner Configuration\"") &&
                            headerString.contains("nonce");
                });
    }
}