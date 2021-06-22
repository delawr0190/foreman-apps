package mn.foreman.antminer;

import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.util.AbstractMacITest;
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

/** Tests obtaining a MAC address from an Antminer. */
@RunWith(Parameterized.class)
public class AntminerMacITest
        extends AbstractMacITest {

    /**
     * Constructor.
     *
     * @param httpHandlers   The HTTP handlers.
     * @param serverHandlers The server handlers.
     * @param expectedMac    The expected MAC.
     */
    public AntminerMacITest(
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, HandlerInterface> serverHandlers,
            final String expectedMac) {
        super(
                new AntminerFactory(1),
                Arrays.asList(
                        new FakeHttpMinerServer(
                                8080,
                                httpHandlers),
                        new FakeRpcMinerServer(
                                4028,
                                serverHandlers)),
                expectedMac);
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
                                        "/cgi-bin/get_network_info.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"nettype\":\"DHCP\",\n" +
                                                        "\"netdevice\":\"eth0\",\n" +
                                                        "\"macaddr\":\"C4:F3:12:B3:9F:FC\",\n" +
                                                        "\"ipaddress\":\"192.168.1.189\",\n" +
                                                        "\"netmask\":\"255.255.255.0\",\n" +
                                                        "\"conf_nettype\":\"DHCP\",\n" +
                                                        "\"conf_hostname\":\"antMiner\",\n" +
                                                        "\"conf_ipaddress\":\"\",\n" +
                                                        "\"conf_netmask\":\"\",\n" +
                                                        "\"conf_gateway\":\"\",\n" +
                                                        "\"conf_dnsservers\":\"\"\n" +
                                                        "}",
                                                exchange ->
                                                        AntminerTestUtils.validateDigest(
                                                                exchange,
                                                                "antMiner Configuration"))),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer D3\"}],\"id\":1}")),
                                "C4:F3:12:B3:9F:FC"
                        },
                        {
                                ImmutableMap.of(
                                        "/cgi-bin/luci/admin/miner/overview",
                                        new HttpHandler(
                                                "luci_username=username&luci_password=password",
                                                Collections.emptyMap(),
                                                "<html></html>",
                                                ImmutableMap.of(
                                                        "Set-Cookie",
                                                        "sysauth=foreman")),
                                        "/cgi-bin/luci/admin/status/overview",
                                        new HttpHandler(
                                                "hosts=1",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sysauth=foreman"),
                                                "{\"18:31:BF:94:C5:20\":{\"ipv4\":\"192.168.1.185\"},\"20:C0:47:10:39:71\":{\"ipv4\":\"192.168.1.1\"},\"00:67:B4:DC:C9:A2\":{\"ipv6\":\"fe80::267:b4ff:fedc:c9a2\",\"ipv4\":\"127.0.0.1\"}}",
                                                Collections.emptyMap())),
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1597187881,\"Code\":22,\"Msg\":\"BOSminer+ versions\",\"Description\":\"BOSminer+ 0.2.0-36c56a9363\"}],\"VERSION\":[{\"API\":\"3.7\",\"BOSminer+\":\"0.2.0-36c56a9363\"}],\"id\":1}")),
                                "00:67:B4:DC:C9:A2"
                        }
                });
    }
}
