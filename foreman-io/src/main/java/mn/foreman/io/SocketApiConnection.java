package mn.foreman.io;

import com.google.common.net.InetAddresses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/** A raw, java socket connection. */
public class SocketApiConnection
        implements Connection {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(SocketApiConnection.class);

    /** The request. */
    private final ApiRequest apiRequest;

    /** The socket timeout. */
    private final int socketTimeout;

    /**
     * Constructor.
     *
     * @param apiRequest          The request.
     * @param connectTimeout      The connect timeout.
     * @param connectTimeoutUnits The connect timeout (units).
     */
    public SocketApiConnection(
            final ApiRequest apiRequest,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits) {
        this.apiRequest = apiRequest;
        this.socketTimeout = (int) connectTimeoutUnits.toMillis(connectTimeout);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void query() {
        try (final Socket socket = new Socket()) {
            final InetSocketAddress socketAddress =
                    new InetSocketAddress(
                            InetAddresses.forString(
                                    this.apiRequest.getIp()),
                            this.apiRequest.getPort());
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(this.socketTimeout);
            socket.connect(socketAddress, 100);

            try (final PrintWriter printWriter =
                         new PrintWriter(
                                 socket.getOutputStream());
                 final BufferedReader bufferedReader =
                         new BufferedReader(
                                 new InputStreamReader(
                                         socket.getInputStream()))) {
                printWriter.write(this.apiRequest.getRequest());
                printWriter.flush();

                final StringBuilder stringBuilder =
                        new StringBuilder();
                String line;
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                } catch (final Exception e) {
                    LOG.info("Socket closed");
                }
                this.apiRequest.setResponse(stringBuilder.toString());
            } catch (final Exception e) {
                LOG.warn("Exception occurred while sending/receiving", e);
            }
        } catch (final Exception e) {
            LOG.info("Exception occurred while connecting", e);
        }
        this.apiRequest.completed();
    }
}
