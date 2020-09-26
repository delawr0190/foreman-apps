package mn.foreman.excavator;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of excavator.
 */
public class ExcavatorFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, Object> config) {
        return new Excavator(
                config.get("apiIp").toString(),
                Integer.parseInt(config.get("apiPort").toString()));
    }
}
