package mn.foreman.io;

import org.apache.commons.lang3.Validate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link ApiRequest} that leverages a {@link CountDownLatch} as the
 * indicator that a response has been received.
 */
public class ApiRequestImpl
        implements ApiRequest {

    /** The completion latch. */
    private final CountDownLatch completedLatch = new CountDownLatch(1);

    /** The IP. */
    private final String ip;

    /** The port. */
    private final int port;

    /** The request to send. */
    private final String request;

    /** The response. */
    private String response;

    /**
     * Constructor.
     *
     * @param ip      The IP.
     * @param port    The port.
     * @param request The request.
     */
    public ApiRequestImpl(
            final String ip,
            final int port,
            final String request) {
        Validate.notNull(
                ip,
                "IP cannot be null");
        Validate.notEmpty(
                ip,
                "IP cannot be empty");
        Validate.isTrue(
                port > 0,
                "Port must be > 0");
        Validate.notNull(
                request,
                "Request cannot be null");
        Validate.notEmpty(
                request,
                "Request cannot be empty");
        this.ip = ip;
        this.port = port;
        this.request = request;
    }

    @Override
    public void completed() {
        this.completedLatch.countDown();
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getRequest() {
        return this.request;
    }

    @Override
    public String getResponse() {
        return this.response;
    }

    @Override
    public void setResponse(final String response) {
        if (response != null && !response.isEmpty()) {
            this.response = response;
        }
    }

    @Override
    public boolean waitForCompletion(
            final long deadline,
            final TimeUnit deadlineUnits) {
        boolean result = false;
        try {
            result =
                    this.completedLatch.await(
                            deadline,
                            deadlineUnits);
        } catch (final InterruptedException ignored) {
            // Ignore
        }
        return result;
    }
}