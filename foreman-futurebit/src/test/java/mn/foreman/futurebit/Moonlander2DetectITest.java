package mn.foreman.futurebit;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests detection of a Moonlander. */
public class Moonlander2DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public Moonlander2DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.DEVS,
                        new FutureBitTypeFactory()),
                () -> new FakeRpcMinerServer(
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
                                                "}"))),
                Detection.builder()
                        .minerType(FutureBitType.FUTUREBIT_MOONLANDER)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}