package mn.foreman.io;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * An {@link ApiRequest} represents a single request to a remote miner
 * instance.
 *
 * <p>This class is provides request to response mapping, while providing a
 * mechanism to observe when the request has been completed (a response has been
 * fully received).</p>
 *
 * <h3>Concurrency</h3>
 *
 * <p>This class is thread safe - however, it is only thread safe under the
 * condition that no getters are invoked until a successful {@link
 * #waitForCompletion(long, TimeUnit)} has completed (the response has been
 * returned).  When this happens (true return value), no other threads will be
 * updating this request (i.e.: no socket threads will still be reading
 * updates).</p>
 *
 * <p>The behavior of this class when used outside of the constraints above is
 * undefined.</p>
 */
public interface ApiRequest {

    /** Marks the request as completed. */
    void completed();

    /**
     * Returns whether or not the request connected.
     *
     * @return Whether or not the request connected.
     */
    boolean connected();

    /**
     * Sets whether or not connected.
     *
     * @param connected Whether or not connected.
     */
    void connected(boolean connected);

    /**
     * Returns the content.
     *
     * @return The content.
     */
    Optional<String> getContent();

    /**
     * Returns the IP.
     *
     * @return The IP.
     */
    String getIp();

    /**
     * Returns the port.
     *
     * @return The port.
     */
    int getPort();

    /**
     * Returns the request properties.
     *
     * @return The request properties.
     */
    Map<String, String> getProperties();

    /**
     * Returns the request.
     *
     * @return The request.
     */
    String getRequest();

    /**
     * Returns the response.
     *
     * @return The response.
     */
    String getResponse();

    /**
     * Sets the response to the request.
     *
     * @param response The response.
     */
    void setResponse(String response);

    /**
     * Waits for the request to finish.
     *
     * @param deadline      How long to wait.
     * @param deadlineUnits How long to wait (units).
     *
     * @return True if completed; false otherwise (possibly timed out).
     */
    boolean waitForCompletion(
            long deadline,
            TimeUnit deadlineUnits);
}
