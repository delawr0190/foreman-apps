package mn.foreman.claymore;

import mn.foreman.io.Query;
import mn.foreman.model.error.MinerException;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/** Unit tests for the connection to a Claymore API. */
public class ConnectionITest {

    /**
     * Verifies that a hung API server will get terminated upon read timeout.
     *
     * @throws MinerException       on failure to parse.
     * @throws InterruptedException on failure to sleep.
     */
    @Test(expected = MinerException.class)
    public void testSlowRead()
            throws MinerException, InterruptedException {
        final int serverPort = 36420;

        try (final MyServer server =
                     new MyServer(
                             serverPort)) {
            final Executor executor =
                    Executors.newSingleThreadExecutor();
            executor.execute(server);

            // Wait for the server to start (nbio in the future?)
            TimeUnit.SECONDS.sleep(5);

            Query.jsonQuery(
                    "localhost",
                    serverPort,
                    "blah",
                    String.class);
        }
    }

    /** A test server that will accept a connection and hang for a bit. */
    private static class MyServer
            implements Runnable, AutoCloseable {

        /** The server port. */
        private final int serverPort;

        /** The socket. */
        private ServerSocket serverSocket;

        /**
         * Constructor.
         *
         * @param serverPort The server port.
         */
        MyServer(final int serverPort) {
            this.serverPort = serverPort;
        }

        @Override
        public void close() {
            try {
                this.serverSocket.close();
            } catch (final IOException ioe) {
                // Ignore
            }
        }

        @Override
        public void run() {
            try {
                this.serverSocket =
                        new ServerSocket(
                                this.serverPort);
                this.serverSocket.accept();

                // Do nothing - just wait
                TimeUnit.SECONDS.sleep(30);
            } catch (final Exception e) {
                // Ignore
            }
        }
    }
}