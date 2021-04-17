package mn.foreman.epic;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;
import mn.foreman.model.NullMacStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of epic.
 */
public class EpicFactory
        implements MinerFactory {

    @SuppressWarnings("unchecked")
    @Override
    public Miner create(final Map<String, Object> config) {
        return new Epic(
                config.get("apiIp").toString(),
                Integer.parseInt(config.get("apiPort").toString()),
                (List<String>) config.getOrDefault(
                        "statsWhitelist",
                        Collections.emptyList()),
                new NullMacStrategy());
    }
}
