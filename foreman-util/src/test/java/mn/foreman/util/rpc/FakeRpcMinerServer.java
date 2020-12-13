package mn.foreman.util.rpc;

import mn.foreman.util.AbstractFakeMinerServer;
import mn.foreman.util.http.FakeHttpMinerServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A {@link FakeHttpMinerServer} provides a fake RPC-based miner API that can be
 * leveraged for integration testing.
 */
public class FakeRpcMinerServer
        extends AbstractFakeMinerServer<HandlerInterface> {

    /**
     * The maximum number of times to wait for a connection to be established.
     */
    private static final int MAX_CONNECT_ATTEMPTS = 5;

    /** A reusable thread pool. */
    private static final Executor THREAD_POOL =
            Executors.newCachedThreadPool();

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
            final Map<String, HandlerInterface> handlers) {
        super(port, handlers);
    }

    @Override
    public void close() throws Exception {
        this.serverSocket.close();
        this.closeLatch.await();
    }

    @Override
    public void start() {
        boolean connected = false;
        int attempts = 0;
        do {
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
                connected = true;
            } catch (final IOException ioe) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
        } while (!connected && attempts++ < MAX_CONNECT_ATTEMPTS);
    }
}