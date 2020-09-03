package mn.foreman.cgminer;

import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.response.CgMinerPoolStatus;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.util.PoolUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * A {@link PoolsResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that's capable of parsing a {@link CgMinerCommand#POOLS}
 * response from a cgminer.
 */
public class PoolsResponseStrategy
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(PoolsResponseStrategy.class);

    /** The callback to invoke when a pool is found. */
    private final PoolCallback poolCallback;

    /** Constructor. */
    public PoolsResponseStrategy() {
        this(new NullPoolCallback());
    }

    /**
     * Constructor.
     *
     * @param poolCallback The callback to invoke when a pool is found.
     */
    public PoolsResponseStrategy(final PoolCallback poolCallback) {
        this.poolCallback = poolCallback;
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        if (response.hasValues()) {
            response.getValues()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().startsWith("POOL"))
                    .forEach(entry -> entry.getValue().forEach(
                            value -> {
                                this.poolCallback.foundPool(value);
                                addPoolStats(
                                        builder,
                                        value);
                            }));
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
        final String url = values.get("URL");
        if (!url.isEmpty()) {
            final CgMinerPoolStatus status =
                    CgMinerPoolStatus.forValue(values.get("Status"));
            builder.addPool(
                    new Pool.Builder()
                            .setName(PoolUtils.sanitizeUrl(url))
                            .setPriority(values.get("Priority"))
                            .setStatus(
                                    status.isEnabled(),
                                    status.isUp())
                            .setCounts(
                                    values.get("Accepted"),
                                    values.get("Rejected"),
                                    values.get("Stale"))
                            .build());
        }
    }
}