package mn.foreman.antminer.util;

import mn.foreman.model.AsicAction;
import mn.foreman.model.MinerFactory;
import mn.foreman.util.AbstractAsyncActionITest;
import mn.foreman.util.http.FakeHttpMinerServer;
import mn.foreman.util.http.ServerHandler;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.HandlerInterface;

import java.util.Arrays;
import java.util.Map;

/**
 * Base class for the testing of miners that have similar web interfaces as the
 * Antminer.
 */
public abstract class AntminerAsyncActionITest
        extends AbstractAsyncActionITest {

    /**
     * Constructor.
     *
     * @param additionalArgs The additional args.
     * @param factory        The factory.
     * @param action         The action.
     * @param httpHandlers   The HTTP handlers.
     * @param apiHandlers    The API handlers.
     * @param foundResult    The found result.
     */
    public AntminerAsyncActionITest(
            final Map<String, Object> additionalArgs,
            final MinerFactory factory,
            final AsicAction.CompletableAction action,
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, HandlerInterface> apiHandlers,
            final boolean foundResult) {
        super(
                8080,
                4028,
                action,
                Arrays.asList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                httpHandlers),
                        () -> new FakeRpcMinerServer(
                                4028,
                                apiHandlers)),
                factory,
                additionalArgs,
                foundResult);
    }

    /**
     * Constructor.
     *
     * @param additionalArgs The additional args.
     * @param factory        The factory.
     * @param action         The action.
     * @param httpHandlers   The HTTP handlers.
     * @param apiHandlers    The API handlers.
     */
    public AntminerAsyncActionITest(
            final Map<String, Object> additionalArgs,
            final MinerFactory factory,
            final AsicAction.CompletableAction action,
            final Map<String, ServerHandler> httpHandlers,
            final Map<String, HandlerInterface> apiHandlers) {
        super(
                8080,
                4028,
                action,
                Arrays.asList(
                        () -> new FakeHttpMinerServer(
                                8080,
                                httpHandlers),
                        () -> new FakeRpcMinerServer(
                                4028,
                                apiHandlers)),
                factory,
                additionalArgs,
                true);
    }
}