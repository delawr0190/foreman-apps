package mn.foreman.util;

import mn.foreman.model.*;
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

/** An abstract integration test for testing async asic operations. */
public abstract class AbstractAsyncActionITest {

    /** The default args. */
    private static final Map<String, Object> DEFAULT_ARGS =
            ImmutableMap.of(
                    "username",
                    "my-auth-username",
                    "password",
                    "my-auth-password",
                    "deadlineMillis",
                    20000,
                    "ip",
                    "127.0.0.1");

    /** A test thread pool. */
    private static final ScheduledThreadPoolExecutor THREAD_POOL =
            new ScheduledThreadPoolExecutor(1);

    /** The {@link AsicAction} under test. */
    private final AsicAction.CompletableAction action;

    /** The arguments. */
    private final Map<String, Object> args;

    /** Whether or not a reboot should have occurred. */
    private final boolean foundResult;

    /** The factory. */
    private final MinerFactory minerFactory;

    /** The result for when the miner couldn't be found. */
    private final boolean notFoundResult;

    /** The port. */
    private final int port;

    /** The fake server supplier. */
    private final List<Supplier<FakeMinerServer>> serverSuppliers;

    /**
     * Constructor.
     *
     * @param port            The port.
     * @param apiPort         The api port.
     * @param action          The action under test.
     * @param serverSuppliers A {@link Supplier} for making servers.
     * @param minerFactory    The miner factory.
     * @param additionalArgs  Any additional args.
     * @param foundResult     Whether or not a change should have occurred.
     * @param notFoundResult  The result for when the miner couldn't be found.
     */
    public AbstractAsyncActionITest(
            final int port,
            final int apiPort,
            final AsicAction.CompletableAction action,
            final List<Supplier<FakeMinerServer>> serverSuppliers,
            final MinerFactory minerFactory,
            final Map<String, Object> additionalArgs,
            final boolean foundResult,
            final boolean notFoundResult) {
        this.action = action;
        this.port = port;
        this.args = new HashMap<>(DEFAULT_ARGS);
        additionalArgs.forEach(this.args::put);
        this.args.put("apiPort", apiPort);
        this.serverSuppliers = new ArrayList<>(serverSuppliers);
        this.minerFactory = minerFactory;
        this.foundResult = foundResult;
        this.notFoundResult = notFoundResult;
    }

    /**
     * Constructor.
     *
     * @param port            The port.
     * @param apiPort         The api port.
     * @param action          The action under test.
     * @param serverSuppliers A {@link Supplier} for making servers.
     * @param minerFactory    The miner factory.
     * @param additionalArgs  Any additional args.
     * @param foundResult     Whether or not a change should have occurred.
     */
    public AbstractAsyncActionITest(
            final int port,
            final int apiPort,
            final AsicAction.CompletableAction action,
            final List<Supplier<FakeMinerServer>> serverSuppliers,
            final MinerFactory minerFactory,
            final Map<String, Object> additionalArgs,
            final boolean foundResult) {
        this(
                port,
                apiPort,
                action,
                serverSuppliers,
                minerFactory,
                additionalArgs,
                foundResult,
                false);
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
                this.foundResult,
                true);
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
                this.notFoundResult,
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
     * @param wasFound       Whether or not the miner was found.
     */
    private void runTest(
            final int port,
            final boolean expectedChange,
            final boolean wasFound)
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

                final AsicAction asicAction =
                        new AsyncAsicAction(
                                1,
                                1,
                                TimeUnit.SECONDS,
                                THREAD_POOL,
                                this.minerFactory,
                                this.action);
                asicAction.runAction(
                        "127.0.0.1",
                        port,
                        this.args,
                        new CompletionCallback() {

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
            if (wasFound) {
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
