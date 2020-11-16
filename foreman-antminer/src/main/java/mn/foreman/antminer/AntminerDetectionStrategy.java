package mn.foreman.antminer;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.error.MinerException;

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
        Detection detection = null;
        try {
            final Optional<AntminerType> type =
                    AntminerUtils.getType(
                            ip,
                            port,
                            Integer.parseInt(args.getOrDefault("webPort", "80").toString()),
                            this.realm,
                            args.getOrDefault("username", "root").toString(),
                            args.getOrDefault("password", "root").toString(),
                            s -> {
                            });
            if (type.isPresent()) {
                final AntminerType realType = type.get();
                detection =
                        Detection
                                .builder()
                                .ipAddress(ip)
                                .port(port)
                                .minerType(realType)
                                .parameters(args)
                                .build();
            }
        } catch (final MinerException e) {
            // Failed to find
        }
        return Optional.ofNullable(detection);
    }
}