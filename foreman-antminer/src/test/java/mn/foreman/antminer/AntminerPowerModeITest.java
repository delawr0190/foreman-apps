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

import java.math.BigDecimal;
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
     */
    public AntminerPowerModeITest(
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, HandlerInterface> rpcHandlers,
            final boolean foundResult) {
        super(
                TestUtils.toPoolJson(
                        ImmutableMap.of(
                                "webPort",
                                "8080",
                                "mode",
                                "sleeping")),
                new AntminerFactory(BigDecimal.ONE),
                new FirmwareAwareAction(
                        "antMiner Configuration",
                        new StockPowerModeAction(
                                "antMiner Configuration",
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
                                true
                        }
                });
    }
}