package mn.foreman.baikal;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Tests detection of a Baikal. */
public class BaikalX10DetectITest
        extends AbstractDetectITest {

    /** Constructor. */
    public BaikalX10DetectITest() {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.DEVS,
                        new BaikalTypeFactory()),
                () -> new FakeRpcMinerServer(
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
                                                "}"))),
                Detection.builder()
                        .minerType(BaikalType.BAIKAL)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}