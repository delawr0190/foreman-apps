package mn.foreman.dragonmint;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of dragonmint.
 */
public class DragonmintFactory
        implements MinerFactory {

    @SuppressWarnings("unchecked")
    @Override
    public Miner create(final Map<String, Object> config) {
        final String ip = config.get("apiIp").toString();
        final String username = config.get("username").toString();
        final String password = config.get("password").toString();
        return new Dragonmint(
                ip,
                Integer.parseInt(config.get("apiPort").toString()),
                username,
                password,
                (List<String>) config.getOrDefault(
                        "statsWhitelist",
                        Collections.emptyList()),
                new DragonmintMacStrategy(
                        ip,
                        Integer.parseInt(config.getOrDefault("port", "80").toString()),
                        username,
                        password));
    }
}
