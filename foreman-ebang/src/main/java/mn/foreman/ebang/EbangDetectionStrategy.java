package mn.foreman.ebang;

import mn.foreman.ebang.json.CgminerVal;
import mn.foreman.ebang.json.SystemStatus;
import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.MinerException;
import mn.foreman.util.ArgUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * An {@link EbangDetectionStrategy} provides a {@link DetectionStrategy} for
 * querying ebang instances and identifying the {@link MinerType} that's
 * running.
 */
public class EbangDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(EbangDetectionStrategy.class);

    /** The strategy for detecting MACs. */
    private final MacStrategy macStrategy;

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param macStrategy        The MAC strategy.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     * @param objectMapper       The json mapper.
     */
    public EbangDetectionStrategy(
            final MacStrategy macStrategy,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final ObjectMapper objectMapper) {
        this.macStrategy = macStrategy;
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
            final String username =
                    (String) args.getOrDefault("username", "");
            final String password =
                    (String) args.getOrDefault("password", "");

            final SystemStatus systemStatus =
                    EbangQuery.query(
                            ip,
                            port,
                            username,
                            password,
                            "/Status/getsystemstatus",
                            null,
                            Collections.emptyList(),
                            (code, body) ->
                                    this.objectMapper.readValue(
                                            body,
                                            SystemStatus.class),
                            (code, body) -> {
                            },
                            this.socketTimeout,
                            this.socketTimeoutUnits)
                            .orElseThrow(() -> new MinerException("Failed to obtain systemstatus"));
            final Optional<EbangType> type =
                    EbangType.forType(systemStatus.feedback.productName);
            if (type.isPresent()) {
                final Map<String, Object> newArgs = new HashMap<>(args);
                this.macStrategy.getMacAddress()
                        .ifPresent(mac -> newArgs.put("mac", mac));

                if (ArgUtils.isWorkerPreferred(args)) {
                    EbangQuery.query(
                            ip,
                            port,
                            username,
                            password,
                            "/Cgminer/CgminerGetVal",
                            null,
                            Collections.emptyList(),
                            (code, body) ->
                                    this.objectMapper.readValue(
                                            body,
                                            CgminerVal.class),
                            (code, body) -> {
                            },
                            this.socketTimeout,
                            this.socketTimeoutUnits)
                            .flatMap(cgminerVal1 -> getWorker(cgminerVal1.feedback))
                            .ifPresent(s -> newArgs.put("worker", s));
                }

                detection =
                        Detection.builder()
                                .ipAddress(ip)
                                .port(port)
                                .minerType(type.get())
                                .parameters(newArgs)
                                .build();
            }
        } catch (final Exception me) {
            LOG.debug("No ebang found on {}:{}",
                    ip,
                    port);
        }
        return Optional.ofNullable(detection);
    }

    /**
     * Obtains the worker from the feedback.
     *
     * @param feedback The feedback.
     *
     * @return The worker.
     */
    private static Optional<String> getWorker(
            final CgminerVal.Feedback feedback) {
        if (feedback.pool1Worker != null && !feedback.pool1Worker.isEmpty()) {
            return Optional.of(feedback.pool1Worker);
        } else if (feedback.pool2Worker != null && !feedback.pool2Worker.isEmpty()) {
            return Optional.of(feedback.pool2Worker);
        } else {
            return Optional.ofNullable(feedback.pool3Worker);
        }
    }
}
