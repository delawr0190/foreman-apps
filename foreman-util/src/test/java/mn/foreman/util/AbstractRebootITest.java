package mn.foreman.util;

import mn.foreman.model.Detection;
import mn.foreman.model.RebootStrategy;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/** An abstract integration test for testing reboot operations. */
public abstract class AbstractRebootITest {

    /** A test thread pool. */
    protected static final ScheduledThreadPoolExecutor THREAD_POOL =
            new ScheduledThreadPoolExecutor(1);

    /** The default args. */
    private static final Map<String, Object> DEFAULT_ARGS =
            ImmutableMap.of(
                    "username",
                    "my-auth-username",
                    "password",
                    "my-auth-password",
                    "deadlineMillis",
                    "20000",
                    "ip",
                    "127.0.0.1");

    /** The arguments. */
    private final Map<String, Object> args;

    /** Whether or not a reboot should have occurred. */
    private final boolean expectedChange;

    /** The port. */
    private final int port;

    /** The {@link RebootStrategy} under test. */
    private final RebootStrategy rebootStrategy;

    /** The fake server supplier. */
    private final List<Supplier<FakeMinerServer>> serverSuppliers;

    /**
     * Constructor.
     *
     * @param port            The port.
     * @param apiPort         The api port.
     * @param rebootStrategy  The strategy under test.
     * @param serverSuppliers A {@link Supplier} for making servers.
     * @param expectedChange  Whether or not a change should have occurred.
     */
    public AbstractRebootITest(
            final int port,
            final int apiPort,
            final RebootStrategy rebootStrategy,
            final List<Supplier<FakeMinerServer>> serverSuppliers,
            final boolean expectedChange) {
        this.rebootStrategy = rebootStrategy;
        this.port = port;
        this.args = new HashMap<>(DEFAULT_ARGS);
        this.args.put("apiPort", Integer.toString(apiPort));
        this.serverSuppliers = new ArrayList<>(serverSuppliers);
        this.expectedChange = expectedChange;
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
     * Waits for test completion.
     *
     * @param completed The completed flag.
     *
     * @return Whether or not the test was completed in time.
     */
    private static boolean waitForCompletion(final AtomicBoolean completed) {
        long now = System.currentTimeMillis();
        final long deadlineInMillis = now + TimeUnit.MINUTES.toMillis(1);
        while (now < deadlineInMillis) {
            if (completed.get()) {
                return true;
            }

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (final InterruptedException ie) {
                // Ignore
            }
            now = System.currentTimeMillis();
        }
        return false;
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
        final List<FakeMinerServer> servers =
                this.serverSuppliers
                        .stream()
                        .map(Supplier::get)
                        .collect(Collectors.toList());
        try {
            servers.forEach(FakeMinerServer::start);
            try {
                final AtomicBoolean completed = new AtomicBoolean(false);
                final AtomicBoolean status = new AtomicBoolean(false);

                this.rebootStrategy.reboot(
                        "127.0.0.1",
                        port,
                        this.args,
                        new RebootStrategy.Callback() {
                            @Override
                            public void failed(final String message) {
                                status.set(false);
                                completed.set(true);
                            }

                            @Override
                            public void success() {
                                status.set(true);
                                completed.set(true);
                            }
                        });

                assertTrue(
                        waitForCompletion(
                                completed));
                assertEquals(
                        expectedChange,
                        status.get());
            } catch (final MinerException me) {
                assertFalse(
                        me.getMessage(),
                        expectedChange);
            }
            if (expectedChange) {
                servers.forEach(
                        server ->
                                assertTrue(
                                        server.waitTillDone(
                                                10,
                                                TimeUnit.SECONDS)));
            }
        } finally {
            servers.forEach(server -> {
                try {
                    server.close();
                } catch (final Exception e) {
                    // Ignore
                }
            });
        }
    }
}