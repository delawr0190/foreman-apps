package mn.foreman.cgminer;

import java.util.Optional;

/**
 * A {@link Connection} provides a common interface to all cgminer APIs.
 *
 * <p>Implementations of this class are intended to perform only the logic
 * necessary to connect, send, and receive a message to/from the API.</p>
 *
 * <p>In the event that a connection couldn't be made, {@link #query(String)}
 * will return an {@link Optional#empty()}.</p>
 */
public interface Connection {

    /**
     * Queries the cgminer API interface by sending the providing request over
     * the socket interface.
     *
     * @param request The request message.
     *
     * @return The response, if one exists.
     */
    Optional<String> query(String request);
}