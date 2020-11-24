package mn.foreman.srbminer;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of srbminer.
 */
public class SrbminerFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, Object> config) {
        final String apiIp = config.get("apiIp").toString();
        final int apiPort = Integer.parseInt(config.get("apiPort").toString());
        return new VersionDecorator(
                new SrbminerOld(
                        apiIp,
                        apiPort),
                new SrbminerNew(
                        apiIp,
                        apiPort));
    }
}
