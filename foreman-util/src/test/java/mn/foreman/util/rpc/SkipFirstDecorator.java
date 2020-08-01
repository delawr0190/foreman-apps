package mn.foreman.util.rpc;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/** A decorator to make it so the first request is dropped. */
public class SkipFirstDecorator
        implements HandlerInterface {

    /** The real handler. */
    private final RpcHandler real;

    /** Whether or not the first invocation was intercepted. */
    private final AtomicBoolean sawFirst = new AtomicBoolean(false);

    /**
     * Constructor.
     *
     * @param real The real handler.
     */
    public SkipFirstDecorator(final RpcHandler real) {
        this.real = real;
    }

    @Override
    public boolean isDone() {
        return this.real.isDone();
    }

    @Override
    public void process(final Socket socket) {
        if (!this.sawFirst.compareAndSet(false, true)) {
            this.real.process(socket);
        }
    }
}
