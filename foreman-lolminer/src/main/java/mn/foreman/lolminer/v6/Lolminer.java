package mn.foreman.lolminer.v6;

import mn.foreman.io.Query;
import mn.foreman.lolminer.v6.json.Response;
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

import java.util.concurrent.TimeUnit;

/**
 * <h1>Overview</h1>
 *
 * A {@link Lolminer} represents a remote lolminer instance.
 *
 * <p>This class relies on the lolminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries lolminer by opening a connection to the API
 * interface.</p>
 *
 * <h1>Limitations</h1>
 *
 * <p>No fan or frequency info can be obtained from the API.</p>
 */
public class Lolminer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    public Lolminer(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Response response =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/summary",
                        "GET",
                        new TypeReference<Response>() {
                        },
                        1,
                        TimeUnit.SECONDS);

        // Add the pool
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(response.stratum.pool))
                        .setStatus(
                                true,
                                response.session.uptime > 0)
                        .setPriority(0)
                        .setCounts(
                                response.session.accepted,
                                response.session.submitted - response.session.accepted,
                                0)
                        .build());

        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(response.session.hashRate)
                        .addAttribute(
                                "gpu_algo",
                                response.mining.algorithm)
                        .addAttribute(
                                "coin",
                                response.mining.coin);

        // Add the GPUs
        response.gpus.forEach(gpu -> addGpu(gpu, rigBuilder));

        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Adds the provided {@link Response.Gpu}.
     *
     * @param gpu        The {@link Response.Gpu}.
     * @param rigBuilder The builder to update.
     */
    private static void addGpu(
            final Response.Gpu gpu,
            final Rig.Builder rigBuilder) {
        rigBuilder.addGpu(
                new Gpu.Builder()
                        .setIndex(gpu.index)
                        .setBus(gpu.address)
                        .setName(gpu.name)
                        .setTemp(0)
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(0)
                                        .setSpeedUnits("%")
                                        .build())
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(0)
                                        .setMemFreq(0)
                                        .build())
                        .build());
    }
}