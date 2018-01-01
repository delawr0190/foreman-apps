package mn.foreman.pickaxe.itest;

import mn.foreman.model.MetricsReport;
import mn.foreman.model.metadata.Metadata;
import mn.foreman.pickaxe.itest.miner.FakeMiner;
import mn.foreman.pickaxe.itest.model.ExpectedMessages;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.io.IOUtils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * An {@link AbstractPickaxeITest} provides a framework for adding additional
 * pickaxe integration tests that validate pickaxe's ability to connect to
 * miners, query for metrics, and upload those metrics to the Foreman API.
 */
public abstract class AbstractPickaxeITest {

    /** The expected metrics content. */
    private final String expectedPostFile;

    /**
     * A {@link Map} containing each miner to stand up and their corresponding
     * requests and responses.
     */
    private final Map<Integer, Map<String, String>> minerData;

    /**
     * Constructor.
     *
     * @param expectedPostFile The expected POST.
     * @param minerData        A {@link Map} containing each miner to stand up
     *                         and their corresponding requests and responses.
     */
    AbstractPickaxeITest(
            final String expectedPostFile,
            final Map<Integer, Map<String, String>> minerData) {
        this.expectedPostFile = expectedPostFile;
        this.minerData = minerData;
    }

    /**
     * Runs an integration test, which launches the provided fake API
     * interfaces, stands up a local web server, and waits for a pickaxe
     * instance to POST metrics.  Those metrics are then validated against the
     * expected metrics.
     *
     * @throws InterruptedException On failure to sleep.
     * @throws IOException          On failure to read from a file.
     */
    @Test
    public void runTest()
            throws InterruptedException, IOException {
        final Executor threadPool = Executors.newCachedThreadPool();

        final List<FakeMiner> fakeMiners =
                createMiners(
                        this.minerData,
                        threadPool);

        // Setup a local web server so we know when metrics are
        // pushed.  Those are really what we care about
        final MyHandler myHandler =
                new MyHandler(
                        IOUtils.toString(
                                getClass().getResourceAsStream(
                                        this.expectedPostFile),
                                "UTF-8"));

        final HttpServer httpServer =
                createWebServer(myHandler);

        try {
            // Start up the web server
            threadPool.execute(() -> {
                try {
                    httpServer.start();
                } catch (IOException e) {
                    fail("Failed to start the server");
                }
            });

            // Start each fake API
            fakeMiners.forEach(FakeMiner::start);

            // Wait for some metrics
            assertTrue(myHandler.waitForStats());
        } finally {
            TimeUnit.SECONDS.sleep(1);
            fakeMiners.forEach(FakeMiner::stop);
            httpServer.shutdown();
        }
    }

    /**
     * Factory method for creating the concrete {@link FakeMiner}.
     *
     * @param port             The port of the miner.
     * @param expectedMessages The expected messages.
     * @param threadPool       The thread pool to use for processing.
     *
     * @return The new {@link FakeMiner}.
     */
    protected abstract FakeMiner createMiner(
            int port,
            ExpectedMessages expectedMessages,
            Executor threadPool);

    /**
     * Utility method to create a local webserver that pickaxe can use for
     * POSTing.
     *
     * @param myHandler The custom handler to validate the metrics.
     *
     * @return The new server.
     */
    private static HttpServer createWebServer(
            final MyHandler myHandler) {
        final ResourceConfig resourceConfig = new ResourceConfig();
        final Resource.Builder resourceBuilder =
                Resource.builder("api");
        resourceBuilder.addMethod("PUT")
                .handledBy(myHandler);
        resourceConfig.registerResources(resourceBuilder.build());

        return GrizzlyHttpServerFactory.createHttpServer(
                URI.create("http://localhost:8080/"),
                resourceConfig,
                false);
    }

    /**
     * Utility method to create a {@link FakeMiner} from the provided
     * parameters.
     *
     * @param port      The port.
     * @param responses The expected requests and responses.
     * @param executor  The thread pool to use for processing.
     *
     * @return The new {@link FakeMiner}.
     */
    private FakeMiner createMiner(
            final int port,
            final Map<String, String> responses,
            final Executor executor) {
        final ExpectedMessages.Builder builder =
                new ExpectedMessages.Builder();
        responses.forEach(builder::addMessageFromFile);
        return createMiner(
                port,
                builder.build(),
                executor);
    }

    /**
     * Utility method to create {@link FakeMiner fake miners} from the provided
     * parameters.
     *
     * @param minerTestMap Each miner's port and expected requests and
     *                     responses.
     *
     * @return The new {@link FakeMiner fake miners}.
     */
    private List<FakeMiner> createMiners(
            final Map<Integer, Map<String, String>> minerTestMap,
            final Executor threadPool) {
        return minerTestMap
                .entrySet()
                .stream()
                .map(entry -> createMiner(
                        entry.getKey(),
                        entry.getValue(),
                        threadPool))
                .collect(Collectors.toList());
    }

    /** A custom handler to enable the validation of POSTed metrics. */
    private static class MyHandler
            implements Inflector<ContainerRequestContext, Response> {

        /** The expected data. */
        private final String expected;

        /** A latch to allow for simplified waiting while pickaxe runs. */
        private final CountDownLatch statsLatch = new CountDownLatch(1);

        /**
         * Constructor.
         *
         * @param expected The expected data.
         */
        MyHandler(final String expected) {
            this.expected = expected;
        }

        @Override
        public Response apply(final ContainerRequestContext context) {
            final ContainerRequest request = (ContainerRequest) context;

            try {
                final ObjectMapper objectMapper =
                        new ObjectMapper()
                                .enable(
                                        DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)
                                .registerModule(new JavaTimeModule());

                final MetricsReport metricsReport =
                        objectMapper.readValue(
                                request.readEntity(String.class),
                                MetricsReport.class);

                final MetricsReport expected =
                        objectMapper.readValue(
                                this.expected,
                                MetricsReport.class);

                final Metadata expectedMetadata =
                        expected.getMetadata();
                final Metadata actualMetadata =
                        metricsReport.getMetadata();

                // Intentionally didn't validate timestamps
                assertEquals(
                        expectedMetadata.getApiVersion(),
                        actualMetadata.getApiVersion());
                assertEquals(
                        expected.getMiners(),
                        metricsReport.getMiners());

                this.statsLatch.countDown();
            } catch (final IOException e) {
                fail(e.getMessage());
            }

            return Response.created(URI.create("/api/1")).build();
        }

        /**
         * Utility method to wait until the stats have been received.
         *
         * @return Whether or not the stats were ever received.
         *
         * @throws InterruptedException On failure to wait.
         */
        boolean waitForStats() throws InterruptedException {
            return this.statsLatch.await(2, TimeUnit.MINUTES);
        }
    }
}