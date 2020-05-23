package mn.foreman.model;

import java.util.Map;
import java.util.Optional;

/**
 * A {@link DetectionStrategy} provides a strategy for querying and identifying
 * miners.
 */
public interface DetectionStrategy {

    /**
     * Performs a detection on the provided IP and port using the supplied
     * arguments.
     *
     * @param ip   The IP.
     * @param port The port.
     * @param args The arguments.
     *
     * @return The detected miner, if found.
     */
    Optional<Detection> detect(
            String ip,
            int port,
            Map<String, Object> args);
}
