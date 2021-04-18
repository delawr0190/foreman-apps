package mn.foreman.goldshell;

import mn.foreman.goldshell.json.Setting;
import mn.foreman.model.MacStrategy;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/** A {@link MacStrategy} for obtaining a MAC from a goldshell. */
public class GoldshellMacStrategy
        implements MacStrategy {

    /** The ip. */
    private final String ip;

    /** The port. */
    private final int port;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param ip                 The ip.
     * @param port               The port.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     */
    public GoldshellMacStrategy(
            final String ip,
            final int port,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.ip = ip;
        this.port = port;
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
    }

    @Override
    public Optional<String> getMacAddress() {
        return GoldshellQuery.runGet(
                ip,
                port,
                "/mcb/setting",
                null,
                new TypeReference<Setting>() {
                },
                this.socketTimeout,
                this.socketTimeoutUnits)
                .map(value -> {
                    if (value.name != null) {
                        return value.name.toLowerCase();
                    }
                    return null;
                });
    }
}
