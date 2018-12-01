package mn.foreman.gminer;

import mn.foreman.gminer.json.Stat;
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
 * A {@link Gminer} represents a remote gminer instance.
 *
 * <p>This class relies on the gminer api being enabled and configured to allow
 * the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 * <li>http://{@link #apiIp}:{@link #apiPort}/stat</li>
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
public class Gminer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Gminer(
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
                        "/stat",
                        new TypeReference<Stat>() {
                        });
        final long acceptedShares =
                sumDeviceAttribute(
                        stats.devices,
                        (result) -> (long) result.acceptedShares);
        final long rejectedShares =
                sumDeviceAttribute(
                        stats.devices,
                        (result) -> (long) result.rejectedShares);
        final long hashRate =
                sumDeviceAttribute(
                        stats.devices,
                        (result) -> (long) result.speed);
        statsBuilder
                .addPool(
                        new Pool.Builder()
                                .setName(
                                        PoolUtils.sanitizeUrl(
                                                stats.server))
                                .setPriority(0)
                                .setStatus(
                                        true,
                                        true)
                                .setCounts(
                                        acceptedShares,
                                        rejectedShares,
                                        0L)
                                .build());
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(new BigDecimal(hashRate));
        stats.devices.forEach(
                (device) ->
                        addGpu(device, rigBuilder));
        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Sums an attribute on each provided {@link Stat.Device}.
     *
     * @param devices  The {@link Stat.Device devices}.
     * @param function The mapping function.
     *
     * @return The sum.
     */
    private static long sumDeviceAttribute(
            final List<Stat.Device> devices,
            final Function<Stat.Device, Long> function) {
        return devices
                .stream()
                .mapToLong(function::apply)
                .sum();
    }

    /**
     * Converts the provided {@link Stat.Device} to a {@link Gpu} and adds it to
     * the provided {@link Rig.Builder builder}.
     *
     * @param device     The {@link Stat.Device} to convert.
     * @param rigBuilder The builder to update.
     */
    private void addGpu(
            final Stat.Device device,
            final Rig.Builder rigBuilder) {
        rigBuilder
                .addGpu(
                        new Gpu.Builder()
                                .setName(device.name)
                                .setIndex(device.gpuId)
                                .setBus(
                                        Integer.parseInt(
                                                device.busId.split(":")[1],
                                                16))
                                .setTemp(device.temperature)
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