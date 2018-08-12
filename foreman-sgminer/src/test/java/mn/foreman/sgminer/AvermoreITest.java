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
public class AvermoreITest
        extends AbstractApiITest {

    /** Constructor. */
    public AvermoreITest() {
        super(
                new SgminerFactory()
                        .create(
                                ImmutableMap.of(
                                        "name",
                                        "avermore",
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
                                                "      \"When\": 1533441401,\n" +
                                                "      \"Code\": 9,\n" +
                                                "      \"Msg\": \"1 GPU(s)\",\n" +
                                                "      \"Description\": \"avermore 1.4.1\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"DEVS\": [\n" +
                                                "    {\n" +
                                                "      \"GPU\": 0,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 61,\n" +
                                                "      \"Fan Speed\": 1939,\n" +
                                                "      \"Fan Percent\": 44,\n" +
                                                "      \"GPU Clock\": 1178,\n" +
                                                "      \"Memory Clock\": 1500,\n" +
                                                "      \"GPU Voltage\": 1.081,\n" +
                                                "      \"GPU Activity\": 100,\n" +
                                                "      \"Powertune\": 0,\n" +
                                                "      \"MHS av\": 0.8709,\n" +
                                                "      \"MHS 5s\": 0.5251,\n" +
                                                "      \"KHS av\": 870.9,\n" +
                                                "      \"KHS 5s\": 525.1,\n" +
                                                "      \"Accepted\": 1,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 1.998,\n" +
                                                "      \"Intensity\": \"0\",\n" +
                                                "      \"XIntensity\": 256,\n" +
                                                "      \"RawIntensity\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1533441399,\n" +
                                                "      \"Total MH\": 26.1489,\n" +
                                                "      \"Diff1 Work\": 1,\n" +
                                                "      \"Difficulty Accepted\": 1,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Last Share Difficulty\": 1,\n" +
                                                "      \"Last Valid Work\": 1533441399,\n" +
                                                "      \"Device Hardware%\": 0,\n" +
                                                "      \"Device Rejected%\": 0,\n" +
                                                "      \"Device Elapsed\": 30\n" +
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
                                                "      \"When\": 1533441519,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"2 Pool(s)\",\n" +
                                                "      \"Description\": \"avermore 1.4.1\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"Name\": \"minepool.com\",\n" +
                                                "      \"URL\": \"stratum+tcp://minepool.com:3636\",\n" +
                                                "      \"Profile\": \"\",\n" +
                                                "      \"Algorithm\": \"x16r\",\n" +
                                                "      \"Description\": \"\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 0,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 4,\n" +
                                                "      \"Accepted\": 28,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 21,\n" +
                                                "      \"Discarded\": 112,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"RWoSZX6j6WU6SVTVq5hKmdgPmmrYE9be5R\",\n" +
                                                "      \"Last Share Time\": 1533441516,\n" +
                                                "      \"Diff1 Shares\": 28,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 28,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 1,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"minepool.com\",\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 61.49569,\n" +
                                                "      \"Pool Rejected%\": 0,\n" +
                                                "      \"Pool Stale%\": 0\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"POOL\": 1,\n" +
                                                "      \"Name\": \"dev pool\",\n" +
                                                "      \"URL\": \"stratum+tcp://ravenminer.com:9999\",\n" +
                                                "      \"Profile\": \"\",\n" +
                                                "      \"Algorithm\": \"x16r\",\n" +
                                                "      \"Description\": \"\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 1,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 0,\n" +
                                                "      \"Accepted\": 0,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 0,\n" +
                                                "      \"Discarded\": 0,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"RTByBLDAGRF27sNJRvsL4LArihLLZ8Gyv9\",\n" +
                                                "      \"Last Share Time\": 0,\n" +
                                                "      \"Diff1 Shares\": 0,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 0,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 0,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": false,\n" +
                                                "      \"Stratum URL\": \"\",\n" +
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
                                        .setName("minepool.com:3636")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                28,
                                                0,
                                                0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("ravenminer.com:9999")
                                        .setPriority(1)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                0,
                                                0,
                                                0)
                                        .build())
                        .addRig(
                                new Rig.Builder()
                                        .setHashRate(new BigDecimal("525100.0000"))
                                        .addGpu(
                                                new Gpu.Builder()
                                                        .setName("GPU 0")
                                                        .setIndex(0)
                                                        .setBus(0)
                                                        .setTemp(61)
                                                        .setFans(
                                                                new FanInfo.Builder()
                                                                        .setCount(1)
                                                                        .addSpeed(44)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .setFreqInfo(
                                                                new FreqInfo.Builder()
                                                                        .setFreq("1178")
                                                                        .setMemFreq("1500")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build());
    }
}