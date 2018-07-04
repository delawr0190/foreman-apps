package mn.foreman.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/** A common, {@link Handler Handler-based} fake miner server. */
public abstract class AbstractFakeMinerServer<T extends Handler>
        implements FakeMinerServer {

    /** The handlers. */
    protected final Map<String, T> handlers;

    /** The port. */
    protected final int port;

    /**
     * Constructor.
     *
     * @param port     The port.
     * @param handlers The handlers.
     */
    protected AbstractFakeMinerServer(
            final int port,
            final Map<String, T> handlers) {
        this.port = port;
        this.handlers = new HashMap<>(handlers);
    }

    @Override
    public boolean waitTillDone(
            final long deadline,
            final TimeUnit deadlineUnits) {
        boolean done = false;
        long end =
                System.currentTimeMillis() +
                        deadlineUnits.toMillis(deadline);
        while ((!done) && (System.currentTimeMillis() <= end)) {
            done =
                    this.handlers.values()
                            .stream()
                            .allMatch(Handler::isDone);
            Thread.yield();
        }
        return done;
    }
}