package mn.foreman.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link DetectionStrategy} that will evaluate multiple candidate strategies,
 * completing when the first one hits.
 */
public class FirmwareAwareDetectionStrategy
        implements DetectionStrategy {

    /** The candidates. */
    private final List<DetectionStrategy> candidates;

    /**
     * Constructor.
     *
     * @param candidates The candidates.
     */
    public FirmwareAwareDetectionStrategy(final DetectionStrategy... candidates) {
        this.candidates = Arrays.asList(candidates);
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        return this.candidates
                .stream()
                .map(candidate -> {
                    Optional<Detection> detection = Optional.empty();
                    try {
                        detection =
                                candidate.detect(
                                        ip,
                                        port,
                                        args);
                    } catch (final Exception e) {
                        // Ignore
                    }
                    return detection;
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}