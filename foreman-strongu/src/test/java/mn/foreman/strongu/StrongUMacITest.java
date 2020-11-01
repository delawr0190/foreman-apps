package mn.foreman.strongu;

import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.util.AbstractMacITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests obtaining a MAC address from a StrongU. */
public class StrongUMacITest
        extends AbstractMacITest {

    /** Constructor. */
    public StrongUMacITest() {
        super(
                new StrongUFactory(),
                Collections.singletonList(
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
                                                        "\"conf_hostname\":\"stuMiner\",\n" +
                                                        "\"conf_ipaddress\":\"\",\n" +
                                                        "\"conf_netmask\":\"\",\n" +
                                                        "\"conf_gateway\":\"\",\n" +
                                                        "\"conf_dnsservers\":\"\"\n" +
                                                        "}",
                                                exchange ->
                                                        AntminerTestUtils.validateDigest(
                                                                exchange,
                                                                "stuMiner Configuration"))))),
                "C4:F3:12:B3:9F:FC");
    }
}
