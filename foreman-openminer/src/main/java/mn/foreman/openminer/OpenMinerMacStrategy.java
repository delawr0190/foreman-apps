package mn.foreman.openminer;

import mn.foreman.model.MacStrategy;
import mn.foreman.model.error.MinerException;
import mn.foreman.openminer.response.Agg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/** Obtains a MAC from an OpenMiner. */
public class OpenMinerMacStrategy
        implements MacStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(OpenMinerMacStrategy.class);

    /** The ip. */
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
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     */
    public OpenMinerMacStrategy(
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
            final Agg stats =
                    OpenMinerUtils.getStats(
                            this.ip,
                            this.port,
                            this.username,
                            this.password,
                            s -> {
                            });
            mac = stats.mac;
        } catch (final MinerException me) {
            LOG.warn("Failed to obtain MAC", me);
        }
        return Optional.ofNullable(mac);
    }
}
