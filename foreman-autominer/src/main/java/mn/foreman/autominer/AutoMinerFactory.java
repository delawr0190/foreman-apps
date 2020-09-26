package mn.foreman.autominer;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of autominer.
 */
public class AutoMinerFactory
        implements MinerFactory {

    /** Sets the mappings. */
    private final MinerMapping mappings;

    /**
     * Constructor.
     *
     * @param mappings The mappings.
     */
    public AutoMinerFactory(final MinerMapping mappings) {
        this.mappings = mappings;
    }

    @Override
    public Miner create(final Map<String, Object> config) {
        return new AutoMiner(
                config.get("apiIp").toString(),
                Integer.parseInt(config.get("apiPort").toString()),
                this.mappings);
    }
}