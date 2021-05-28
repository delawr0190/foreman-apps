package mn.foreman.goldshell;

import mn.foreman.goldshell.json.Setting;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.MacStrategy;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Optional;

/** A {@link MacStrategy} for obtaining a MAC from a goldshell. */
public class GoldshellMacStrategy
        implements MacStrategy {

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /** The ip. */
    private final String ip;

    /** The port. */
    private final int port;

    /**
     * Constructor.
     *
     * @param ip            The ip.
     * @param port          The port.
     * @param configuration The configuration.
     */
    public GoldshellMacStrategy(
            final String ip,
            final int port,
            final ApplicationConfiguration configuration) {
        this.ip = ip;
        this.port = port;
        this.configuration = configuration;
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
                this.configuration)
                .map(value -> {
                    if (value.name != null) {
                        return value.name.toLowerCase();
                    }
                    return null;
                });
    }
}
