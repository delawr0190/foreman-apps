package mn.foreman.cgminer;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A {@link CgMinerFactory} provides a base {@link MinerFactory} implementation
 * that will handle the parsing of all of the standard cgminer params.
 */
public abstract class CgMinerFactory
        implements MinerFactory {

    @SuppressWarnings("unchecked")
    @Override
    public Miner create(final Map<String, Object> config) {
        return create(
                config.get("apiIp").toString(),
                config.get("apiPort").toString(),
                (List<String>) config.getOrDefault(
                        "statsWhitelist",
                        Collections.emptyList()),
                config);
    }

    /**
     * Creates a {@link Miner} from the provided params.
     *
     * @param apiIp          The API IP.
     * @param apiPort        The API port.
     * @param statsWhitelist The stats whitelist.
     * @param config         The config.
     *
     * @return The {@link Miner}.
     */
    protected abstract Miner create(
            final String apiIp,
            final String apiPort,
            final List<String> statsWhitelist,
            final Map<String, Object> config);
}
