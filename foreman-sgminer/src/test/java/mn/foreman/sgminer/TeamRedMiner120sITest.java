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
public class TeamRedMiner120sITest
        extends AbstractApiITest {

    /** Constructor. */
    public TeamRedMiner120sITest() {
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
                                                "      \"When\": 1546848341,\n" +
                                                "      \"Code\": 9,\n" +
                                                "      \"Msg\": \"6 GPU(s)\",\n" +
                                                "      \"Description\": \"TeamRedMiner 0.3.8\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"DEVS\": [\n" +
                                                "    {\n" +
                                                "      \"GPU\": 0,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 61,\n" +
                                                "      \"Fan Speed\": 3185,\n" +
                                                "      \"Fan Percent\": 65,\n" +
                                                "      \"GPU Clock\": 1383,\n" +
                                                "      \"Memory Clock\": 1100,\n" +
                                                "      \"GPU Voltage\": 0.875,\n" +
                                                "      \"GPU Activity\": 0,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 0.002107,\n" +
                                                "      \"MHS 120s\": 0.002107,\n" +
                                                "      \"KHS av\": 2.107,\n" +
                                                "      \"KHS 120s\": 2.107,\n" +
                                                "      \"Accepted\": 60,\n" +
                                                "      \"Rejected\": 2,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.2592,\n" +
                                                "      \"Intensity\": \"20\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1546848268,\n" +
                                                "      \"Total MH\": 29258880,\n" +
                                                "      \"Diff1 Work\": 28596687.775875,\n" +
                                                "      \"Difficulty Accepted\": 27699630.69422065,\n" +
                                                "      \"Difficulty Rejected\": 897057.08165406,\n" +
                                                "      \"Last Share Difficulty\": 510030.55409096,\n" +
                                                "      \"Last Valid Work\": 1546848268,\n" +
                                                "      \"Device Hardware%\": 0,\n" +
                                                "      \"Device Rejected%\": 3.1369,\n" +
                                                "      \"Device Elapsed\": 13887\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"GPU\": 1,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 61,\n" +
                                                "      \"Fan Speed\": 3226,\n" +
                                                "      \"Fan Percent\": 65,\n" +
                                                "      \"GPU Clock\": 1385,\n" +
                                                "      \"Memory Clock\": 1100,\n" +
                                                "      \"GPU Voltage\": 0.875,\n" +
                                                "      \"GPU Activity\": 0,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 0.002112,\n" +
                                                "      \"MHS 120s\": 0.002118,\n" +
                                                "      \"KHS av\": 2.112,\n" +
                                                "      \"KHS 120s\": 2.118,\n" +
                                                "      \"Accepted\": 53,\n" +
                                                "      \"Rejected\": 1,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.229,\n" +
                                                "      \"Intensity\": \"20\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1546848241,\n" +
                                                "      \"Total MH\": 29328000,\n" +
                                                "      \"Diff1 Work\": 25941630.98397,\n" +
                                                "      \"Difficulty Accepted\": 25141525.30289723,\n" +
                                                "      \"Difficulty Rejected\": 800105.68107303,\n" +
                                                "      \"Last Share Difficulty\": 510030.55409096,\n" +
                                                "      \"Last Valid Work\": 1546848241,\n" +
                                                "      \"Device Hardware%\": 0,\n" +
                                                "      \"Device Rejected%\": 3.0843,\n" +
                                                "      \"Device Elapsed\": 13886\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"GPU\": 2,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 63,\n" +
                                                "      \"Fan Speed\": 3273,\n" +
                                                "      \"Fan Percent\": 66,\n" +
                                                "      \"GPU Clock\": 1376,\n" +
                                                "      \"Memory Clock\": 1100,\n" +
                                                "      \"GPU Voltage\": 0.875,\n" +
                                                "      \"GPU Activity\": 0,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 0.002094,\n" +
                                                "      \"MHS 120s\": 0.002095,\n" +
                                                "      \"KHS av\": 2.094,\n" +
                                                "      \"KHS 120s\": 2.095,\n" +
                                                "      \"Accepted\": 70,\n" +
                                                "      \"Rejected\": 1,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.3025,\n" +
                                                "      \"Intensity\": \"20\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1546848299,\n" +
                                                "      \"Total MH\": 29076480,\n" +
                                                "      \"Diff1 Work\": 33290522.943687,\n" +
                                                "      \"Difficulty Accepted\": 32890507.3624263,\n" +
                                                "      \"Difficulty Rejected\": 400015.58126106,\n" +
                                                "      \"Last Share Difficulty\": 510030.55409096,\n" +
                                                "      \"Last Valid Work\": 1546848299,\n" +
                                                "      \"Device Hardware%\": 0,\n" +
                                                "      \"Device Rejected%\": 1.2016,\n" +
                                                "      \"Device Elapsed\": 13886\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"GPU\": 3,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 60,\n" +
                                                "      \"Fan Speed\": 3185,\n" +
                                                "      \"Fan Percent\": 65,\n" +
                                                "      \"GPU Clock\": 1383,\n" +
                                                "      \"Memory Clock\": 1050,\n" +
                                                "      \"GPU Voltage\": 0.875,\n" +
                                                "      \"GPU Activity\": 0,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 0.002058,\n" +
                                                "      \"MHS 120s\": 0.002058,\n" +
                                                "      \"KHS av\": 2.058,\n" +
                                                "      \"KHS 120s\": 2.058,\n" +
                                                "      \"Accepted\": 73,\n" +
                                                "      \"Rejected\": 4,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.3154,\n" +
                                                "      \"Intensity\": \"20\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1546847883,\n" +
                                                "      \"Total MH\": 28581120,\n" +
                                                "      \"Diff1 Work\": 34388748.437963,\n" +
                                                "      \"Difficulty Accepted\": 31908475.97136397,\n" +
                                                "      \"Difficulty Rejected\": 2480272.46659924,\n" +
                                                "      \"Last Share Difficulty\": 510030.55409096,\n" +
                                                "      \"Last Valid Work\": 1546847883,\n" +
                                                "      \"Device Hardware%\": 0,\n" +
                                                "      \"Device Rejected%\": 7.2125,\n" +
                                                "      \"Device Elapsed\": 13886\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"GPU\": 4,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 63,\n" +
                                                "      \"Fan Speed\": 3299,\n" +
                                                "      \"Fan Percent\": 67,\n" +
                                                "      \"GPU Clock\": 1381,\n" +
                                                "      \"Memory Clock\": 1100,\n" +
                                                "      \"GPU Voltage\": 0.875,\n" +
                                                "      \"GPU Activity\": 0,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 0.002101,\n" +
                                                "      \"MHS 120s\": 0.002101,\n" +
                                                "      \"KHS av\": 2.101,\n" +
                                                "      \"KHS 120s\": 2.101,\n" +
                                                "      \"Accepted\": 55,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.2377,\n" +
                                                "      \"Intensity\": \"20\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1546848190,\n" +
                                                "      \"Total MH\": 29174400,\n" +
                                                "      \"Diff1 Work\": 23648145.793206,\n" +
                                                "      \"Difficulty Accepted\": 23648145.79320625,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Last Share Difficulty\": 510030.55409096,\n" +
                                                "      \"Last Valid Work\": 1546848190,\n" +
                                                "      \"Device Hardware%\": 0,\n" +
                                                "      \"Device Rejected%\": 0,\n" +
                                                "      \"Device Elapsed\": 13886\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"GPU\": 5,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 62,\n" +
                                                "      \"Fan Speed\": 3216,\n" +
                                                "      \"Fan Percent\": 65,\n" +
                                                "      \"GPU Clock\": 1375,\n" +
                                                "      \"Memory Clock\": 1100,\n" +
                                                "      \"GPU Voltage\": 0.875,\n" +
                                                "      \"GPU Activity\": 0,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 0.002094,\n" +
                                                "      \"MHS 120s\": 0.002094,\n" +
                                                "      \"KHS av\": 2.094,\n" +
                                                "      \"KHS 120s\": 2.094,\n" +
                                                "      \"Accepted\": 60,\n" +
                                                "      \"Rejected\": 4,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.2592,\n" +
                                                "      \"Intensity\": \"20\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1546848129,\n" +
                                                "      \"Total MH\": 29084160,\n" +
                                                "      \"Diff1 Work\": 26091597.067281,\n" +
                                                "      \"Difficulty Accepted\": 24011414.70049367,\n" +
                                                "      \"Difficulty Rejected\": 2080182.36678728,\n" +
                                                "      \"Last Share Difficulty\": 510030.55409096,\n" +
                                                "      \"Last Valid Work\": 1546848129,\n" +
                                                "      \"Device Hardware%\": 0,\n" +
                                                "      \"Device Rejected%\": 7.9726,\n" +
                                                "      \"Device Elapsed\": 13887\n" +
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
                                        .setWorker("479c6JsyawEVAMNZU8GMmXgVPTxd1vdejR6vVpsm7z8y2AvP7C5hz2g5gfrqyffpvLPLYb2eUmmWA5yhRw5ANYyePX7SvLE")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                13,
                                                0,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("12566.000000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(61)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(65)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1383")
                                                                        .setMemFreq("1100")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 1")
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setTemp(61)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(65)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1385")
                                                                        .setMemFreq("1100")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 2")
                                                        .setIndex(2)
                                                        .setBus(0)
                                                        .setTemp(63)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(66)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1376")
                                                                        .setMemFreq("1100")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 3")
                                                        .setIndex(3)
                                                        .setBus(0)
                                                        .setTemp(60)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(65)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1383")
                                                                        .setMemFreq("1050")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 4")
                                                        .setIndex(4)
                                                        .setBus(0)
                                                        .setTemp(63)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(67)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1381")
                                                                        .setMemFreq("1100")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 5")
                                                        .setIndex(5)
                                                        .setBus(0)
                                                        .setTemp(62)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(65)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1375")
                                                                        .setMemFreq("1100")
                                                                        .build())
                                                        .build())
                                        .addAttribute(
                                                "gpu_algo",
                                                "cnv8")
                                        .build())
                        .build());
    }
}