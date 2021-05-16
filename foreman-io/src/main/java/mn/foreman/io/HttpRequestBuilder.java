package mn.foreman.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * A builder for making HTTP requests.
 *
 * @param <U> The response type.
 */
public class HttpRequestBuilder<U> {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(HttpRequestBuilder.class);

    /** The json mapper. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** The cookie store for holding state. */
    private CookieStore cookieStore = new BasicCookieStore();

    /** The IP. */
    private String ip;

    /** The port. */
    private int port;

    /** The callback to allow for intercepting raw responses. */
    private BiConsumer<Integer, String> rawCallback = (code, s) -> {
    };

    /** The transformer to convert from the raw response to an object. */
    private ResponseTransformer<U> responseTransformer;

    /** The scheme. */
    private String scheme = "http";

    /** The socket timeout. */
    private int socketTimeout = 1;

    /** The socket timeout (units). */
    private TimeUnit socketTimeoutUnits = TimeUnit.SECONDS;

    /** The URI. */
    private String uri;

    /** The validator for verifying responses. */
    private BiPredicate<Integer, String> validator = (code, s) -> true;

    /**
     * Sets the cookie store.
     *
     * @param cookieStore The store.
     *
     * @return This builder.
     */
    public HttpRequestBuilder<U> cookieStore(final CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        return this;
    }

    public Optional<U> get() {
        final AtomicReference<U> result = new AtomicReference<>();
        try {
            runOp(
                    false,
                    null,
                    null,
                    result::set);
        } catch (final QueryException e) {
            LOG.warn("Exception occurred while querying", e);
        }
        return Optional.ofNullable(result.get());
    }

    /**
     * Sets the IP.
     *
     * @param ip The IP.
     *
     * @return This builder.
     */
    public HttpRequestBuilder<U> ip(final String ip) {
        this.ip = ip;
        return this;
    }

    /**
     * Sets the port.
     *
     * @param port The port.
     *
     * @return This builder.
     */
    public HttpRequestBuilder<U> port(final int port) {
        this.port = port;
        return this;
    }

    /**
     * Performs a POST operation.
     *
     * @param params The content.
     *
     * @return The result.
     */
    public boolean postJsonNoResponse(final Object params) {
        boolean result = false;
        try {
            runOp(
                    true,
                    OBJECT_MAPPER.writeValueAsString(params),
                    null,
                    null);
            result = true;
        } catch (final Exception e) {
            //
        }
        return result;
    }

    /**
     * Performs a POST operation.
     *
     * @param content The content.
     *
     * @return The result.
     *
     * @throws QueryException on failure.
     */
    public Optional<U> postJsonWithResponse(final String content)
            throws QueryException {
        final AtomicReference<U> result = new AtomicReference<>();
        runOp(
                true,
                content,
                null,
                result::set);
        return Optional.ofNullable(result.get());
    }

    /**
     * Performs a POST operation.
     *
     * @param content The content.
     *
     * @return The result.
     *
     * @throws QueryException on failure.
     */
    public Optional<U> postWithResponse(final List<Map<String, Object>> content)
            throws QueryException {
        final AtomicReference<U> result = new AtomicReference<>();
        runOp(
                true,
                null,
                content,
                result::set);
        return Optional.ofNullable(result.get());
    }

    /**
     * Sets the raw callback.
     *
     * @param rawCallback The callback.
     *
     * @return This builder.
     */
    public HttpRequestBuilder<U> rawCallback(
            final BiConsumer<Integer, String> rawCallback) {
        this.rawCallback = rawCallback;
        return this;
    }

    /**
     * Sets the response transformer.
     *
     * @param responseTransformer The transformer.
     *
     * @return This builder.
     */
    public HttpRequestBuilder<U> responseTransformer(
            final ResponseTransformer<U> responseTransformer) {
        this.responseTransformer = responseTransformer;
        return this;
    }

    /**
     * Sets the scheme.
     *
     * @param scheme The scheme.
     *
     * @return This builder.
     */
    public HttpRequestBuilder<U> scheme(final String scheme) {
        this.scheme = scheme;
        return this;
    }

    /**
     * Sets the socket timeout.
     *
     * @param socketTimeout      The timeout.
     * @param socketTimeoutUnits The timeout (units).
     *
     * @return This builder.
     */
    public HttpRequestBuilder<U> socketTimeout(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
        return this;
    }

