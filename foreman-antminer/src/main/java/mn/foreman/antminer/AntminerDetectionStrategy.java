package mn.foreman.antminer;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.error.MinerException;

import java.util.*;

/** A strategy for detecting Antminers. */
public class AntminerDetectionStrategy
        implements DetectionStrategy {

    /** The strategies for detecting MACs. */
    private final List<MacStrategy> macStrategies;

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm         The realm.
     * @param macStrategies The mac strategies.
     */
    public AntminerDetectionStrategy(
            final String realm,
            final MacStrategy... macStrategies) {
        this.realm = realm;
        this.macStrategies = Arrays.asList(macStrategies);
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        final boolean hostnamePreferred =
                Boolean.parseBoolean(
                        args.getOrDefault(
                                "hostnamePreferred",
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

                this.macStrategies
                        .stream()
                        .map(MacStrategy::getMacAddress)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .findFirst()
                        .ifPresent(mac -> newArgs.put("mac", mac));

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