package mn.foreman.util.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/** Skips the first invocation of handle. */
public class SkipFirstHandler
        implements ServerHandler {

    /** The real handler. */
    private final ServerHandler real;

    /** Whether or not the first was seen. */
    private final AtomicBoolean sawFirst = new AtomicBoolean(false);

    /**
     * Constructor.
     *
     * @param real The real handler.
     */
    public SkipFirstHandler(final ServerHandler real) {
        this.real = real;
    }

    @Override
    public void handle(final HttpExchange exchange) throws IOException {
        if (!this.sawFirst.compareAndSet(false, true)) {
            this.real.handle(exchange);
        }
    }

    @Override
    public boolean isDone() {
        return this.real.isDone();
    }
}
