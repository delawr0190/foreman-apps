package mn.foreman.obelisk;

import mn.foreman.util.AbstractSyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;
import mn.foreman.util.http.ServerHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** Test changing passwords on an Obelisk. */
@RunWith(Parameterized.class)
public class ObeliskPasswordITest
        extends AbstractSyncActionITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     */
    public ObeliskPasswordITest(
            final Map<String, ServerHandler> handlers) {
        super(
                8080,
                new ObeliskPasswordAction(),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                handlers)),
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
    public static List<Object[]> parameters() {
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
                                        "/api/action/changePassword",
                                        new HttpHandler(
                                                "{\"username\":\"admin\",\"oldPassword\":\"old\",\"newPassword\":\"new\"}",
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
