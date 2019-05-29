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
public class BwL21ITest
        extends AbstractApiITest {

    /** Constructor. */
    public BwL21ITest() {
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
                                                "      \"When\": 1558529871,\n" +
                                                "      \"Code\": 9,\n" +
                                                "      \"Msg\": \"0 ASC(s)\",\n" +
                                                "      \"Description\": \"cpuminer 2.3.2\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"DEVS\": [\n" +
                                                "    {\n" +
                                                "      \"ASC\": 0,\n" +
                                                "      \"Name\": \"ttyS1\",\n" +
                                                "      \"ID\": 0,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"MHS av\": 132.41,\n" +
                                                "      \"MHS 5s\": 132.95,\n" +
                                                "      \"MHS 1m\": 131.91,\n" +
                                                "      \"MHS 5m\": 131.91,\n" +
                                                "      \"MHS 15m\": 131.91,\n" +
                                                "      \"Accepted\": 7,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Device Elapsed\": 270,\n" +
                                                "      \"temperature\": 34\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"ASC\": 1,\n" +
                                                "      \"Name\": \"ttyS2\",\n" +
                                                "      \"ID\": 1,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"MHS av\": 132.38,\n" +
                                                "      \"MHS 5s\": 132.38,\n" +
                                                "      \"MHS 1m\": 131.91,\n" +
                                                "      \"MHS 5m\": 131.91,\n" +
                                                "      \"MHS 15m\": 131.91,\n" +
                                                "      \"Accepted\": 7,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Device Elapsed\": 270,\n" +
                                                "      \"temperature\": 34\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"ASC\": 2,\n" +
                                                "      \"Name\": \"ttyS3\",\n" +
                                                "      \"ID\": 2,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"MHS av\": 132.44,\n" +
                                                "      \"MHS 5s\": 132.86,\n" +
                                                "      \"MHS 1m\": 132.34,\n" +
                                                "      \"MHS 5m\": 132.34,\n" +
                                                "      \"MHS 15m\": 132.34,\n" +
                                                "      \"Accepted\": 6,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Device Elapsed\": 270,\n" +
                                                "      \"temperature\": 34\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"ASC\": 3,\n" +
                                                "      \"Name\": \"ttyS4\",\n" +
                                                "      \"ID\": 3,\n" +
                                                "      \"Enabled\": \"Y\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"MHS av\": 132.35,\n" +
                                                "      \"MHS 5s\": 132.37,\n" +
                                                "      \"MHS 1m\": 132.41,\n" +
                                                "      \"MHS 5m\": 132.41,\n" +
                                                "      \"MHS 15m\": 132.41,\n" +
                                                "      \"Accepted\": 10,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Hardware Errors\": 0,\n" +
                                                "      \"Device Elapsed\": 270,\n" +
                                                "      \"temperature\": 34\n" +
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
                                                "      \"When\": 1558529871,\n" +
                                                "      \"Code\": 7,\n" +
                                                "      \"Msg\": \"3 Pool(s)\",\n" +
                                                "      \"Description\": \"cpuminer 2.3.2\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"POOLS\": [\n" +
                                                "    {\n" +
                                                "      \"POOL\": 0,\n" +
                                                "      \"URL\": \"stratum+tcp:\\/\\/us-central01.miningrigrentals.com:50934\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 16777216,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"Y\",\n" +
                                                "      \"Getworks\": 0,\n" +
                                                "      \"Accepted\": 0,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 0,\n" +
                                                "      \"Discarded\": 0,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"xxxx\",\n" +
                                                "      \"Passwd\": \"xxxx\",\n" +
                                                "      \"Last Share Time\": 1558529871,\n" +
                                                "      \"Diff1 Shares\": 2048,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 0,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 0,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"stratum+tcp:\\/\\/us-central01.miningrigrentals.com:50934\",\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 0,\n" +
                                                "      \"Pool Rejected%\": 0,\n" +
                                                "      \"Pool Stale%\": 0,\n" +
                                                "      \"Bad Work\": 0\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"POOL\": 1,\n" +
                                                "      \"URL\": \"stratum+tcp:\\/\\/prohashing.com:3333\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 16842753,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"Y\",\n" +
                                                "      \"Getworks\": 0,\n" +
                                                "      \"Accepted\": 30,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 0,\n" +
                                                "      \"Discarded\": 0,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"xxxx\",\n" +
                                                "      \"Passwd\": \"xxxx\",\n" +
                                                "      \"Last Share Time\": 1558529871,\n" +
                                                "      \"Diff1 Shares\": 2048,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 0,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 0,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"stratum+tcp:\\/\\/prohashing.com:3333\",\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 0,\n" +
                                                "      \"Pool Rejected%\": 0,\n" +
                                                "      \"Pool Stale%\": 0,\n" +
                                                "      \"Bad Work\": 0\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"POOL\": 2,\n" +
                                                "      \"URL\": \"stratum+tcp:\\/\\/us.litecoinpool.org:3333\",\n" +
                                                "      \"Status\": \"Alive\",\n" +
                                                "      \"Priority\": 2,\n" +
                                                "      \"Quota\": 1,\n" +
                                                "      \"Long Poll\": \"Y\",\n" +
                                                "      \"Getworks\": 0,\n" +
                                                "      \"Accepted\": 0,\n" +
                                                "      \"Rejected\": 0,\n" +
                                                "      \"Works\": 0,\n" +
                                                "      \"Discarded\": 0,\n" +
                                                "      \"Stale\": 0,\n" +
                                                "      \"Get Failures\": 0,\n" +
                                                "      \"Remote Failures\": 0,\n" +
                                                "      \"User\": \"xxxx\",\n" +
                                                "      \"Passwd\": \"xxxx\",\n" +
                                                "      \"Last Share Time\": 1558529871,\n" +
                                                "      \"Diff1 Shares\": 2048,\n" +
                                                "      \"Proxy Type\": \"\",\n" +
                                                "      \"Proxy\": \"\",\n" +
                                                "      \"Difficulty Accepted\": 0,\n" +
                                                "      \"Difficulty Rejected\": 0,\n" +
                                                "      \"Difficulty Stale\": 0,\n" +
                                                "      \"Last Share Difficulty\": 0,\n" +
                                                "      \"Has Stratum\": true,\n" +
                                                "      \"Stratum Active\": true,\n" +
                                                "      \"Stratum URL\": \"stratum+tcp:\\/\\/us.litecoinpool.org:3333\",\n" +
                                                "      \"Has GBT\": false,\n" +
                                                "      \"Best Share\": 0,\n" +
                                                "      \"Pool Rejected%\": 0,\n" +
                                                "      \"Pool Stale%\": 0,\n" +
                                                "      \"Bad Work\": 0\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"id\": 1\n" +
                                                "}"))),
                new MinerStats.Builder()
                        .setApiIp("127.0.0.1")
                        .setApiPort(4028)
                        .addPool(
                                new Pool.Builder()
                                        .setName("us-central01.miningrigrentals.com:50934")
                                        .setPriority(16777216)
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
                                        .setName("prohashing.com:3333")
                                        .setPriority(16842753)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                30,
                                                0,
                                                0)
                                        .build())
                        .addPool(
                                new Pool.Builder()
                                        .setName("us.litecoinpool.org:3333")
                                        .setPriority(2)
                                        .setStatus(
                                                true,
                                                true)
                                        .setCounts(
                                                0,
                                                0,
                                                0)
                                        .build())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(new BigDecimal("530560000.00"))
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(0)
                                                        .setSpeedUnits("%")
                                                        .build())
                                        .addTemp(34)
                                        .addTemp(34)
                                        .addTemp(34)
                                        .addTemp(34)
                                        .build())
                        .build());
    }
}