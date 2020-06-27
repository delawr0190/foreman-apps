package mn.foreman.util;

import mn.foreman.model.ChangePoolsStrategy;
import mn.foreman.model.Detection;
import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.Assert.*;

/** An abstract integration test for testing change pools operations. */
public abstract class AbstractChangePoolsITest {

    /** Default expected pools. */
    protected static final List<Pool> DEFAULT_POOLS =
            Arrays.asList(
                    Pool
                            .builder()
                            .url("stratum+tcp://my-test-pool1.com:5588")
                            .username("my-test-username1")
                            .password("my-test-password1")
                            .build(),
                    Pool
                            .builder()
                            .url("stratum+tcp://my-test-pool2.com:5588")
                            .username("my-test-username2")
                            .password("my-test-password2")
                            .build(),
                    Pool
                            .builder()
                            .url("stratum+tcp://my-test-pool3.com:5588")
                            .username("my-test-username3")
                            .password("my-test-password3")
                            .build());

    /** The default port. */
    protected static final int DEFAULT_PORT = 8080;

    /** The default args. */
    private static final Map<String, Object> DEFAULT_ARGS =
            ImmutableMap.of(
                    "username",
                    "my-auth-username",
                    "password",
                    "my-auth-password");

    /** The arguments. */
    private final Map<String, Object> args;

    /** The {@link ChangePoolsStrategy} under test. */
    private final ChangePoolsStrategy changePoolsStrategy;

    /** Whether or not a change should have occurred. */
    private final boolean expectedChange;

    /** The new pools. */
    private final List<Pool> pools;

    /** The port. */
    private final int port;

    /** The fake server supplier. */
    private final Supplier<FakeMinerServer> serverSupplier;

    /**
     * Constructor.
     *
     * @param changePoolsStrategy The strategy under test.
     * @param port                The port.
     * @param args                The arguments.
     * @param serverSupplier      A {@link Supplier} for making servers.
     * @param expectedChange      Whether or not a change should have occurred.
     * @param expectedPools       The new pools.
     */
    public AbstractChangePoolsITest(
            final ChangePoolsStrategy changePoolsStrategy,
            final int port,
            final Map<String, Object> args,
            final Supplier<FakeMinerServer> serverSupplier,
            final boolean expectedChange,
            final List<Pool> expectedPools) {
        this.changePoolsStrategy = changePoolsStrategy;
        this.port = port;
        this.args = new HashMap<>(args);
        this.serverSupplier = serverSupplier;
        this.expectedChange = expectedChange;
        this.pools = new ArrayList<>(expectedPools);
    }

    /**
     * Constructor.
     *
     * @param changePoolsStrategy The strategy under test.
     * @param serverSupplier      A {@link Supplier} for making servers.
     * @param expectedChange      Whether or not a change was expected.
     */
    public AbstractChangePoolsITest(
            final ChangePoolsStrategy changePoolsStrategy,
            final Supplier<FakeMinerServer> serverSupplier,
            final boolean expectedChange) {
        this(
                changePoolsStrategy,
                DEFAULT_PORT,
                DEFAULT_ARGS,
                serverSupplier,
                expectedChange,
                DEFAULT_POOLS);
    }

    /**
     * Tests detection.
     *
     * @throws Exception on failure.
     */
    @Test
    public void testFound()
            throws Exception {
        runTest(
                this.port,
                this.expectedChange);
    }

    /**
     * Tests detection.
     *
     * @throws Exception on failure.
     */
    @Test
    public void testNotFound()
            throws Exception {
        runTest(
                this.port + 1,
                false);
    }

    /**
     * Tests finding a {@link Detection}.
     *
     * @param port           The API port to query.
     * @param expectedChange Whether or not a change was expected.
     */
    private void runTest(
            final int port,
            final boolean expectedChange)
            throws Exception {
        try (final FakeMinerServer fakeMinerServer =
                     this.serverSupplier.get()) {
            fakeMinerServer.start();
            try {
                assertEquals(
                        expectedChange,
                        this.changePoolsStrategy.change(
                                "127.0.0.1",
                                port,
                                this.args,
                                this.pools));
            } catch (final MinerException me) {
                assertFalse(
                        me.getMessage(),
                        expectedChange);
            }
            if (expectedChange) {
                assertTrue(
                        fakeMinerServer.waitTillDone(
                                10,
                                TimeUnit.SECONDS));
            }
        }
    }
}