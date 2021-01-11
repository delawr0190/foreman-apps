package mn.foreman.antminer;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MacStrategy;

import java.util.*;

/** A strategy for detecting Antminers. */
public class AntminerDetectionStrategy
        implements DetectionStrategy {

    /** The hostname strategies. */
    private final List<HostnameStrategy> hostnameStrategies;

    /** The strategies for detecting MACs. */
    private final List<MacStrategy> macStrategies;

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm              The realm.
     * @param macStrategies      The mac strategies.
     * @param hostnameStrategies The hostname strategies.
     */
    public AntminerDetectionStrategy(
            final String realm,
            final List<MacStrategy> macStrategies,
            final List<HostnameStrategy> hostnameStrategies) {
        this.realm = realm;
        this.macStrategies = new ArrayList<>(macStrategies);
        this.hostnameStrategies = new ArrayList<>(hostnameStrategies);
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
                    this.hostnameStrategies
                            .stream()
                            .map(hostnameStrategy -> {
                                try {
                                    return hostnameStrategy.getHostname(
                                            ip,
                                            port,
                                            args);
                                } catch (final Exception e) {
                                    return Optional.empty();
                                }
                            })
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .findFirst()
                            .ifPresent(
                                    hostname ->
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
        } catch (final Exception e) {
            // Failed to find
        }
        return Optional.ofNullable(detection);
    }
}