package mn.foreman.jceminer;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of jceminer.
 */
public class JceminerFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        return new Jceminer(
                config.get("apiIp"),
                Integer.parseInt(config.get("apiPort")));
    }
}