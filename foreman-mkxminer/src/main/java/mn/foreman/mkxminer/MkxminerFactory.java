package mn.foreman.mkxminer;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of mkxminer.
 */
public class MkxminerFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        return new Mkxminer(
                config.get("apiIp"),
                Integer.parseInt(config.get("apiPort")));
    }
}
