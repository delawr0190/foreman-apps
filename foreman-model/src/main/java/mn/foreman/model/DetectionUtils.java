package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;

import java.util.Map;
import java.util.Objects;

/** Utilities leveraged during miner detection. */
public class DetectionUtils {

    /**
     * Adds the workername from the provided {@link Miner miner's} stats, if
     * they're obtainable.
     *
     * @param miner The miner to query.
     * @param args  The args to enrich.
     */
    public static void addWorkerFromStats(
            final Miner miner,
            final Map<String, Object> args) {
        try {
            final MinerStats stats = miner.getStats();
            stats
                    .getPools()
                    .stream()
                    .map(Pool::getWorker)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .ifPresent(worker ->
                            args.put(
                                    "worker",
                                    worker));
        } catch (final MinerException me) {
            // Ignore
        }
    }
}
