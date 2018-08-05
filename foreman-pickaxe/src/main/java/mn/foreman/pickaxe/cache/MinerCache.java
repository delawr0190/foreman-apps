package mn.foreman.pickaxe.cache;

import mn.foreman.model.Miner;

import java.util.List;

/**
 * A {@link MinerCache} provides an in-memory cache that will store {@link Miner
 * miners}.
 */
public interface MinerCache {

    /**
     * Adds the {@link Miner} to the cache.
     *
     * @param miner The {@link Miner} to add.
     */
    void add(Miner miner);

    /**
     * Returns all of the {@link Miner miners} in the cache.
     *
     * @return The {@link Miner miners}.
     */
    List<Miner> getMiners();

    /**
     * Removes the {@link Miner} from the cache.
     *
     * @param miner The {@link Miner} to remove.
     */
    void remove(Miner miner);
}