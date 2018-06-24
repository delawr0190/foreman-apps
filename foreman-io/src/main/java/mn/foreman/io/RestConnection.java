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

/**
 * A {@link RestConnection} provides a connection to a remote miner instance.
 *
 * <p>{@link #query()} will block until the response has been fully received and
 * the connection is terminated.</p>
 *
 * @see HttpURLConnection
 */
public class RestConnection
        implements Connection {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(RestConnection.class);

    /** The request. */
    private final ApiRequest request;

    /** The URL. */
    private final String url;

    /**
     * Constructor.
     *
     * @param url     The URL.
     * @param request The request.
     */
    RestConnection(
            final String url,
            final ApiRequest request) {
        Validate.notEmpty(
                url,
                "url cannot be empty");
        Validate.notNull(
                request,
                "request cannot be empty");
        this.url = url;
        this.request = request;
    }

    @Override
    public void query() {
        try {
            final URL url = new URL(this.url);
            final HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

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
            LOG.warn("Exception occurred while querying", ioe);
        }
    }
}
