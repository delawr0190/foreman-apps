package mn.foreman.honorknight;

import mn.foreman.honorknight.response.Overview;
import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.MinerType;
import mn.foreman.model.error.MinerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/** Detects a honorknight miner. */
public class HonorKnightDetectionStrategy<T extends MinerType>
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(HonorKnightDetectionStrategy.class);

    /** A mapper for creating the miner type from the model. */
    private final Function<String, Optional<T>> mapper;

    /**
     * Constructor.
     *
     * @param mapper The mapper.
     */
    public HonorKnightDetectionStrategy(
            final Function<String, Optional<T>> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;
        try {
            final Overview overview =
                    HonorKnightQuery.overview(
                            ip,
                            args.containsKey("test")
                                    ? port
                                    : 80)
                            .orElseThrow(() -> new MinerException("Not found"));
            final Optional<T> type =
                    this.mapper.apply(
                            overview.minerInfo.model);
            if (type.isPresent()) {
                detection =
                        Detection.builder()
                                .ipAddress(ip)
                                .port(4028)
                                .minerType(type.get())
                                .parameters(args)
                                .build();
            }
        } catch (final MinerException me) {
            LOG.debug("No miner found on {}:{}",
                    ip,
                    port);
        }
        return Optional.ofNullable(detection);
    }
}