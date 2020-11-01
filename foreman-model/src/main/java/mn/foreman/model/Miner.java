package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import java.util.Optional;

/**
 * A common interface to all {@link Miner miners} that provide the ability to
 * remotely obtain metrics.
 */
public interface Miner {

    /**
     * Returns the miner's API port.
     *
     * @return The miner's API port.
     */
    int getApiPort();

    /**
     * Returns the miner's IP.
     *
     * @return The miner's IP.
     */
    String getIp();

    /**
     * Returns the MAC address, if present.
     *
     * @return The MAC address.
     */
    Optional<String> getMacAddress();

    /**
     * Returns the ID.
     *
     * @return The ID.
     */
    MinerID getMinerID();

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