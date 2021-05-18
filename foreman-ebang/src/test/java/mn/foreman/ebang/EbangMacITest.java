package mn.foreman.ebang;

import mn.foreman.util.AbstractMacITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/** Tests obtaining a MAC address from an ebang. */
public class EbangMacITest
        extends AbstractMacITest {

    /** Constructor. */
    public EbangMacITest() {
        super(
                new EbangFactory(
                        1,
                        TimeUnit.SECONDS,
                        new ObjectMapper()),
                Collections.singletonList(
                        new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.<String, ServerHandler>builder()
                                        .put(
                                                "/user/login",
                                                new HttpHandler(
                                                        "username=my-auth-username&word=my-auth-password&yuyan=1&login=Login&get_password=",
                                                        Collections.emptyMap(),
                                                        "",
                                                        ImmutableMap.of(
                                                                "Set-Cookie",
                                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473; path=/; domain=127.0.0.1; httponly")))
                                        .put(
                                                "/admininfo/Getadmininfo",
                                                new HttpHandler(
                                                        "",
                                                        ImmutableMap.of(
                                                                "Cookie",
                                                                "-http-session-=1::http.session::647531ff4c6d47243a8ed9a41a26e473"),
                                                        "{\"status\": 1, \"feedback\": {\"newipaddr\":\"10.0.4.23\",\"macaddr\":\"8C:C7:D0:1B:76:F3\",\"dnsaddr\":\"10.0.0.1\",\"ipmask\":\"255.255.0.0\",\"geteway\":\"10.0.0.1\",\"dhcpstatus\":0,\"dhcpnc\":0,\"dhcpovertime\":10}}",
                                                        Collections.emptyMap()))
                                        .build())),
                "8c:c7:d0:1b:76:f3");
    }
}
