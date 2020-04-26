package mn.foreman.dragonmint;

import mn.foreman.dragonmint.json.Summary;
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

/**
 * A {@link DragonmintDetectionStrategy} provides a {@link DetectionStrategy}
 * for querying dragonmint instances and identifying the {@link MinerType}
 * that's running.
 */
public class DragonmintDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(DragonmintDetectionStrategy.class);

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, String> args) {
        Detection detection = null;
        try {
            final Summary summary =
                    Query.restQuery(
                            ip,
                            port,
                            "/api/summary",
                            args.getOrDefault("username", ""),
                            args.getOrDefault("password", ""),
                            new TypeReference<Summary>() {
                            },
                            1,
                            TimeUnit.SECONDS);
            if (!summary.devs.isEmpty()) {
                final Optional<DragonmintType> type =
                        DragonmintType.forModel(
                                summary.devs.get(0).name);
                if (type.isPresent()) {
                    detection =
                            Detection.builder()
                                    .ipAddress(ip)
                                    .port(port)
                                    .minerType(type.get())
                                    .parameters(args)
                                    .build();
                }
            }
        } catch (final MinerException me) {
            LOG.debug("No Dragonmint found on {}:{}", ip, port);
        }
        return Optional.ofNullable(detection);
    }
}
