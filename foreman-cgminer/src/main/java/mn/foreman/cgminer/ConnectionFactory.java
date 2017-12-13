package mn.foreman.cgminer;

/**
 * A common interface to factories that are capable of creating {@link
 * Connection connections} to the cgminer API.
 */
public interface ConnectionFactory {

    /**
     * Creates a new {@link Connection}.
     *
     * @param ip   The API IP.
     * @param port The API port.
     *
     * @return The new {@link Connection}.
     */
    Connection create(
            String ip,
            int port);
}