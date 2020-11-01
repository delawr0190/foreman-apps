package mn.foreman.obelisk;

import mn.foreman.io.Query;
import mn.foreman.model.MacStrategy;
import mn.foreman.obelisk.json.Info;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/** Obtains the MAC address from an Obelisk. */
public class ObeliskMacStrategy
        implements MacStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(ObeliskMacStrategy.class);

    /** The IP. */
    private final String ip;

    /** The port. */
    private final int port;

    /**
     * Constructor.
     *
     * @param ip   The IP.
     * @param port The port.
     */
    ObeliskMacStrategy(
            final String ip,
            final int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public Optional<String> getMacAddress() {
        String mac = null;
        try {
            final Info info =
                    Query.restQuery(
                            this.ip,
                            this.port,
                            "/api/info",
                            new TypeReference<Info>() {
                            },
                            s -> {
                            });
            mac = info.mac;
        } catch (final Exception e) {
            LOG.warn("Failed to obtain MAC", e);
        }
        return Optional.ofNullable(mac);
    }
}
