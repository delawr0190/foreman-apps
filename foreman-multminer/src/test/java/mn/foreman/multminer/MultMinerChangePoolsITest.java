package mn.foreman.multminer;

import mn.foreman.util.AbstractChangePoolsITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.HttpHandler;

import com.google.common.collect.ImmutableMap;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Test changing pools on a multminer. */
@RunWith(Parameterized.class)
public class MultMinerChangePoolsITest
        extends AbstractChangePoolsITest {

    /**
     * Constructor.
     *
     * @param handlers The handlers.
     */
    public MultMinerChangePoolsITest(
            final Map<String, HttpHandler> handlers) {
        super(
                new MultMinerChangePoolsStrategy(),
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
                                // MultMiner M1
                                ImmutableMap.of(
                                        "/index.csp",
                                        new HttpHandler(
                                                "act=pol&p0url=-o+stratum%2Btcp%3A%2F%2Fmy-test-pool1.com%3A5588&p0user=-u+my-test-username1&p0pwd=-p+my-test-password1&p1url=-o1+stratum%2Btcp%3A%2F%2Fmy-test-pool2.com%3A5588&p1user=-u1+my-test-username2&p1pwd=-p1+my-test-password2&p2url=-o2+stratum%2Btcp%3A%2F%2Fmy-test-pool3.com%3A5588&p2user=-u2+my-test-username3&p2pwd=-p2+my-test-password3",
                                                "ok"))
                        }
                });
    }
}