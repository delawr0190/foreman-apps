package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import java.util.Arrays;
import java.util.List;

/**
 * An {@link AlternatingMiner} provides a {@link Miner} implementation that will
 * evaluate two possible {@link Miner miners} for metrics.
 */
public class AlternatingMiner
        extends AbstractMiner {

    /** The candidates. */
    private final List<Miner> candidates;

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    public AlternatingMiner(
            final String apiIp,
            final int apiPort,
            final Miner... candidates) {
        super(
                apiIp,
                apiPort);
        this.candidates = Arrays.asList(candidates);
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        MinerStats minerStats = null;
        for (final Miner candidate : this.candidates) {
            try {
                minerStats = candidate.getStats();
                break;
            } catch (final MinerException me) {
                // Skip
            }
        }

        if (minerStats != null) {
            statsBuilder
                    .addPools(minerStats.getPools())
                    .addAsics(minerStats.getAsics())
                    .addRigs(minerStats.getRigs());
        } else {
            throw new MinerException("Failed to find a valid candidate");
        }
    }
}