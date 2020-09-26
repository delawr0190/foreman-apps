package mn.foreman.dragonmint;

import mn.foreman.dragonmint.json.Overview;
import mn.foreman.io.Query;
import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * A {@link DragonmintDetectionStrategy} provides a {@link DetectionStrategy}
 * for querying dragonmint instances and identifying the {@link MinerType}
 * that's running.
 *
 * @param <T> The miner type.
 */
public class DragonmintDetectionStrategy<T extends MinerType>
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(DragonmintDetectionStrategy.class);

    /** Type mapper. */
    private final Function<String, Optional<T>> mapper;

    /** The real miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param mapper The type mapper.
     * @param name   The name.
     */
    public DragonmintDetectionStrategy(
            final Function<String, Optional<T>> mapper,
            final String name) {
        this.mapper = mapper;
        this.name = name;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;
        try {
            final Overview overview =
                    Query.restQuery(
                            ip,
                            port,
                            "/api/overview",
                            (String) args.getOrDefault("username", ""),
                            (String) args.getOrDefault("password", ""),
                            new TypeReference<Overview>() {
                            },
                            1,
                            TimeUnit.SECONDS,
                            s -> {
                            });
            final Optional<T> type =
                    this.mapper.apply(
                            overview.type);
            if (type.isPresent()) {
                detection =
                        Detection.builder()
                                .ipAddress(ip)
                                .port(port)
                                .minerType(type.get())
                                .parameters(args)
                                .build();
            }
        } catch (final MinerException me) {
            LOG.debug("No {} found on {}:{}",
                    this.name,
                    ip,
                    port);
        }
        return Optional.ofNullable(detection);
    }
}
