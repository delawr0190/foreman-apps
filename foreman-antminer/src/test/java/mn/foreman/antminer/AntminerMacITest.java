package mn.foreman.antminer;

import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.util.AbstractMacITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Arrays;

/** Tests obtaining a MAC address from an Antminer. */
public class AntminerMacITest
        extends AbstractMacITest {

    /** Constructor. */
    public AntminerMacITest() {
        super(
                new AntminerFactory(BigDecimal.ONE),
                Arrays.asList(
                        new FakeHttpMinerServer(
                                8080,
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
                                                                "antMiner Configuration")))),
                        new FakeRpcMinerServer(
                                4028,
                                ImmutableMap.of(
                                        "{\"command\":\"version\"}",
                                        new RpcHandler(
                                                "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer D3\"}],\"id\":1}")))),
                "C4:F3:12:B3:9F:FC");
    }
}
