package mn.foreman.nicehash;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.SimpleMinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.rig.Rig;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A {@link NiceHashMiner} provides a {@link Miner} that will find the active
 * miner being used by nicehash and query it for metrics.
 */
public class NiceHashMiner
        implements Miner {

    /** The API IP. */
    private final String apiIp;

    /** The API port. */
    private final int apiPort;

    /** The candidate miners. */
    private final List<Miner> candidates;

    /** A lock to prevent multiple threads from discovering at the same time. */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /** The miner that's actively running (prevents continuous discovery). */
    private Miner miner;

    /**
     * Constructor.
     *
     * @param apiIp      The API IP.
     * @param apiPort    The API port.
     * @param candidates The candidates.
     */
    NiceHashMiner(
            final String apiIp,
            final int apiPort,
            final List<Miner> candidates) {
        this.apiIp = apiIp;
        this.apiPort = apiPort;
        this.candidates = new ArrayList<>(candidates);
    }

    @Override
    public boolean equals(final Object other) {
        boolean isEqual = false;
        if (other == this) {
            isEqual = true;
        } else if ((other != null) && (getClass() == other.getClass())) {
            final NiceHashMiner miner = (NiceHashMiner) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.apiIp,
                                    miner.apiIp)
                            .append(this.apiPort,
                                    miner.apiPort)
                            .append(this.candidates,
                                    miner.candidates)
                            .isEquals();
        }
        return isEqual;
    }

    @Override
    public MinerID getMinerID() {
        return new SimpleMinerID(
                this.apiIp,
                this.apiPort);
    }

    @Override
    public MinerStats getStats() throws MinerException {
        this.lock.writeLock().lock();
        try {
            if (this.miner == null) {
                discover();
            }

            MinerStats minerStats = null;
            try {
                minerStats = this.miner.getStats();
            } catch (final Exception me) {
                this.miner = null;
                discover();
            }

            if (minerStats != null) {
                final int foundApiPort = minerStats.getApiPort();

                // Rebuild the stats to be the correct port
                final MinerStats.Builder builder =
                        new MinerStats.Builder()
                                .setApiIp(this.apiIp)
                                .setApiPort(this.apiPort);
                minerStats.getPools().forEach(builder::addPool);
                minerStats.getAsics().forEach(builder::addAsic);
                minerStats.getRigs().forEach(
                        rig ->
                                addRig(
                                        rig,
                                        builder,
                                        foundApiPort));
                minerStats = builder.build();
            }

            // Would have thrown if nothing could be found
            return minerStats;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.apiIp)
                .append(this.apiPort)
                .append(this.candidates)
                .toHashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ apiIp=%s, apiPort=%d, candidates=%s ]",
                getClass().getSimpleName(),
                this.apiIp,
                this.apiPort,
                this.candidates);
    }

    /**
     * Adds a {@link Rig} to the provided {@link MinerStats.Builder} with an
     * attribute representing the found api port.
     *
     * @param original The original {@link Rig}.
     * @param builder  The builder.
     * @param apiPort  The found API port.
     */
    private static void addRig(
            final Rig original,
            final MinerStats.Builder builder,
            final int apiPort) {
        builder.addRig(
                new Rig.Builder()
                        .setHashRate(original.getHashRate())
                        .addGpus(original.getGpus())
                        .addAttributes(original.getAttributes())
                        .addAttribute(
                                "api_port",
                                Integer.toString(apiPort))
                        .build());
    }

    /**
     * Attempts to find a miner for the active algorithm.
     *
     * @throws MinerException on failure to find a {@link Miner}.
     */
    private void discover() throws MinerException {
        this.miner = this.candidates
                .parallelStream()
                .filter(this::discover)
                .findFirst()
                .orElseThrow(
                        () -> new MinerException(
                                "Failed to find a running nicehash miner"));
    }

    /**
     * Attempts to find the provided {@link Miner}.
     *
     * @param miner The {@link Miner} to query.
     *
     * @return Whether or not the {@link Miner} was found.
     */
    private boolean discover(final Miner miner) {
        boolean found = false;
        try {
            final MinerStats stats = miner.getStats();

            // Only accept the metrics if there's a rig and at least one GPU
            final List<Rig> rigs = stats.getRigs();
            if (rigs.size() > 0) {
                final Rig rig = rigs.get(0);
                if (rig.getGpus().size() > 0) {
                    found = true;
                }
            }
        } catch (final Exception e) {
            // Ignore
        }
        return found;
    }
}