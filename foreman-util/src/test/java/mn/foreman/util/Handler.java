package mn.foreman.util;

import mn.foreman.util.http.FakeHttpMinerServer;

/**
 * A {@link Handler} provides a mechanism for processing URI-specific {@link
 * FakeHttpMinerServer} requests.
 */
public interface Handler {

    /**
     * Returns whether or not the expected request was observed.
     *
     * @return Whether or not the expected request was observed.
     */
    boolean isDone();
}