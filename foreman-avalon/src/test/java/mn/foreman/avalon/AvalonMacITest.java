package mn.foreman.avalon;

import mn.foreman.util.AbstractMacITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Tests obtaining a MAC address from an Avalon. */
public class AvalonMacITest
        extends AbstractMacITest {

    /** Constructor. */
    public AvalonMacITest() {
        super(
                new AvalonFactory(),
                Collections.singletonList(
                        new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/get_minerinfo.cgi",
                                        new HttpHandler(
                                                "",
                                                "minerinfoCallback({\"version\":\"20090401_fb963dc_1236943\",\"mac\":\"b4:a2:eb:31:2f:7c\", \"ipv4\":\"10.16.250.99\", \"hwtype\":\"AvalonMiner 1047\", \"sys_status\":\"1\", });")))),
                "b4:a2:eb:31:2f:7c");
    }
}
