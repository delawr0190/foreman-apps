package mn.foreman.obelisk;

import mn.foreman.io.HttpRequestBuilder;
import mn.foreman.model.*;
import mn.foreman.model.error.MinerException;
import mn.foreman.obelisk.json.Info;
import mn.foreman.util.ArgUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The timeout. */
    private final int socketTimeout;

    /** The timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param macStrategy        The strategy.
     * @param miner              The miner.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     * @param objectMapper       The mapper.
     */
    public ObeliskDetectionStrategy(
            final MacStrategy macStrategy,
            final Miner miner,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final ObjectMapper objectMapper) {
        this.macStrategy = macStrategy;
        this.miner = miner;
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;
        try {
            final Info info =
                    new HttpRequestBuilder<Info>()
                            .ip(ip)
                            .port(port)
                            .uri(
                                    "/api/info")
                            .socketTimeout(
                                    this.socketTimeout,
                                    this.socketTimeoutUnits)
                            .responseTransformer((integer, s) ->
                                    this.objectMapper.readValue(
                                            s,
                                            Info.class))
                            .get()
                            .orElseThrow(() -> new MinerException("Failed to obtain info"));
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
