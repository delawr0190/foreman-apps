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
public class SgminerTecracoinITest
        extends AbstractApiITest {

    /** Constructor. */
    public SgminerTecracoinITest() {
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
                                                "      \"When\": 1571154734,\n" +
                                                "      \"Code\": 9,\n" +
                                                "      \"Msg\": \"6 GPU(s)\",\n" +
                                                "      \"Description\": \"sgminer v0.1.4\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"DEVS\": [\n" +
                                                "    {\n" +
                                                "      \"GPU\": 0,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 58.0,\n" +
                                                "      \"Fan Speed\": 4566,\n" +
                                                "      \"Fan Percent\": 93,\n" +
                                                "      \"GPU Clock\": 1135,\n" +
                                                "      \"Memory Clock\": 500,\n" +
                                                "      \"GPU Voltage\": 0.825,\n" +
                                                "      \"GPU Activity\": 100,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 7.766,\n" +
                                                "      \"MHS 5s\": 9.069,\n" +
                                                "      \"KHS av\": 7766,\n" +
                                                "      \"KHS 5s\": 9069,\n" +
                                                "      \"Accepted\": 2,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.125,\n" +
                                                "      \"Intensity\": \"23\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1571153980,\n" +
                                                "      \"Total MH\": 7457.4725,\n" +
                                                "      \"Diff1 Work\": 0.128,\n" +
                                                "      \"Difficulty Accepted\": 0.576,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 0.512,\n" +
                                                "      \"Last Valid Work\": 1571153980,\n" +
                                                "      \"Device Hardware%\": 0.0,\n" +
                                                "      \"Device Rejected%\": 0.0,\n" +
                                                "      \"Device Elapsed\": 960\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"GPU\": 1,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 59.0,\n" +
                                                "      \"Fan Speed\": 4520,\n" +
                                                "      \"Fan Percent\": 92,\n" +
                                                "      \"GPU Clock\": 1135,\n" +
                                                "      \"Memory Clock\": 500,\n" +
                                                "      \"GPU Voltage\": 0.825,\n" +
                                                "      \"GPU Activity\": 100,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 7.766,\n" +
                                                "      \"MHS 5s\": 9.069,\n" +
                                                "      \"KHS av\": 7766,\n" +
                                                "      \"KHS 5s\": 9069,\n" +
                                                "      \"Accepted\": 11,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.6873,\n" +
                                                "      \"Intensity\": \"23\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1571154497,\n" +
                                                "      \"Total MH\": 7457.4725,\n" +
                                                "      \"Diff1 Work\": 0.704,\n" +
                                                "      \"Difficulty Accepted\": 4.032,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 0.512,\n" +
                                                "      \"Last Valid Work\": 1571154496,\n" +
                                                "      \"Device Hardware%\": 0.0,\n" +
                                                "      \"Device Rejected%\": 0.0,\n" +
                                                "      \"Device Elapsed\": 960\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"GPU\": 2,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 57.0,\n" +
                                                "      \"Fan Speed\": 4592,\n" +
                                                "      \"Fan Percent\": 93,\n" +
                                                "      \"GPU Clock\": 1134,\n" +
                                                "      \"Memory Clock\": 500,\n" +
                                                "      \"GPU Voltage\": 0.825,\n" +
                                                "      \"GPU Activity\": 99,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 7.775,\n" +
                                                "      \"MHS 5s\": 9.062,\n" +
                                                "      \"KHS av\": 7775,\n" +
                                                "      \"KHS 5s\": 9062,\n" +
                                                "      \"Accepted\": 2,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.125,\n" +
                                                "      \"Intensity\": \"23\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1571154261,\n" +
                                                "      \"Total MH\": 7465.8611,\n" +
                                                "      \"Diff1 Work\": 0.128,\n" +
                                                "      \"Difficulty Accepted\": 0.64,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 0.512,\n" +
                                                "      \"Last Valid Work\": 1571154261,\n" +
                                                "      \"Device Hardware%\": 0.0,\n" +
                                                "      \"Device Rejected%\": 0.0,\n" +
                                                "      \"Device Elapsed\": 960\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"GPU\": 3,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 55.0,\n" +
                                                "      \"Fan Speed\": 4572,\n" +
                                                "      \"Fan Percent\": 93,\n" +
                                                "      \"GPU Clock\": 1134,\n" +
                                                "      \"Memory Clock\": 500,\n" +
                                                "      \"GPU Voltage\": 0.825,\n" +
                                                "      \"GPU Activity\": 100,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 7.784,\n" +
                                                "      \"MHS 5s\": 9.07,\n" +
                                                "      \"KHS av\": 7784,\n" +
                                                "      \"KHS 5s\": 9070,\n" +
                                                "      \"Accepted\": 4,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 1,\n" +
                                                "      \"Utility\": 0.2499,\n" +
                                                "      \"Intensity\": \"23\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1571153922,\n" +
                                                "      \"Total MH\": 7474.2497,\n" +
                                                "      \"Diff1 Work\": 0.256,\n" +
                                                "      \"Difficulty Accepted\": 0.512,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 0.128,\n" +
                                                "      \"Last Valid Work\": 1571153922,\n" +
                                                "      \"Device Hardware%\": 79.6178,\n" +
                                                "      \"Device Rejected%\": 0.0,\n" +
                                                "      \"Device Elapsed\": 960\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"GPU\": 4,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 56.0,\n" +
                                                "      \"Fan Speed\": 4536,\n" +
                                                "      \"Fan Percent\": 92,\n" +
                                                "      \"GPU Clock\": 1135,\n" +
                                                "      \"Memory Clock\": 500,\n" +
                                                "      \"GPU Voltage\": 0.825,\n" +
                                                "      \"GPU Activity\": 100,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 7.775,\n" +
                                                "      \"MHS 5s\": 9.069,\n" +
                                                "      \"KHS av\": 7775,\n" +
                                                "      \"KHS 5s\": 9069,\n" +
                                                "      \"Accepted\": 5,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.3124,\n" +
                                                "      \"Intensity\": \"23\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1571154313,\n" +
                                                "      \"Total MH\": 7465.8611,\n" +
                                                "      \"Diff1 Work\": 0.32,\n" +
                                                "      \"Difficulty Accepted\": 2.56,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 0.512,\n" +
                                                "      \"Last Valid Work\": 1571154313,\n" +
                                                "      \"Device Hardware%\": 0.0,\n" +
                                                "      \"Device Rejected%\": 0.0,\n" +
                                                "      \"Device Elapsed\": 960\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"GPU\": 5,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 59.0,\n" +
                                                "      \"Fan Speed\": 4616,\n" +
                                                "      \"Fan Percent\": 94,\n" +
                                                "      \"GPU Clock\": 1134,\n" +
                                                "      \"Memory Clock\": 500,\n" +
                                                "      \"GPU Voltage\": 0.825,\n" +
                                                "      \"GPU Activity\": 99,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 7.766,\n" +
                                                "      \"MHS 5s\": 9.069,\n" +
                                                "      \"KHS av\": 7766,\n" +
                                                "      \"KHS 5s\": 9069,\n" +
                                                "      \"Accepted\": 3,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 0.1875,\n" +
                                                "      \"Intensity\": \"23\",\n" +
                                                "      \"XIntensity\": 0,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1571154671,\n" +
                                                "      \"Total MH\": 7457.4725,\n" +
                                                "      \"Diff1 Work\": 0.192,\n" +
                                                "      \"Difficulty Accepted\": 1.088,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 0.512,\n" +
                                                "      \"Last Valid Work\": 1571154670,\n" +
                                                "      \"Device Hardware%\": 0.0,\n" +
                                                "      \"Device Rejected%\": 0.0,\n" +
                                                "      \"Device Elapsed\": 960\n" +
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
                                                "      \"When\": 1571154734,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"1 Pool(s)\",\n" +
                                                "      \"Description\": \"sgminer v0.1.4\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"Name\": \"Tecracoin.io\",\n" +
                                                "      \"URL\": \"stratum+tcp://pool-mtp.tecracoin.io:4556\",\n" +
                                                "      \"Profile\": \"\",\n" +
                                                "      \"Algorithm\": \"mtp-tcr\",\n" +
                                                "      \"Description\": \"\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 0,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 20,\n" +
                                                "      \"Accepted\": 27,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 752,\n" +
                                                "      \"Discarded\": 826,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"xxxx\",\n" +
                                                "      \"Last Share Time\": 1571154671,\n" +
                                                "      \"Diff1 Shares\": 1.728,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 9.408,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Difficulty Stale\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 0.512,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"pool-mtp.tecracoin.io\",\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 15.567331,\n" +
                                                "      \"Pool Rejected%\": 0.0,\n" +
                                                "      \"Pool Stale%\": 0.0\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"id\": 1\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("pool-mtp.tecracoin.io:4556")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                27,
                                                0,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("54408000.000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(58)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(93)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1135")
                                                                        .setMemFreq("500")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 1")
                                                        .setIndex(1)
                                                        .setBus(0)
                                                        .setTemp(59)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(92)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1135")
                                                                        .setMemFreq("500")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 2")
                                                        .setIndex(2)
                                                        .setBus(0)
                                                        .setTemp(57)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(93)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1134")
                                                                        .setMemFreq("500")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 3")
                                                        .setIndex(3)
                                                        .setBus(0)
                                                        .setTemp(55)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(93)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1134")
                                                                        .setMemFreq("500")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 4")
                                                        .setIndex(4)
                                                        .setBus(0)
                                                        .setTemp(56)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(92)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1135")
                                                                        .setMemFreq("500")
                                                                        .build())
                                                        .build())
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 5")
                                                        .setIndex(5)
                                                        .setBus(0)
                                                        .setTemp(59)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(94)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1134")
                                                                        .setMemFreq("500")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}