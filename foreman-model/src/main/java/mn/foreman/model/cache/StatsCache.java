package mn.foreman.model.cache;

import mn.foreman.model.MinerID;
import mn.foreman.model.miners.MinerStats;

import java.util.List;

/**
 * A {@link StatsCache} provides an in-memory cache that will store {@link
 * MinerStats}.
 */
public interface StatsCache {

    /**
     * Adds the metrics to the cache for the ID.
     *
     * @param minerID The ID.
     * @param stats   The stats.
     */
    void add(
            MinerID minerID,
            MinerStats stats);

    /**
     * Returns the metrics in the cache.
     *
     * @return The metrics in the cache.
     */
    List<MinerStats> getMetrics();

    /**
     * Invalidates the stats associated with the provided {@link MinerID}.
     *
     * @param minerID The ID to invalidate.
     */
    void invalidate(
            MinerID minerID);
}