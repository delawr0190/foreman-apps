package mn.foreman.miniz;

import mn.foreman.io.Query;
import mn.foreman.miniz.json.Response;
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
 * A {@link Miniz} represents a remote miniz instance.
 *
 * <p>This class relies on the dstm-api being enabled and configured to allow
 * the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries "getstat" via HTTP.</p>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>GPU frequency info isn't exposed via the API.  Therefore, it can't be
 * reported.</p>
 *
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares are not directly reported.  They are most likely included in
 * the reported rejected shares.</p>
 */
public class Miniz
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Miniz(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Response response =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/",
                        makeCommand(),
                        new TypeReference<Response>() {
                        });
        addPool(
                response,
                statsBuilder);
        addRig(
                response.results,
                statsBuilder);
    }

    /**
     * Converts the provided {@link Response.Result} to a {@link Gpu} and adds
     * it to the provided builder.
     *
     * @param result     The result.
     * @param rigBuilder The builder.
     */
    private void addGpu(
            final Response.Result result,
            final Rig.Builder rigBuilder) {
        rigBuilder
                .addGpu(
                        new Gpu.Builder()
                                .setName(result.gpuName)
                                .setIndex(result.gpuId)
                                .setBus(0)
                                .setTemp(result.temperature)
                                .setFreqInfo(
                                        // No freq info in API
                                        new FreqInfo.Builder()
                                                .setFreq(result.coreClock)
                                                .setMemFreq(result.memoryClock)
                                                .build())
                                .setFans(
                                        new FanInfo.Builder()
                                                .setCount(1)
                                                .addSpeed(result.fanSpeed)
                                                .setSpeedUnits("%")
                                                .build())
                                .build());
    }

    /**
     * Adds the {@link Pool} in the {@link Response} to the provided builder.
     *
     * @param response     The response.
     * @param statsBuilder The builder.
     */
    private void addPool(
            final Response response,
            final MinerStats.Builder statsBuilder) {
        final long totalAccepted =
                response.results
                        .stream()
                        .mapToInt((result) -> result.acceptedShares)
                        .sum();
        final long totalRejected =
                response.results
                        .stream()
                        .mapToInt((result) -> result.rejectedShares)
                        .sum();
        statsBuilder
                .addPool(
                        new Pool.Builder()
                                .setName(PoolUtils.sanitizeUrl(response.server))
                                .setPriority(0)
                                .setStatus(
                                        true,
                                        true)
                                .setCounts(
                                        totalAccepted,
                                        totalRejected,
                                        0)
                                .build());
    }

    /**
     * Converts the provided {@link Response.Result} to a {@link Rig} and adds
     * it to the provided builder.
     *
     * @param results      The results.
     * @param statsBuilder The builder to update.
     */
    private void addRig(
            final List<Response.Result> results,
            final MinerStats.Builder statsBuilder) {
        final double hashRate =
                results
                        .stream()
                        .mapToDouble((result) -> result.solutionRate)
                        .sum();
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(new BigDecimal(hashRate));
        results
                .forEach(
                        (result) ->
                                addGpu(result, rigBuilder));
        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Generates a JSON RPC command.
     *
     * @return The command.
     */
    private String makeCommand() {
        return String.format(
                "{\"id\":%d,\"method\":\"%s\"}\n",
                0,
                "getstat");
    }
}