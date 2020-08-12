package mn.foreman.blackminer;

import mn.foreman.antminer.StockChangePoolsAction;
import mn.foreman.antminer.util.AntminerAsyncActionITest;
import mn.foreman.util.TestUtils;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Test changing pools on a Blackminer. */
@RunWith(Parameterized.class)
public class BlackminerChangePoolsITest
        extends AntminerAsyncActionITest {

    /**
     * Constructor.
     *
     * @param algoChanged  Whether or not the algo was changed.
     * @param httpHandlers The HTTP handlers.
     * @param rpcHandlers  The RPC handlers.
     */
    public BlackminerChangePoolsITest(
            final boolean algoChanged,
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, HandlerInterface> rpcHandlers) {
        super(
                toTestData(algoChanged),
                new BlackminerFactory(),
                new StockChangePoolsAction(
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
                httpHandlers,
                rpcHandlers);
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
                                // Blackminer F1-Ultra (no algo change)
                                false,
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
                                                "ok")),
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{ \"STATUS\": [ { \"STATUS\": \"S\", \"When\": 1590413031, \"Code\": 11, \"Msg\": \"Summary\", \"Description\": \"ccminer 2.3.3\" } ], \"SUMMARY\": [ { \"Elapsed\": 298732, \"GHS 5s\": \"4.2088\", \"GHS av\": \"4.5009\", \"Found Blocks\": 0, \"Getworks\": 11954, \"Accepted\": \" 12.0000\", \"Rejected\": \" 12.0000\", \"Hardware Errors\": 0, \"Utility\": 2.19, \"Discarded\": 172324, \"Stale\": 0, \"Get Failures\": 0, \"Local Work\": 2309127, \"Remote Failures\": 0, \"Network Blocks\": 515, \"Total MH\": 1344557110, \"Work Utility\": 63.33, \"Difficulty Accepted\": 314944, \"Difficulty Rejected\": 368, \"Difficulty Stale\": 0, \"Best Share\": 17859103, \"Device Hardware%\": 0, \"Device Rejected%\": 0.1167, \"Pool Rejected%\": 0.1167, \"Pool Stale%\": 0, \"Last getwork\": 1590413031 } ], \"id\": 1 }"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{ \"STATUS\": [ { \"STATUS\": \"S\", \"When\": 1590413031, \"Code\": 70, \"Msg\": \"CGMiner stats\", \"Description\": \"ccminer 2.3.3\" } ], \"STATS\": [ { \"CGMiner\": \"2.3.3\", \"Miner\": \"1.3.0.8\", \"CompileTime\": \"2020-04-05 16-27-23 CST\", \"Type\": \"Blackminer F1-ULTRA\" }{ \"STATS\": 0, \"ID\": \"A30\", \"Elapsed\": 298732, \"Calls\": 0, \"Wait\": 0, \"Max\": 0, \"Min\": 99999999, \"GHS 5s\": \"4.2088\", \"GHS av\": \"4.5009\", \"miner_count\": 2, \"frequency\": \"380\", \"fan_num\": 2, \"fan1\": 2820, \"fan2\": 2850, \"temp_num\": 2, \"temp1\": 52, \"chipTemp1\": \"[65.21, 64.10, 62.38, 62.26, 67.06, 69.64]\", \"temp2\": 52, \"chipTemp2\": \"[69.40, 64.35, 63.12, 61.89, 67.55, 71.73]\", \"temp3\": 0, \"chipTemp3\": \"[0.00, 0.00, 0.00, 0.00, 0.00, 0.00]\", \"temp4\": 0, \"chipTemp4\": \"[0.00, 0.00, 0.00, 0.00, 0.00, 0.00]\", \"temp_max\": 52, \"Device Hardware%\": 0, \"no_matching_work\": 0, \"chain_acn1\": 6, \"chain_acn2\": 6, \"chain_acn3\": 0, \"chain_acn4\": 0, \"chain_acs1\": \" oooooo\", \"chain_acs2\": \" oooooo\", \"chain_acs3\": \"\", \"chain_acs4\": \"\", \"chain_hw1\": 0, \"chain_hw2\": 0, \"chain_hw3\": 0, \"chain_hw4\": 0, \"chain_rate1\": \"1.8897\", \"chain_rate2\": \"2.3191\", \"chain_rate3\": \"\", \"chain_rate4\": \"\" } ], \"id\": 1 }"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{ \"STATUS\": [ { \"STATUS\": \"S\", \"When\": 1590413031, \"Code\": 7, \"Msg\": \"3 Pool(s)\", \"Description\": \"ccminer 2.3.3\" } ], \"POOLS\": [ { \"POOL\": 0, \"URL\": \"stratum+tcp://hns.pool.blackminer.com:9052\", \"Status\": \"Alive\", \"Priority\": 0, \"Quota\": 0, \"Long Poll\": \"N\", \"Getworks\": 11952, \"Accepted\": 10921, \"Rejected\": 12, \"Discarded\": 172324, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"xxx\", \"Last Share Time\": \"0:00:55\", \"Diff\": \" 32.0000\", \"Diff1 Shares\": 313122, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 314944.0, \"Difficulty Rejected\": 368.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 32.0, \"Has Stratum\": true, \"Stratum Active\": true, \"Stratum URL\": \"hns.pool.blackminer.com\", \"Has GBT\": false, \"Best Share\": \" 0.0000\", \"Pool Rejected%\": 0.1167, \"Pool Stale%\": 0.0 }, { \"POOL\": 1, \"URL\": \"stratum+tcp://stratum-us.hnspool.com:3001\", \"Status\": \"Alive\", \"Priority\": 1, \"Quota\": 0, \"Long Poll\": \"N\", \"Getworks\": 2, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"xxx\", \"Last Share Time\": \"0\", \"Diff\": \" 5.0000\", \"Diff1 Shares\": 1, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": \" 0.0000\", \"Pool Rejected%\": 0.0, \"Pool Stale%\": 0.0 }, { \"POOL\": 2, \"URL\": \"\", \"Status\": \"Dead\", \"Priority\": 2, \"Quota\": 0, \"Long Poll\": \"N\", \"Getworks\": 0, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"\", \"Last Share Time\": \"0\", \"Diff\": \" 0.0000\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": false, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": \" 0.0000\", \"Pool Rejected%\": 0.0, \"Pool Stale%\": 0.0 } ], \"id\": 1 }"))
                        },
                        {
                                // Blackminer F1-Ultra (algo change)
                                true,
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
                                                "_bb_pool1url=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&_bb_pool1user=my-test-username1&_bb_pool1pw=my-test-password1&_bb_pool2url=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&_bb_pool2user=my-test-username2&_bb_pool2pw=my-test-password2&_bb_pool3url=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&_bb_pool3user=my-test-username3&_bb_pool3pw=my-test-password3&_bb_nobeeper=false&_bb_notempoverctrl=false&_bb_fan_customize_switch=false&_bb_fan_customize_value=&_bb_freq=400&_bb_coin_type=my-algo",
                                                "ok")),
                                ImmutableMap.of(
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{ \"STATUS\": [ { \"STATUS\": \"S\", \"When\": 1590413031, \"Code\": 11, \"Msg\": \"Summary\", \"Description\": \"ccminer 2.3.3\" } ], \"SUMMARY\": [ { \"Elapsed\": 298732, \"GHS 5s\": \"4.2088\", \"GHS av\": \"4.5009\", \"Found Blocks\": 0, \"Getworks\": 11954, \"Accepted\": \" 12.0000\", \"Rejected\": \" 12.0000\", \"Hardware Errors\": 0, \"Utility\": 2.19, \"Discarded\": 172324, \"Stale\": 0, \"Get Failures\": 0, \"Local Work\": 2309127, \"Remote Failures\": 0, \"Network Blocks\": 515, \"Total MH\": 1344557110, \"Work Utility\": 63.33, \"Difficulty Accepted\": 314944, \"Difficulty Rejected\": 368, \"Difficulty Stale\": 0, \"Best Share\": 17859103, \"Device Hardware%\": 0, \"Device Rejected%\": 0.1167, \"Pool Rejected%\": 0.1167, \"Pool Stale%\": 0, \"Last getwork\": 1590413031 } ], \"id\": 1 }"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{ \"STATUS\": [ { \"STATUS\": \"S\", \"When\": 1590413031, \"Code\": 70, \"Msg\": \"CGMiner stats\", \"Description\": \"ccminer 2.3.3\" } ], \"STATS\": [ { \"CGMiner\": \"2.3.3\", \"Miner\": \"1.3.0.8\", \"CompileTime\": \"2020-04-05 16-27-23 CST\", \"Type\": \"Blackminer F1-ULTRA\" }{ \"STATS\": 0, \"ID\": \"A30\", \"Elapsed\": 298732, \"Calls\": 0, \"Wait\": 0, \"Max\": 0, \"Min\": 99999999, \"GHS 5s\": \"4.2088\", \"GHS av\": \"4.5009\", \"miner_count\": 2, \"frequency\": \"380\", \"fan_num\": 2, \"fan1\": 2820, \"fan2\": 2850, \"temp_num\": 2, \"temp1\": 52, \"chipTemp1\": \"[65.21, 64.10, 62.38, 62.26, 67.06, 69.64]\", \"temp2\": 52, \"chipTemp2\": \"[69.40, 64.35, 63.12, 61.89, 67.55, 71.73]\", \"temp3\": 0, \"chipTemp3\": \"[0.00, 0.00, 0.00, 0.00, 0.00, 0.00]\", \"temp4\": 0, \"chipTemp4\": \"[0.00, 0.00, 0.00, 0.00, 0.00, 0.00]\", \"temp_max\": 52, \"Device Hardware%\": 0, \"no_matching_work\": 0, \"chain_acn1\": 6, \"chain_acn2\": 6, \"chain_acn3\": 0, \"chain_acn4\": 0, \"chain_acs1\": \" oooooo\", \"chain_acs2\": \" oooooo\", \"chain_acs3\": \"\", \"chain_acs4\": \"\", \"chain_hw1\": 0, \"chain_hw2\": 0, \"chain_hw3\": 0, \"chain_hw4\": 0, \"chain_rate1\": \"1.8897\", \"chain_rate2\": \"2.3191\", \"chain_rate3\": \"\", \"chain_rate4\": \"\" } ], \"id\": 1 }"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{ \"STATUS\": [ { \"STATUS\": \"S\", \"When\": 1590413031, \"Code\": 7, \"Msg\": \"3 Pool(s)\", \"Description\": \"ccminer 2.3.3\" } ], \"POOLS\": [ { \"POOL\": 0, \"URL\": \"stratum+tcp://hns.pool.blackminer.com:9052\", \"Status\": \"Alive\", \"Priority\": 0, \"Quota\": 0, \"Long Poll\": \"N\", \"Getworks\": 11952, \"Accepted\": 10921, \"Rejected\": 12, \"Discarded\": 172324, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"xxx\", \"Last Share Time\": \"0:00:55\", \"Diff\": \" 32.0000\", \"Diff1 Shares\": 313122, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 314944.0, \"Difficulty Rejected\": 368.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 32.0, \"Has Stratum\": true, \"Stratum Active\": true, \"Stratum URL\": \"hns.pool.blackminer.com\", \"Has GBT\": false, \"Best Share\": \" 0.0000\", \"Pool Rejected%\": 0.1167, \"Pool Stale%\": 0.0 }, { \"POOL\": 1, \"URL\": \"stratum+tcp://stratum-us.hnspool.com:3001\", \"Status\": \"Alive\", \"Priority\": 1, \"Quota\": 0, \"Long Poll\": \"N\", \"Getworks\": 2, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"xxx\", \"Last Share Time\": \"0\", \"Diff\": \" 5.0000\", \"Diff1 Shares\": 1, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": \" 0.0000\", \"Pool Rejected%\": 0.0, \"Pool Stale%\": 0.0 }, { \"POOL\": 2, \"URL\": \"\", \"Status\": \"Dead\", \"Priority\": 2, \"Quota\": 0, \"Long Poll\": \"N\", \"Getworks\": 0, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"\", \"Last Share Time\": \"0\", \"Diff\": \" 0.0000\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": false, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": \" 0.0000\", \"Pool Rejected%\": 0.0, \"Pool Stale%\": 0.0 } ], \"id\": 1 }"))
                        }
                });
    }

    /**
     * Creates test Blackminer data.
     *
     * @param algoChanged Whether or not the algo was changed.
     *
     * @return The test data.
     */
    private static Map<String, Object> toTestData(final boolean algoChanged) {
        final Map<String, Object> testData = new HashMap<>();
        TestUtils.toPoolJson().forEach(testData::put);
        testData.put("algo", algoChanged ? "my-algo" : "");
        return testData;
    }
}