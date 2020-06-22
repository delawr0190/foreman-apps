package mn.foreman.util.http;

import mn.foreman.util.Handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

/**
 * A {@link HttpHandler} provides a {@link Handler} implementation that will
 * return a canned response.
 *
 * <p>This handler will only indicate {@link #isDone() done} if the request
 * that was received matches the {@link #expectedRequest}.</p>
 */
public class HttpHandler
        implements Handler, com.sun.net.httpserver.HttpHandler {

    /** The expected request. */
    private final String expectedRequest;

    /** Whether or not the expected request was observed. */
    private final AtomicBoolean matched = new AtomicBoolean(false);

    /** The request headers. */
    private final Map<String, String> requestHeaders;

    /** The request parameters. */
    private final String requestParameters;

    /** The validator to use for requests. */
    private final Predicate<HttpExchange> requestValidator;

    /** The response to return. */
    private final String response;

    /** The response headers. */
    private final Map<String, String> responseHeaders;

    /**
     * Constructor.
     *
     * @param expectedRequest   The expected request.
     * @param requestHeaders    The request headers.
     * @param requestParameters The request parameters.
     * @param response          The response.
     * @param responseHeaders   The response headers.
     * @param requestValidator  The validator for requests.
     */
    public HttpHandler(
            final String expectedRequest,
            final Map<String, String> requestHeaders,
            final String requestParameters,
            final String response,
            final Map<String, String> responseHeaders,
            final Predicate<HttpExchange> requestValidator) {
        this.expectedRequest = expectedRequest;
        this.requestHeaders = new HashMap<>(requestHeaders);
        this.requestParameters = requestParameters;
        this.response = response;
        this.responseHeaders = new HashMap<>(responseHeaders);
        this.requestValidator = requestValidator;
    }

    /**
     * Constructor.
     *
     * @param expectedRequest   The expected request.
     * @param requestHeaders    The request headers.
     * @param requestParameters The request parameters.
     * @param response          The response.
     * @param responseHeaders   The response headers.
     */
    public HttpHandler(
            final String expectedRequest,
            final Map<String, String> requestHeaders,
            final String requestParameters,
            final String response,
            final Map<String, String> responseHeaders) {
        this(
                expectedRequest,
                requestHeaders,
                requestParameters,
                response,
                responseHeaders,
                exchange -> true);
    }

    /**
     * Constructor.
     *
     * @param expectedRequest  The expected request.
     * @param requestHeaders   The request headers.
     * @param response         The response.
     * @param responseHeaders  The response headers.
     * @param requestValidator The request validator.
     */
    public HttpHandler(
            final String expectedRequest,
            final Map<String, String> requestHeaders,
            final String response,
            final Map<String, String> responseHeaders,
            final Predicate<HttpExchange> requestValidator) {
        this(
                expectedRequest,
                requestHeaders,
                null,
                response,
                responseHeaders,
                requestValidator);
    }

    /**
     * Constructor.
     *
     * @param expectedRequest The expected request.
     * @param response        The response.
     * @param responseHeaders The response headers.
     */
    public HttpHandler(
            final String expectedRequest,
            final Map<String, String> requestHeaders,
            final String response,
            final Map<String, String> responseHeaders) {
        this(
                expectedRequest,
                requestHeaders,
                null,
                response,
                responseHeaders);
    }

    /**
     * Constructor.
     *
     * @param expectedRequest  The expected request.
     * @param response         The response.
     * @param requestValidator The validator for requests.
     */
    public HttpHandler(
            final String expectedRequest,
            final String response,
            final Predicate<HttpExchange> requestValidator) {
        this(
                expectedRequest,
                Collections.emptyMap(),
                response,
                Collections.emptyMap(),
                requestValidator);
    }

    /**
     * Constructor.
     *
     * @param expectedRequest The expected request.
     * @param response        The response.
     */
    public HttpHandler(
            final String expectedRequest,
            final String response) {
        this(
                expectedRequest,
                Collections.emptyMap(),
                response,
                Collections.emptyMap());
    }

    @Override
    public void handle(
            final HttpExchange exchange) throws IOException {
        this.matched.set(didMatch(exchange));

        if (!this.responseHeaders.isEmpty()) {
            addResponseHeaders(
                    this.responseHeaders,
                    exchange.getResponseHeaders());
        }

        final byte[] response = this.response.getBytes();
        exchange.sendResponseHeaders(
                200,
                response.length);

        final OutputStream outputStream =
                exchange.getResponseBody();
        outputStream.write(response);
        outputStream.close();
    }

    @Override
    public boolean isDone() {
        return this.matched.get();
    }

    /**
     * Adds the headers.
     *
     * @param toAdd   The headers to add.
     * @param headers The destination.
     */
    private static void addResponseHeaders(
            final Map<String, String> toAdd,
            final Headers headers) {
        toAdd.forEach(headers::add);
    }

    /**
     * Returns whether or not the headers matched.
     *
     * @param headers  The request headers.
     * @param expected The expected headers.
     *
     * @return Whether or not they matched.
     */
    private static boolean matchedHeaders(
            final Headers headers,
            final Map<String, String> expected) {
        if (!expected.isEmpty()) {
            final Map<String, String> headerMap = new HashMap<>();
            for (final Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (expected.containsKey(entry.getKey())) {
                    headerMap.put(
                            entry.getKey(),
                            entry.getValue().get(0));
                }
            }
            return headerMap.equals(expected);
        }
        return true;
    }

    /**
     * Whether or not there was a match.
     *
     * @param exchange The exchange.
     *
     * @return Whether or not there was a match.
     *
     * @throws IOException on failure to query.
     */
    private boolean didMatch(final HttpExchange exchange)
            throws IOException {
        final byte[] requestBytes =
                IOUtils.toByteArray(
                        exchange.getRequestBody());
        boolean matched =
                Arrays.equals(
                        requestBytes,
                        this.expectedRequest.getBytes()) &&
                        matchedHeaders(
                                exchange.getRequestHeaders(),
                                this.requestHeaders);
        if (matched && this.requestParameters != null) {
            final URI uri = exchange.getRequestURI();
            matched = uri.getQuery().equals(this.requestParameters);
        }
        if (matched) {
            matched = this.requestValidator.test(exchange);
        }
        return matched;
    }
}