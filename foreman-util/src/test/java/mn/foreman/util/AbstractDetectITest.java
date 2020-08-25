package mn.foreman.util;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** An abstract integration test for testing miner detections. */
public abstract class AbstractDetectITest {

    /** A default test IP. */
    private static final String DEFAULT_IP = "127.0.0.1";

    /** A default test port. */
    private static final int DEFAULT_PORT = 4028;

    /** Default arguments. */
    protected static Map<String, Object> DEFAULT_ARGS =
            ImmutableMap.of(
                    "arg1",
                    "val1");

    /** The arguments. */
    private final Map<String, Object> args;

    /** The expected metrics. */
    private final Detection detection;

    /** The {@link DetectionStrategy} under test. */
    private final DetectionStrategy detectionStrategy;

    /** The IP. */
    private final String ip;

    /** The port. */
    private final int port;

    /** The fake server supplier. */
    private final Supplier<FakeMinerServer> serverSupplier;

    /**
     * Constructor.
     *
     * @param detectionStrategy The detection strategy.
     * @param ip                The ip.
     * @param port              The port.
     * @param args              The arguments.
     * @param serverSupplier    A {@link Supplier} for making servers.
     * @param detection         The expected detection.
     */
    public AbstractDetectITest(
            final DetectionStrategy detectionStrategy,
            final String ip,
            final int port,
            final Map<String, Object> args,
            final Supplier<FakeMinerServer> serverSupplier,
            final Detection detection) {
        this.detectionStrategy = detectionStrategy;
        this.ip = ip;
        this.port = port;
        this.args = args;
        this.serverSupplier = serverSupplier;
        this.detection = detection;
    }

    /**
     * Constructor.
     *
     * @param detectionStrategy The detection strategy.
     * @param serverSupplier    A {@link Supplier} for making servers.
     * @param detection         The expected detection.
     */
    public AbstractDetectITest(
            final DetectionStrategy detectionStrategy,
            final Supplier<FakeMinerServer> serverSupplier,
            final Detection detection) {
        this(
                detectionStrategy,
                DEFAULT_IP,
                DEFAULT_PORT,
                ImmutableMap.of(
                        "arg1",
                        "val1"),
                serverSupplier,
                detection);
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
                this.detection);
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
                null);
    }

    /**
     * Tests finding a {@link Detection}.
     *
     * @param port              The API port to query.
     * @param expectedDetection The expected result.
     */
    private void runTest(
            final int port,
            final Detection expectedDetection)
            throws Exception {
        try (final FakeMinerServer fakeMinerServer =
                     this.serverSupplier.get()) {
            fakeMinerServer.start();
            assertEquals(
                    expectedDetection,
                    this.detectionStrategy.detect(
                            this.ip,
                            port,
                            this.args).orElse(null));
            if (expectedDetection != null) {
                assertTrue(
                        fakeMinerServer.waitTillDone(
                                10,
                                TimeUnit.SECONDS));
            }
        }
    }
}