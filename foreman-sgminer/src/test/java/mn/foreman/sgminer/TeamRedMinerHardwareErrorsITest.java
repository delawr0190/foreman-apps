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
public class TeamRedMinerHardwareErrorsITest
        extends AbstractApiITest {

    /** Constructor. */
    public TeamRedMinerHardwareErrorsITest() {
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
                                                "      \"When\": 1543769076,\n" +
                                                "      \"Code\": 9,\n" +
                                                "      \"Msg\": \"1 GPU(s)\",\n" +
                                                "      \"Description\": \"TeamRedMiner 0.3.8\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"DEVS\": [\n" +
                                                "    {\n" +
                                                "      \"GPU\": 0,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 68,\n" +
                                                "      \"Fan Speed\": 1417,\n" +
                                                "      \"Fan Percent\": 34,\n" +
                                                "      \"GPU Clock\": 1187,\n" +
                                                "      \"Memory Clock\": 1500,\n" +
                                                "      \"GPU Voltage\": 1.081,\n" +
                                                "      \"GPU Activity\": 0,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 0.0003418,\n" +
                                                "      \"MHS 30s\": 0.0003441,\n" +
                                                "      \"KHS av\": 0.3418,\n" +
                                                "      \"KHS 30s\": 0.3441,\n" +
                                                "      \"Accepted\": 13,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 1,\n" +
                                                "      \"Utility\": 2.264,\n" +
                                                "      \"Intensity\": \"20\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1543769053,\n" +
                                                "      \"Total MH\": 117760,\n" +
                                                "      \"Diff1 Work\": 95895.055303,\n" +
                                                "      \"Difficulty Accepted\": 95895.05530314,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Last Share Difficulty\": 5910.00662696,\n" +
                                                "      \"Last Valid Work\": 1543769053,\n" +
                                                "      \"Device Hardware%\": 0,\n" +
                                                "      \"Device Rejected%\": 0,\n" +
                                                "      \"Device Elapsed\": 344\n" +
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
                                                "      \"When\": 1543769076,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"1 Pool(s)\",\n" +
                                                "      \"Description\": \"TeamRedMiner 0.3.8\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"Name\": \"pool.supportxmr.com\",\n" +
                                                "      \"URL\": \"stratum+tcp://pool.supportxmr.com:7777\",\n" +
                                                "      \"Profile\": \"\",\n" +
                                                "      \"Algorithm\": \"cnv8\",\n" +
                                                "      \"Description\": \"\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 0,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 10,\n" +
                                                "      \"Accepted\": 13,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 0,\n" +
                                                "      \"Discarded\": 0,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"479c6JsyawEVAMNZU8GMmXgVPTxd1vdejR6vVpsm7z8y2AvP7C5hz2g5gfrqyffpvLPLYb2eUmmWA5yhRw5ANYyePX7SvLE\",\n" +
                                                "      \"Last Share Time\": 1543769053,\n" +
                                                "      \"Diff1 Shares\": 95895.055303,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 95895.05530314,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 5910.00662696,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"pool.supportxmr.com\",\n" +
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
                                        .setName("pool.supportxmr.com:7777")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                13,
                                                1,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("344.1000000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(68)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(34)
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