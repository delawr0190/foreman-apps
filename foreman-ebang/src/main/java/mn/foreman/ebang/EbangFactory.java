package mn.foreman.ebang;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link MinerFactory} implementation that parses a configuration and creates
 * a {@link Miner} that will query an instance of ebang.
 */
public class EbangFactory
        implements MinerFactory {

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param socketTimeout      The timeout.
     * @param socketTimeoutUnits The timeout (units).
     * @param objectMapper       The json mapper.
     */
    public EbangFactory(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final ObjectMapper objectMapper) {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Miner create(final Map<String, Object> config) {
        final String ip = config.get("apiIp").toString();
        final String username = config.get("username").toString();
        final String password = config.get("password").toString();
        return new Ebang(
                ip,
                Integer.parseInt(config.get("apiPort").toString()),
                username,
                password,
                (List<String>) config.getOrDefault(
                        "statsWhitelist",
                        Collections.emptyList()),
                new EbangMacStrategy(
                        ip,
                        Integer.parseInt(config.getOrDefault("port", "80").toString()),
                        username,
                        password,
                        this.socketTimeout,
                        this.socketTimeoutUnits,
                        this.objectMapper),
                this.socketTimeout,
                this.socketTimeoutUnits,
                this.objectMapper);
    }
}
