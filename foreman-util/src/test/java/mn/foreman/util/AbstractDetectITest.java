package mn.foreman.util;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

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

    /** The pre-args hook. */
    private final BiFunction<
            Integer,
            Map<String, Object>,
            Map<String, Object>> preArgsHook;

    /** The fake server supplier. */
    private final List<Supplier<FakeMinerServer>> serverSuppliers;

    /**
     * Constructor.
     *
     * @param detectionStrategy The detection strategy.
     * @param ip                The ip.
     * @param port              The port.
     * @param args              The arguments.
     * @param serverSuppliers   A {@link Supplier} for making servers.
     * @param detection         The expected detection.
     */
    public AbstractDetectITest(
            final DetectionStrategy detectionStrategy,
            final String ip,
            final int port,
            final Map<String, Object> args,
            final List<Supplier<FakeMinerServer>> serverSuppliers,
            final Detection detection) {
        this.detectionStrategy = detectionStrategy;
        this.ip = ip;
        this.port = port;
        this.args = args;
        this.serverSuppliers = serverSuppliers;
        this.detection = detection;
        this.preArgsHook = (integer, stringObjectMap) -> stringObjectMap;
    }

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
        this(
                detectionStrategy,
                ip,
                port,
                args,
                serverSupplier,
                detection,
                (integer, stringObjectMap) -> stringObjectMap);
    }

    /**
     * Constructor.
     *
     * @param detectionStrategy The detection strategy.
     * @param ip                The ip.
     * @param port              The port.
     * @param args              The arguments.
     * @param serverSupplier    A {@link Supplier} for making servers.
     * @param detection         The expected detection.
     * @param preArgsHook       The pre-args hook.
     */
    public AbstractDetectITest(
            final DetectionStrategy detectionStrategy,
            final String ip,
            final int port,
            final Map<String, Object> args,
            final List<Supplier<FakeMinerServer>> serverSupplier,
            final Detection detection,
            final BiFunction<
                    Integer,
                    Map<String, Object>,
                    Map<String, Object>> preArgsHook) {
        this.detectionStrategy = detectionStrategy;
        this.ip = ip;
        this.port = port;
        this.args = args;
        this.serverSuppliers = serverSupplier;
        this.detection = detection;
        this.preArgsHook = preArgsHook;
    }

    /**
     * Constructor.
     *
     * @param detectionStrategy The detection strategy.
     * @param ip                The ip.
     * @param port              The port.
     * @param args              The arguments.
     * @param serverSupplier    A {@link Supplier} for making servers.
     * @param detection         The expected detection.
     * @param preArgsHook       The pre-args hook.
     */
    public AbstractDetectITest(
            final DetectionStrategy detectionStrategy,
            final String ip,
            final int port,
            final Map<String, Object> args,
            final Supplier<FakeMinerServer> serverSupplier,
            final Detection detection,
            final BiFunction<
                    Integer,
                    Map<String, Object>,
                    Map<String, Object>> preArgsHook) {
        this.detectionStrategy = detectionStrategy;
        this.ip = ip;
        this.port = port;
        this.args = args;
        this.serverSuppliers = Collections.singletonList(serverSupplier);
        this.detection = detection;
        this.preArgsHook = preArgsHook;
    }

    /**
     * Constructor.
     *
     * @param detectionStrategy The detection strategy.
     * @param serverSuppliers   A {@link Supplier} for making servers.
     * @param args              The args.
     * @param detection         The expected detection.
     */
    public AbstractDetectITest(
            final DetectionStrategy detectionStrategy,
            final List<Supplier<FakeMinerServer>> serverSuppliers,
            final Map<String, Object> args,
            final Detection detection,
            final BiFunction<
                    Integer,
                    Map<String, Object>,
                    Map<String, Object>> preArgsHook) {
        this(
                detectionStrategy,
                DEFAULT_IP,
                DEFAULT_PORT,
                args,
                serverSuppliers,
                detection,
                preArgsHook);
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

    /** Tests detection. */
    @Test
    public void testFound() {
        runTest(
                this.port,
                this.detection);
    }

    /** Tests detection. */
    @Test
    public void testNotFound() {
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
            final Detection expectedDetection) {
        final List<FakeMinerServer> fakeMinerServers =
                this.serverSuppliers
                        .stream()
                        .map(Supplier::get)
                        .collect(Collectors.toList());
        try {
            fakeMinerServers.forEach(FakeMinerServer::start);
            final Map<String, Object> args =
                    this.preArgsHook.apply(
                            port,
                            new HashMap<>(this.args));
            assertEquals(
                    expectedDetection,
                    this.detectionStrategy.detect(
                            this.ip,
                            port,
                            args).orElse(null));
            if (expectedDetection != null) {
                fakeMinerServers.forEach(
                        fakeMinerServer ->
                                fakeMinerServer.waitTillDone(
                                        10,
                                        TimeUnit.SECONDS));
            }
        } finally {
            for (final FakeMinerServer fakeMinerServer : fakeMinerServers) {
                try {
                    fakeMinerServer.close();
                } catch (final Exception e) {
                    // Ignore
                }
            }
        }
    }
}