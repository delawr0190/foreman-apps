package mn.foreman.util.http;

import mn.foreman.util.Handler;

import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A {@link HttpHandler} provides a {@link Handler} implementation that will
 * return a canned response.
 *
 * <p>This handler will only indicate {@link #isDone() done} if the request that
 * was received matches the {@link #expectedRequest}.</p>
 */
public class HttpHandler
        implements Handler, com.sun.net.httpserver.HttpHandler {

    /** The expected request. */
    private final String expectedRequest;

    /** Whether or not the expected request was observed. */
    private final AtomicBoolean matched = new AtomicBoolean(false);

    /** The response to return. */
    private final String response;

    /**
     * Constructor.
     *
     * @param expectedRequest The expected request.
     * @param response        The response.
     */
    public HttpHandler(
            final String expectedRequest,
            final String response) {
        this.expectedRequest = expectedRequest;
        this.response = response;
    }

    @Override
    public void handle(
            final HttpExchange exchange) throws IOException {
        final byte[] requestBytes =
                IOUtils.toByteArray(
                        exchange.getRequestBody());

        this.matched.set(
                Arrays.equals(
                        requestBytes,
                        this.expectedRequest.getBytes()));

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
}