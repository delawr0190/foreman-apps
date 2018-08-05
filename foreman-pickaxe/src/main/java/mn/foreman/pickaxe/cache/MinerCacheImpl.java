package mn.foreman.pickaxe.cache;

import mn.foreman.model.Miner;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A {@link MinerCache} implementation that's backed by an {@link
 * AtomicReference} to a {@link List}.
 */
public class MinerCacheImpl
        implements MinerCache {

    /** The cache. */
    private final AtomicReference<List<Miner>> minerCache =
            new AtomicReference<>(new LinkedList<>());

    @Override
    public void add(final Miner miner) {
        List<Miner> currentCache;
        List<Miner> newCache;
        do {
            currentCache = this.minerCache.get();
            newCache = new LinkedList<>();
            newCache.addAll(currentCache);
            newCache.add(miner);
        } while (!this.minerCache.compareAndSet(currentCache, newCache));
    }

    @Override
    public List<Miner> getMiners() {
        return Collections.unmodifiableList(this.minerCache.get());
    }

    @Override
    public void remove(final Miner miner) {
        List<Miner> currentCache;
        List<Miner> newCache;
        do {
            currentCache = this.minerCache.get();
            newCache = new LinkedList<>();
            newCache.addAll(currentCache);
            newCache.remove(miner);
        } while (!this.minerCache.compareAndSet(currentCache, newCache));
    }
}