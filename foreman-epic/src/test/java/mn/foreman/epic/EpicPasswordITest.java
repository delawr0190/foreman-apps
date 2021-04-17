package mn.foreman.epic;

import mn.foreman.util.AbstractSyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Test changing passwords on an epic. */
public class EpicPasswordITest
        extends AbstractSyncActionITest {

    /** Constructor. */
    public EpicPasswordITest() {
        super(
                8080,
                new EpicPasswordAction(),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/password",
                                        new HttpHandler(
                                                "{\"param\":\"new\",\"password\":\"old\"}",
                                                "{\"result\":true,\"error\":null}")))),
                ImmutableMap.of(
                        "oldPassword",
                        "old",
                        "newPassword",
                        "new"),
                true,
                false);
    }
}
