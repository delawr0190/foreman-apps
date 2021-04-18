package mn.foreman.goldshell;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of goldshell.
 */
public class GoldshellFactory
        implements MinerFactory {

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout units. */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout units.
     */
    public GoldshellFactory(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
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
                        this.socketTimeout,
                        this.socketTimeoutUnits),
                this.socketTimeout,
                this.socketTimeoutUnits);
    }
}
