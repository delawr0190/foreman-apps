package mn.foreman.nicehash;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of nicehash.
 */
public class NiceHashMinerFactory
        implements MinerFactory {

    /** The candidate miners. */
    private final List<Miner> candidates;

    /**
     * Constructor.
     *
     * @param candidates The candidates.
     */
    public NiceHashMinerFactory(
            final List<Miner> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }

    @Override
    public Miner create(final Map<String, String> config) {
        return new NiceHashMiner(
                config.get("apiIp"),
                Integer.parseInt(config.get("apiPort")),
                this.candidates);
    }
}
