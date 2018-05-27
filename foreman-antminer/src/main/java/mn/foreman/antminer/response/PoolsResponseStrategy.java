package mn.foreman.antminer.response;

import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.response.CgMinerPoolStatus;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * A {@link PoolsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#POOLS}
 * response from an antminer.
 */
public class PoolsResponseStrategy
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(PoolsResponseStrategy.class);

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        if (response.hasValues()) {
            final List<Map<String, String>> values = response.getValues();
            values.forEach(value -> addPoolStats(builder, value));
        } else {
            LOG.debug("No pools found");
        }
    }

    /**
     * Utility method to convert the provided values, which contain per-pool
     * metrics, to a {@link Pool} and adds it to the provided builder.
     *
     * @param builder The builder to update.
     * @param values  The pool values.
     */
    private static void addPoolStats(
            final MinerStats.Builder builder,
            final Map<String, String> values) {
        final CgMinerPoolStatus status =
                CgMinerPoolStatus.forValue(values.get("Status"));
        builder.addPool(
                new Pool.Builder()
                        .setName(values.get("URL"))
                        .setPriority(values.get("Priority"))
                        .setStatus(
                                status.isEnabled(),
                                status.isUp())
                        .setCounts(
                                values.get("Accepted"),
                                values.get("Rejected"),
                                values.get("Stale"))
                        .setDifficulty(
                                values.get("Last Share Difficulty"))
                        .build());
    }
}