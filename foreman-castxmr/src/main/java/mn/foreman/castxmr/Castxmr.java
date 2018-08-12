package mn.foreman.castxmr;

import mn.foreman.castxmr.json.Response;
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

/**
 * <h1>Overview</h1>
 *
 * A {@link Castxmr} represents a remote castxmr instance.
 *
 * <p>This class relies on the castxmr-api being enabled and configured to allow
 * the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <pre>
 *     GET /
 * </pre>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>Frequency info isn't exposed via the API.</p>
 */
public class Castxmr
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Castxmr(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        addResponse(
                statsBuilder,
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/",
                        Response.class));
    }

    /**
     * Adds a {@link Gpu} to the provided {@link Rig.Builder builder} from the
     * information in the provided {@link Response.Device}.
     *
     * @param rigBuilder The builder to update.
     * @param device     The device.
     */
    private static void addGpu(
            final Rig.Builder rigBuilder,
            final Response.Device device) {
        rigBuilder.addGpu(
                new Gpu.Builder()
                        .setName(device.device)
                        .setIndex(device.deviceId)
                        .setBus(device.deviceId)
                        .setTemp(device.temperature)
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(0)
                                        .setMemFreq(0)
                                        .build())
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(1)
                                        .addSpeed(device.fanRpm)
                                        .setSpeedUnits("RPM")
                                        .build())
                        .build());
    }

    /**
     * Processes the {@link Response}.
     *
     * @param builder  The builder.
     * @param response The response to process.
     */
    private static void addResponse(
            final MinerStats.Builder builder,
            final Response response) {
        builder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(response.pool.server))
                        .setStatus(
                                true,
                                "connected".equals(response.pool.status))
                        .setPriority(0)
                        .setCounts(
                                response.shares.accepted,
                                response.shares.rejected,
                                response.shares.invalid)
                        .build());

        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(response.hashRate);
        response.devices.forEach(
                (device) ->
                        addGpu(
                                rigBuilder,
                                device));
        builder.addRig(rigBuilder.build());
    }
}