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
    public Miner create(final Map<String, Object> config) {
        return new Castxmr(
                config.get("apiIp").toString(),
                Integer.parseInt(config.get("apiPort").toString()));
    }
}
