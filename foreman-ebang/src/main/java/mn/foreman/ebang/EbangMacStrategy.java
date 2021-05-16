package mn.foreman.ebang;

import mn.foreman.ebang.json.AdminInfo;
import mn.foreman.model.MacStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/** Obtains the MAC address from an ebang. */
public class EbangMacStrategy
        implements MacStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(EbangMacStrategy.class);

    /** The ip. */
    private final String ip;

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The password. */
    private final String password;

    /** The port. */
    private final int port;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /** The username. */
    private final String username;

    /**
     * Constructor.
     *
     * @param ip                 The ip.
     * @param port               The port.
     * @param username           The username.
     * @param password           The password.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     * @param objectMapper       The json mapper.
     */
    public EbangMacStrategy(
            final String ip,
            final int port,
            final String username,
            final String password,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final ObjectMapper objectMapper) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<String> getMacAddress() {
        String mac = null;
        try {
            mac =
                    EbangQuery.query(
                            this.ip,
                            this.port,
                            this.username,
                            this.password,
                            "/admininfo/Getadmininfo",
                            null,
                            Collections.emptyList(),
                            (code, body) ->
                                    this.objectMapper.readValue(
                                            body,
                                            AdminInfo.class),
                            (code, body) -> {
                            },
                            this.socketTimeout,
                            this.socketTimeoutUnits)
                            .map(cgminerStatus -> cgminerStatus.feedback.mac)
                            .orElse(null);
        } catch (final Exception e) {
            LOG.warn("Exception occurred", e);
        }
        return Optional.ofNullable(mac);
    }
}