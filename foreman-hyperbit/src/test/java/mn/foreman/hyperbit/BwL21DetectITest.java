package mn.foreman.hyperbit;

import mn.foreman.cgminer.CgMinerDetectionStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
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
                        new HyperbitTypeFactory()),
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
                                                "}"))),
                Detection.builder()
                        .minerType(HyperbitType.BW_L21)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .build());
    }
}