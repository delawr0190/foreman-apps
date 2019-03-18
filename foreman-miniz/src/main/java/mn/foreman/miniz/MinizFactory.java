package mn.foreman.miniz;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of miniz.
 */
public class MinizFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        return new Miniz(
                config.get("apiIp"),
                Integer.parseInt(config.get("apiPort")));
    }
}