package mn.foreman.pickaxe.itest;

import mn.foreman.pickaxe.itest.miner.FakeCgMiner;
import mn.foreman.pickaxe.itest.miner.FakeMiner;
import mn.foreman.pickaxe.itest.model.ExpectedMessages;

import com.google.common.collect.ImmutableMap;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * The following integration test validates that pickaxe can successfully
 * connect to multiple instances of cgminer, pull metrics, and upload those
 * metrics to the Foreman API.
 *
 * <p>Note: this test will only pass if a dev installation of pickaxe is
 * running.  It's specifically named as an "ITest" so that the Maven build, by
 * default, will not run this test.</p>
 *
 * <p>At the time of this writing, TravisCI performs a full build, properly
 * configures a local pickaxe installation, and then manually runs this
 * test.</p>
 *
 * <p>This test acts under the following assumptions:</p>
 *
 * <ol>
 *   <li>pickaxe is installed and running locally.</li>
 *   <li>pickaxe is configured to query 2 cgminers (one listening on
 *   127.0.0.1:42069, and the other listening on 127.0.0.1:42070).</li>
 *   <li>pickaxe has been configured with an API URL of
 *   http://127.0.0.1:8080/api.</li>
 * </ol>
 */
@Ignore
@RunWith(Parameterized.class)
public class PickaxeCgminerITest
        extends AbstractPickaxeITest {

    /**
     * Constructor.
     *
     * @param expectedPostFile The expected JSON to be sent to the server.
     * @param responseMap      The test data for each server.
     */
    public PickaxeCgminerITest(
            final String expectedPostFile,
            final Map<Integer, Map<String, String>> responseMap) {
        super(
                expectedPostFile,
                responseMap);
    }

    /**
     * Creates the test parameters.
     *
     * @return The test parameters.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[][]{
                        {
                                "/itest/cgminer/samples-1-2.json",
                                ImmutableMap.of(
                                        42069,
                                        ImmutableMap.of(
                                                "/itest/cgminer/pools/sample-1.request",
                                                "/itest/cgminer/pools/sample-1.response",
                                                "/itest/cgminer/stats/sample-1.request",
                                                "/itest/cgminer/stats/sample-1.response",
                                                "/itest/cgminer/version/sample-1.request",
                                                "/itest/cgminer/version/sample-1.response"),
                                        42070,
                                        ImmutableMap.of(
                                                "/itest/cgminer/pools/sample-2.request",
                                                "/itest/cgminer/pools/sample-2.response",
                                                "/itest/cgminer/stats/sample-2.request",
                                                "/itest/cgminer/stats/sample-2.response",
                                                "/itest/cgminer/version/sample-2.request",
                                                "/itest/cgminer/version/sample-2.response"))
                        }
                });
    }

    @Override
    protected FakeMiner createMiner(
            final int port,
            final ExpectedMessages expectedMessages,
            final Executor threadPool) {
        return new FakeCgMiner(
                port,
                expectedMessages,
                threadPool);
    }
}
