package mn.foreman.lolminer;

import mn.foreman.lolminer.v4.Lolminer;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of lolminer.
 */
public class LolminerFactory
        implements MinerFactory {

    @Override
    public Miner create(final Map<String, String> config) {
        final String apiIp = config.get("apiIp");
        final int apiPort = Integer.parseInt(config.get("apiPort"));
        return new VersionDecorator(
                new Lolminer(
                        apiIp,
                        apiPort),
                new mn.foreman.lolminer.v6.Lolminer(
                        apiIp,
                        apiPort));
    }
}