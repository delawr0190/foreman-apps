package mn.foreman.util.rpc;

import mn.foreman.util.Handler;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An {@link RpcHandler} provides a {@link Handler} that will return a response
 * to a client {@link Socket}.
 */
public class RpcHandler
        implements HandlerInterface {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(RpcHandler.class);

    /** Whether or not a request was processed. */
    private final AtomicBoolean matched = new AtomicBoolean(false);

    /** The response. */
    private final String response;

    /**
     * Constructor.
     *
     * @param response The response.
     */
    public RpcHandler(final String response) {
        this.response = response;
    }

    @Override
    public boolean isDone() {
        return this.matched.get();
    }

    /**
     * Returns the {@link #response} on the provided socket.
     *
     * @param socket The socket.
     */
    @Override
    public void process(final Socket socket) {
        try {
            IOUtils.write(
                    this.response.getBytes(),
                    socket.getOutputStream());
            this.matched.set(true);
        } catch (final IOException ioe) {
            LOG.error("Exception occurred", ioe);
        }
    }
}