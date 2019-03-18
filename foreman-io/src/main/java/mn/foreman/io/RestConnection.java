package mn.foreman.io;

import org.apache.commons.lang3.Validate;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
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
        final int socketTimeout =
                (int) this.connectionTimeoutUnits.toMillis(
                        this.connectionTimeout);
        final RequestConfig requestConfig =
                RequestConfig.custom()
                        .setConnectTimeout(socketTimeout)
                        .setConnectionRequestTimeout(socketTimeout)
                        .setSocketTimeout(socketTimeout)
                        .build();

        try (final CloseableHttpClient httpClient =
                     HttpClients.custom()
                             .setDefaultRequestConfig(requestConfig)
                             .build()) {
            final HttpRequestBase httpRequest =
                    new HttpRequestBase() {
                        @Override
                        public String getMethod() {
                            return method;
                        }

                        @Override
                        public URI getURI() {
                            try {
                                return new URI(url);
                            } catch (final URISyntaxException use) {
                                return super.getURI();
                            }
                        }
                    };
            for (final Map.Entry<String, String> property :
                    this.request.getProperties().entrySet()) {
                httpRequest.setHeader(
                        property.getKey(),
                        property.getValue());
            }

            try (final CloseableHttpResponse httpResponse =
                         httpClient.execute(httpRequest)) {
                final int statusCode =
                        httpResponse
                                .getStatusLine()
                                .getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    LOG.warn("Received a bad response from {}: code({})",
                            this.url,
                            statusCode);
                }
                request.setResponse(
                        EntityUtils.toString(httpResponse.getEntity()));
            } catch (final IOException ioe) {
                LOG.warn("Exception occurred while querying", ioe);
            }
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while querying", ioe);
        }

        request.completed();
    }
}