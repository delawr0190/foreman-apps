package mn.foreman.antminer;

import mn.foreman.model.ApplicationConfiguration;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** Gets the hostname for stock firmware. */
public class StockHostnameStrategy
        implements HostnameStrategy {

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm                    The realm.
     * @param applicationConfiguration The configuration.
     */
    public StockHostnameStrategy(
            final String realm,
            final ApplicationConfiguration applicationConfiguration) {
        this.realm = realm;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public Optional<String> getHostname(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        final int webPort =
                Integer.parseInt(args.getOrDefault("webPort", "80").toString());
        final String username =
                args.getOrDefault("username", "root").toString();
        final String password =
                args.getOrDefault("password", "root").toString();
        final AtomicReference<String> hostname = new AtomicReference<>();
        AntminerUtils.getSystemAttribute(
                ip,
                webPort,
                username,
                password,
                this.realm,
                "hostname",
                this.applicationConfiguration.getReadSocketTimeout())
                .ifPresent(hostname::set);
        return Optional.ofNullable(hostname.get());
    }
}
