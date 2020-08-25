package mn.foreman.baikal;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Tests detection of a Baikal. */
@RunWith(Parameterized.class)
public class BaikalDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     * @param type     The type.
     */
    public BaikalDetectITest(
            final Map<String, HandlerInterface> handlers,
            final BaikalType type) {
        super(
                new CgMinerDetectionStrategy(
                        CgMinerCommand.DEVS,
                        new BaikalTypeFactory()),
                () -> new FakeRpcMinerServer(
                        4028,
                        handlers),
                Detection.builder()
                        .minerType(type)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(DEFAULT_ARGS)
                        .build());
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
                                                        "}")),
                                BaikalType.X
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
                                                        "}")),
                                BaikalType.GIANT
                        }
                });
    }
}