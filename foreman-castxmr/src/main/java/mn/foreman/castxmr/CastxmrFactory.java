package mn.foreman.castxmr;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of castxmr.
 */
public class CastxmrFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        return new Castxmr(
                config.get("apiIp"),
                Integer.parseInt(config.get("apiPort")));
    }
}
