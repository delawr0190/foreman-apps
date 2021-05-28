package mn.foreman.goldshell;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.util.AbstractSyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;

/** Test changing passwords on a goldshell. */
public class GoldshellPasswordITest
        extends AbstractSyncActionITest {

    /** Constructor. */
    public GoldshellPasswordITest() {
        super(
                8080,
                new GoldshellPasswordAction(
                        new ApplicationConfiguration()),
                Collections.singletonList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                ImmutableMap.of(
                                        "/user/updatepd",
                                        new HttpHandler(
                                                "{\"oldpassword\":\"old\",\"newpassword\":\"new\",\"cfmpassword\":\"new\"}",
                                                "{}")))),
                ImmutableMap.of(
                        "oldPassword",
                        "old",
                        "newPassword",
                        "new"),
                true,
                false);
    }
}
