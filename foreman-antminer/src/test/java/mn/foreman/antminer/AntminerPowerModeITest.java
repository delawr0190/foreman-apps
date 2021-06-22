package mn.foreman.antminer;

import mn.foreman.antminer.util.AntminerAsyncActionITest;
import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.util.TestUtils;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Test changing power modes on an Antminer. */
@RunWith(Parameterized.class)
public class AntminerPowerModeITest
        extends AntminerAsyncActionITest {

    /**
     * Constructor.
     *
     * @param httpHandlers The HTTP handlers.
     * @param rpcHandlers  The RPC handlers.
     * @param foundResult  The found result.
     * @param sleeping     Whether or not sleeping.
     */
    public AntminerPowerModeITest(
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, HandlerInterface> rpcHandlers,
            final boolean foundResult,
            final boolean sleeping) {
        super(
                TestUtils.toPoolJson(
                        ImmutableMap.of(
                                "webPort",
                                "8080",
                                "mode",
                                sleeping ? "sleeping" : "normal")),
                new AntminerFactory(1),
                new FirmwareAwareAction(
                        "antMiner Configuration",
                        new StockPowerModeAction(
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
                                        AntminerConfValue.FREQ,
                                        AntminerConfValue.VOLTAGE),
                                new ObjectMapper()),
                        new BraiinsPowerModeAction()),
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
                                // Antminer S19
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "\n" +
                                                        "\n" +
                                                        "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"aaa:700\",\n" +
                                                        "\"user\" : \"aaa.226x94\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"bbb:6000\",\n" +
                                                        "\"user\" : \"bbb.226x94\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"ccc:443\",\n" +
                                                        "\"user\" : \"ccc.226x94\",\n" +
                                                        "\"pass\" : \"x\"\n" +
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
                                                        "\"bitmain-freq\" : \"675\",\n" +
                                                        "\"bitmain-voltage\" : \"1400\",\n" +
                                                        "\"bitmain-ccdelay\" : \"0\",\n" +
                                                        "\"bitmain-pwth\" : \"0\",\n" +
                                                        "\"bitmain-work-mode\" : \"1\",\n" +
                                                        "\"bitmain-freq-level\" : \"100\"\n" +
                                                        "}\n",
                                                AntminerTestUtils::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "{\"freq-level\":\"100\",\"miner-mode\":1,\"bitmain-fan-pwm\":\"100\",\"bitmain-fan-ctrl\":false,\"pools\":[{\"url\":\"aaa:700\",\"user\":\"aaa.226x94\",\"pass\":\"x\"},{\"url\":\"bbb:6000\",\"user\":\"bbb.226x94\",\"pass\":\"x\"},{\"url\":\"ccc:443\",\"user\":\"ccc.226x94\",\"pass\":\"x\"}]}",
                                                "ok",
                                                AntminerTestUtils::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1621211214, \"Code\": 22, \"Msg\": \"CGMiner versions\", \"Description\": \"cgminer 1.0.0\"}], \"VERSION\": [{\"BMMiner\": \"1.0.0\", \"API\": \"3.1\", \"Miner\": \"49.0.1.3\", \"CompileTime\": \"Fri Dec 11 11:15:40 CST 2020\", \"Type\": \"Antminer S19\"}], \"id\": 1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1621205793, \"Code\": 70, \"Msg\": \"CGMiner stats\", \"Description\": \"cgminer 1.0.0\"}], \"STATS\": [{\"BMMiner\": \"1.0.0\", \"Miner\": \"49.0.1.3\", \"CompileTime\": \"Fri Dec 11 11:15:40 CST 2020\", \"Type\": \"Antminer S19\"}, {\"STATS\": 0, \"ID\": \"BTM_SOC0\", \"Elapsed\": 735, \"Calls\": 0, \"Wait\": 0, \"Max\": 0, \"Min\": 99999999, \"GHS 5s\": 0.0, \"GHS av\": 0.0, \"rate_30m\": 0.0, \"Mode\": 2, \"miner_count\": 2, \"frequency\": 675, \"fan_num\": 4, \"fan1\": 1560, \"fan2\": 1560, \"fan3\": 600, \"fan4\": 720, \"temp_num\": 2, \"temp2\": 40, \"temp2_2\": 40, \"temp3\": 40, \"temp2_3\": 40, \"temp_pcb1\": \"0-0-0-0\", \"temp_pcb2\": \"39-37-40-39\", \"temp_pcb3\": \"38-38-40-40\", \"temp_pcb4\": \"0-0-0-0\", \"temp_chip1\": \"0-0-0-0\", \"temp_chip2\": \"39-37-40-39\", \"temp_chip3\": \"38-38-40-40\", \"temp_chip4\": \"0-0-0-0\", \"temp_pic1\": \"0-0-0-0\", \"temp_pic2\": \"39-37-40-39\", \"temp_pic3\": \"38-38-40-40\", \"temp_pic4\": \"0-0-0-0\", \"total_rateideal\": 63919.0, \"rate_unit\": \"GH\", \"total_freqavg\": 675, \"total_acn\": 152, \"total rate\": 0.0, \"temp_max\": 0, \"no_matching_work\": 0, \"chain_acn1\": 0, \"chain_acn2\": 76, \"chain_acn3\": 76, \"chain_acn4\": 0, \"chain_acs1\": \"\", \"chain_acs2\": \" xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx\", \"chain_acs3\": \" xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx\", \"chain_acs4\": \"\", \"chain_hw1\": 0, \"chain_hw2\": 0, \"chain_hw3\": 0, \"chain_hw4\": 0, \"chain_rate1\": \"\", \"chain_rate2\": \"0.00\", \"chain_rate3\": \"0.00\", \"chain_rate4\": \"\", \"freq1\": 0, \"freq2\": 675, \"freq3\": 675, \"freq4\": 0, \"miner_version\": \"49.0.1.3\", \"miner_id\": \"803c2c867510481c\"}], \"id\": 1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1621205615, \"Code\": 7, \"Msg\": \"3 Pool(s)\", \"Description\": \"cgminer 1.0.0\"}], \"POOLS\": [{\"POOL\": 0, \"URL\": \"stratum+tcp://aaa:700\", \"Status\": \"Alive\", \"Priority\": 0, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 22, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 296, \"Stale\": 0, \"Get Failures\": 2, \"Remote Failures\": 0, \"User\": \"aaa.226x94\", \"Last Share Time\": \"0\", \"Diff\": \"32.8K\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": true, \"Stratum URL\": \"aaa\", \"Has GBT\": false, \"Best Share\": 0.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}, {\"POOL\": 1, \"URL\": \"stratum+tcp://bbb:6000\", \"Status\": \"Alive\", \"Priority\": 1, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 5, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 2, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"bbb.226x94\", \"Last Share Time\": \"0\", \"Diff\": \"42K\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": 0.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}, {\"POOL\": 2, \"URL\": \"stratum+tcp://ccc:443\", \"Status\": \"Deed\", \"Priority\": 2, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 0, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"ccc.226x94\", \"Last Share Time\": \"0\", \"Diff\": \"\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": 0.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}], \"id\": 1}")),
                                true,
                                true
                        },
                        {
                                // Antminer S17 (sleeping)
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "\n" +
                                                        "\n" +
                                                        "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"aaa:700\",\n" +
                                                        "\"user\" : \"aaa.226x94\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"bbb:6000\",\n" +
                                                        "\"user\" : \"bbb.226x94\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"ccc:443\",\n" +
                                                        "\"user\" : \"ccc.226x94\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-work-mode\" : \"0\",\n" +
                                                        "\"bitmain-freq\" : \"O\",\n" +
                                                        "\"bitmain-voltage\" : \"1950\"" +
                                                        "}\n",
                                                AntminerTestUtils::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "_ant_pool1url=aaa%3A700&_ant_pool1user=aaa.226x94&_ant_pool1pw=x&_ant_pool2url=bbb%3A6000&_ant_pool2user=bbb.226x94&_ant_pool2pw=x&_ant_pool3url=ccc%3A443&_ant_pool3user=ccc.226x94&_ant_pool3pw=x&_ant_nobeeper=false&_ant_notempoverctrl=false&_ant_fan_customize_switch=false&_ant_fan_customize_value=&_ant_freq=O&_ant_voltage=1950&_ant_work_mode=254",
                                                "",
                                                AntminerTestUtils::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1623519365,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"1.0.0\",\"API\":\"3.1\",\"Miner\":\"b023.0.1.3\",\"CompileTime\":\"Tue Jun  2 10:11:10 CST 2020\",\"Type\":\"Antminer T17+\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1623519389,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 1.0.0\"}],\"STATS\":[{\"BMMiner\":\"1.0.0\",\"Miner\":\"b023.0.1.3\",\"CompileTime\":\"Tue Jun  2 10:11:10 CST 2020\",\"Type\":\"Antminer T17+\"},{\"STATS\":0,\"ID\":\"BTM_SOC0\",\"Elapsed\":8105,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"0.00\",\"GHS av\":0.00,\"GHS 30m\":0.00,\"Mode\":254,\"Ex Hash Rate\":0,\"miner_count\":2,\"frequency\":\"\",\"fan_num\":0,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":0,\"fan6\":0,\"fan7\":0,\"fan8\":0,\"fan9\":0,\"fan10\":0,\"fan11\":0,\"fan12\":0,\"fan13\":0,\"fan14\":0,\"fan15\":0,\"fan16\":0,\"temp_num\":2,\"temp1\":0,\"temp2\":0,\"temp3\":0,\"temp4\":0,\"temp5\":0,\"temp6\":0,\"temp7\":0,\"temp8\":0,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":0,\"temp2_2\":0,\"temp2_3\":0,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":0,\"temp2_7\":0,\"temp2_8\":0,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":0,\"temp3_2\":0,\"temp3_3\":0,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"temp_pcb1\":\"0-0-0-0\",\"temp_pcb2\":\"0-0-0-0\",\"temp_pcb3\":\"0-0-0-0\",\"temp_pcb4\":\"0-0-0-0\",\"temp_pcb5\":\"0-0-0-0\",\"temp_pcb6\":\"0-0-0-0\",\"temp_pcb7\":\"0-0-0-0\",\"temp_pcb8\":\"0-0-0-0\",\"temp_pcb9\":\"0-0-0-0\",\"temp_pcb10\":\"0-0-0-0\",\"temp_pcb11\":\"0-0-0-0\",\"temp_pcb12\":\"0-0-0-0\",\"temp_pcb13\":\"0-0-0-0\",\"temp_pcb14\":\"0-0-0-0\",\"temp_pcb15\":\"0-0-0-0\",\"temp_pcb16\":\"0-0-0-0\",\"temp_chip1\":\"0-0-0-0\",\"temp_chip2\":\"0-0-0-0\",\"temp_chip3\":\"0-0-0-0\",\"temp_chip4\":\"0-0-0-0\",\"temp_chip5\":\"0-0-0-0\",\"temp_chip6\":\"0-0-0-0\",\"temp_chip7\":\"0-0-0-0\",\"temp_chip8\":\"0-0-0-0\",\"temp_chip9\":\"0-0-0-0\",\"temp_chip10\":\"0-0-0-0\",\"temp_chip11\":\"0-0-0-0\",\"temp_chip12\":\"0-0-0-0\",\"temp_chip13\":\"0-0-0-0\",\"temp_chip14\":\"0-0-0-0\",\"temp_chip15\":\"0-0-0-0\",\"temp_chip16\":\"0-0-0-0\",\"total_rateideal\":0.00,\"rate_unit\":\"GH\",\"total_freqavg\":0.00,\"total_acn\":0,\"total_rate\":0.00,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":0.00,\"chain_rateideal7\":0.00,\"chain_rateideal8\":0.00,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":0,\"no_matching_work\":0,\"chain_acn1\":0,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":0,\"chain_acn7\":0,\"chain_acn8\":0,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\"\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\"\",\"chain_acs7\":\"\",\"chain_acs8\":\"\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"\",\"chain_rate2\":\"\",\"chain_rate3\":\"\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"\",\"chain_rate7\":\"\",\"chain_rate8\":\"\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime2\":\"{}\",\"chain_xtime3\":\"{}\",\"chain_offside_2\":\"\",\"chain_offside_3\":\"\",\"chain_opencore_2\":\"1\",\"chain_opencore_3\":\"1\",\"chain_SN2\":\"unknow\",\"chain_SN3\":\"unknow\",\"freq1\":0,\"freq2\":0,\"freq3\":0,\"freq4\":0,\"freq5\":0,\"freq6\":0,\"freq7\":0,\"freq8\":0,\"freq9\":0,\"freq10\":0,\"freq11\":0,\"freq12\":0,\"freq13\":0,\"freq14\":0,\"freq15\":0,\"freq16\":0,\"miner_version\":\"b023.0.1.3\",\"miner_id\":\"8158b42429e0481c\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1621338564, \"Code\": 7, \"Msg\": \"3 Pool(s)\", \"Description\": \"cgminer 1.0.0\"}], \"POOLS\": [{\"POOL\": 0, \"URL\": \"stratum+tcp://aaa.com:9999#xnsub\", \"Status\": \"Alive\", \"Priority\": 0, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 22134, \"Accepted\": 100129, \"Rejected\": 31, \"Discarded\": 183393, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"aaa.bbb\", \"Last Share Time\": \"14:34:59\", \"Diff\": \"8.19K\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 6993665950.0, \"Difficulty Rejected\": 2184386.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 76206.0, \"Has Stratum\": true, \"Stratum Active\": true, \"Stratum URL\": \"aaa.com\", \"Has GBT\": false, \"Best Share\": 2333874959.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}, {\"POOL\": 1, \"URL\": \"stratum+tcp://aaa.com:9999#xnsub\", \"Status\": \"Alive\", \"Priority\": 1, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 1, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"aaa.bbb\", \"Last Share Time\": \"0\", \"Diff\": \"\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": 0.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}, {\"POOL\": 2, \"URL\": \"stratum+tcp://aaa.com:9999#xnsub\", \"Status\": \"Alive\", \"Priority\": 2, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 22119, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"aaa.bbb\", \"Last Share Time\": \"0\", \"Diff\": \"\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": true, \"Stratum URL\": \"aaa.com\", \"Has GBT\": false, \"Best Share\": 0.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}], \"id\": 1}")),
                                true,
                                true
                        },
                        {
                                // Antminer S17 (normal)
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "\n" +
                                                        "\n" +
                                                        "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"aaa:700\",\n" +
                                                        "\"user\" : \"aaa.226x94\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"bbb:6000\",\n" +
                                                        "\"user\" : \"bbb.226x94\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"ccc:443\",\n" +
                                                        "\"user\" : \"ccc.226x94\",\n" +
                                                        "\"pass\" : \"x\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-work-mode\" : \"0\",\n" +
                                                        "\"bitmain-freq\" : \"O\",\n" +
                                                        "\"bitmain-voltage\" : \"1950\"" +
                                                        "}\n",
                                                AntminerTestUtils::validateDigest),
                                        "/cgi-bin/set_miner_conf.cgi",
                                        new HttpHandler(
                                                "_ant_pool1url=aaa%3A700&_ant_pool1user=aaa.226x94&_ant_pool1pw=x&_ant_pool2url=bbb%3A6000&_ant_pool2user=bbb.226x94&_ant_pool2pw=x&_ant_pool3url=ccc%3A443&_ant_pool3user=ccc.226x94&_ant_pool3pw=x&_ant_nobeeper=false&_ant_notempoverctrl=false&_ant_fan_customize_switch=false&_ant_fan_customize_value=&_ant_freq=O&_ant_voltage=1950&_ant_work_mode=0",
                                                "",
                                                AntminerTestUtils::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1623519365,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"1.0.0\",\"API\":\"3.1\",\"Miner\":\"b023.0.1.3\",\"CompileTime\":\"Tue Jun  2 10:11:10 CST 2020\",\"Type\":\"Antminer T17+\"}],\"id\":1}"),
                                        "{\"command\":\"stats\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1623519389,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"cgminer 1.0.0\"}],\"STATS\":[{\"BMMiner\":\"1.0.0\",\"Miner\":\"b023.0.1.3\",\"CompileTime\":\"Tue Jun  2 10:11:10 CST 2020\",\"Type\":\"Antminer T17+\"},{\"STATS\":0,\"ID\":\"BTM_SOC0\",\"Elapsed\":8105,\"Calls\":0,\"Wait\":0.000000,\"Max\":0.000000,\"Min\":99999999.000000,\"GHS 5s\":\"0.00\",\"GHS av\":0.00,\"GHS 30m\":0.00,\"Mode\":254,\"Ex Hash Rate\":0,\"miner_count\":2,\"frequency\":\"\",\"fan_num\":0,\"fan1\":0,\"fan2\":0,\"fan3\":0,\"fan4\":0,\"fan5\":0,\"fan6\":0,\"fan7\":0,\"fan8\":0,\"fan9\":0,\"fan10\":0,\"fan11\":0,\"fan12\":0,\"fan13\":0,\"fan14\":0,\"fan15\":0,\"fan16\":0,\"temp_num\":2,\"temp1\":0,\"temp2\":0,\"temp3\":0,\"temp4\":0,\"temp5\":0,\"temp6\":0,\"temp7\":0,\"temp8\":0,\"temp9\":0,\"temp10\":0,\"temp11\":0,\"temp12\":0,\"temp13\":0,\"temp14\":0,\"temp15\":0,\"temp16\":0,\"temp2_1\":0,\"temp2_2\":0,\"temp2_3\":0,\"temp2_4\":0,\"temp2_5\":0,\"temp2_6\":0,\"temp2_7\":0,\"temp2_8\":0,\"temp2_9\":0,\"temp2_10\":0,\"temp2_11\":0,\"temp2_12\":0,\"temp2_13\":0,\"temp2_14\":0,\"temp2_15\":0,\"temp2_16\":0,\"temp3_1\":0,\"temp3_2\":0,\"temp3_3\":0,\"temp3_4\":0,\"temp3_5\":0,\"temp3_6\":0,\"temp3_7\":0,\"temp3_8\":0,\"temp3_9\":0,\"temp3_10\":0,\"temp3_11\":0,\"temp3_12\":0,\"temp3_13\":0,\"temp3_14\":0,\"temp3_15\":0,\"temp3_16\":0,\"temp_pcb1\":\"0-0-0-0\",\"temp_pcb2\":\"0-0-0-0\",\"temp_pcb3\":\"0-0-0-0\",\"temp_pcb4\":\"0-0-0-0\",\"temp_pcb5\":\"0-0-0-0\",\"temp_pcb6\":\"0-0-0-0\",\"temp_pcb7\":\"0-0-0-0\",\"temp_pcb8\":\"0-0-0-0\",\"temp_pcb9\":\"0-0-0-0\",\"temp_pcb10\":\"0-0-0-0\",\"temp_pcb11\":\"0-0-0-0\",\"temp_pcb12\":\"0-0-0-0\",\"temp_pcb13\":\"0-0-0-0\",\"temp_pcb14\":\"0-0-0-0\",\"temp_pcb15\":\"0-0-0-0\",\"temp_pcb16\":\"0-0-0-0\",\"temp_chip1\":\"0-0-0-0\",\"temp_chip2\":\"0-0-0-0\",\"temp_chip3\":\"0-0-0-0\",\"temp_chip4\":\"0-0-0-0\",\"temp_chip5\":\"0-0-0-0\",\"temp_chip6\":\"0-0-0-0\",\"temp_chip7\":\"0-0-0-0\",\"temp_chip8\":\"0-0-0-0\",\"temp_chip9\":\"0-0-0-0\",\"temp_chip10\":\"0-0-0-0\",\"temp_chip11\":\"0-0-0-0\",\"temp_chip12\":\"0-0-0-0\",\"temp_chip13\":\"0-0-0-0\",\"temp_chip14\":\"0-0-0-0\",\"temp_chip15\":\"0-0-0-0\",\"temp_chip16\":\"0-0-0-0\",\"total_rateideal\":0.00,\"rate_unit\":\"GH\",\"total_freqavg\":0.00,\"total_acn\":0,\"total_rate\":0.00,\"chain_rateideal1\":0.00,\"chain_rateideal2\":0.00,\"chain_rateideal3\":0.00,\"chain_rateideal4\":0.00,\"chain_rateideal5\":0.00,\"chain_rateideal6\":0.00,\"chain_rateideal7\":0.00,\"chain_rateideal8\":0.00,\"chain_rateideal9\":0.00,\"chain_rateideal10\":0.00,\"chain_rateideal11\":0.00,\"chain_rateideal12\":0.00,\"chain_rateideal13\":0.00,\"chain_rateideal14\":0.00,\"chain_rateideal15\":0.00,\"chain_rateideal16\":0.00,\"temp_max\":0,\"no_matching_work\":0,\"chain_acn1\":0,\"chain_acn2\":0,\"chain_acn3\":0,\"chain_acn4\":0,\"chain_acn5\":0,\"chain_acn6\":0,\"chain_acn7\":0,\"chain_acn8\":0,\"chain_acn9\":0,\"chain_acn10\":0,\"chain_acn11\":0,\"chain_acn12\":0,\"chain_acn13\":0,\"chain_acn14\":0,\"chain_acn15\":0,\"chain_acn16\":0,\"chain_acs1\":\"\",\"chain_acs2\":\"\",\"chain_acs3\":\"\",\"chain_acs4\":\"\",\"chain_acs5\":\"\",\"chain_acs6\":\"\",\"chain_acs7\":\"\",\"chain_acs8\":\"\",\"chain_acs9\":\"\",\"chain_acs10\":\"\",\"chain_acs11\":\"\",\"chain_acs12\":\"\",\"chain_acs13\":\"\",\"chain_acs14\":\"\",\"chain_acs15\":\"\",\"chain_acs16\":\"\",\"chain_hw1\":0,\"chain_hw2\":0,\"chain_hw3\":0,\"chain_hw4\":0,\"chain_hw5\":0,\"chain_hw6\":0,\"chain_hw7\":0,\"chain_hw8\":0,\"chain_hw9\":0,\"chain_hw10\":0,\"chain_hw11\":0,\"chain_hw12\":0,\"chain_hw13\":0,\"chain_hw14\":0,\"chain_hw15\":0,\"chain_hw16\":0,\"chain_rate1\":\"\",\"chain_rate2\":\"\",\"chain_rate3\":\"\",\"chain_rate4\":\"\",\"chain_rate5\":\"\",\"chain_rate6\":\"\",\"chain_rate7\":\"\",\"chain_rate8\":\"\",\"chain_rate9\":\"\",\"chain_rate10\":\"\",\"chain_rate11\":\"\",\"chain_rate12\":\"\",\"chain_rate13\":\"\",\"chain_rate14\":\"\",\"chain_rate15\":\"\",\"chain_rate16\":\"\",\"chain_xtime2\":\"{}\",\"chain_xtime3\":\"{}\",\"chain_offside_2\":\"\",\"chain_offside_3\":\"\",\"chain_opencore_2\":\"1\",\"chain_opencore_3\":\"1\",\"chain_SN2\":\"unknow\",\"chain_SN3\":\"unknow\",\"freq1\":0,\"freq2\":0,\"freq3\":0,\"freq4\":0,\"freq5\":0,\"freq6\":0,\"freq7\":0,\"freq8\":0,\"freq9\":0,\"freq10\":0,\"freq11\":0,\"freq12\":0,\"freq13\":0,\"freq14\":0,\"freq15\":0,\"freq16\":0,\"miner_version\":\"b023.0.1.3\",\"miner_id\":\"8158b42429e0481c\"}],\"id\":1}"),
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "{\"STATUS\": [{\"STATUS\": \"S\", \"When\": 1621338564, \"Code\": 7, \"Msg\": \"3 Pool(s)\", \"Description\": \"cgminer 1.0.0\"}], \"POOLS\": [{\"POOL\": 0, \"URL\": \"stratum+tcp://aaa.com:9999#xnsub\", \"Status\": \"Alive\", \"Priority\": 0, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 22134, \"Accepted\": 100129, \"Rejected\": 31, \"Discarded\": 183393, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"aaa.bbb\", \"Last Share Time\": \"14:34:59\", \"Diff\": \"8.19K\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 6993665950.0, \"Difficulty Rejected\": 2184386.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 76206.0, \"Has Stratum\": true, \"Stratum Active\": true, \"Stratum URL\": \"aaa.com\", \"Has GBT\": false, \"Best Share\": 2333874959.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}, {\"POOL\": 1, \"URL\": \"stratum+tcp://aaa.com:9999#xnsub\", \"Status\": \"Alive\", \"Priority\": 1, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 1, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"aaa.bbb\", \"Last Share Time\": \"0\", \"Diff\": \"\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": false, \"Stratum URL\": \"\", \"Has GBT\": false, \"Best Share\": 0.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}, {\"POOL\": 2, \"URL\": \"stratum+tcp://aaa.com:9999#xnsub\", \"Status\": \"Alive\", \"Priority\": 2, \"Quota\": 1, \"Long Poll\": \"N\", \"Getworks\": 22119, \"Accepted\": 0, \"Rejected\": 0, \"Discarded\": 0, \"Stale\": 0, \"Get Failures\": 0, \"Remote Failures\": 0, \"User\": \"aaa.bbb\", \"Last Share Time\": \"0\", \"Diff\": \"\", \"Diff1 Shares\": 0, \"Proxy Type\": \"\", \"Proxy\": \"\", \"Difficulty Accepted\": 0.0, \"Difficulty Rejected\": 0.0, \"Difficulty Stale\": 0.0, \"Last Share Difficulty\": 0.0, \"Has Stratum\": true, \"Stratum Active\": true, \"Stratum URL\": \"aaa.com\", \"Has GBT\": false, \"Best Share\": 0.0, \"Pool Rejected%\": 0.0, \"Pool Stale%%\": 0.0}], \"id\": 1}")),
                                true,
                                false
                        }
                });
    }
}