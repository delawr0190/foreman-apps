package mn.foreman.ewbf;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of ewbf.
 */
public class EwbfFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, Object> config) {
        return new Ewbf(
                config.get("apiIp").toString(),
                Integer.parseInt(config.get("apiPort").toString()));
    }
}