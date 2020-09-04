package mn.foreman.util.http;

import mn.foreman.util.AbstractFakeMinerServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link FakeHttpMinerServer} provides a fake HTTP-based miner API that can
 * be leveraged for integration testing.
 */
public class FakeHttpMinerServer
        extends AbstractFakeMinerServer<ServerHandler> {

    /**
     * The maximum number of times to wait for a connection to be established.
     */
    private static final int MAX_CONNECT_ATTEMPTS = 5;

    /** The server. */
    private HttpServer server;

    /**
     * Constructor.
     *
     * @param port     The port.
     * @param handlers The handlers.
     */
    public FakeHttpMinerServer(
            final int port,
            final Map<String, ServerHandler> handlers) {
        super(port, handlers);
    }

    @Override
    public void close() {
        this.server.stop(0);
    }

    @Override
    public void start() {
        boolean connected = false;
        int attempts = 0;
        do {
            try {
                this.server =
                        HttpServer.create(
                                new InetSocketAddress(this.port),
                                0);
                for (final Map.Entry<String, ServerHandler> entry :
                        this.handlers.entrySet()) {
                    this.server.createContext(entry.getKey(), entry.getValue());
                }
                this.server.start();
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