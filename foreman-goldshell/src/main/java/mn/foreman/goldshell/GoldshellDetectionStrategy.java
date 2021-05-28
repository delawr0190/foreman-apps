package mn.foreman.goldshell;

import mn.foreman.goldshell.json.Pool;
import mn.foreman.goldshell.json.Status;
import mn.foreman.io.Query;
import mn.foreman.model.*;
import mn.foreman.model.error.MinerException;
import mn.foreman.util.ArgUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /** The strategy for detecting MACs. */
    private final MacStrategy macStrategy;

    /**
     * Constructor.
     *
     * @param macStrategy   The MAC strategy.
     * @param configuration The configuration.
     */
    public GoldshellDetectionStrategy(
            final MacStrategy macStrategy,
            final ApplicationConfiguration configuration) {
        this.macStrategy = macStrategy;
        this.configuration = configuration;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;
        try {
            final ApplicationConfiguration.SocketConfig socketConfig =
                    this.configuration.getReadSocketTimeout();

            final Status status =
                    GoldshellQuery.runGet(
                            ip,
                            port,
                            "/mcb/status",
                            null,
                            new TypeReference<Status>() {
                            },
                            this.configuration)
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
                                socketConfig.getSocketTimeout(),
                                socketConfig.getSocketTimeoutUnits(),
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
