package mn.foreman.dragonmint;

import mn.foreman.util.AbstractSyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Test changing passwords on a Dragonmint. */
public class DragonmintPasswordITest
        extends AbstractSyncActionITest {

    /** Constructor. */
    public DragonmintPasswordITest() {
        super(
                8080,
                new DragonmintPasswordAction(),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/api/updatePassword",
                                        new HttpHandler(
                                                "user=admin&currentPassword=old&newPassword=new",
                                                "{\"success\": true}",
                                                DragonmintTestUtils::validateBasicAuth)))),
                ImmutableMap.of(
                        "oldPassword",
                        "old",
                        "newPassword",
                        "new"),
                true,
                false);
    }
}
