package mn.foreman.baikal;

import mn.foreman.model.Detection;
import mn.foreman.util.AbstractDetectITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import one.util.streamex.EntryStream;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

/** Tests detection of a Baikal. */
@RunWith(Parameterized.class)
public class BaikalDetectITest
        extends AbstractDetectITest {

    /**
     * Constructor.
     *
     * @param httpHandlers    The HTTP handlers.
     * @param rpcHandlers     The RPC handlers.
     * @param type            The type.
     * @param workerPreferred Whether or not the worker name is preferred.
     * @param detectionArgs   The detection args.
     */
    public BaikalDetectITest(
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, HandlerInterface> rpcHandlers,
            final BaikalType type,
            final boolean workerPreferred,
            final Map<String, Object> detectionArgs) {
        super(
                new BaikalDetectionStrategy(
                        "80",
                        new BaikalFactory()
                                .create(
                                        ImmutableMap.of(
                                                "apiIp",
                                                "127.0.0.1",
                                                "apiPort",
                                                "4028"))),
                "127.0.0.1",
                4028,
                args(workerPreferred),
                Arrays.asList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                httpHandlers),
                        () -> new FakeRpcMinerServer(
                                4028,
                                rpcHandlers)),
                Detection.builder()
                        .minerType(type)
                        .ipAddress("127.0.0.1")
                        .port(4028)
                        .parameters(detectionArgs)
                        .build(),
                (integer, stringObjectMap) -> {
                    stringObjectMap.put(
                            "webPort",
                            (integer != 4028
                                    ? "8081"
                                    : "8080"));
                    return stringObjectMap;
                });
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
                                ImmutableMap.of(
                                        "/login.php",
                                        new HttpHandler(
                                                "",
                                                "<html></html>")),
                                Collections.emptyMap(),
                                BaikalType.GENERIC,
                                false,
                                args(false)
                        },
                        {
                                ImmutableMap.of(
                                        "/login.php",
                                        new HttpHandler(
                                                "",
                                                "<html></html>")),
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
                                BaikalType.GENERIC,
                                true,
                                EntryStream
                                        .of(args(true))
                                        .append(
                                                "worker",
                                                "ZZZZZZZZZZZZZZZZZZZZZZZ")
                                        .toMap()
                        }
                });
    }

    /**
     * Creates test args.
     *
     * @param workerPreferred Whether or not the workername is preferred.
     *
     * @return Test args.
     */
    private static Map<String, Object> args(final boolean workerPreferred) {
        final Map<String, Object> args = new HashMap<>(DEFAULT_ARGS);
        args.put("webPort", "8080");
        args.put("workerPreferred", Boolean.toString(workerPreferred));
        return args;
    }
}