package mn.foreman.bminer;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of bminer.
 */
public class BminerFactory
        implements MinerFactory {

    @Override
    public Miner create(Map<String, String> config) {
        return new Bminer(
                config.get("name"),
                config.get("apiIp"),
                Integer.parseInt(config.get("apiPort")));
    }
}
