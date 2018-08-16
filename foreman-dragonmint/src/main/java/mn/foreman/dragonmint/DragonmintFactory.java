package mn.foreman.dragonmint;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of dragonmint.
 */
public class DragonmintFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        return new Dragonmint(
                config.get("name"),
                config.get("apiIp"),
                Integer.parseInt(config.get("apiPort")),
                config.get("username"),
                config.get("password"));
    }
}
