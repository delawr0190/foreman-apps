package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

/**
 * A common interface to all {@link Miner miners} that provide the ability to
 * remotely obtain metrics.
 */
public interface Miner {

    /**
     * Obtains a {@link MinerStats}.
     *
     * @return The {@link MinerStats}.
     *
     * @throws MinerException on failure to get stats.
     */
    MinerStats getStats()
            throws MinerException;
}