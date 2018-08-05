package mn.foreman.sgminer;

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

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
public class SgminerGmITest
        extends AbstractApiITest {

    /** Constructor. */
    public SgminerGmITest() {
        super(
                new SgminerFactory()
                        .create(
                                ImmutableMap.of(
                                        "name",
                                        "sgminer",
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
                                                "      \"When\": 1533424278,\n" +
                                                "      \"Code\": 9,\n" +
                                                "      \"Msg\": \"1 GPU(s)\",\n" +
                                                "      \"Description\": \"sgminer 5.5.5-gm\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"DEVS\": [\n" +
                                                "    {\n" +
                                                "      \"GPU\": 0,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 70,\n" +
                                                "      \"Fan Speed\": 0,\n" +
                                                "      \"Fan Percent\": 80,\n" +
                                                "      \"GPU Clock\": 1234,\n" +
                                                "      \"Memory Clock\": 2345,\n" +
                                                "      \"GPU Voltage\": 0,\n" +
                                                "      \"GPU Activity\": 0,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 0.0000125,\n" +
                                                "      \"MHS 5s\": 0.00001325,\n" +
                                                "      \"KHS av\": 0.0125,\n" +
                                                "      \"KHS 5s\": 0.01325,\n" +
                                                "      \"Accepted\": 0,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0,\n" +
                                                "      \"Intensity\": \"8\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": -1,\n" +
                                                "      \"Last Share Time\": 0,\n" +
                                                "      \"Total MH\": 0.0014,\n" +
                                                "      \"Diff1 Work\": 0,\n" +
                                                "      \"Difficulty Accepted\": 0,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Last Share Difficulty\": 0,\n" +
                                                "      \"Last Valid Work\": 1533424167,\n" +
                                                "      \"Device Hardware%\": 0,\n" +
                                                "      \"Device Rejected%\": 0,\n" +
                                                "      \"Device Elapsed\": 109\n" +
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
                                                "      \"When\": 1533424284,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"1 Pool(s)\",\n" +
                                                "      \"Description\": \"sgminer 5.5.5-gm\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"Name\": \"Nanopool ZCash\",\n" +
                                                "      \"URL\": \"stratum+tcp://zec-us-east1.nanopool.org:16666\",\n" +
                                                "      \"Profile\": \"zcash\",\n" +
                                                "      \"Algorithm\": \"equihash\",\n" +
                                                "      \"Description\": \"\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 0,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 3,\n" +
                                                "      \"Accepted\": 10,\n" +
                                                "      \"Rejected\": 9,\n" +
                                                "      \"Works\": 34,\n" +
                                                "      \"Discarded\": 89,\n" +
                                                "      \"Stale\": 2,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"zzzz\",\n" +
                                                "      \"Last Share Time\": 0,\n" +
                                                "      \"Diff1 Shares\": 0,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 0,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 0,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"zec-us-east1.nanopool.org\",\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 0,\n" +
                                                "      \"Pool Rejected%\": 0,\n" +
                                                "      \"Pool Stale%\": 0\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"id\": 1\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setName("sgminer")
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("zec-us-east1.nanopool.org:16666")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                10,
                                                9,
                                                2)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setName("sgminer")
                                        .setHashRate(new BigDecimal("13.25000000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(70)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(80)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1234")
                                                                        .setMemFreq("2345")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}
