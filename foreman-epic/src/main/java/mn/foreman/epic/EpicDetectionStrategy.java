package mn.foreman.epic;

import mn.foreman.epic.json.Summary;
import mn.foreman.io.Query;
import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.MinerException;
import mn.foreman.util.ArgUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link EpicDetectionStrategy} provides a {@link DetectionStrategy} for
 * querying epic instances and identifying the {@link MinerType} that's
 * running.
 */
public class EpicDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(EpicDetectionStrategy.class);

    /** The strategy for detecting MACs. */
    private final MacStrategy macStrategy;

    /**
     * Constructor.
     *
     * @param macStrategy The MAC strategy.
     */
    public EpicDetectionStrategy(
            final MacStrategy macStrategy) {
        this.macStrategy = macStrategy;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;
        try {
            final Summary summary =
                    Query.restQuery(
                            ip,
                            port,
                            "/summary",
                            new TypeReference<Summary>() {
                            },
                            s -> {
                            });

            // If we got a summary, there must be an SC200
            final Map<String, Object> newArgs = new HashMap<>(args);
            this.macStrategy.getMacAddress()
                    .ifPresent(mac -> newArgs.put("mac", mac));

            if (ArgUtils.isWorkerPreferred(args)) {
                newArgs.put(
                        "worker",
                        summary.stratum.user);
            } else if (ArgUtils.isHostnamePreferred(args)) {
                newArgs.put(
                        "hostname",
                        summary.hostname);
            }

            detection =
                    Detection.builder()
                            .ipAddress(ip)
                            .port(port)
                            .minerType(EpicType.SC200)
                            .parameters(newArgs)
                            .build();
        } catch (final MinerException me) {
            LOG.debug("No epic found on {}:{}",
                    ip,
                    port);
        }
        return Optional.ofNullable(detection);
    }
}
