package mn.foreman.obelisk;

import mn.foreman.util.AbstractChangePoolsITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/** Test changing pools on an Obelisk. */
@RunWith(Parameterized.class)
public class ObeliskChangePoolsITest
        extends AbstractChangePoolsITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     */
    public ObeliskChangePoolsITest(
            final Map<String, ServerHandler> handlers) {
        super(
                new ObeliskChangePoolsStrategy(),
                () -> new FakeHttpMinerServer(
                        8080,
                        handlers),
                true);
    }

    /**
     * Test parameters
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                // Obelisk SC1
                                ImmutableMap.of(
                                        "/api/login",
                                        new HttpHandler(
                                                "{\"username\":\"my-auth-username\",\"password\":\"my-auth-password\"}",
                                                Collections.emptyMap(),
                                                "",
                                                ImmutableMap.of(
                                                        "Set-Cookie",
                                                        "sessionid=foreman")),
                                        "/api/config/pools",
                                        new HttpHandler(
                                                "[{\"url\":\"stratum+tcp://my-test-pool1.com:5588\",\"worker\":\"my-test-username1\",\"password\":\"my-test-password1\"},{\"url\":\"stratum+tcp://my-test-pool2.com:5588\",\"worker\":\"my-test-username2\",\"password\":\"my-test-password2\"},{\"url\":\"stratum+tcp://my-test-pool3.com:5588\",\"worker\":\"my-test-username3\",\"password\":\"my-test-password3\"}]",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sessionid=foreman"),
                                                "",
                                                Collections.emptyMap()),
                                        "/api/logout",
                                        new HttpHandler(
                                                "",
                                                ImmutableMap.of(
                                                        "Cookie",
                                                        "sessionid=foreman"),
                                                "",
                                                Collections.emptyMap()))
                        }
                });
    }
}
