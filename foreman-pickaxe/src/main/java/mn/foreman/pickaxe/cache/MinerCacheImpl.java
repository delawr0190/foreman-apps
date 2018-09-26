package mn.foreman.pickaxe.cache;

import mn.foreman.model.Miner;

import java.util.ArrayList;
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
    public List<Miner> getMiners() {
        return Collections.unmodifiableList(this.minerCache.get());
    }

    @Override
    public void setMiners(final List<Miner> miners) {
        this.minerCache.set(new ArrayList<>(miners));
    }
}