    /**
     * Sets the uri.
     *
     * @param uri The uri.
     *
     * @return This builder.
     */
    public HttpRequestBuilder<U> uri(final String uri) {
        this.uri = uri;
        return this;
    }

    /**
     * Sets the validator.
     *
     * @param validator The validator.
     *
     * @return This builder.
     */
    public HttpRequestBuilder<U> validator(final BiPredicate<Integer, String> validator) {
        this.validator = validator;
        return this;
    }

    /**
     * Runs an HTTP operation.
     *
     * @param isPost         Whether or not the request is a POST.
     * @param content        The content.
     * @param contentParams  The content, if params.
     * @param resultConsumer The result consumer.
     *
     * @throws QueryException on failure.
     */
    private void runOp(
            final boolean isPost,
            final String content,
            final List<Map<String, Object>> contentParams,
            final Consumer<U> resultConsumer)
            throws QueryException {
        try {
            final URI uri =
                    new URI(
                            this.scheme,
                            null,
                            this.ip,
                            this.port,
                            this.uri,
                            null,
                            null);
            final URL url = uri.toURL();

            final HttpHost targetHost =
                    new HttpHost(
                            url.getHost(),
                            url.getPort(),
                            url.getProtocol());

            final int socketTimeoutMillis =
                    (int) this.socketTimeoutUnits.toMillis(this.socketTimeout);

            final HttpClientContext context = HttpClientContext.create();
            context.setCookieStore(this.cookieStore);

            final CloseableHttpClient httpClient =
                    HttpClients
                            .custom()
                            .setRedirectStrategy(new LaxRedirectStrategy())
                            .setDefaultRequestConfig(
                                    RequestConfig
                                            .custom()
                                            .setConnectTimeout(socketTimeoutMillis)
                                            .setConnectionRequestTimeout(socketTimeoutMillis)
                                            .setSocketTimeout(socketTimeoutMillis)
                                            .setCookieSpec(CookieSpecs.DEFAULT)
                                            .build())
                            .setDefaultCookieStore(this.cookieStore)
                            .disableAutomaticRetries()
                            .build();

            final HttpRequest httpRequest;
            if (isPost) {
                final HttpPost post = new HttpPost(url.getPath());
                if (contentParams != null) {
                    final List<NameValuePair> params = new ArrayList<>();
                    contentParams.forEach(entry ->
                            params.add(
                                    new BasicNameValuePair(
                                            entry.get("key").toString(),
                                            entry.get("value").toString())));
                    LOG.debug("Params for POST: {}", params);

                    final HttpEntity entity =
                            new UrlEncodedFormEntity(
                                    params,
                                    "UTF-8");
                    LOG.debug("Entity: {}", entity);
                    post.setEntity(entity);
                } else if (content != null) {
                    post.setEntity(new StringEntity(content));
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-type", "application/json");
                }
                httpRequest = post;
            } else {
                // GET
                final URIBuilder uriBuilder =
                        new URIBuilder()
                                .setScheme(this.scheme)
                                .setHost(this.ip)
                                .setPort(this.port)
                                .setPath(this.uri);
                if (contentParams != null) {
                    contentParams.forEach(entry ->
                            uriBuilder.addParameter(
                                    entry.get("key").toString(),
                                    entry.get("value").toString()));
                }
                httpRequest = new HttpGet(uriBuilder.build());
            }

            try (final CloseableHttpResponse response =
                         httpClient.execute(
                                 targetHost,
                                 httpRequest,
                                 context)) {
                final StatusLine statusLine =
                        response.getStatusLine();
                final String responseBody =
                        EntityUtils.toString(response.getEntity());
                LOG.debug("Received API response: {}", responseBody);

                final int statusCode = statusLine.getStatusCode();
                this.rawCallback.accept(
                        statusLine.getStatusCode(),
                        responseBody);

                if (this.validator.test(statusCode, responseBody) &&
                        resultConsumer != null &&
                        this.responseTransformer != null) {
                    final U result =
                            this.responseTransformer.transform(
                                    statusCode,
                                    responseBody);
                    resultConsumer.accept(result);
                }
            }
        } catch (final Exception e) {
            throw new QueryException(e);
        }
    }

    public interface ResponseTransformer<U> {

        U transform(
                int code,
                String body) throws Exception;
    }
}
