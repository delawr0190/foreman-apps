package mn.foreman.util;

import mn.foreman.model.Detection;
import mn.foreman.model.RebootStrategy;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.Assert.*;

/** An abstract integration test for testing reboot operations. */
public abstract class AbstractRebootITest {

    /** The default port. */
    public static final int DEFAULT_PORT = 8080;

    /** The default args. */
    private static final Map<String, Object> DEFAULT_ARGS =
            ImmutableMap.of(
                    "username",
                    "my-auth-username",
                    "password",
                    "my-auth-password");

    /** The arguments. */
    private final Map<String, Object> args;

    /** Whether or not a reboot should have occurred. */
    private final boolean expectedChange;

    /** The port. */
    private final int port;

    /** The {@link RebootStrategy} under test. */
    private final RebootStrategy rebootStrategy;

    /** The fake server supplier. */
    private final Supplier<FakeMinerServer> serverSupplier;

    /**
     * Constructor.
     *
     * @param rebootStrategy The strategy under test.
     * @param port           The port.
     * @param args           The arguments.
     * @param serverSupplier A {@link Supplier} for making servers.
     * @param expectedChange Whether or not a change should have occurred.
     */
    public AbstractRebootITest(
            final RebootStrategy rebootStrategy,
            final int port,
            final Map<String, Object> args,
            final Supplier<FakeMinerServer> serverSupplier,
            final boolean expectedChange) {
        this.rebootStrategy = rebootStrategy;
        this.port = port;
        this.args = new HashMap<>(args);
        this.serverSupplier = serverSupplier;
        this.expectedChange = expectedChange;
    }

    /**
     * Constructor.
     *
     * @param rebootStrategy The strategy under test.
     * @param serverSupplier A {@link Supplier} for making servers.
     * @param expectedChange Whether or not a change should have occurred.
     */
    public AbstractRebootITest(
            final RebootStrategy rebootStrategy,
            final Supplier<FakeMinerServer> serverSupplier,
            final boolean expectedChange) {
        this(
                rebootStrategy,
                DEFAULT_PORT,
                DEFAULT_ARGS,
                serverSupplier,
                expectedChange);
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
                        this.rebootStrategy.reboot(
                                "127.0.0.1",
                                port,
                                this.args));
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