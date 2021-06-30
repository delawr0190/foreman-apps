package mn.foreman.hyperbit;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests detection of a Hyperbit. */
public class BwL21DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public BwL21DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.DEVS,
                        new HyperbitTypeFactory(),
                        new ApplicationConfiguration()),
                () -> new FakeRpcMinerServer(
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
                                                "      \"User\": \"xxxx.yyyy\",\n" +
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
                Detection.builder()
                        .minerType(HyperbitType.BW_L21)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(DEFAULT_ARGS)
                        .build());
    }
}