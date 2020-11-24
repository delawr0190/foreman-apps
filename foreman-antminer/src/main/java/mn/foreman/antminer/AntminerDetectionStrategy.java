package mn.foreman.antminer;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.error.MinerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** A strategy for detecting Antminers. */
public class AntminerDetectionStrategy
        implements DetectionStrategy {

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm The realm.
     */
    public AntminerDetectionStrategy(final String realm) {
        this.realm = realm;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        final boolean hostnamePreferred =
                Boolean.parseBoolean(
                        args.getOrDefault(
                                "hostname_preferred",
                                "false").toString());

        Detection detection = null;
        try {
            final int webPort =
                    Integer.parseInt(args.getOrDefault("webPort", "80").toString());
            final String username =
                    args.getOrDefault("username", "root").toString();
            final String password =
                    args.getOrDefault("password", "root").toString();

            final Optional<AntminerType> type =
                    AntminerUtils.getType(
                            ip,
                            port,
                            webPort,
                            this.realm,
                            username,
                            password,
                            s -> {
                            });
            if (type.isPresent()) {
                final AntminerType realType = type.get();

                final Map<String, Object> newArgs = new HashMap<>(args);
                if (hostnamePreferred) {
                    AntminerUtils.getSystemAttribute(
                            ip,
                            webPort,
                            username,
                            password,
                            this.realm,
                            "hostname")
                            .ifPresent(hostname ->
                                    newArgs.put(
                                            "hostname",
                                            hostname));
                }

                detection =
                        Detection
                                .builder()
                                .ipAddress(ip)
                                .port(port)
                                .minerType(realType)
                                .parameters(newArgs)
                                .build();
            }
        } catch (final MinerException e) {
            // Failed to find
        }
        return Optional.ofNullable(detection);
    }
}