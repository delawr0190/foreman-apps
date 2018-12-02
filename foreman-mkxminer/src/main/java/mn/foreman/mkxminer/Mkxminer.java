package mn.foreman.mkxminer;

import mn.foreman.io.Query;
import mn.foreman.mkxminer.json.Response;
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
 * A {@link Mkxminer} represents a remote mkxminer instance.
 *
 * <p>This class relies on the mkxminer api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 * <li>http://{@link #apiIp}:{@link #apiPort}/stats</li>
 * </ul>
 *
 * <h1>Limitations</h1>
 *
 * <h2>Pools</h2>
 *
 * <p>Shares aren't reported via the mkxminer API.</p>
 */
public class Mkxminer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    public Mkxminer(
            final String apiIp,
            final int apiPort) {
        super(apiIp, apiPort);
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Response stats =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/stats",
                        new TypeReference<Response>() {
                        });

        addPool(
                stats.pool,
                statsBuilder);
        addRig(
                stats,
                statsBuilder);
    }

    /**
     * Adds the {@link mn.foreman.mkxminer.json.Response.Gpu} to the {@link
     * Rig.Builder builder}.
     *
     * @param gpu        The {@link mn.foreman.mkxminer.json.Response.Gpu}.
     * @param index      The index.
     * @param rigBuilder The builder.
     */
    private static void addGpu(
            final Response.Gpu gpu,
            final int index,
            final Rig.Builder rigBuilder) {
        rigBuilder.addGpu(
                new Gpu.Builder()
                        .setName(gpu.name)
                        .setIndex(index)
                        .setTemp(gpu.temperature)
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(gpu.gpuClock)
                                        .setMemFreq(gpu.memClock)
                                        .build())
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(1)
                                        .addSpeed(gpu.fan)
                                        .setSpeedUnits("%")
                                        .build())
                        .build());
    }

    /**
     * Adds the {@link mn.foreman.mkxminer.json.Response.Pool}.
     *
     * @param pool         The pool.
     * @param statsBuilder The builder.
     */
    private static void addPool(
            final Response.Pool pool,
            final MinerStats.Builder statsBuilder) {
        statsBuilder
                .addPool(
                        new Pool.Builder()
                                .setName(PoolUtils.sanitizeUrl(pool.address))
                                .setPriority(0)
                                .setStatus(
                                        true,
                                        true)
                                .setCounts(
                                        0,
                                        0,
                                        0)
                                .build());
    }

    /**
     * Adds the {@link Rig}.
     *
     * @param response     The response.
     * @param statsBuilder The builder.
     */
    private static void addRig(
            final Response response,
            final MinerStats.Builder statsBuilder) {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(
                                response.gpuTotals.hashrate.multiply(
                                        BigDecimal.valueOf(1000 * 1000)));
        final List<Response.Gpu> gpus = response.gpus;
        for (int i = 0; i < gpus.size(); i++) {
            addGpu(
                    gpus.get(i),
                    i,
                    rigBuilder);
        }
        statsBuilder.addRig(rigBuilder.build());
    }
}