package mn.foreman.nicehash;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of nicehash.
 */
public class NiceHashMinerFactory
        implements MinerFactory {

    /** The algorithm that's actively being mined. */
    private final int algo;

    /** The candidate miners for each algorithm. */
    private final AlgorithmCandidates candidates;

    /**
     * Constructor.
     *
     * @param algo       The algorithm.
     * @param candidates The candidates.
     */
    public NiceHashMinerFactory(
            final int algo,
            final AlgorithmCandidates candidates) {
        this.algo = algo;
        this.candidates = candidates;
    }

    @Override
    public Miner create(final Map<String, String> config) {
        return new NiceHashMiner(
                config.get("apiIp"),
                Integer.parseInt(config.get("apiPort")),
                this.algo,
                this.candidates);
    }
}
