package mn.foreman.antminer;

import mn.foreman.antminer.util.AntminerAsyncActionITest;
import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.util.TestUtils;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import com.sun.net.httpserver.HttpExchange;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Test changing pools on an Antminer. */
@RunWith(Parameterized.class)
public class AntminerChangePoolsITest
        extends AntminerAsyncActionITest {

    /**
     * Constructor.
     *
     * @param httpHandlers The HTTP handlers.
     * @param rpcHandlers  The RPC handlers.
     * @param foundResult  The found result.
     */
    public AntminerChangePoolsITest(
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, HandlerInterface> rpcHandlers,
            final boolean foundResult) {
        super(
                TestUtils.toPoolJson(
                        ImmutableMap.of(
                                "webPort",
                                "8080")),
                new AntminerFactory(BigDecimal.ONE),
                new FirmwareAwareAction(
                        "antMiner Configuration",
                        new StockChangePoolsAction(
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
                        new BraiinsChangePoolsAction()),
                httpHandlers,
                rpcHandlers,
                foundResult);
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
                                                AntminerChangePoolsITest::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315384,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"CGMiner\":\"4.9.0\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}{\"STATS\":0,\"ID\":\"L30\",\"Elapsed\":393983,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"504.56\",\"GHS av\":500.28,\"miner_count\":4,\"frequency\":\"384\",\"fan_num\":2,\"fan1\":3840,\"fan2\":3810,\"temp_num\":4,\"temp1\":46,\"temp2\":46,\"temp3\":46,\"temp4\":45,\"temp2_1\":54,\"temp2_2\":56,\"temp2_3\":56,\"temp2_4\":53,\"temp31\":0,\"temp32\":0,\"temp33\":0,\"temp34\":0,\"temp4_1\":0,\"temp4_2\":0,\"temp4_3\":0,\"temp4_4\":0,\"temp_max\":46,\"Device Hardware%\":0.0000,\"no_matching_work\":152,\"chain_acn1\":72,\"chain_acn2\":72,\"chain_acn3\":72,\"chain_acn4\":72,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs4\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":153,\"chain_hw4\":0,\"chain_rate1\":\"126.12\",\"chain_rate2\":\"125.79\",\"chain_rate3\":\"126.06\",\"chain_rate4\":\"126.59\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315222,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us.litecoinpool.org:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":24933,\"Accepted\":47384,\"Rejected\":212,\"Discarded\":230740,\"Stale\":15,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"obmllc.l3_1\",\"Last Share Time\":\"0:00:23\",\"Diff\":\"65.5K\",\"Diff1 Shares\":11805080,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":2988769280.00000000,\"Difficulty Rejected\":13254656.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us.litecoinpool.org\",\"Has GBT\":false,\"Best Share\":11224839560,\"Pool Rejected%\":0.4415,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                true
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
                                                AntminerChangePoolsITest::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315384,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"CGMiner\":\"4.9.0\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}{\"STATS\":0,\"ID\":\"L30\",\"Elapsed\":393983,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"504.56\",\"GHS av\":500.28,\"miner_count\":4,\"frequency\":\"384\",\"fan_num\":2,\"fan1\":3840,\"fan2\":3810,\"temp_num\":4,\"temp1\":46,\"temp2\":46,\"temp3\":46,\"temp4\":45,\"temp2_1\":54,\"temp2_2\":56,\"temp2_3\":56,\"temp2_4\":53,\"temp31\":0,\"temp32\":0,\"temp33\":0,\"temp34\":0,\"temp4_1\":0,\"temp4_2\":0,\"temp4_3\":0,\"temp4_4\":0,\"temp_max\":46,\"Device Hardware%\":0.0000,\"no_matching_work\":152,\"chain_acn1\":72,\"chain_acn2\":72,\"chain_acn3\":72,\"chain_acn4\":72,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs4\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":153,\"chain_hw4\":0,\"chain_rate1\":\"126.12\",\"chain_rate2\":\"125.79\",\"chain_rate3\":\"126.06\",\"chain_rate4\":\"126.59\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315222,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us.litecoinpool.org:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":24933,\"Accepted\":47384,\"Rejected\":212,\"Discarded\":230740,\"Stale\":15,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"obmllc.l3_1\",\"Last Share Time\":\"0:00:23\",\"Diff\":\"65.5K\",\"Diff1 Shares\":11805080,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":2988769280.00000000,\"Difficulty Rejected\":13254656.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us.litecoinpool.org\",\"Has GBT\":false,\"Best Share\":11224839560,\"Pool Rejected%\":0.4415,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                true
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
                                                AntminerChangePoolsITest::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1516328450,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"2.0.0\",\"API\":\"3.1\",\"Miner\":\"16.8.1.3\",\"CompileTime\":\"Fri Nov 17 17:37:49 CST 2017\",\"Type\":\"Antminer S9\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765846,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 1.0.0\"}],\"STATS\":[{\"BMMiner\":\"1.0.0\",\"Miner\":\"19.10.1.3\",\"CompileTime\":\"Tue Aug 20 10:37:07 CST 2019\",\"Type\":\"Antminer S17 Pro\"},{\"STATS\":0,\"ID\":\"BTM_SOC0\",\"Elapsed\":253447,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"54997.01\",\"GHS av\":54512.07,\"GHS 30m\":54297.86,\"Mode\":1,\"miner_count\":3,\"frequency\":\"\",\"fan_num\":4,\"fan1\":2400,\"fan2\":3000,\"fan3\":3120,\"fan4\":2400,\"fan5\":0,\"fan6\":0,\"fan7\":0,\"fan8\":0,\"fan9\":0,\"fan10\":0,\"fan11\":0,\"fan12\":0,\"fan13\":0,\"fan14\":0,\"fan15\":0,\"fan16\":0,\"temp_num\":3,\"temp1\":50,\"temp2\":53,\"temp3\":45,\"temp4\":0,\"temp5\":0,\"temp6\":0,\"temp7\":0,\"temp8\":0,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":80,\"temp2_2\":75,\"temp2_3\":63,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":0,\"temp2_7\":0,\"temp2_8\":0,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":80,\"temp3_2\":75,\"temp3_3\":63,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"temp_pcb1\":\"22-53-21-50\",\"temp_pcb2\":\"22-53-19-46\",\"temp_pcb3\":\"15-45-12-37\",\"temp_pcb4\":\"0-0-0-0\",\"temp_pcb5\":\"0-0-0-0\",\"temp_pcb6\":\"0-0-0-0\",\"temp_pcb7\":\"0-0-0-0\",\"temp_pcb8\":\"0-0-0-0\",\"temp_pcb9\":\"0-0-0-0\",\"temp_pcb10\":\"0-0-0-0\",\"temp_pcb11\":\"0-0-0-0\",\"temp_pcb12\":\"0-0-0-0\",\"temp_pcb13\":\"0-0-0-0\",\"temp_pcb14\":\"0-0-0-0\",\"temp_pcb15\":\"0-0-0-0\",\"temp_pcb16\":\"0-0-0-0\",\"temp_chip1\":\"48-80-49-70\",\"temp_chip2\":\"46-75-43-66\",\"temp_chip3\":\"41-63-40-55\",\"temp_chip4\":\"0-0-0-0\",\"temp_chip5\":\"0-0-0-0\",\"temp_chip6\":\"0-0-0-0\",\"temp_chip7\":\"0-0-0-0\",\"temp_chip8\":\"0-0-0-0\",\"temp_chip9\":\"0-0-0-0\",\"temp_chip10\":\"0-0-0-0\",\"temp_chip11\":\"0-0-0-0\",\"temp_chip12\":\"0-0-0-0\",\"temp_chip13\":\"0-0-0-0\",\"temp_chip14\":\"0-0-0-0\",\"temp_chip15\":\"0-0-0-0\",\"temp_chip16\":\"0-0-0-0\",\"total_rateideal\":53619.00,\"rate_unit\":\"GH\",\"total_freqavg\":0.00,\"total_acn\":144,\"total_rate\":54997.00,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":0.00,\"chain_rateideal7\":0.00,\"chain_rateideal8\":0.00,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":53,\"no_matching_work\":2885,\"chain_acn1\":48,\"chain_acn2\":48,\"chain_acn3\":48,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":0,\"chain_acn7\":0,\"chain_acn8\":0,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\"\",\"chain_acs7\":\"\",\"chain_acs8\":\"\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":731,\"chain_hw2\":1074,\"chain_hw3\":1080,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"17968.26\",\"chain_rate2\":\"18418.00\",\"chain_rate3\":\"18610.75\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"\",\"chain_rate7\":\"\",\"chain_rate8\":\"\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime1\":\"{}\",\"chain_xtime2\":\"{}\",\"chain_xtime3\":\"{}\",\"chain_offside_1\":\"\",\"chain_offside_2\":\"\",\"chain_offside_3\":\"\",\"chain_opencore_1\":\"1\",\"chain_opencore_2\":\"1\",\"chain_opencore_3\":\"1\",\"freq1\":570,\"freq2\":560,\"freq3\":566,\"freq4\":0,\"freq5\":0,\"freq6\":0,\"freq7\":0,\"freq8\":0,\"freq9\":0,\"freq10\":0,\"freq11\":0,\"freq12\":0,\"freq13\":0,\"freq14\":0,\"freq15\":0,\"freq16\":0,\"miner_version\":\"19.10.1.3\",\"miner_id\":\"801085242b104814\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765846,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 1.0.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://xxxxxx:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":9487,\"Accepted\":86241,\"Rejected\":411,\"Discarded\":136597,\"Stale\":116,\"Get Failures\":5,\"Remote Failures\":4,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"0:00:03\",\"Diff\":\"39.5K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":3210701589.00000000,\"Difficulty Rejected\":17465628.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":39491.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxxxxx\",\"Has GBT\":false,\"Best Share\":1431836158,\"Pool Rejected%\":0.5410,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://xxxxxx:3333\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":8879,\"Accepted\":63,\"Rejected\":0,\"Discarded\":86,\"Stale\":2,\"Get Failures\":55,\"Remote Failures\":0,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"66:18:15\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1901924.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":81558.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"stratum.slushpool.com\",\"Has GBT\":false,\"Best Share\":2237539,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"stratum+tcp://stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":8878,\"Accepted\":196,\"Rejected\":5,\"Discarded\":193,\"Stale\":4,\"Get Failures\":51,\"Remote Failures\":0,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"66:29:37\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":3690626.00000000,\"Difficulty Rejected\":40960.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":28756.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxxxxx\",\"Has GBT\":false,\"Best Share\":1433415,\"Pool Rejected%\":1.0977,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                true
                        },
                        {
                                // Antminer S9 (bOS+)
                                ImmutableMap.of(
                                        "/cgi-bin/luci/admin/miner/overview",
                                        new HttpHandler(
                                                "luci_username=my-auth-username&luci_password=my-auth-password",
                                                Collections.emptyMap(),
                                                "<html></html>",
                                                ImmutableMap.of(
                                                        "Set-Cookie",
                                                        "sysauth=foreman")),
                                        "/cgi-bin/luci/admin/miner/cfg_data/",
                                        new HttpHandler(
                                                "",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sysauth=foreman"),
                                                "{\n" +
                                                        "  \"status\": {\n" +
                                                        "    \"code\": 0,\n" +
                                                        "    \"message\": null,\n" +
                                                        "    \"generator\": \"BOSminer+ 0.2.0-36c56a9363\",\n" +
                                                        "    \"timestamp\": 1597106558\n" +
                                                        "  },\n" +
                                                        "  \"data\": {\n" +
                                                        "    \"format\": {\n" +
                                                        "      \"version\": \"1.1+\",\n" +
                                                        "      \"model\": \"Antminer S9\",\n" +
                                                        "      \"generator\": \"BOSminer+ 0.2.0-36c56a9363\",\n" +
                                                        "      \"timestamp\": 1597106558\n" +
                                                        "    },\n" +
                                                        "    \"group\": [\n" +
                                                        "      {\n" +
                                                        "        \"name\": \"Default\",\n" +
                                                        "        \"pool\": [\n" +
                                                        "          {\n" +
                                                        "            \"url\": \"stratum2+tcp://v2.stratum.slushpool.com/u95GEReVMjK6k5YqiSFNqqTnKU4ypU2Wm8awa6tmbmDmk1bWt\",\n" +
                                                        "            \"user\": \"!non-existent-user!!\",\n" +
                                                        "            \"password\": \"x\"\n" +
                                                        "          },\n" +
                                                        "          {\n" +
                                                        "            \"url\": \"stratum+tcp://stratum.slushpool.com:3333\",\n" +
                                                        "            \"user\": \"!non-existent-user!!\",\n" +
                                                        "            \"password\": \"xx\"\n" +
                                                        "          },\n" +
                                                        "          {\n" +
                                                        "            \"url\": \"stratum+tcp://stratum.slushpool.com:3333\",\n" +
                                                        "            \"user\": \"!non-existent-user!!!\",\n" +
                                                        "            \"password\": \"xxx\"\n" +
                                                        "          }\n" +
                                                        "        ]\n" +
                                                        "      }\n" +
                                                        "    ],\n" +
                                                        "    \"autotuning\": {\n" +
                                                        "      \"enabled\": true\n" +
                                                        "    }\n" +
                                                        "  }\n" +
                                                        "}",
                                                Collections.emptyMap()),
                                        "/cgi-bin/luci/admin/miner/cfg_save/",
                                        new HttpHandler(
                                                "{\"data\":{\"group\":[{\"name\":\"Foreman Group\",\"pool\":[{\"url\":\"stratum+tcp://my-test-pool1.com:5588\",\"user\":\"my-test-username1\",\"password\":\"my-test-password1\"},{\"url\":\"stratum+tcp://my-test-pool2.com:5588\",\"user\":\"my-test-username2\",\"password\":\"my-test-password2\"},{\"url\":\"stratum+tcp://my-test-pool3.com:5588\",\"user\":\"my-test-username3\",\"password\":\"my-test-password3\"}]}],\"autotuning\":{\"enabled\":true}}}",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sysauth=foreman"),
                                                "{\n" +
                                                        "  \"status\": {\n" +
                                                        "    \"code\": 0,\n" +
                                                        "    \"message\": null,\n" +
                                                        "    \"generator\": \"BOSminer+ 0.2.0-36c56a9363\",\n" +
                                                        "    \"timestamp\": 1597184258\n" +
                                                        "  },\n" +
                                                        "  \"data\": {\n" +
                                                        "    \"path\": \"/etc/bosminer.toml\",\n" +
                                                        "    \"format\": {\n" +
                                                        "      \"version\": \"1.1+\",\n" +
                                                        "      \"model\": \"Antminer S9\",\n" +
                                                        "      \"generator\": \"BOSminer+ 0.2.0-36c56a9363\",\n" +
                                                        "      \"timestamp\": 1597184258\n" +
                                                        "    }\n" +
                                                        "  }\n" +
                                                        "}",
                                                Collections.emptyMap()),
                                        "/cgi-bin/luci/admin/miner/cfg_apply/",
                                        new HttpHandler(
                                                "",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sysauth=foreman"),
                                                "{\n" +
                                                        "  \"status\": {\n" +
                                                        "    \"message\": \"Reloading configuration...\",\n" +
                                                        "    \"generator\": \"LuCI backend\",\n" +
                                                        "    \"timestamp\": 1597184258,\n" +
                                                        "    \"code\": 0\n" +
                                                        "  }\n" +
                                                        "}",
                                                Collections.emptyMap())),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187881,\"Code\":22,\"Msg\":\"BOSminer+ versions\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"VERSION\":[{\"API\":\"3.7\",\"BOSminer+\":\"0.2.0-36c56a9363\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187909,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"POOLS\":[{\"Accepted\":152,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":65536,\"Current Block Height\":0,\"Current Block Version\":536870912,\"Diff1 Shares\":10984863,\"Difficulty Accepted\":9961472.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":106,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":65536.0,\"Last Share Time\":1597187897,\"Long Poll\":\"N\",\"POOL\":0,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":0,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":true,\"Stratum Difficulty\":65536.0,\"Stratum URL\":\"stratum.antpool.com:3333\",\"URL\":\"stratum+tcp://stratum.antpool.com:3333\",\"User\":\"antminer_1\",\"Work Difficulty\":65536.0,\"Works\":16655828},{\"Accepted\":0,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":0,\"Current Block Height\":0,\"Current Block Version\":536870912,\"Diff1 Shares\":0,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":3,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":0.0,\"Last Share Time\":0,\"Long Poll\":\"N\",\"POOL\":1,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":1,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":false,\"Stratum Difficulty\":65536.0,\"Stratum URL\":\"stratum.antpool.com:443\",\"URL\":\"stratum+tcp://stratum.antpool.com:443\",\"User\":\"antminer_1\",\"Work Difficulty\":65536.0,\"Works\":0},{\"Accepted\":0,\"AsicBoost\":true,\"Bad Work\":0,\"Best Share\":0,\"Current Block Height\":0,\"Current Block Version\":0,\"Diff1 Shares\":0,\"Difficulty Accepted\":0.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Get Failures\":0,\"Getworks\":0,\"Has GBT\":false,\"Has Stratum\":true,\"Has Vmask\":true,\"Last Share Difficulty\":0.0,\"Last Share Time\":0,\"Long Poll\":\"N\",\"POOL\":2,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Priority\":2,\"Proxy\":\"\",\"Proxy Type\":\"\",\"Quota\":1,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Status\":\"Alive\",\"Stratum Active\":false,\"Stratum Difficulty\":0.0,\"Stratum URL\":\"stratum.antpool.com:25\",\"URL\":\"stratum+tcp://stratum.antpool.com:25\",\"User\":\"antminer_1\",\"Work Difficulty\":0.0,\"Works\":0}],\"id\":1}"),
                                        "{\"command\":\"summary\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187939,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"SUMMARY\":[{\"Accepted\":153,\"Best Share\":65536,\"Device Hardware%\":0.001644850292093576,\"Device Rejected%\":0.0,\"Difficulty Accepted\":10027008.0,\"Difficulty Rejected\":0.0,\"Difficulty Stale\":0.0,\"Discarded\":0,\"Elapsed\":3680,\"Found Blocks\":0,\"Get Failures\":0,\"Getworks\":110,\"Hardware Errors\":186,\"Last getwork\":1597187939,\"Local Work\":17131300,\"MHS 15m\":14199249.521457672,\"MHS 1m\":13967276.98800245,\"MHS 24h\":562115.4771588066,\"MHS 5m\":14257979.893394984,\"MHS 5s\":15740915.842676325,\"MHS av\":11988299.650652435,\"Network Blocks\":0,\"Pool Rejected%\":0.0,\"Pool Stale%\":0.0,\"Rejected\":0,\"Remote Failures\":0,\"Stale\":0,\"Total MH\":44121202599.395325,\"Utility\":2.4945652173913047,\"Work Utility\":184349.05814696933}],\"id\":1}"),
                                        "{\"command\":\"fans\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187973,\"Code\":202,\"Msg\":\"4 Fan(s)\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"FANS\":[{\"FAN\":0,\"ID\":0,\"RPM\":2820,\"Speed\":40},{\"FAN\":1,\"ID\":1,\"RPM\":3780,\"Speed\":40},{\"FAN\":2,\"ID\":2,\"RPM\":0,\"Speed\":40},{\"FAN\":3,\"ID\":3,\"RPM\":0,\"Speed\":40}],\"id\":1}"),
                                        "{\"command\":\"temps\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187996,\"Code\":201,\"Msg\":\"3 Temp(s)\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"TEMPS\":[{\"Board\":75.0,\"Chip\":0.0,\"ID\":6,\"TEMP\":0},{\"Board\":63.0,\"Chip\":0.0,\"ID\":7,\"TEMP\":1},{\"Board\":70.0,\"Chip\":0.0,\"ID\":8,\"TEMP\":2}],\"id\":1}")),
                                true
                        },
                        {
                                // Antminer S9 (Hiveon)
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
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-freq\" : \"550\",\n" +
                                                        "\"multi-version\" : \"1\"\n" +
                                                        "}",
                                                AntminerChangePoolsITest::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "_ant_pool1url=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&_ant_pool1user=my-test-username1&_ant_pool1pw=my-test-password1&_ant_pool2url=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&_ant_pool2user=my-test-username2&_ant_pool2pw=my-test-password2&_ant_pool3url=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&_ant_pool3user=my-test-username3&_ant_pool3pw=my-test-password3&_ant_nobeeper=false&_ant_notempoverctrl=false&_ant_fan_customize_switch=false&_ant_fan_customize_value=&_ant_freq=550",
                                                "ok\nok\n",
                                                AntminerChangePoolsITest::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600292900,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"2.0.0 rwglr\",\"API\":\"3.1\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Tue Nov 20 10:12:30 UTC 2019\",\"Type\":\"Antminer S9 Hiveon\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600292920,\"Code\":70,\"Msg\":\"BMMiner stats\",\"Description\":\"bmminer 1.0.0\"}],\"STATS\":[{\"BMMiner\":\"2.0.0 rwglr\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Tue Nov 20 10:12:30 UTC 2019\",\"Type\":\"Antminer S9 Hiveon\"}{\"STATS\":0,\"ID\":\"BC50\",\"Elapsed\":136,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"11813.91\",\"GHS av\":11793.28,\"miner_count\":3,\"frequency\":\"550\",\"fan_num\":2,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":3360,\"fan6\":4440,\"fan7\":0,\"fan8\":0,\"temp_num\":3,\"temp1\":0,\"temp2\":0,\"temp3\":0,\"temp4\":0,\"temp5\":0,\"temp6\":62,\"temp7\":54,\"temp8\":60,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":0,\"temp2_2\":0,\"temp2_3\":0,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":77,\"temp2_7\":69,\"temp2_8\":75,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":0,\"temp3_2\":0,\"temp3_3\":0,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"freq_avg1\":0.00,\"freq_avg2\":0.00,\"freq_avg3\":0.00,\"freq_avg4\":0.00,\"freq_avg5\":0.00,\"freq_avg6\":550.00,\"freq_avg7\":550.00,\"freq_avg8\":550.00,\"freq_avg9\":0.00,\"freq_avg10\":0.00,\"freq_avg11\":0.00,\"freq_avg12\":0.00,\"freq_avg13\":0.00,\"freq_avg14\":0.00,\"freq_avg15\":0.00,\"freq_avg16\":0.00,\"total_rateideal\":11850.30,\"total_freqavg\":550.00,\"total_acn\":189,\"total_rate\":11813.91,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":3950.10,\"chain_rateideal7\":3950.10,\"chain_rateideal8\":3950.10,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":77,\"Device Hardware%\":0.0000,\"no_matching_work\":0,\"chain_acn1\":0,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":63,\"chain_acn7\":63,\"chain_acn8\":63,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\"\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs7\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs8\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"\",\"chain_rate2\":\"\",\"chain_rate3\":\"\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"3967.89\",\"chain_rate7\":\"3955.87\",\"chain_rate8\":\"3890.15\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime6\":\"{}\",\"chain_xtime7\":\"{}\",\"chain_xtime8\":\"{}\",\"chain_offside_6\":\"0\",\"chain_offside_7\":\"0\",\"chain_offside_8\":\"0\",\"chain_opencore_6\":\"0\",\"chain_opencore_7\":\"0\",\"chain_opencore_8\":\"0\",\"miner_version\":\"30.0.1.3\",\"chain_power1\":0.00,\"chain_power2\":0.00,\"chain_power3\":0.00,\"chain_power4\":0.00,\"chain_power5\":0.00,\"chain_power6\":363.00,\"chain_power7\":363.00,\"chain_power8\":363.00,\"chain_power9\":0.00,\"chain_power10\":0.00,\"chain_power11\":0.00,\"chain_power12\":0.00,\"chain_power13\":0.00,\"chain_power14\":0.00,\"chain_power15\":0.00,\"chain_power16\":0.00,\"chain_power\":\"1089.00 (AB)\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765846,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 1.0.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://xxxxxx:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":9487,\"Accepted\":86241,\"Rejected\":411,\"Discarded\":136597,\"Stale\":116,\"Get Failures\":5,\"Remote Failures\":4,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"0:00:03\",\"Diff\":\"39.5K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":3210701589.00000000,\"Difficulty Rejected\":17465628.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":39491.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxxxxx\",\"Has GBT\":false,\"Best Share\":1431836158,\"Pool Rejected%\":0.5410,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://xxxxxx:3333\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":8879,\"Accepted\":63,\"Rejected\":0,\"Discarded\":86,\"Stale\":2,\"Get Failures\":55,\"Remote Failures\":0,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"66:18:15\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":1901924.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":81558.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"stratum.slushpool.com\",\"Has GBT\":false,\"Best Share\":2237539,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"stratum+tcp://stratum.slushpool.com:3333\",\"Status\":\"Alive\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":8878,\"Accepted\":196,\"Rejected\":5,\"Discarded\":193,\"Stale\":4,\"Get Failures\":51,\"Remote Failures\":0,\"User\":\"CosmosCapitalFund.COSMOSxCNKxS17Px00001\",\"Last Share Time\":\"66:29:37\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":3690626.00000000,\"Difficulty Rejected\":40960.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":28756.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"xxxxxx\",\"Has GBT\":false,\"Best Share\":1433415,\"Pool Rejected%\":1.0977,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                true
                        },
                        {
                                // Antminer S9 (NiceHash)
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
                                                        "\"user\" : \"antminer_1\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"W:0/0\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-freq\" : \"650\",\n" +
                                                        "\"bitmain-minhr\" : \"0\",\n" +
                                                        "\"altdfno\" : \"0\",\n" +
                                                        "\"bitmain-autodownscale-hw\" : \"0\",\n" +
                                                        "\"bitmain-maxx\" : \"0\",\n" +
                                                        "\"bitmain-autodownscale-timer\" : \"1\",\n" +
                                                        "\"bitmain-autodownscale-after\" : \"120\",\n" +
                                                        "\"bitmain-autodownscale-step\" : \"2\",\n" +
                                                        "\"bitmain-autodownscale-min\" : \"650\",\n" +
                                                        "\"bitmain-autodownscale-prec\" : \"75\",\n" +
                                                        "\"bitmain-autodownscale-profile\" : \"0\",\n" +
                                                        "\"bitmain-freq1\" : \"0\",\n" +
                                                        "\"bitmain-freq2\" : \"0\",\n" +
                                                        "\"bitmain-freq3\" : \"0\",\n" +
                                                        "\"bitmain-voltage\" : \"890\",\n" +
                                                        "\"bitmain-voltage1\" : \"0\",\n" +
                                                        "\"bitmain-voltage2\" : \"0\",\n" +
                                                        "\"bitmain-voltage3\" : \"0\",\n" +
                                                        "\"bitmain-chip-freq\" : \"0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0\",\n" +
                                                        "\"bitmain-fan-rpm-off\" : \"0\",\n" +
                                                        "\"bitmain-trigger-reboot\" : \"0\",\n" +
                                                        "\"multi-version\" : \"1\"\n" +
                                                        "}",
                                                AntminerChangePoolsITest::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "_ant_pool1url=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&_ant_pool1user=my-test-username1&_ant_pool1pw=my-test-password1&_ant_pool2url=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&_ant_pool2user=my-test-username2&_ant_pool2pw=my-test-password2&_ant_pool3url=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&_ant_pool3user=my-test-username3&_ant_pool3pw=my-test-password3&_ant_nobeeper=false&_ant_notempoverctrl=false&_ant_fan_customize_switch=false&_ant_fan_customize_value=&_ant_freq=650",
                                                "ok",
                                                AntminerChangePoolsITest::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600299177,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.11.1\"}],\"VERSION\":[{\"CGMiner\":\"4.11.1\",\"API\":\"3.7\",\"Miner\":\"26.0.1.3\",\"CompileTime\":\"Fri May  8 15:27:59 CST 2020\",\"Type\":\"Antminer S9 (vnish 3.8.6)\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600299244,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.11.1\"}],\"STATS\":[{\"Cgminer\":\"4.11.1\",\"Miner\":\"26.0.1.3\",\"CompileTime\":\"Fri May  8 15:27:59 CST 2020\",\"Type\":\"Antminer S9 (vnish 3.8.6)\"}{\"STATS\":0,\"ID\":\"BC50\",\"Elapsed\":3617,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"12795.48\",\"GHS av\":12900.71,\"miner_count\":3,\"frequency\":\"600\",\"fan_num\":2,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":5400,\"fan6\":5880,\"fan7\":0,\"fan8\":0,\"temp_num\":3,\"temp1\":0,\"temp2\":0,\"temp3\":0,\"temp4\":0,\"temp5\":0,\"temp6\":65,\"temp7\":56,\"temp8\":63,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":0,\"temp2_2\":0,\"temp2_3\":0,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":80,\"temp2_7\":71,\"temp2_8\":78,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":0,\"temp3_2\":0,\"temp3_3\":0,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"freq_avg1\":0.00,\"freq_avg2\":0.00,\"freq_avg3\":0.00,\"freq_avg4\":0.00,\"freq_avg5\":0.00,\"freq_avg6\":600.00,\"freq_avg7\":600.00,\"freq_avg8\":600.00,\"freq_avg9\":0.00,\"freq_avg10\":0.00,\"freq_avg11\":0.00,\"freq_avg12\":0.00,\"freq_avg13\":0.00,\"freq_avg14\":0.00,\"freq_avg15\":0.00,\"freq_avg16\":0.00,\"total_rateideal\":12927.60,\"total_freqavg\":600.00,\"total_acn\":189,\"total_rate\":12795.47,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":4309.20,\"chain_rateideal7\":4309.20,\"chain_rateideal8\":4309.20,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":80,\"Device Hardware%\":0.0034,\"no_matching_work\":297,\"chain_acn1\":0,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":63,\"chain_acn7\":63,\"chain_acn8\":63,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\"\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs7\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs8\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo ooooooo\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":1,\"chain_hw7\":3,\"chain_hw8\":293,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"\",\"chain_rate2\":\"\",\"chain_rate3\":\"\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"4264.48\",\"chain_rate7\":\"4298.32\",\"chain_rate8\":\"4232.67\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_bchips6\":\"Warm up\",\"chain_bchips7\":\"Warm up\",\"chain_bchips8\":\"Warm up\",\"chain_xtime6\":\"{}\",\"chain_xtime7\":\"{}\",\"chain_xtime8\":\"{}\",\"chain_offside_6\":\"0\",\"chain_offside_7\":\"0\",\"chain_offside_8\":\"0\",\"chain_opencore_6\":\"0\",\"chain_opencore_7\":\"0\",\"chain_opencore_8\":\"0\",\"chain_vol6\":920,\"chain_vol7\":920,\"chain_vol8\":920,\"chain_consumption6\":495,\"chain_consumption7\":495,\"chain_consumption8\":495,\"miner_version\":\"26.0.1.3\",\"manual_fan_mode\":false,\"build_version\":\"3.8.6\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600299268,\"Code\":7,\"Msg\":\"6 Pool(s)\",\"Description\":\"cgminer 4.11.1\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://solo.antpool.com:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":81,\"Accepted\":135,\"Rejected\":0,\"Discarded\":3311,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"antminer_1\",\"Last Share Time\":\"0:00:03\",\"Diff\":\"65.5K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":8650752.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"solo.antpool.com\",\"Has GBT\":false,\"Best Share\":47581166,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Boost\":\"Normal\"},{\"POOL\":1,\"URL\":\"stratum+tcp://stratum.antpool.com:3333\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":2,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"antminer_1\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Boost\":\"Normal\"},{\"POOL\":2,\"URL\":\"stratum+tcp://cn.ss.btc.com:3333\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"antminer.1\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Boost\":\"Normal\"},{\"POOL\":3,\"URL\":\"DevFee\",\"Status\":\"Alive\",\"Priority\":993,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":132,\"Accepted\":166,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"DevFee\",\"Last Share Time\":\"0:03:06\",\"Diff\":\"512\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":84992.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":512.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":25363,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Boost\":\"Normal\"}],\"id\":1}")),
                                true
                        },
                        {
                                // Antminer S9 (asicseer)
                                Collections.emptyMap(),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1600709575,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 2.4.0-asicseer-1.1.9-f7b7\"}],\"VERSION\":[{\"BMMiner\":\"2.4.0-asicseer-1.1.9-f7b7\",\"Miner\":\"30.0.1.3\",\"CompileTime\":\"Mon Nov 11 17:07:20 CST 2019\",\"Type\":\"Antminer S9\",\"API\":\"3.1\"}],\"id\":1}")),
                                false
                        },
                        {
                                // Antminer S19 Pro
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:3333\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:25\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:443\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-fan-ctrl\" : false,\n" +
                                                        "\"bitmain-fan-pwm\" : \"100\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-freq\" : \"500\",\n" +
                                                        "\"bitmain-voltage\" : \"1280\",\n" +
                                                        "\"bitmain-ccdelay\" : \"1\",\n" +
                                                        "\"bitmain-pwth\" : \"1\",\n" +
                                                        "\"bitmain-work-mode\" : \"0\",\n" +
                                                        "\"bitmain-freq-level\" : \"100\"\n" +
                                                        "}",
                                                AntminerChangePoolsITest::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "{\"bitmain-fan-ctrl\":false,\"bitmain-fan-pwm\":\"100\",\"miner-mode\":\"0\",\"freq-level\":\"100\",\"pools\":[{\"url\":\"stratum+tcp://my-test-pool1.com:5588\",\"user\":\"my-test-username1\",\"pass\":\"my-test-password1\"},{\"url\":\"stratum+tcp://my-test-pool2.com:5588\",\"user\":\"my-test-username2\",\"pass\":\"my-test-password2\"},{\"url\":\"stratum+tcp://my-test-pool3.com:5588\",\"user\":\"my-test-username3\",\"pass\":\"my-test-password3\"}]}",
                                                "ok",
                                                AntminerChangePoolsITest::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer S19 Pro\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315384,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"CGMiner\":\"4.9.0\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}{\"STATS\":0,\"ID\":\"L30\",\"Elapsed\":393983,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"504.56\",\"GHS av\":500.28,\"miner_count\":4,\"frequency\":\"384\",\"fan_num\":2,\"fan1\":3840,\"fan2\":3810,\"temp_num\":4,\"temp1\":46,\"temp2\":46,\"temp3\":46,\"temp4\":45,\"temp2_1\":54,\"temp2_2\":56,\"temp2_3\":56,\"temp2_4\":53,\"temp31\":0,\"temp32\":0,\"temp33\":0,\"temp34\":0,\"temp4_1\":0,\"temp4_2\":0,\"temp4_3\":0,\"temp4_4\":0,\"temp_max\":46,\"Device Hardware%\":0.0000,\"no_matching_work\":152,\"chain_acn1\":72,\"chain_acn2\":72,\"chain_acn3\":72,\"chain_acn4\":72,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs4\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":153,\"chain_hw4\":0,\"chain_rate1\":\"126.12\",\"chain_rate2\":\"125.79\",\"chain_rate3\":\"126.06\",\"chain_rate4\":\"126.59\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315222,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us.litecoinpool.org:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":24933,\"Accepted\":47384,\"Rejected\":212,\"Discarded\":230740,\"Stale\":15,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"obmllc.l3_1\",\"Last Share Time\":\"0:00:23\",\"Diff\":\"65.5K\",\"Diff1 Shares\":11805080,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":2988769280.00000000,\"Difficulty Rejected\":13254656.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us.litecoinpool.org\",\"Has GBT\":false,\"Best Share\":11224839560,\"Pool Rejected%\":0.4415,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                true
                        },
                        {
                                // Antminer S19 Pro (missing fan ctrl)
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:3333\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:25\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:443\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-fan-ctrl\" : ,\n" +
                                                        "\"bitmain-fan-pwm\" : \"\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-freq\" : \"500\",\n" +
                                                        "\"bitmain-voltage\" : \"1280\",\n" +
                                                        "\"bitmain-ccdelay\" : \"1\",\n" +
                                                        "\"bitmain-pwth\" : \"1\",\n" +
                                                        "\"bitmain-work-mode\" : \"\",\n" +
                                                        "\"bitmain-freq-level\" : \"\"" +
                                                        "}",
                                                AntminerChangePoolsITest::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "{\"bitmain-fan-ctrl\":false,\"bitmain-fan-pwm\":\"100\",\"miner-mode\":\"0\",\"freq-level\":\"100\",\"pools\":[{\"url\":\"stratum+tcp://my-test-pool1.com:5588\",\"user\":\"my-test-username1\",\"pass\":\"my-test-password1\"},{\"url\":\"stratum+tcp://my-test-pool2.com:5588\",\"user\":\"my-test-username2\",\"pass\":\"my-test-password2\"},{\"url\":\"stratum+tcp://my-test-pool3.com:5588\",\"user\":\"my-test-username3\",\"pass\":\"my-test-password3\"}]}",
                                                "ok",
                                                AntminerChangePoolsITest::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer S19 Pro\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315384,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.9.0\"}],\"STATS\":[{\"CGMiner\":\"4.9.0\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}{\"STATS\":0,\"ID\":\"L30\",\"Elapsed\":393983,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"504.56\",\"GHS av\":500.28,\"miner_count\":4,\"frequency\":\"384\",\"fan_num\":2,\"fan1\":3840,\"fan2\":3810,\"temp_num\":4,\"temp1\":46,\"temp2\":46,\"temp3\":46,\"temp4\":45,\"temp2_1\":54,\"temp2_2\":56,\"temp2_3\":56,\"temp2_4\":53,\"temp31\":0,\"temp32\":0,\"temp33\":0,\"temp34\":0,\"temp4_1\":0,\"temp4_2\":0,\"temp4_3\":0,\"temp4_4\":0,\"temp_max\":46,\"Device Hardware%\":0.0000,\"no_matching_work\":152,\"chain_acn1\":72,\"chain_acn2\":72,\"chain_acn3\":72,\"chain_acn4\":72,\"chain_acs1\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs2\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs3\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_acs4\":\" oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo oooooooo\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":153,\"chain_hw4\":0,\"chain_rate1\":\"126.12\",\"chain_rate2\":\"125.79\",\"chain_rate3\":\"126.06\",\"chain_rate4\":\"126.59\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315222,\"Code\":7,\"Msg\":\"3 Pool(s)\",\"Description\":\"cgminer 4.9.0\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://us.litecoinpool.org:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":24933,\"Accepted\":47384,\"Rejected\":212,\"Discarded\":230740,\"Stale\":15,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"obmllc.l3_1\",\"Last Share Time\":\"0:00:23\",\"Diff\":\"65.5K\",\"Diff1 Shares\":11805080,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":2988769280.00000000,\"Difficulty Rejected\":13254656.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":65536.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"us.litecoinpool.org\",\"Has GBT\":false,\"Best Share\":11224839560,\"Pool Rejected%\":0.4415,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}")),
                                true
                        },
                        {
                                // Antminer S17 (asicdip)
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://sha256.eu.mine.zpool.ca:3333\",\n" +
                                                        "\"user\" : \"xxx.S17\",\n" +
                                                        "\"pass\" : \"c=BTC\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"\",\n" +
                                                        "\"user\" : \"\",\n" +
                                                        "\"pass\" : \"\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"\",\n" +
                                                        "\"user\" : \"\",\n" +
                                                        "\"pass\" : \"\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:chip:chiphw:devs:summary:version:dosleep:switchpool\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:W:127.0.0.1/24\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-freq\" : \"565\",\n" +
                                                        "\"bitmain-minhr\" : \"0\",\n" +
                                                        "\"bitmain-tempoff\" : \"0\",\n" +
                                                        "\"altdfno\" : \"1\",\n" +
                                                        "\"bitmain-autotune-profile\" : \"disabled\",\n" +
                                                        "\"bitmain-autofix-period\" : \"0\",\n" +
                                                        "\"lower-profile-if-temp-above\" : \"85\",\n" +
                                                        "\"lower-profile-if-fan-pwm-above\" : \"90\",\n" +
                                                        "\"raise-profile-if-fan-pwm-below\" : \"50\",\n" +
                                                        "\"raise-profile-if-temp-below\" : \"60\",\n" +
                                                        "\"raise-profile-up-to\" : \"disabled\",\n" +
                                                        "\"bitmain-trigger-reboot\" : \"0\",\n" +
                                                        "\"bitmain-freq1\" : \"565\",\n" +
                                                        "\"bitmain-freq2\" : \"565\",\n" +
                                                        "\"bitmain-freq3\" : \"565\",\n" +
                                                        "\"bitmain-voltage\" : \"1930\",\n" +
                                                        "\"bitmain-chip-freq1\" : \"615:615:610:615:615:605:605:605:605:605:595:595:595:605:595:600:600:600:605:600:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615\",\n" +
                                                        "\"bitmain-chip-freq2\" : \"620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620:620\",\n" +
                                                        "\"bitmain-chip-freq3\" : \"615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:535:545:545:545:545:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615:615\",\n" +
                                                        "\"multi-version\" : \"4\"\n" +
                                                        "}",
                                                AntminerChangePoolsITest::validateDigest),
                                        "/cgi-bin/get_system_info.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"minertype\":\"Antminer S17+ (vnish 2.0.3)\",\n" +
                                                        "\"nettype\":\"DHCP\",\n" +
                                                        "\"netdevice\":\"eth0\",\n" +
                                                        "\"macaddr\":\"56:86:4D:32:D2:EF\",\n" +
                                                        "\"hostname\":\"S17\",\n" +
                                                        "\"ipaddress\":\"192.168.0.101\",\n" +
                                                        "\"netmask\":\"255.255.255.0\",\n" +
                                                        "\"gateway\":\"\",\n" +
                                                        "\"dnsservers\":\"\",\n" +
                                                        "\"curtime\":\"14:32:38\",\n" +
                                                        "\"uptime\":\"25\",\n" +
                                                        "\"loadaverage\":\"0.62, 0.18, 0.08\",\n" +
                                                        "\"mem_total\":\"233744\",\n" +
                                                        "\"mem_used\":\"113632\",\n" +
                                                        "\"mem_free\":\"120112\",\n" +
                                                        "\"mem_buffers\":\"764\",\n" +
                                                        "\"mem_cached\":\"0\",\n" +
                                                        "\"system_mode\":\"GNU/Linux\",\n" +
                                                        "\"ant_hwv\":\"35.0.1.3\",\n" +
                                                        "\"system_kernel_version\":\"Linux 4.6.0-xilinx-gff8137b-dirty #25 SMP PREEMPT Fri Nov 23 15:30:52 CST 2018\",\n" +
                                                        "\"system_filesystem_version\":\"Sat Aug 15 18:55:51 CST 2020\",\n" +
                                                        "\"bmminer_version\":\"4.11.1\"\n" +
                                                        "}",
                                                AntminerChangePoolsITest::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "_ant_pool1url=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&_ant_pool1user=my-test-username1&_ant_pool1pw=my-test-password1&_ant_pool2url=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&_ant_pool2user=my-test-username2&_ant_pool2pw=my-test-password2&_ant_pool3url=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&_ant_pool3user=my-test-username3&_ant_pool3pw=my-test-password3&_ant_nobeeper=false&_ant_notempoverctrl=false&_ant_fan_customize_switch=false&_ant_fan_customize_value=&_ant_freq=565",
                                                "ok",
                                                AntminerChangePoolsITest::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\":\"S\",\"When\":1605446911,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.11.1\"}],\"VERSION\": [{\"CGMiner\":\"4.11.1\",\"API\":\"3.7\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\":\"S\",\"When\":1605446911,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 4.11.1\"}],\"STATS\": [{\"Cgminer\":\"4.11.1\",\"Miner\":\"35.0.1.3\",\"CompileTime\":\"Sat Aug 15 18:55:51 CST 2020\",\"Type\":\"Antminer S17+ (vnish 2.0.3)\"},{\"STATS\":0,\"ID\":\"BTM_S17P_0\",\"Elapsed\":1201,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"77458.73\",\"GHS av\":77544.32,\"miner_count\":3,\"total_acn\":195,\"frequency\":565,\"total_freqavg\":610.26,\"total_rateideal\":79968.00,\"total_rate\":77458.73,\"state\":\"mining\",\"fan_num\":4,\"fan_mode\":0,\"fan_pwm\":100,\"fan1\":6120,\"fan2\":6120,\"fan3\":5160,\"fan4\":5160,\"temp_num\":3,\"temp1\":66,\"temp2\":65,\"temp3\":62,\"temp2_1\":81,\"temp2_2\":80,\"temp2_3\":76,\"temp3_1\":81,\"temp3_2\":80,\"temp3_3\":76,\"temp_pcb1\":\"55-66-51-60\",\"temp_pcb2\":\"55-65-52-64\",\"temp_pcb3\":\"51-62-51-62\",\"temp_chip1\":\"75-81-71-75\",\"temp_chip2\":\"74-80-71-80\",\"temp_chip3\":\"70-76-71-75\",\"chain_acn1\":65,\"chain_acn2\":65,\"chain_acn3\":65,\"chain_vol1\":19300,\"chain_vol2\":19300,\"chain_vol3\":19300,\"freq_avg1\":606.54,\"freq_avg2\":620.00,\"freq_avg3\":604.23,\"chain_rateideal1\":26493.60,\"chain_rateideal2\":27081.60,\"chain_rateideal3\":26392.80,\"chain_rate1\":24326.87,\"chain_rate2\":26800.28,\"chain_rate3\":26331.57,\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_acs1\":\"xxxxx ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo\",\"chain_acs2\":\"ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo\",\"chain_acs3\":\"ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo ooooo\",\"chain_consumption1\":991,\"chain_consumption2\":1013,\"chain_consumption3\":987,\"miner_version\":\"35.0.1.3\",\"build_version\":\"2.0.3\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\":\"S\",\"When\":1605446911,\"Code\":7,\"Msg\":\"5 Pool(s)\",\"Description\":\"cgminer 4.11.1\"}],\"POOLS\": [{\"POOL\":0,\"URL\":\"stratum+tcp://sha256.eu.mine.zpool.ca:3333\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":171,\"Accepted\":145,\"Rejected\":0,\"Works\":330851,\"Discarded\":971,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"xxx.S17\",\"Last Share Time\":\"0:00:01\",\"Diff\":\"149K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":19976288.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":149440.00000000,\"Work Difficulty\":149440.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"sha256.eu.mine.zpool.ca\",\"Stratum Difficulty\":149440.00000000,\"Has Vmask\":true,\"Has GBT\":false,\"Best Share\":165807125,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":177,\"Current Block Height\":358628,\"Current Block Version\":536870912},{\"POOL\":1,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":0},{\"POOL\":2,\"URL\":\"\",\"Status\":\"Dead\",\"Priority\":2,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":0,\"Accepted\":0,\"Rejected\":0,\"Works\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"\",\"Last Share Time\":\"0\",\"Diff\":\"\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Work Difficulty\":0.00000000,\"Has Stratum\":false,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":false,\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":0,\"Current Block Version\":0},{\"POOL\":3,\"URL\":\"DevFee\",\"Status\":\"Alive\",\"Priority\":3,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":39,\"Accepted\":2320,\"Rejected\":0,\"Works\":5747,\"Discarded\":0,\"Stale\":0,\"Get Failures\":1,\"Remote Failures\":0,\"User\":\"DevFee\",\"Last Share Time\":\"0:01:36\",\"Diff\":\"8.19K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":349888.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":4096.00000000,\"Work Difficulty\":8192.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":true,\"Has GBT\":false,\"Best Share\":100348,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":0,\"Current Block Height\":657040,\"Current Block Version\":536870912},{\"POOL\":4,\"URL\":\"DevFee\",\"Status\":\"Alive\",\"Priority\":4,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":60,\"Accepted\":84,\"Rejected\":0,\"Works\":2673,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"DevFee\",\"Last Share Time\":\"0:04:39\",\"Diff\":\"2.05K\",\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":172032.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":2048.00000000,\"Work Difficulty\":2048.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"\",\"Stratum Difficulty\":0.00000000,\"Has Vmask\":true,\"Has GBT\":false,\"Best Share\":289329,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000,\"Bad Work\":2,\"Current Block Height\":657040,\"Current Block Version\":536870912}],\"id\":1}")),
                                true
                        }
                });
    }

    /**
     * Validates the exchange digest.
     *
     * @param exchange The exchange.
     *
     * @return Whether or not the digest was validated.
     */
    private static boolean validateDigest(final HttpExchange exchange) {
        return AntminerTestUtils.validateDigest(
                exchange,
                "antMiner Configuration");
    }
}