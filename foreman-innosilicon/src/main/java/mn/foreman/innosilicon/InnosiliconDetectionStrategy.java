package mn.foreman.innosilicon;

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

/**
 * An {@link InnosiliconDetectionStrategy} provides a {@link DetectionStrategy}
 * for querying innosilicon instances and identifying the {@link MinerType}
 * that's running.
 */
public class InnosiliconDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(InnosiliconDetectionStrategy.class);

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, String> args) {

        Detection detection = null;
        try {
            final Overview overview =
                    Query.restQuery(
                            ip,
                            port,
                            "/api/overview",
                            args.getOrDefault("username", ""),
                            args.getOrDefault("password", ""),
                            new TypeReference<Overview>() {
                            },
                            1,
                            TimeUnit.SECONDS);
            final Optional<InnosiliconType> type =
                    InnosiliconType.forType(
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
            LOG.debug("No Innosilicon found on {}:{}", ip, port);
        }
        return Optional.ofNullable(detection);
    }
}
