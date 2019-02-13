package mn.foreman.util.rpc;

import mn.foreman.util.AbstractFakeMinerServer;
import mn.foreman.util.http.FakeHttpMinerServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A {@link FakeHttpMinerServer} provides a fake RPC-based miner API that can be
 * leveraged for integration testing.
 */
public class FakeRpcMinerServer
        extends AbstractFakeMinerServer<RpcHandler> {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(FakeRpcMinerServer.class);

    /** A reusable thread pool. */
    private static final Executor THREAD_POOL =
            Executors.newSingleThreadExecutor();

    /** A latch for waiting until the socket is closed. */
    private final CountDownLatch closeLatch =
            new CountDownLatch(1);

    /** The server. */
    private ServerSocket serverSocket;

    /**
     * Constructor.
     *
     * @param port     The port.
     * @param handlers The handlers.
     */
    public FakeRpcMinerServer(
            final int port,
            final Map<String, RpcHandler> handlers) {
        super(port, handlers);
    }

    @Override
    public void close() throws Exception {
        this.serverSocket.close();
        this.closeLatch.await();
    }

    @Override
    public void start() {
        try {
            this.serverSocket = new ServerSocket(this.port);

            THREAD_POOL.execute(() -> {
                while (true) {
                    try (final Socket socket = this.serverSocket.accept()) {
                        // If there's a no-request handler, dump
                        if (this.handlers.containsKey("")) {
                            this.handlers.get("").process(socket);
                        } else {
                            String request = "";
                            final InputStream inputStream =
                                    socket.getInputStream();
                            while (true) {
                                final int read = inputStream.read();
                                if (read >= 0) {
                                    request += (char) read;
                                    if (this.handlers.containsKey(request)) {
                                        this.handlers.get(request).process(socket);
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    } catch (final IOException ioe) {
                        // Ignore
                        break;
                    }
                }
                this.closeLatch.countDown();
            });
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred", ioe);
        }
    }
}