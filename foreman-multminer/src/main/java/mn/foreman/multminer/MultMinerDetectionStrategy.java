package mn.foreman.multminer;

import mn.foreman.io.Query;
import mn.foreman.model.*;
import mn.foreman.model.error.MinerException;
import mn.foreman.multminer.response.Stats;
import mn.foreman.util.ArgUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link MultMinerDetectionStrategy} provides a {@link DetectionStrategy} for
 * querying multminer instances and identifying the {@link MinerType} that's
 * running.
 */
public class MultMinerDetectionStrategy
        implements DetectionStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(MultMinerDetectionStrategy.class);

    /** The miner for obtaining stats. */
    private final Miner miner;

    /**
     * Constructor.
     *
     * @param miner The miner for obtaining stats.
     */
    public MultMinerDetectionStrategy(final Miner miner) {
        this.miner = miner;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Detection detection = null;
        try {
            final Stats stats =
                    Query.restQuery(
                            ip,
                            port,
                            "/gst.csp?a=a",
                            new TypeReference<Stats>() {
                            });
            final int numChips =
                    stats
                            .boards
                            .stream()
                            .mapToInt(board -> board.chipNumber)
                            .sum();
            final Optional<MultMinerType> type =
                    MultMinerType.forNumChips(numChips);
            if (type.isPresent()) {
                final Map<String, Object> newArgs = new HashMap<>(args);
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
            LOG.debug("No multminer found on {}:{}",
                    ip,
                    port);
        }
        return Optional.ofNullable(detection);
    }
}