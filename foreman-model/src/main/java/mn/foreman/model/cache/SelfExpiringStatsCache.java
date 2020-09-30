package mn.foreman.model.cache;

import mn.foreman.model.MinerID;
import mn.foreman.model.miners.MinerStats;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A {@link SelfExpiringStatsCache} provides a {@link StatsCache} implementation
 * that will automatically evict {@link MinerStats} from the cache some
 * configurable interval after they were added.
 */
public class SelfExpiringStatsCache
        implements StatsCache {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(SelfExpiringStatsCache.class);

    /** The backing cache. */
    private final Cache<MinerID, MinerStats> cache;

    /**
     * Constructor.
     *
     * @param evictAfterWrite      When to auto-evict stats.
     * @param evictAfterWriteUnits When to auto-evict stats (units).
     */
    public SelfExpiringStatsCache(
            final int evictAfterWrite,
            final TimeUnit evictAfterWriteUnits) {
        this.cache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(
                                evictAfterWrite,
                                evictAfterWriteUnits)
                        .removalListener(notification -> {
                            if (notification.getCause() !=
                                    RemovalCause.REPLACED) {
                                LOG.debug(
                                        "Evicted {}",
                                        notification.getValue());
                            }
                        })
                        .build();
    }

    @Override
    public void add(
            final MinerID minerID,
            final MinerStats stats) {
        this.cache.put(minerID, stats);
    }

    @Override
    public List<MinerStats> getMetrics() {
        this.cache.cleanUp();
        return new ArrayList<>(this.cache.asMap().values());
    }

    @Override
    public void invalidate(final MinerID minerID) {
        this.cache.invalidate(minerID);
    }
}