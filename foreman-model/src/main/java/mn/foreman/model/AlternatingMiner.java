package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

/**
 * An {@link AlternatingMiner} provides a {@link Miner} implementation that will
 * evaluate two possible {@link Miner miners} for metrics.
 */
public class AlternatingMiner
        extends AbstractMiner {

    /** The first candidate. */
    private final Miner candidate1;

    /** The second candidate. */
    private final Miner candidate2;

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    public AlternatingMiner(
            final String apiIp,
            final int apiPort,
            final Miner candidate1,
            final Miner candidate2) {
        super(
                apiIp,
                apiPort);
        this.candidate1 = candidate1;
        this.candidate2 = candidate2;
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        MinerStats minerStats;
        try {
            minerStats = this.candidate1.getStats();
        } catch (final MinerException me) {
            minerStats = this.candidate2.getStats();
        }

        statsBuilder
                .addPools(minerStats.getPools())
                .addAsics(minerStats.getAsics())
                .addRigs(minerStats.getRigs());
    }
}