package mn.foreman.util.http;

import mn.foreman.util.AbstractFakeMinerServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * A {@link FakeHttpMinerServer} provides a fake HTTP-based miner API that can
 * be leveraged for integration testing.
 */
public class FakeHttpMinerServer
        extends AbstractFakeMinerServer<ServerHandler> {

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
        } catch (final IOException ioe) {
            throw new IllegalArgumentException(
                    "Exception occurred: " + ioe);
        }
    }
}