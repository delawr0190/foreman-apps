package mn.foreman.goldshell;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of goldshell.
 */
public class GoldshellFactory
        implements MinerFactory {

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /**
     * Constructor.
     *
     * @param configuration The configuration.
     */
    public GoldshellFactory(final ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Miner create(final Map<String, Object> config) {
        final String ip = config.get("apiIp").toString();
        final int port = Integer.parseInt(config.get("apiPort").toString());
        return new Goldshell(
                ip,
                port,
                (List<String>) config.getOrDefault(
                        "statsWhitelist",
                        Collections.emptyList()),
                new GoldshellMacStrategy(
                        ip,
                        port,
                        this.configuration),
                this.configuration);
    }
}
