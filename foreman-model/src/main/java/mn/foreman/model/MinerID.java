package mn.foreman.model;

/** A {@link MinerID} represents a unique identifier to a {@link Miner}. */
public interface MinerID {

    /**
     * Returns the API IP.
     *
     * @return The API IP.
     */
    String getApiIp();

    /**
     * Returns the API port.
     *
     * @return The API port.
     */
    int getApiPort();
}
