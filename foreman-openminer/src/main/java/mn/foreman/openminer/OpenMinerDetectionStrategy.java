package mn.foreman.openminer;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.MinerException;
import mn.foreman.openminer.response.Agg;
import mn.foreman.util.ArgUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link OpenMinerDetectionStrategy} provides a {@link DetectionStrategy} for
 * querying OpenMiner instances and identifying the {@link MinerType} that's
 * running.
 */
public class OpenMinerDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(OpenMinerDetectionStrategy.class);

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;

        final String username =
                args.getOrDefault("username", "").toString();
        final String password =
                args.getOrDefault("password", "").toString();

        // Test hook
        final int realPort =
                port != 8080 && port != 8081
                        ? 80
                        : port;

        try {
            final Agg stats = OpenMinerUtils.getStats(
                    ip,
                    realPort,
                    username,
                    password,
                    s -> {
                    });
            final int boards =
                    stats
                            .slots
                            .values()
                            .size();
            final Optional<OpenMinerType> type =
                    OpenMinerType.forBoards(
                            boards);
            if (type.isPresent()) {
                final Map<String, Object> newArgs = new HashMap<>(args);
                newArgs.put(
                        "mac",
                        stats.mac);

                if (ArgUtils.isWorkerPreferred(args)) {
                    newArgs.put(
                            "worker",
                            stats.pool.username);
                }

                detection =
                        Detection.builder()
                                .ipAddress(ip)
                                .port(port)
                                .minerType(type.get())
                                .parameters(newArgs)
                                .build();
            }
        } catch (final MinerException me) {
            LOG.debug("No openminer found on {}:{}",
                    ip,
                    port);
        }
        return Optional.ofNullable(detection);
    }
}