package mn.foreman.obelisk;

import mn.foreman.io.HttpRequestBuilder;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.error.MinerException;
import mn.foreman.obelisk.json.Info;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/** Obtains the MAC address from an Obelisk. */
public class ObeliskMacStrategy
        implements MacStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(ObeliskMacStrategy.class);

    /** The IP. */
    private final String ip;

    /** The mapper. */
    private final ObjectMapper objectMapper;

    /** The port. */
    private final int port;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param ip                 The IP.
     * @param port               The port.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     * @param objectMapper       The mapper.
     */
    public ObeliskMacStrategy(
            final String ip,
            final int port,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final ObjectMapper objectMapper) {
        this.ip = ip;
        this.port = port;
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<String> getMacAddress() {
        String mac = null;
        try {
            final Info info =
                    new HttpRequestBuilder<Info>()
                            .ip(this.ip)
                            .port(this.port)
                            .uri(
                                    "/api/info")
                            .socketTimeout(
                                    this.socketTimeout,
                                    this.socketTimeoutUnits)
                            .responseTransformer((integer, s) ->
                                    this.objectMapper.readValue(
                                            s,
                                            Info.class))
                            .get()
                            .orElseThrow(() -> new MinerException("Failed to obtain info"));
            mac = info.mac;
        } catch (final Exception e) {
            LOG.warn("Failed to obtain MAC", e);
        }
        return Optional.ofNullable(mac);
    }
}
