package mn.foreman.baikal;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.AbstractApiITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Runs an integration tests using {@link CgMiner} against a fake API. */
@RunWith(Parameterized.class)
public class BaikalStatsITest
        extends AbstractApiITest {

    /**
     * Constructor.
     *
     * @param rpcHandlers  The RPC handlers.
     * @param httpHandlers The HTTP handlers.
     * @param stats        The stats.
     */
    public BaikalStatsITest(
            final Map<String, HandlerInterface> rpcHandlers,
            final Map<String, ServerHandler> httpHandlers,
            final MinerStats stats) {
        super(
                new BaikalFactory()
                        .create(
                                ImmutableMap.of(
                                        "apiIp",
                                        "127.0.0.1",
                                        "apiPort",
                                        "4028",
                                        "webPort",
                                        "8080",
                                        "statsWhitelist",
                                        Arrays.asList(
                                                "DEVS.1.No Device",
                                                "DEVS.1.Device Hardware%"))),
                Arrays.asList(
                        new FakeRpcMinerServer(
                                4028,
                                rpcHandlers),
                        new FakeHttpMinerServer(
                                8080,
                                httpHandlers)),
                stats);
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
                                // Baikal X10
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
                                                        "}")),
                                Collections.emptyMap(),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("hub.miningpoolhub.com:17016")
                                                        .setWorker("ZZZZZZZZZZZZZZZZZZZZZZZ")
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
                                                        .setWorker("ZZZZZZZZZZZZZZZZZZZZZZZZ")
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
                                                        .setPowerState("qubit")
                                                        .addTemp(47)
                                                        .addTemp(47)
                                                        .addTemp(46)
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "DEVS.1.No Device",
                                                                        false,
                                                                        "DEVS.1.Device Hardware%",
                                                                        new BigDecimal("0.0")))
                                                        .build())
                                        .build()
                        },
                        {
                                // Baikal Giant
                                ImmutableMap.of(
                                        "{\"command\":\"devs\"}",
                                        new RpcHandler(
                                                "{\n" +
                                                        "  \"STATUS\": [\n" +
                                                        "    {\n" +
                                                        "      \"STATUS\": \"S\",\n" +
                                                        "      \"When\": 1598131826,\n" +
                                                        "      \"Code\": 9,\n" +
                                                        "      \"Msg\": \"8 GPU(s)\",\n" +
                                                        "      \"Description\": \"sgminer 5.5.6-b\"\n" +
                                                        "    }\n" +
                                                        "  ],\n" +
                                                        "  \"DEVS\": [\n" +
                                                        "    {\n" +
                                                        "      \"ASC\": 0,\n" +
                                                        "      \"Name\": \"BKLS\",\n" +
                                                        "      \"ID\": 0,\n" +
                                                        "      \"Enabled\": \"Y\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Temperature\": 51,\n" +
                                                        "      \"MHS av\": 683458897.1219,\n" +
                                                        "      \"MHS 5s\": 854.0053,\n" +
                                                        "      \"Accepted\": 335,\n" +
                                                        "      \"Rejected\": 1,\n" +
                                                        "      \"Hardware Errors\": 0,\n" +
                                                        "      \"Utility\": 0.7447,\n" +
                                                        "      \"Last Share Pool\": 2,\n" +
                                                        "      \"Last Share Time\": 1598131759,\n" +
                                                        "      \"Total MH\": 18446767111905.715,\n" +
                                                        "      \"Diff1 Work\": 173224,\n" +
                                                        "      \"Difficulty Accepted\": 5424,\n" +
                                                        "      \"Difficulty Rejected\": 16,\n" +
                                                        "      \"Last Share Difficulty\": 16,\n" +
                                                        "      \"No Device\": false,\n" +
                                                        "      \"Last Valid Work\": 1598131819,\n" +
                                                        "      \"Device Hardware%\": 0,\n" +
                                                        "      \"Device Rejected%\": 0.0092,\n" +
                                                        "      \"Device Elapsed\": 26990\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"ASC\": 1,\n" +
                                                        "      \"Name\": \"BKLS\",\n" +
                                                        "      \"ID\": 1,\n" +
                                                        "      \"Enabled\": \"Y\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Temperature\": 48,\n" +
                                                        "      \"MHS av\": 846.5047,\n" +
                                                        "      \"MHS 5s\": 846.6561,\n" +
                                                        "      \"Accepted\": 336,\n" +
                                                        "      \"Rejected\": 2,\n" +
                                                        "      \"Hardware Errors\": 0,\n" +
                                                        "      \"Utility\": 0.7469,\n" +
                                                        "      \"Last Share Pool\": 2,\n" +
                                                        "      \"Last Share Time\": 1598131822,\n" +
                                                        "      \"Total MH\": 22847423.6928,\n" +
                                                        "      \"Diff1 Work\": 174008,\n" +
                                                        "      \"Difficulty Accepted\": 5384,\n" +
                                                        "      \"Difficulty Rejected\": 32,\n" +
                                                        "      \"Last Share Difficulty\": 16,\n" +
                                                        "      \"No Device\": false,\n" +
                                                        "      \"Last Valid Work\": 1598131825,\n" +
                                                        "      \"Device Hardware%\": 0,\n" +
                                                        "      \"Device Rejected%\": 0.0184,\n" +
                                                        "      \"Device Elapsed\": 26990\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"ASC\": 2,\n" +
                                                        "      \"Name\": \"BKLS\",\n" +
                                                        "      \"ID\": 2,\n" +
                                                        "      \"Enabled\": \"Y\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Temperature\": 48,\n" +
                                                        "      \"MHS av\": 846.4914,\n" +
                                                        "      \"MHS 5s\": 848.6471,\n" +
                                                        "      \"Accepted\": 370,\n" +
                                                        "      \"Rejected\": 2,\n" +
                                                        "      \"Hardware Errors\": 0,\n" +
                                                        "      \"Utility\": 0.8225,\n" +
                                                        "      \"Last Share Pool\": 2,\n" +
                                                        "      \"Last Share Time\": 1598131742,\n" +
                                                        "      \"Total MH\": 22847066.2349,\n" +
                                                        "      \"Diff1 Work\": 171664,\n" +
                                                        "      \"Difficulty Accepted\": 5960,\n" +
                                                        "      \"Difficulty Rejected\": 32,\n" +
                                                        "      \"Last Share Difficulty\": 16,\n" +
                                                        "      \"No Device\": false,\n" +
                                                        "      \"Last Valid Work\": 1598131825,\n" +
                                                        "      \"Device Hardware%\": 0,\n" +
                                                        "      \"Device Rejected%\": 0.0186,\n" +
                                                        "      \"Device Elapsed\": 26990\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"ASC\": 3,\n" +
                                                        "      \"Name\": \"BKLS\",\n" +
                                                        "      \"ID\": 3,\n" +
                                                        "      \"Enabled\": \"Y\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Temperature\": 49,\n" +
                                                        "      \"MHS av\": 846.7743,\n" +
                                                        "      \"MHS 5s\": 846.0428,\n" +
                                                        "      \"Accepted\": 339,\n" +
                                                        "      \"Rejected\": 5,\n" +
                                                        "      \"Hardware Errors\": 0,\n" +
                                                        "      \"Utility\": 0.7536,\n" +
                                                        "      \"Last Share Pool\": 2,\n" +
                                                        "      \"Last Share Time\": 1598131727,\n" +
                                                        "      \"Total MH\": 22854700.5235,\n" +
                                                        "      \"Diff1 Work\": 175104,\n" +
                                                        "      \"Difficulty Accepted\": 5504,\n" +
                                                        "      \"Difficulty Rejected\": 80,\n" +
                                                        "      \"Last Share Difficulty\": 16,\n" +
                                                        "      \"No Device\": false,\n" +
                                                        "      \"Last Valid Work\": 1598131822,\n" +
                                                        "      \"Device Hardware%\": 0,\n" +
                                                        "      \"Device Rejected%\": 0.0457,\n" +
                                                        "      \"Device Elapsed\": 26990\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"ASC\": 4,\n" +
                                                        "      \"Name\": \"BKLS\",\n" +
                                                        "      \"ID\": 4,\n" +
                                                        "      \"Enabled\": \"Y\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Temperature\": 51,\n" +
                                                        "      \"MHS av\": 846.8746,\n" +
                                                        "      \"MHS 5s\": 848.3875,\n" +
                                                        "      \"Accepted\": 325,\n" +
                                                        "      \"Rejected\": 5,\n" +
                                                        "      \"Hardware Errors\": 0,\n" +
                                                        "      \"Utility\": 0.7225,\n" +
                                                        "      \"Last Share Pool\": 2,\n" +
                                                        "      \"Last Share Time\": 1598131674,\n" +
                                                        "      \"Total MH\": 22857409.7203,\n" +
                                                        "      \"Diff1 Work\": 174624,\n" +
                                                        "      \"Difficulty Accepted\": 5208,\n" +
                                                        "      \"Difficulty Rejected\": 80,\n" +
                                                        "      \"Last Share Difficulty\": 16,\n" +
                                                        "      \"No Device\": false,\n" +
                                                        "      \"Last Valid Work\": 1598131820,\n" +
                                                        "      \"Device Hardware%\": 0,\n" +
                                                        "      \"Device Rejected%\": 0.0458,\n" +
                                                        "      \"Device Elapsed\": 26990\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"ASC\": 5,\n" +
                                                        "      \"Name\": \"BKLS\",\n" +
                                                        "      \"ID\": 5,\n" +
                                                        "      \"Enabled\": \"Y\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Temperature\": 49,\n" +
                                                        "      \"MHS av\": 846.8645,\n" +
                                                        "      \"MHS 5s\": 843.464,\n" +
                                                        "      \"Accepted\": 309,\n" +
                                                        "      \"Rejected\": 2,\n" +
                                                        "      \"Hardware Errors\": 0,\n" +
                                                        "      \"Utility\": 0.6869,\n" +
                                                        "      \"Last Share Pool\": 2,\n" +
                                                        "      \"Last Share Time\": 1598131718,\n" +
                                                        "      \"Total MH\": 22857135.2064,\n" +
                                                        "      \"Diff1 Work\": 172528,\n" +
                                                        "      \"Difficulty Accepted\": 5024,\n" +
                                                        "      \"Difficulty Rejected\": 32,\n" +
                                                        "      \"Last Share Difficulty\": 16,\n" +
                                                        "      \"No Device\": false,\n" +
                                                        "      \"Last Valid Work\": 1598131824,\n" +
                                                        "      \"Device Hardware%\": 0,\n" +
                                                        "      \"Device Rejected%\": 0.0185,\n" +
                                                        "      \"Device Elapsed\": 26990\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"ASC\": 6,\n" +
                                                        "      \"Name\": \"BKLS\",\n" +
                                                        "      \"ID\": 6,\n" +
                                                        "      \"Enabled\": \"Y\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Temperature\": 44,\n" +
                                                        "      \"MHS av\": 847.011,\n" +
                                                        "      \"MHS 5s\": 844.6986,\n" +
                                                        "      \"Accepted\": 332,\n" +
                                                        "      \"Rejected\": 4,\n" +
                                                        "      \"Hardware Errors\": 0,\n" +
                                                        "      \"Utility\": 0.738,\n" +
                                                        "      \"Last Share Pool\": 2,\n" +
                                                        "      \"Last Share Time\": 1598131765,\n" +
                                                        "      \"Total MH\": 22861090.5907,\n" +
                                                        "      \"Diff1 Work\": 172920,\n" +
                                                        "      \"Difficulty Accepted\": 5392,\n" +
                                                        "      \"Difficulty Rejected\": 80,\n" +
                                                        "      \"Last Share Difficulty\": 16,\n" +
                                                        "      \"No Device\": false,\n" +
                                                        "      \"Last Valid Work\": 1598131824,\n" +
                                                        "      \"Device Hardware%\": 0,\n" +
                                                        "      \"Device Rejected%\": 0.0463,\n" +
                                                        "      \"Device Elapsed\": 26990\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"ASC\": 7,\n" +
                                                        "      \"Name\": \"BKLS\",\n" +
                                                        "      \"ID\": 7,\n" +
                                                        "      \"Enabled\": \"Y\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Temperature\": 44,\n" +
                                                        "      \"MHS av\": 846.8412,\n" +
                                                        "      \"MHS 5s\": 843.0033,\n" +
                                                        "      \"Accepted\": 329,\n" +
                                                        "      \"Rejected\": 4,\n" +
                                                        "      \"Hardware Errors\": 0,\n" +
                                                        "      \"Utility\": 0.7314,\n" +
                                                        "      \"Last Share Pool\": 2,\n" +
                                                        "      \"Last Share Time\": 1598131694,\n" +
                                                        "      \"Total MH\": 22856507.5354,\n" +
                                                        "      \"Diff1 Work\": 174200,\n" +
                                                        "      \"Difficulty Accepted\": 5304,\n" +
                                                        "      \"Difficulty Rejected\": 64,\n" +
                                                        "      \"Last Share Difficulty\": 16,\n" +
                                                        "      \"No Device\": false,\n" +
                                                        "      \"Last Valid Work\": 1598131826,\n" +
                                                        "      \"Device Hardware%\": 0,\n" +
                                                        "      \"Device Rejected%\": 0.0367,\n" +
                                                        "      \"Device Elapsed\": 26990\n" +
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
                                                        "      \"When\": 1598131826,\n" +
                                                        "      \"Code\": 7,\n" +
                                                        "      \"Msg\": \"8 Pool(s)\",\n" +
                                                        "      \"Description\": \"sgminer 5.5.6-b\"\n" +
                                                        "    }\n" +
                                                        "  ],\n" +
                                                        "  \"POOLS\": [\n" +
                                                        "    {\n" +
                                                        "      \"POOL\": 0,\n" +
                                                        "      \"Name\": \"000\",\n" +
                                                        "      \"URL\": \"stratum+tcp://000:6003/\",\n" +
                                                        "      \"Profile\": \"\",\n" +
                                                        "      \"Algorithm\": \"x14\",\n" +
                                                        "      \"Algorithm Type\": \"x14\",\n" +
                                                        "      \"Description\": \"\",\n" +
                                                        "      \"Status\": \"Dead\",\n" +
                                                        "      \"Priority\": 0,\n" +
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
                                                        "      \"User\": \"xxx\",\n" +
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
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"POOL\": 1,\n" +
                                                        "      \"Name\": \"111\",\n" +
                                                        "      \"URL\": \"stratum+tcp://111:3333/\",\n" +
                                                        "      \"Profile\": \"\",\n" +
                                                        "      \"Algorithm\": \"x14\",\n" +
                                                        "      \"Algorithm Type\": \"x14\",\n" +
                                                        "      \"Description\": \"\",\n" +
                                                        "      \"Status\": \"Dead\",\n" +
                                                        "      \"Priority\": 1,\n" +
                                                        "      \"Quota\": 1,\n" +
                                                        "      \"Long Poll\": \"N\",\n" +
                                                        "      \"Getworks\": 0,\n" +
                                                        "      \"Accepted\": 0,\n" +
                                                        "      \"Rejected\": 1,\n" +
                                                        "      \"Works\": 0,\n" +
                                                        "      \"Discarded\": 0,\n" +
                                                        "      \"Stale\": 0,\n" +
                                                        "      \"Get Failures\": 1,\n" +
                                                        "      \"Remote Failures\": 0,\n" +
                                                        "      \"User\": \"xxx\",\n" +
                                                        "      \"Last Share Time\": 0,\n" +
                                                        "      \"Diff1 Shares\": 0,\n" +
                                                        "      \"Proxy Type\": \"\",\n" +
                                                        "      \"Proxy\": \"\",\n" +
                                                        "      \"Difficulty Accepted\": 0,\n" +
                                                        "      \"Difficulty Rejected\": 1,\n" +
                                                        "      \"Difficulty Stale\": 0,\n" +
                                                        "      \"Last Share Difficulty\": 0,\n" +
                                                        "      \"Has Stratum\": true,\n" +
                                                        "      \"Stratum Active\": false,\n" +
                                                        "      \"Stratum URL\": \"\",\n" +
                                                        "      \"Has GBT\": false,\n" +
                                                        "      \"Best Share\": 0,\n" +
                                                        "      \"Pool Rejected%\": 100,\n" +
                                                        "      \"Pool Stale%\": 0\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"POOL\": 2,\n" +
                                                        "      \"Name\": \"222\",\n" +
                                                        "      \"URL\": \"stratum+tcp://222:6002/\",\n" +
                                                        "      \"Profile\": \"\",\n" +
                                                        "      \"Algorithm\": \"x13\",\n" +
                                                        "      \"Algorithm Type\": \"x13\",\n" +
                                                        "      \"Description\": \"\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Priority\": 3,\n" +
                                                        "      \"Quota\": 1,\n" +
                                                        "      \"Long Poll\": \"N\",\n" +
                                                        "      \"Getworks\": 535,\n" +
                                                        "      \"Accepted\": 2675,\n" +
                                                        "      \"Rejected\": 25,\n" +
                                                        "      \"Works\": 67344,\n" +
                                                        "      \"Discarded\": 15039,\n" +
                                                        "      \"Stale\": 0,\n" +
                                                        "      \"Get Failures\": 0,\n" +
                                                        "      \"Remote Failures\": 0,\n" +
                                                        "      \"User\": \"xxx\",\n" +
                                                        "      \"Last Share Time\": 1598131822,\n" +
                                                        "      \"Diff1 Shares\": 1388272,\n" +
                                                        "      \"Proxy Type\": \"\",\n" +
                                                        "      \"Proxy\": \"\",\n" +
                                                        "      \"Difficulty Accepted\": 43200,\n" +
                                                        "      \"Difficulty Rejected\": 416,\n" +
                                                        "      \"Difficulty Stale\": 0,\n" +
                                                        "      \"Last Share Difficulty\": 16,\n" +
                                                        "      \"Has Stratum\": true,\n" +
                                                        "      \"Stratum Active\": true,\n" +
                                                        "      \"Stratum URL\": \"222\",\n" +
                                                        "      \"Has GBT\": false,\n" +
                                                        "      \"Best Share\": 66333.79038,\n" +
                                                        "      \"Pool Rejected%\": 0.9538,\n" +
                                                        "      \"Pool Stale%\": 0\n" +
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"POOL\": 3,\n" +
                                                        "      \"Name\": \"333\",\n" +
                                                        "      \"URL\": \"stratum+tcp://333:3333/\",\n" +
                                                        "      \"Profile\": \"\",\n" +
                                                        "      \"Algorithm\": \"x13\",\n" +
                                                        "      \"Algorithm Type\": \"x13\",\n" +
                                                        "      \"Description\": \"\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Priority\": 4,\n" +
                                                        "      \"Quota\": 1,\n" +
                                                        "      \"Long Poll\": \"N\",\n" +
                                                        "      \"Getworks\": 1,\n" +
                                                        "      \"Accepted\": 0,\n" +
                                                        "      \"Rejected\": 0,\n" +
                                                        "      \"Works\": 0,\n" +
                                                        "      \"Discarded\": 0,\n" +
                                                        "      \"Stale\": 0,\n" +
                                                        "      \"Get Failures\": 0,\n" +
                                                        "      \"Remote Failures\": 0,\n" +
                                                        "      \"User\": \"xxx\",\n" +
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
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"POOL\": 4,\n" +
                                                        "      \"Name\": \"444\",\n" +
                                                        "      \"URL\": \"stratum+tcp://444:3323/\",\n" +
                                                        "      \"Profile\": \"\",\n" +
                                                        "      \"Algorithm\": \"x11\",\n" +
                                                        "      \"Algorithm Type\": \"x11\",\n" +
                                                        "      \"Description\": \"\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Priority\": 91,\n" +
                                                        "      \"Quota\": 1,\n" +
                                                        "      \"Long Poll\": \"N\",\n" +
                                                        "      \"Getworks\": 2,\n" +
                                                        "      \"Accepted\": 0,\n" +
                                                        "      \"Rejected\": 0,\n" +
                                                        "      \"Works\": 0,\n" +
                                                        "      \"Discarded\": 0,\n" +
                                                        "      \"Stale\": 0,\n" +
                                                        "      \"Get Failures\": 0,\n" +
                                                        "      \"Remote Failures\": 0,\n" +
                                                        "      \"User\": \"xxx\",\n" +
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
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"POOL\": 5,\n" +
                                                        "      \"Name\": \"555\",\n" +
                                                        "      \"URL\": \"stratum+tcp://555:3343/\",\n" +
                                                        "      \"Profile\": \"\",\n" +
                                                        "      \"Algorithm\": \"x11\",\n" +
                                                        "      \"Algorithm Type\": \"x11\",\n" +
                                                        "      \"Description\": \"\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Priority\": 92,\n" +
                                                        "      \"Quota\": 1,\n" +
                                                        "      \"Long Poll\": \"N\",\n" +
                                                        "      \"Getworks\": 2,\n" +
                                                        "      \"Accepted\": 0,\n" +
                                                        "      \"Rejected\": 0,\n" +
                                                        "      \"Works\": 0,\n" +
                                                        "      \"Discarded\": 0,\n" +
                                                        "      \"Stale\": 0,\n" +
                                                        "      \"Get Failures\": 0,\n" +
                                                        "      \"Remote Failures\": 0,\n" +
                                                        "      \"User\": \"xxx\",\n" +
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
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"POOL\": 6,\n" +
                                                        "      \"Name\": \"666\",\n" +
                                                        "      \"URL\": \"stratum+tcp://666:3337/\",\n" +
                                                        "      \"Profile\": \"\",\n" +
                                                        "      \"Algorithm\": \"x13\",\n" +
                                                        "      \"Algorithm Type\": \"x13\",\n" +
                                                        "      \"Description\": \"\",\n" +
                                                        "      \"Status\": \"Alive\",\n" +
                                                        "      \"Priority\": 600,\n" +
                                                        "      \"Quota\": 1,\n" +
                                                        "      \"Long Poll\": \"N\",\n" +
                                                        "      \"Getworks\": 2,\n" +
                                                        "      \"Accepted\": 0,\n" +
                                                        "      \"Rejected\": 0,\n" +
                                                        "      \"Works\": 5,\n" +
                                                        "      \"Discarded\": 0,\n" +
                                                        "      \"Stale\": 0,\n" +
                                                        "      \"Get Failures\": 0,\n" +
                                                        "      \"Remote Failures\": 0,\n" +
                                                        "      \"User\": \"xxx\",\n" +
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
                                                        "    },\n" +
                                                        "    {\n" +
                                                        "      \"POOL\": 7,\n" +
                                                        "      \"Name\": \"777\",\n" +
                                                        "      \"URL\": \"stratum+tcp://777:5334/\",\n" +
                                                        "      \"Profile\": \"\",\n" +
                                                        "      \"Algorithm\": \"x14\",\n" +
                                                        "      \"Algorithm Type\": \"x14\",\n" +
                                                        "      \"Description\": \"\",\n" +
                                                        "      \"Status\": \"Dead\",\n" +
                                                        "      \"Priority\": 701,\n" +
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
                                                        "      \"User\": \"xxx\",\n" +
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
                                                        "}")),
                                Collections.emptyMap(),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("000:6003")
                                                        .setWorker("xxx")
                                                        .setPriority(0)
                                                        .setStatus(
                                                                true,
                                                                false)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("111:3333")
                                                        .setWorker("xxx")
                                                        .setPriority(1)
                                                        .setStatus(
                                                                true,
                                                                false)
                                                        .setCounts(
                                                                0,
                                                                1,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("222:6002")
                                                        .setWorker("xxx")
                                                        .setPriority(3)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                2675,
                                                                25,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("333:3333")
                                                        .setWorker("xxx")
                                                        .setPriority(4)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("444:3323")
                                                        .setWorker("xxx")
                                                        .setPriority(91)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("555:3343")
                                                        .setWorker("xxx")
                                                        .setPriority(92)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("666:3337")
                                                        .setWorker("xxx")
                                                        .setPriority(600)
                                                        .setStatus(
                                                                true,
                                                                true)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addPool(
                                                new Pool.Builder()
                                                        .setName("777:5334")
                                                        .setWorker("xxx")
                                                        .setPriority(701)
                                                        .setStatus(
                                                                true,
                                                                false)
                                                        .setCounts(
                                                                0,
                                                                0,
                                                                0)
                                                        .build())
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(new BigDecimal("6774904700.0000"))
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .addTemp(51)
                                                        .addTemp(48)
                                                        .addTemp(48)
                                                        .addTemp(49)
                                                        .addTemp(51)
                                                        .addTemp(49)
                                                        .addTemp(44)
                                                        .addTemp(44)
                                                        .setPowerState("x13")
                                                        .addRawStats(
                                                                ImmutableMap.of(
                                                                        "DEVS.1.No Device",
                                                                        false,
                                                                        "DEVS.1.Device Hardware%",
                                                                        new BigDecimal("0")))
                                                        .build())
                                        .build()
                        },
                        {
                                // Baikal (not hashing)
                                ImmutableMap.of(
                                        "{\"command\":\"pools\"}",
                                        new RpcHandler(
                                                "")),
                                ImmutableMap.of(
                                        "/login.php",
                                        new HttpHandler(
                                                "",
                                                "<html></html>")),
                                new MinerStats.Builder()
                                        .setApiIp("127.0.0.1")
                                        .setApiPort(4028)
                                        .addAsic(
                                                new Asic.Builder()
                                                        .setHashRate(BigDecimal.ZERO)
                                                        .setFanInfo(
                                                                new FanInfo.Builder()
                                                                        .setCount(0)
                                                                        .setSpeedUnits("%")
                                                                        .build())
                                                        .build())
                                        .build()
                        }
                });
    }
}