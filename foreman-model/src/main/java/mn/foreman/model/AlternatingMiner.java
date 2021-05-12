package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * An {@link AlternatingMiner} provides a {@link Miner} implementation that will
 * evaluate two possible {@link Miner miners} for metrics.
 */
public class AlternatingMiner
        extends AbstractMiner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AlternatingMiner.class);

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
    public Optional<String> getMacAddress() {
        for (final Miner candidate : this.candidates) {
            try {
                return candidate.getMacAddress();
            } catch (final Exception e) {
                // Skip
            }
        }
        return Optional.empty();
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
            } catch (final Exception e) {
                // Skip
            }
        }

        if (minerStats != null) {
            statsBuilder
                    .addPools(minerStats.getPools())
                    .addAsics(minerStats.getAsics())
                    .addRigs(minerStats.getRigs());
        } else {
            LOG.warn("Failed to find a valid candidate");
            throw new MinerException("Failed to find a valid candidate");
        }
    }
}