package mn.foreman.io;

import org.apache.commons.lang3.Validate;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Optional;
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

    /** The cookie store. */
    private final CookieStore cookieStore;

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
            final TimeUnit connectTimeoutUnits,
            final CookieStore cookieStore) {
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
        this.cookieStore = cookieStore;
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
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .build();

        try (final CloseableHttpClient httpClient =
                     HttpClients
                             .custom()
                             .setRedirectStrategy(new LaxRedirectStrategy())
                             .setDefaultRequestConfig(requestConfig)
                             .setDefaultCookieStore(this.cookieStore)
                             .disableAutomaticRetries()
                             .build()) {
            final HttpRequestBase httpRequest =
                    toRequest();

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
                if (statusCode == HttpStatus.SC_OK) {
                    this.request.setResponse(
                            EntityUtils.toString(
                                    httpResponse.getEntity()));
                } else {
                    LOG.warn("Received a bad response from {}: code({})",
                            this.url,
                            statusCode);
                }
            } catch (final IOException ioe) {
                LOG.warn("Exception occurred while querying", ioe);
            }
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while querying", ioe);
        }

        this.request.completed();
    }

    /**
     * Creates a request with the desired configuration based on the {@link
     * #method}.
     *
     * @return The reqiest.
     *
     * @throws UnsupportedEncodingException if an unsupported encoding is
     *                                      encountered.
     */
    private HttpRequestBase toRequest()
            throws UnsupportedEncodingException {
        final HttpRequestBase requestBase;
        switch (this.method) {
            case "POST":
                final HttpPost post = new HttpPost(this.url);
                final Optional<String> content =
                        this.request.getContent();
                if (content.isPresent()) {
                    post.setEntity(new StringEntity(content.get()));
                }
                requestBase = post;
                break;
            case "GET":
            default:
                requestBase = new HttpGet(this.url);
                break;
        }
        return requestBase;
    }
}