package mn.foreman.antminer;

import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.util.AbstractSyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Tests changing passwords against a stock Antminer. */
@RunWith(Parameterized.class)
public class AntminerPasswordITest
        extends AbstractSyncActionITest {

    /**
     * Constructor.
     *
     * @param httpHandlers The handlers.
     * @param rpcHandlers  The RPC handlers.
     */
    public AntminerPasswordITest(
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, HandlerInterface> rpcHandlers) {
        super(
                8080,
                new FirmwareAwareAction(
                        "antMiner Configuration",
                        new StockPasswordAction(
                                "antMiner Configuration",
                                new ApplicationConfiguration()),
                        new BraiinsPasswordAction(),
                        new ApplicationConfiguration()),
                Arrays.asList(
                        () -> new FakeRpcMinerServer(
                                4028,
                                rpcHandlers),
                        () -> new FakeHttpMinerServer(
                                8080,
                                httpHandlers)),
                ImmutableMap.of(
                        "oldPassword",
                        "old",
                        "newPassword",
                        "new"),
                true,
                false);
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
                                // Antminer L3
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum-ltc.antpool.com:8888\",\n" +
                                                        "\"user\" : \"antminer_1\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum-ltc.antpool.com:443\",\n" +
                                                        "\"user\" : \"antminer_1\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum.f2pool.com:8888\",\n" +
                                                        "\"user\" : \"ant.123\",\n" +
                                                        "\"pass\" : \"123\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-freq\" : \"384\"\n" +
                                                        "}",
                                                AntminerTestUtils::validateDigest),
                                        "/cgi-bin/passwd.cgi",
                                        new HttpHandler(
                                                "",
                                                Collections.emptyMap(),
                                                "current_pw=old&new_pw=new&new_pw_ctrl=new",
                                                "",
                                                Collections.emptyMap())),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}],\"id\":1}"))
                        },
                        {
                                // Antminer S19
                                ImmutableMap.of(
                                        "/cgi-bin/get_miner_conf.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"pools\" : [\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:3333\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:25\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "},\n" +
                                                        "{\n" +
                                                        "\"url\" : \"stratum+tcp://xxx:443\",\n" +
                                                        "\"user\" : \"xxx\",\n" +
                                                        "\"pass\" : \"xxx\"\n" +
                                                        "}\n" +
                                                        "]\n" +
                                                        ",\n" +
                                                        "\"api-listen\" : true,\n" +
                                                        "\"api-network\" : true,\n" +
                                                        "\"api-groups\" : \"A:stats:pools:devs:summary:version\",\n" +
                                                        "\"api-allow\" : \"A:0/0,W:*\",\n" +
                                                        "\"bitmain-fan-ctrl\" : false,\n" +
                                                        "\"bitmain-fan-pwm\" : \"100\",\n" +
                                                        "\"bitmain-use-vil\" : true,\n" +
                                                        "\"bitmain-freq\" : \"500\",\n" +
                                                        "\"bitmain-voltage\" : \"1280\",\n" +
                                                        "\"bitmain-ccdelay\" : \"1\",\n" +
                                                        "\"bitmain-pwth\" : \"1\",\n" +
                                                        "\"bitmain-work-mode\" : \"0\",\n" +
                                                        "\"bitmain-freq-level\" : \"100\"\n" +
                                                        "}",
                                                AntminerTestUtils::validateDigest),
                                        "/cgi-bin/passwd.cgi",
                                        new HttpHandler(
                                                "{\"curPwd\":\"old\",\"newPwd\":\"new\",\"confirmPwd\":\"new\"}",
                                                "ok",
                                                AntminerTestUtils::validateDigest)),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}],\"id\":1}"))
                        }
                });
    }
}