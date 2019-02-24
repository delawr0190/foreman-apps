package mn.foreman.grinpro;

import mn.foreman.grinpro.json.Status;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.List;

/**
 * <h1>Overview</h1>
 *
 * A {@link GrinPro} represents a remote grinpro miner instance.
 *
 * <p>This class currently queries:</p>
 *
 * <pre>
 *     GET /api/status
 * </pre>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>Clocks, temps, and fans are not exposed via the API. Therefore, they
 * aren't reported to Foreman.</p>
 *
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares are not directly reported.  They are most likely included in
 * the reported rejected shares.</p>
 */
public class GrinPro
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    GrinPro(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Status status =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/api/status",
                        new TypeReference<Status>() {
                        });
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(
                                PoolUtils.sanitizeUrl(
                                        status.stratum))
                        .setStatus(
                                true,
                                "Connected".equals(status.stratumStatus))
                        .setPriority(0)
                        .setCounts(
                                status.shares.accepted,
                                status.shares.rejected,
                                0)
                        .build());

        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(toHashRate(status.workers));
        status.workers.forEach(
                (gpu) ->
                        addGpu(
                                rigBuilder,
                                gpu));
        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Adds a GPU from the provided {@link Status.Worker worker}.
     *
     * @param rigBuilder The builder to update.
     * @param worker     The {@link Status.Worker}.
     */
    private static void addGpu(
            final Rig.Builder rigBuilder,
            final Status.Worker worker) {
        rigBuilder.addGpu(
                new Gpu.Builder()
                        .setIndex(worker.id)
                        .setBus(0)
                        .setName(worker.name)
                        .setTemp(0)
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(0)
                                        .setMemFreq(0)
                                        .build())
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(0)
                                        .setSpeedUnits("%")
                                        .build())
                        .build());
    }

    /**
     * Sums the hash rate of each worker.
     *
     * @param workers The workers.
     *
     * @return The hash rate.
     */
    private static BigDecimal toHashRate(final List<Status.Worker> workers) {
        return workers
                .stream()
                .map(worker -> worker.graphsPerSecond)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}