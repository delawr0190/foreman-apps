package mn.foreman.ewbf;

import mn.foreman.ewbf.json.Stat;
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
import java.util.function.Function;

/**
 * <h1>Overview</h1>
 *
 * An {@link Ewbf} represents a remote ewbf instance.
 *
 * <p>This class relies on the ewbf api being enabled and configured to allow
 * the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 * <li>http://{@link #apiIp}:{@link #apiPort}/getstat</li>
 * </ul>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>GPU fan speeds and frequency info isn't reported via the API. Therefore,
 * they can't be reported.</p>
 *
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares aren't reported specifically.  Therefore, they can't be
 * reported.  They are, however, most likely included in the rejected
 * shares.</p>
 */
public class Ewbf
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Ewbf(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Stat stats =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/getstat",
                        new TypeReference<Stat>() {
                        });
        final long acceptedShares =
                sumResultAttribute(
                        stats.results,
                        (result) -> (long) result.acceptedShares);
        final long rejectedShares =
                sumResultAttribute(
                        stats.results,
                        (result) -> (long) result.rejectedShares);
        final long hashRate =
                sumResultAttribute(
                        stats.results,
                        (result) -> (long) result.speedSps);
        statsBuilder
                .addPool(
                        new Pool.Builder()
                                .setName(
                                        PoolUtils.sanitizeUrl(
                                                stats.currentServer))
                                .setPriority(0)
                                .setStatus(
                                        true,
                                        stats.serverStatus == 2)
                                .setCounts(
                                        acceptedShares,
                                        rejectedShares,
                                        0L)
                                .build());
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(new BigDecimal(hashRate));
        stats.results.forEach(
                (result) ->
                        addGpu(result, rigBuilder));
        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Sums an attribute on each provided {@link Stat.Result}.
     *
     * @param results  The {@link Stat.Result results}.
     * @param function The mapping function.
     *
     * @return The sum.
     */
    private static long sumResultAttribute(
            final List<Stat.Result> results,
            final Function<Stat.Result, Long> function) {
        return results
                .stream()
                .mapToLong(function::apply)
                .sum();
    }

    /**
     * Converts the provided {@link Stat.Result} to a {@link Gpu} and adds it to
     * the provided {@link Rig.Builder builder}.
     *
     * @param result     The {@link Stat.Result} to convert.
     * @param rigBuilder The builder to update.
     */
    private void addGpu(
            final Stat.Result result,
            final Rig.Builder rigBuilder) {
        rigBuilder
                .addGpu(
                        new Gpu.Builder()
                                .setName(result.name)
                                .setIndex(result.gpuId)
                                .setBus(
                                        Integer.parseInt(
                                                result.busId.split(":")[1],
                                                16))
                                .setTemp(result.temperature)
                                // No fan info in EWBF
                                .setFans(
                                        new FanInfo.Builder()
                                                .setCount(0)
                                                .setSpeedUnits("%")
                                                .build())
                                // No freq info in EWBF
                                .setFreqInfo(
                                        new FreqInfo.Builder()
                                                .setFreq(0)
                                                .setMemFreq(0)
                                                .build())
                                .build());
    }
}