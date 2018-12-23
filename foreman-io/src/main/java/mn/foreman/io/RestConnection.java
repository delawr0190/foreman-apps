package mn.foreman.io;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link RestConnection} provides a connection to a remote miner instance.
 *
 * <p>{@link #query()} will block until the response has been fully received
 * and the connection is terminated.</p>
 *
 * @see HttpURLConnection
 */
public class RestConnection
        implements Connection {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(RestConnection.class);

    /** The connection timeout. */
    private final int connectionTimeout;

    /** The connection timeout units. */
    private final TimeUnit connectionTimeoutUnits;

    /** The method. */
    private final String method;

    /** The request. */
    private final ApiRequest request;

    /** The URL. */
    private final String url;

    /**
     * Constructor.
     *
     * @param url                 The URL.
     * @param method              The method.
     * @param request             The request.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     */
    RestConnection(
            final String url,
            final String method,
            final ApiRequest request,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits) {
        Validate.notEmpty(
                url,
                "url cannot be empty");
        Validate.notEmpty(
                method,
                "method cannot be empty");
        Validate.notNull(
                request,
                "request cannot be empty");
        Validate.isTrue(
                connectTimeout >= 0,
                "connectTimeout must be >= 0");
        Validate.notNull(
                connectTimeoutUnits,
                "connectTimeoutUnits cannot be null");
        this.url = url;
        this.method = method;
        this.request = request;
        this.connectionTimeout = connectTimeout;
        this.connectionTimeoutUnits = connectTimeoutUnits;
    }

    @Override
    public void query() {
        try {
            final int socketTimeout =
                    (int) this.connectionTimeoutUnits.toMillis(
                            this.connectionTimeout);
            final URL url = new URL(this.url);
            final HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(this.method);
            connection.setConnectTimeout(socketTimeout);
            connection.setReadTimeout(socketTimeout);
            for (final Map.Entry<String, String> property :
                    this.request.getProperties().entrySet()) {
                connection.setRequestProperty(
                        property.getKey(),
                        property.getValue());
            }

            final int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                try (final InputStreamReader inputStreamReader =
                             new InputStreamReader(
                                     connection.getInputStream());
                     final BufferedReader reader =
                             new BufferedReader(
                                     inputStreamReader)) {
                    this.request.setResponse(
                            IOUtils.toString(reader));
                    this.request.completed();
                }
            } else {
                LOG.warn("Received an unexpected status code: {}", code);
            }
        } catch (final IOException ioe) {
            LOG.debug("Exception occurred while querying", ioe);
        }
    }
}
