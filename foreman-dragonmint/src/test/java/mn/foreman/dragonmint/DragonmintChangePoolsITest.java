package mn.foreman.dragonmint;

import mn.foreman.util.AbstractChangePoolsITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;

/** Test changing pools on a Dragonmint. */
public class DragonmintChangePoolsITest
        extends AbstractChangePoolsITest {

    /** Constructor. */
    public DragonmintChangePoolsITest() {
        super(
                new DragonmintChangePoolsStrategy(),
                () -> new FakeHttpMinerServer(
                        8080,
                        ImmutableMap.of(
                                "/api/updatePools",
                                new HttpHandler(
                                        "Pool1=stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&UserName1=my-test-username1&Password1=my-test-password1&Pool2=stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&UserName2=my-test-username2&Password2=my-test-password2&Pool3=stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&UserName3=my-test-username3&Password3=my-test-password3",
                                        "{\"success\": true}",
                                        DragonmintTestUtils::validateBasicAuth))),
                true);
    }
}
