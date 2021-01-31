package mn.foreman.obelisk;

import mn.foreman.io.Query;
import mn.foreman.model.*;
import mn.foreman.model.error.MinerException;
import mn.foreman.obelisk.json.Info;
import mn.foreman.util.ArgUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link ObeliskDetectionStrategy} provides a {@link DetectionStrategy} for
 * querying obelisk instances and identifying the {@link MinerType} that's
 * running.
 *
 * @param <T> The miner type.
 */
public class ObeliskDetectionStrategy<T extends MinerType>
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(ObeliskDetectionStrategy.class);

    /** The strategy for MACs. */
    private final MacStrategy macStrategy;

    /** The miner. */
    private final Miner miner;

    /**
     * Constructor.
     *
     * @param macStrategy The strategy.
     * @param miner       The miner.
     */
    public ObeliskDetectionStrategy(
            final MacStrategy macStrategy,
            final Miner miner) {
        this.macStrategy = macStrategy;
        this.miner = miner;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;
        try {
            final Info info =
                    Query.restQuery(
                            ip,
                            port,
                            "/api/info",
                            new TypeReference<Info>() {
                            });
            final Optional<ObeliskType> type =
                    ObeliskType.forType(
                            info.model);
            if (type.isPresent()) {
                final Map<String, Object> newArgs = new HashMap<>(args);
                this.macStrategy.getMacAddress()
                        .ifPresent(mac -> newArgs.put("mac", mac));
                if (ArgUtils.isWorkerPreferred(newArgs)) {
                    DetectionUtils.addWorkerFromStats(
                            this.miner,
                            newArgs);
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
            LOG.debug("No obelisk found on {}:{}",
                    ip,
                    port);
        }
        return Optional.ofNullable(detection);
    }
}
