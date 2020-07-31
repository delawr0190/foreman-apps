package mn.foreman.obelisk;

import mn.foreman.util.AbstractRebootITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Test rebooting an Obelisk. */
public class ObeliskRebootITest
        extends AbstractRebootITest {

    /** Constructor. */
    public ObeliskRebootITest() {
        super(
                new ObeliskRebootStrategy(),
                () -> new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/api/login",
                                new HttpHandler(
                                        "{\"username\":\"my-auth-username\",\"password\":\"my-auth-password\"}",
                                        Collections.emptyMap(),
                                        "",
                                        ImmutableMap.of(
                                                "Set-Cookie",
                                                "sessionid=foreman")),
                                "/api/action/reboot",
                                new HttpHandler(
                                        "",
                                        ImmutableMap.of(
                                                "Cookie",
                                                "sessionid=foreman"),
                                        "",
                                        Collections.emptyMap()))),
                true);
    }
}
