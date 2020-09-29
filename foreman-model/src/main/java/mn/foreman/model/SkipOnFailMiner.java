package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A {@link Miner} that will begin skipping stats requests for a random amount
 * of windows until it returns.
 */
public class SkipOnFailMiner
        implements Miner {

    /** The maximum number of iterations to skip. */
    private final int maxToSkip;

    /** The real miner. */
    private final Miner miner;

    /** The skip count. */
    private AtomicInteger skipCounter;

    /**
     * Constructor.
     *
     * @param miner     The miner.
     * @param maxToSkip The maximum number of iterations to skip.
     */
    public SkipOnFailMiner(
            final Miner miner,
            final int maxToSkip) {
        this.miner = miner;
        this.maxToSkip = maxToSkip;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(final Object obj) {
        return this.miner.equals(obj);
    }

    @Override
    public int getApiPort() {
        return this.miner.getApiPort();
    }

    @Override
    public String getIp() {
        return this.miner.getIp();
    }

    @Override
    public MinerID getMinerID() {
        return this.miner.getMinerID();
    }

    @Override
    public MinerStats getStats() throws MinerException {
        MinerStats stats = null;
        if (this.skipCounter != null) {
            if (this.skipCounter.decrementAndGet() <= 0) {
                try {
                    stats = this.miner.getStats();
                } catch (final Exception e) {
                    resetSkip();
                }
            }
        } else {
            try {
                stats = this.miner.getStats();
            } catch (final Exception e) {
                resetSkip();
            }
        }

        if (stats == null) {
            throw new MinerException("Failed to obtain stats - miner is down?");
        }

        this.skipCounter = null;
        return stats;
    }

    @Override
    public int hashCode() {
        return this.miner.hashCode();
    }

    @Override
    public String toString() {
        return this.miner.toString();
    }

    private void resetSkip() {
        this.skipCounter =
                new AtomicInteger(
                        ThreadLocalRandom.current().nextInt(this.maxToSkip));
    }
}
