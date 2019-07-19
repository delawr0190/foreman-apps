package mn.foreman.sgminer;

import com.google.common.collect.ImmutableMap;
import mn.foreman.cgminer.CgMiner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import java.math.BigDecimal;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
public class TeamRedMiner_0_3_10_ITest
        extends AbstractApiITest {

    /** Constructor. */
    public TeamRedMiner_0_3_10_ITest() {
        super(
                new SgminerFactory()
                        .create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "4028")),
                new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"devs\"}",
                                new RpcHandler(
                                        "{\n" +
                                                "  \"STATUS\": [\n" +
                                                "    {\n" +
                                                "      \"STATUS\": \"S\",\n" +
                                                "      \"When\": 1549343632,\n" +
                                                "      \"Code\": 9,\n" +
                                                "      \"Msg\": \"1 GPU(s)\",\n" +
                                                "      \"Description\": \"TeamRedMiner 0.3.10\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"DEVS\": [\n" +
                                                "    {\n" +
                                                "      \"GPU\": 0,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 69,\n" +
                                                "      \"Fan Speed\": 2106,\n" +
                                                "      \"Fan Percent\": 47,\n" +
                                                "      \"GPU Clock\": 1187,\n" +
                                                "      \"Memory Clock\": 1500,\n" +
                                                "      \"GPU Voltage\": 1.081,\n" +
                                                "      \"GPU Activity\": 0,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 1.741,\n" +
                                                "      \"MHS 30s\": 1.794,\n" +
                                                "      \"KHS av\": 1741,\n" +
                                                "      \"KHS 30s\": 1794,\n" +
                                                "      \"Accepted\": 9,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 5.268,\n" +
                                                "      \"Intensity\": \"20\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1549343623,\n" +
                                                "      \"Total MH\": 178454528,\n" +
                                                "      \"Diff1 Work\": 0.035156,\n" +
                                                "      \"Difficulty Accepted\": 9,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Last Share Difficulty\": 1,\n" +
                                                "      \"Last Valid Work\": 1549343623,\n" +
                                                "      \"Device Hardware%\": 0,\n" +
                                                "      \"Device Rejected%\": 0,\n" +
                                                "      \"Device Elapsed\": 103\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"id\": 1\n" +
                                                "}"),
                                "{\"command\":\"pools\"}",
                                new RpcHandler(
                                        "{\n" +
                                                "  \"STATUS\": [\n" +
                                                "    {\n" +
                                                "      \"STATUS\": \"S\",\n" +
                                                "      \"When\": 1549343632,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"1 Pool(s)\",\n" +
                                                "      \"Description\": \"TeamRedMiner 0.3.10\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"Name\": \"lux.pickaxe.pro\",\n" +
                                                "      \"URL\": \"stratum+tcp:\\/\\/lux.pickaxe.pro:8332\",\n" +
                                                "      \"Profile\": \"\",\n" +
                                                "      \"Algorithm\": \"phi2\",\n" +
                                                "      \"Description\": \"\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 0,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 2,\n" +
                                                "      \"Accepted\": 9,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 2,\n" +
                                                "      \"Discarded\": 0,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"LhreQGewLdoGFiqq882Am6i644Qc1h28Wh\",\n" +
                                                "      \"Last Share Time\": 1549343623,\n" +
                                                "      \"Diff1 Shares\": 9,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 9,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 1,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"lux.pickaxe.pro\",\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 0,\n" +
                                                "      \"Pool Rejected%\": 0,\n" +
                                                "      \"Pool Stale%\": 0\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"id\": 1\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("lux.pickaxe.pro:8332")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                9,
                                                0,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("1794000.000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(69)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(47)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1187")
                                                                        .setMemFreq("1500")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}