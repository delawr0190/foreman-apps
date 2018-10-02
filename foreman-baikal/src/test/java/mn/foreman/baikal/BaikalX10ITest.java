package mn.foreman.baikal;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
public class BaikalX10ITest
        extends AbstractApiITest {

    /** Constructor. */
    public BaikalX10ITest() {
        super(
                new BaikalFactory()
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
                                                "      \"When\": 1533397884,\n" +
                                                "      \"Code\": 9,\n" +
                                                "      \"Msg\": \"3 GPU(s)\",\n" +
                                                "      \"Description\": \"sgminer 5.6.6-l\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"DEVS\": [\n" +
                                                "    {\n" +
                                                "      \"ASC\": 0,\n" +
                                                "      \"Name\": \"BKLU\",\n" +
                                                "      \"ID\": 0,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 47.0,\n" +
                                                "      \"MHS av\": 3111.0421,\n" +
                                                "      \"MHS 5s\": 3379.1931,\n" +
                                                "      \"Accepted\": 1277,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 103.2529,\n" +
                                                "      \"Last Share Pool\": 1,\n" +
                                                "      \"Last Share Time\": 1533397884,\n" +
                                                "      \"Total MH\": 2308584.7347,\n" +
                                                "      \"Diff1 Work\": 158691.497589,\n" +
                                                "      \"Difficulty Accepted\": 130197.14096064,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 149.66147328,\n" +
                                                "      \"No Device\": false,\n" +
                                                "      \"Last Valid Work\": 1533397884,\n" +
                                                "      \"Device Hardware%\": 0.0,\n" +
                                                "      \"Device Rejected%\": 0.0,\n" +
                                                "      \"Device Elapsed\": 742\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"ASC\": 1,\n" +
                                                "      \"Name\": \"BKLU\",\n" +
                                                "      \"ID\": 1,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 47.0,\n" +
                                                "      \"MHS av\": 3091.0,\n" +
                                                "      \"MHS 5s\": 3379.2005,\n" +
                                                "      \"Accepted\": 1262,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 102.0401,\n" +
                                                "      \"Last Share Pool\": 1,\n" +
                                                "      \"Last Share Time\": 1533397884,\n" +
                                                "      \"Total MH\": 2293712.1997,\n" +
                                                "      \"Diff1 Work\": 158618.478538,\n" +
                                                "      \"Difficulty Accepted\": 127321.87427328,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 149.66147328,\n" +
                                                "      \"No Device\": false,\n" +
                                                "      \"Last Valid Work\": 1533397884,\n" +
                                                "      \"Device Hardware%\": 0.0,\n" +
                                                "      \"Device Rejected%\": 0.0,\n" +
                                                "      \"Device Elapsed\": 742\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"ASC\": 2,\n" +
                                                "      \"Name\": \"BKLU\",\n" +
                                                "      \"ID\": 2,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Temperature\": 46.0,\n" +
                                                "      \"MHS av\": 3090.5468,\n" +
                                                "      \"MHS 5s\": 3379.1582,\n" +
                                                "      \"Accepted\": 1270,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Utility\": 102.6869,\n" +
                                                "      \"Last Share Pool\": 1,\n" +
                                                "      \"Last Share Time\": 1533397882,\n" +
                                                "      \"Total MH\": 2293375.913,\n" +
                                                "      \"Diff1 Work\": 161574.95538,\n" +
                                                "      \"Difficulty Accepted\": 128734.96687936,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 149.66147328,\n" +
                                                "      \"No Device\": false,\n" +
                                                "      \"Last Valid Work\": 1533397884,\n" +
                                                "      \"Device Hardware%\": 0.0,\n" +
                                                "      \"Device Rejected%\": 0.0,\n" +
                                                "      \"Device Elapsed\": 742\n" +
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
                                                "      \"When\": 1533397884,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"8 Pool(s)\",\n" +
                                                "      \"Description\": \"sgminer 5.6.6-l\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"Name\": \"hub.miningpoolhub.com\",\n" +
                                                "      \"URL\": \"stratum+tcp://hub.miningpoolhub.com:17016\",\n" +
                                                "      \"Profile\": \"\",\n" +
                                                "      \"Algorithm\": \"skein-sha256\",\n" +
                                                "      \"Algorithm Type\": \"Skeincoin\",\n" +
                                                "      \"Description\": \"\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 3,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 5,\n" +
                                                "      \"Accepted\": 10,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 379,\n" +
                                                "      \"Discarded\": 72,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"ZZZZZZZZZZZZZZZZZZZZZZZ\",\n" +
                                                "      \"Last Share Time\": 1533397250,\n" +
                                                "      \"Diff1 Shares\": 159.83552,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 159.83552,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Difficulty Stale\": 0.0,\n" +
                                                "      \"Last Share Difficulty\": 16.90568,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": false,\n" +
                                                "      \"Stratum URL\": \"\",\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 186.11316,\n" +
                                                "      \"Pool Rejected%\": 0.0,\n" +
                                                "      \"Pool Stale%\": 0.0\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"POOL\": 1,\n" +
                                                "      \"Name\": \"hub.miningpoolhub.com\",\n" +
                                                "      \"URL\": \"stratum+tcp://hub.miningpoolhub.com:17014\",\n" +
                                                "      \"Profile\": \"\",\n" +
                                                "      \"Algorithm\": \"qubit\",\n" +
                                                "      \"Algorithm Type\": \"Qubit\",\n" +
                                                "      \"Description\": \"\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 0,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 32,\n" +
                                                "      \"Accepted\": 3788,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 1921,\n" +
                                                "      \"Discarded\": 364,\n" +
                                                "      \"Stale\": 18,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"ZZZZZZZZZZZZZZZZZZZZZZZZ\",\n" +
                                                "      \"Last Share Time\": 1533397884,\n" +
                                                "      \"Diff1 Shares\": 478192.615987,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 385643.58659327,\n" +
                                                "      \"Difficulty Rejected\": 0.0,\n" +
                                                "      \"Difficulty Stale\": 1981.00918272,\n" +
                                                "      \"Last Share Difficulty\": 149.66147328,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"hub.miningpoolhub.com\",\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 1860019.542141,\n" +
                                                "      \"Pool Rejected%\": 0.0,\n" +
                                                "      \"Pool Stale%\": 0.5111\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"id\": 1\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("hub.miningpoolhub.com:17016")
                                        .setPriority(3)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                10,
                                                0,
                                                0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("hub.miningpoolhub.com:17014")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                3788,
                                                0,
                                                18)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("10137551800.0000"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(0)
                                                        .setSpeedUnits("%")
                                                        .build())
                                        .addTemp(47)
                                        .addTemp(47)
                                        .addTemp(46)
                                        .build())
                        .build());
    }
}