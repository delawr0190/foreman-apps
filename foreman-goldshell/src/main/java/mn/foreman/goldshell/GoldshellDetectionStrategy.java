package mn.foreman.goldshell;

import mn.foreman.goldshell.json.Pool;
import mn.foreman.goldshell.json.Status;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * A {@link GoldshellDetectionStrategy} provides a {@link DetectionStrategy} for
 * querying goldshell instances and identifying the {@link MinerType} that's
 * running.
 */
public class GoldshellDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(GoldshellDetectionStrategy.class);

    /** The strategy for detecting MACs. */
    private final MacStrategy macStrategy;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout units. */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param macStrategy        The MAC strategy.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout units.
     */
    public GoldshellDetectionStrategy(
            final MacStrategy macStrategy,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.macStrategy = macStrategy;
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;
        try {
            final Status status =
                    GoldshellQuery.runGet(
                            ip,
                            port,
                            "/mcb/status",
                            null,
                            new TypeReference<Status>() {
                            },
                            this.socketTimeout,
                            this.socketTimeoutUnits)
                            .orElseThrow(() -> new MinerException("Not found"));

            final GoldshellType goldshellType =
                    GoldshellType.fromModel(status.model)
                            .orElseThrow(() -> new MinerException("Unknown type"));

            final Map<String, Object> newArgs = new HashMap<>(args);
            this.macStrategy.getMacAddress()
                    .ifPresent(mac -> newArgs.put("mac", mac));

            if (ArgUtils.isWorkerPreferred(args)) {
                final List<Pool> pools =
                        Query.restQuery(
                                ip,
                                port,
                                "/mcb/pools",
                                "GET",
                                new TypeReference<List<mn.foreman.goldshell.json.Pool>>() {
                                },
                                this.socketTimeout,
                                this.socketTimeoutUnits,
                                s -> {
                                });
                if (!pools.isEmpty()) {
                    newArgs.put(
                            "worker",
                            pools.get(0).user);
                }
            }

            detection =
                    Detection.builder()
                            .ipAddress(ip)
                            .port(port)
                            .minerType(goldshellType)
                            .parameters(newArgs)
                            .build();
        } catch (final MinerException me) {
            LOG.debug("No goldshell found on {}:{}",
                    ip,
                    port);
        }
        return Optional.ofNullable(detection);
    }
}
