package mn.foreman.nanominer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.nanominer.json.Stats;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * <h1>Overview</h1>
 *
 * A {@link Nanominer} represents a remote nanominer instance.
 *
 * <p>This class relies on the nanominer http api being enabled and
 * configured to allow the server that this application is running on to access
 * it.  If this application is running on the rig server, only localhost
 * connections need to be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <pre>
 *     GET /stats
 * </pre>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>Clocks and fans are not exposed via the API. Therefore, they aren't
 * reported to Foreman.</p>
 */
public class Nanominer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Nanominer(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Stats stats =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/stats",
                        new TypeReference<Stats>() {
                        });

        // Add pools
        stats.algorithms
                .stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .forEach(stat -> addPool(stat, statsBuilder));

        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(stats.algorithms
                                .stream()
                                .map(Map::values)
                                .flatMap(Collection::stream)
                                .map(stat -> stat.total.hashrate)
                                .reduce(BigDecimal.ZERO, BigDecimal::add));

        // Add GPUs
        stats.devices
                .stream()
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .forEach(
                        entry ->
                                addGpu(
                                        entry.getKey(),
                                        entry.getValue(),
                                        rigBuilder));

        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Adds the provided device.
     *
     * @param gpuName   The device name.
     * @param device    The device.
     * @param rigBuiler The builder to update.
     */
    private static void addGpu(
            final String gpuName,
            final Stats.Device device,
            final Rig.Builder rigBuiler) {
        rigBuiler.addGpu(
                new Gpu.Builder()
                        .setIndex(gpuName.split(" ")[1])
                        .setBus(device.pci.split(":")[0])
                        .setName(device.name)
                        .setTemp(device.temp)
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
     * Adds the provided algo pool.
     *
     * @param algoStat     The algo stats.
     * @param statsBuilder The builder to update.
     */
    private static void addPool(
            final Stats.AlgoStat algoStat,
            final MinerStats.Builder statsBuilder) {
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(
                                PoolUtils.sanitizeUrl(algoStat.pool))
                        .setStatus(
                                true,
                                true)
                        .setPriority(0)
                        .setCounts(
                                algoStat.total.accepted,
                                algoStat.total.rejected,
                                0)
                        .build());
    }
}