package mn.foreman.util.http;

import mn.foreman.util.Handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.fail;

/**
 * A {@link MultiHandler} provides a {@link ServerHandler} implementation that's
 * capable of supporting multiple, unique requests to the same HTTP endpoint.
 *
 * <p>As the requests are received, they're processed by each {@link #handlers
 * handler} sequentially (request 1 is processed by {@link #handlers handler 0},
 * request 2 is processed by {@link #handlers handler 1}, and so forth.</p>
 */
public class MultiHandler
        implements ServerHandler {

    /** The decorated handlers. */
    private final List<ServerHandler> handlers;

    /** The index of the handler to be served. */
    private final AtomicInteger index = new AtomicInteger(0);

    /**
     * Constructor.
     *
     * @param handlers The real handlers.
     */
    public MultiHandler(final ServerHandler... handlers) {
        this.handlers = Arrays.asList(handlers);
    }

    @Override
    public void handle(final HttpExchange exchange)
            throws IOException {
        final int next = this.index.getAndIncrement();
        if (next < this.handlers.size()) {
            this.handlers.get(next).handle(exchange);
        } else {
            fail("Obtained too many requests");
        }
    }

    @Override
    public boolean isDone() {
        return this.handlers
                .stream()
                .allMatch(Handler::isDone);
    }
}
