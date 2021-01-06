package mn.foreman.dragonmint;

import mn.foreman.dragonmint.json.Overview;
import mn.foreman.io.Query;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/** Obtains the MAC for a dragonmint. */
public class DragonmintMacStrategy
        implements MacStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(DragonmintMacStrategy.class);

    /** The IP. */
    private final String ip;

    /** The password. */
    private final String password;

    /** The port. */
    private final int port;

    /** The username. */
    private final String username;

    /**
     * Constructor.
     *
     * @param ip       The IP.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     */
    public DragonmintMacStrategy(
            final String ip,
            final int port,
            final String username,
            final String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<String> getMacAddress() {
        String mac = null;
        try {
            final Overview overview =
                    Query.restQuery(
                            this.ip,
                            this.port,
                            "/api/overview",
                            this.username,
                            this.password,
                            new TypeReference<Overview>() {
                            },
                            1,
                            TimeUnit.SECONDS,
                            s -> {
                            });
            mac = overview.version.mac.replace(" ", "");
        } catch (final MinerException e) {
            LOG.warn("Failed to obtain mac", e);
        }
        return Optional.ofNullable(mac);
    }
}
