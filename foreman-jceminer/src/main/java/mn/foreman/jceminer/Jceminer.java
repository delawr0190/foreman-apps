package mn.foreman.jceminer;

import mn.foreman.io.Query;
import mn.foreman.jceminer.json.Response;
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

/**
 * <h1>Overview</h1>
 *
 * A {@link Jceminer} represents a remote jceminer instance.
 *
 * <p>This class relies on the jceminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <pre>
 *     http://{@link #apiIp}:{@link #apiPort}/
 * </pre>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>The GPU name isn't exposed.  Therefore, a name is generated that matches
 * the pattern:  GPU &lt;index&gt;</p>
 *
 * <p>Additionally, GPU bus ID, core clock, and memory clock aren't exposed via
 * the API. Therefore, they can't be reported.</p>
 *
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares aren't reported specifically.  Therefore, they can't be
 * reported.  They are, however, most likely included in the rejected
 * shares.</p>
 */
public class Jceminer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Jceminer(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        addRig(
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/",
                        new TypeReference<Response>() {
                        }),
                statsBuilder);
    }

    /**
     * Converts and adds the gpu to the builder.
     *
     * @param gpu        The GPU.
     * @param rigBuilder The builder.
     */
    private static void addGpu(
            final Response.Gpu gpu,
            final Rig.Builder rigBuilder) {
        rigBuilder.addGpu(
                new Gpu.Builder()
                        .setName(gpu.processor)
                        .setIndex(gpu.index)
                        .setBus(0)
                        .setTemp(gpu.temperature)
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(1)
                                        .addSpeed(gpu.fan)
                                        .setSpeedUnits("%")
                                        .build())
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(0)
                                        .setMemFreq(0)
                                        .build())
                        .build());
    }

    /**
     * Parses the response and adds the {@link Pool} and {@link Rig}.
     *
     * @param response The response.
     * @param builder  The builder.
     *
     * @throws MinerException on failure to query.
     */
    private void addRig(
            final Response response,
            final MinerStats.Builder builder)
            throws MinerException {
        if (response.gpus.size() != 0) {
            final int goodShares =
                    response.gpus
                            .stream()
                            .mapToInt((gpu) -> gpu.goodShares)
                            .sum();
            final int badShares =
                    response.gpus
                            .stream()
                            .mapToInt((gpu) -> gpu.badShares)
                            .sum();
            builder.addPool(
                    new Pool.Builder()
                            .setName(
                                    PoolUtils.sanitizeUrl(
                                            response.result.pool))
                            .setPriority(0)
                            .setStatus(true, true)
                            .setCounts(
                                    goodShares,
                                    badShares,
                                    0)
                            .build());

            final Rig.Builder rigBuilder =
                    new Rig.Builder()
                            .setHashRate(
                                    response.hashrate.hashRates
                                            .stream()
                                            .reduce(BigDecimal.ZERO,
                                                    BigDecimal::add));
            response.gpus.forEach(
                    (gpu) ->
                            addGpu(
                                    gpu,
                                    rigBuilder));
            builder.addRig(rigBuilder.build());
        } else {
            throw new MinerException("Only GPU mining is supported");
        }
    }
}