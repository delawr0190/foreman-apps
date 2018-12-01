package mn.foreman.srbminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.srbminer.json.Response;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.List;

/**
 * <h1>Overview</h1>
 *
 * A {@link Srbminer} represents a remote srbminer instance.
 *
 * <p>This class relies on the srbminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 * <li>http://{@link #apiIp}:{@link #apiPort}/</li>
 * </ul>
 */
public class Srbminer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Srbminer(
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
                        new TypeReference<Response>() {
                        });
        addPool(
                statsBuilder,
                response.pool,
                response.shares);
        addRig(
                statsBuilder,
                response.hashRate,
                response.devices);
    }

    /**
     * Adds a pool to the provided builder.
     *
     * @param builder The builder.
     * @param pool    The pool.
     * @param shares  The shares.
     */
    private static void addPool(
            final MinerStats.Builder builder,
            final Response.Pool pool,
            final Response.Shares shares) {
        builder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(pool.pool))
                        .setPriority(0)
                        .setStatus(
                                true,
                                pool.uptime > 0)
                        .setCounts(
                                shares.accepted,
                                shares.rejected,
                                0)
                        .build());
    }

    /**
     * Adds a rig to the provided builder.
     *
     * @param builder  The builder.
     * @param hashRate The hash rate.
     * @param devices  The devices.
     */
    private static void addRig(
            final MinerStats.Builder builder,
            final BigDecimal hashRate,
            final List<Response.Device> devices) {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(hashRate);
        for (final Response.Device device : devices) {
            rigBuilder.addGpu(
                    new Gpu.Builder()
                            .setName(device.model)
                            .setIndex(device.deviceId)
                            .setBus(device.busId)
                            .setTemp(device.temperature)
                            .setFreqInfo(
                                    new FreqInfo.Builder()
                                            .setFreq(device.coreClock)
                                            .setMemFreq(device.memoryClock)
                                            .build())
                            .setFans(
                                    new FanInfo.Builder()
                                            .setCount(1)
                                            .addSpeed(device.fanSpeedRpm)
                                            .setSpeedUnits("RPM")
                                            .build())
                            .build());
        }
        builder.addRig(rigBuilder.build());
    }
}