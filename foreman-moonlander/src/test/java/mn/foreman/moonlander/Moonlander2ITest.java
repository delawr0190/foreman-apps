package mn.foreman.moonlander;

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
public class Moonlander2ITest
        extends AbstractApiITest {

    /** Constructor. */
    public Moonlander2ITest() {
        super(
                new MoonlanderFactory()
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
                                                "      \"When\": 1552838421,\n" +
                                                "      \"Code\": 9,\n" +
                                                "      \"Msg\": \"1 PGA(s)\",\n" +
                                                "      \"Description\": \"bfgminer 5.4.2\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"DEVS\": [\n" +
                                                "    {\n" +
                                                "      \"PGA\": 0,\n" +
                                                "      \"Name\": \"MLD\",\n" +
                                                "      \"ID\": 0,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Device Elapsed\": 82,\n" +
                                                "      \"MHS av\": 3.245,\n" +
                                                "      \"MHS 20s\": 2.962,\n" +
                                                "      \"MHS rolling\": 2.962,\n" +
                                                "      \"Accepted\": 27,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 1,\n" +
                                                "      \"Utility\": 19.791,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Last Share Pool\": 0,\n" +
                                                "      \"Last Share Time\": 1552838416,\n" +
                                                "      \"Total MH\": 265.6655,\n" +
                                                "      \"Diff1 Work\": 0.06738281,\n" +
                                                "      \"Work Utility\": 0.049,\n" +
                                                "      \"Difficulty Accepted\": 0.10546875,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 0.00390625,\n" +
                                                "      \"Last Valid Work\": 1552838420,\n" +
                                                "      \"Device Hardware%\": 0.7194,\n" +
                                                "      \"Device Rejected%\": 0\n" +
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
                                                "      \"When\": 1552838421,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"1 Pool(s)\",\n" +
                                                "      \"Description\": \"bfgminer 5.4.2\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"URL\": \"stratum+tcp:\\/\\/us.litecoinpool.org:3333\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 0,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Mining Goal\": \"default\",\n" +
                                                "      \"Long Poll\": \"N\",\n" +
                                                "      \"Getworks\": 6,\n" +
                                                "      \"Accepted\": 27,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 9,\n" +
                                                "      \"Discarded\": 15,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"\",\n" +
                                                "      \"Last Share Time\": 1552838416,\n" +
                                                "      \"Diff1 Shares\": 0.06738281,\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 0.10546875,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 0.00390625,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"us.litecoinpool.org\",\n" +
                                                "      \"Best Share\": 0.30697123,\n" +
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
                                        .setName("us.litecoinpool.org:3333")
                                        .setPriority(0)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                27,
                                                0,
                                                0)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("2962000.000"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(0)
                                                        .setSpeedUnits("RPM")
                                                        .build())
                                        .hasErrors(false)
                                        .build())
                        .build());
    }
}