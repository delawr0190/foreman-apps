package mn.foreman.strongu;

import mn.foreman.antminer.util.AntminerTestUtils;
import mn.foreman.model.ApplicationConfiguration;
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
                new StrongUFactory(new ApplicationConfiguration()),
                Collections.singletonList(
                        new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/cgi-bin/get_network_info.cgi",
                                        new HttpHandler(
                                                "",
                                                "{\n" +
                                                        "\"conf_nettype\":\"DHCP\",\n" +
                                                        "\"conf_ntpserver\":\"pool.ntp.org\",\n" +
                                                        "\"conf_ipaddress\":\"10.16.232.52\",\n" +
                                                        "\"conf_netmask\":\"255.255.252.0\",\n" +
                                                        "\"conf_gateway\":\"10.16.232.3\",\n" +
                                                        "\"conf_dnsservers\":\"10.16.232.3\",\n" +
                                                        "\"conf_macaddr\":\"00:8F:A6:97:59:C5\"\n" +
                                                        "}",
                                                exchange ->
                                                        AntminerTestUtils.validateDigest(
                                                                exchange,
                                                                "stuMiner Configuration"))))),
                "00:8F:A6:97:59:C5");
    }
}
