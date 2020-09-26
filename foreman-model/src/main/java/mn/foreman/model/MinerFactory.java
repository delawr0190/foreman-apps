package mn.foreman.model;

import java.util.Map;

/**
 * A {@link MinerFactory} provides a factory interface for creating new {@link
 * Miner miners}.
 */
public interface MinerFactory {

    /**
     * Creates a {@link Miner} from the provided configuration.
     *
     * @param config The configuration.
     *
     * @return The new {@link Miner}.
     */
    Miner create(Map<String, Object> config);
}
