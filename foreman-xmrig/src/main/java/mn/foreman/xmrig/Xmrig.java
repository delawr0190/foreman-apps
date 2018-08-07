package mn.foreman.xmrig;

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
import mn.foreman.xmrig.json.Response;

import java.util.List;

/**
 * <h1>Overview</h1>
 *
 * An {@link Xmrig} represents a remote xmrig instance.
 *
 * <p>This class relies on the xmrig-api being enabled and configured to allow
 * the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 *     <li>http://{@link #apiIp}:{@link #apiPort}/</li>
 * </ul>
 *
 * <h1>Limitations</h1>
 *
 * <h2>xmrig-nvidia</h2>
 *
 * <p>GPU bus is not reported.  Therefore, it isn't exposed to Foreman.</p>
 *
 * <h2>xmrig-amd</h2>
 *
 * <p>The API for xmrig-amd is very limited.  Therefore, the GPUs that are
 * reported contain almost no information.</p>
 *
 * <p>The GPUs are reported with no information so that Foreman, at the very
 * least, can report the number of GPUs that were successfully identified in the
 * rig.</p>
 *
 * <p>If additional AMD support isn't exposed via the API, an alternative
 * program will be provided by Foreman to provide a REST interface that will
 * query and provide temp, freq, and fan metrics from the native APIs.</p>
 *
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares aren't reported specifically.  Therefore, they can't be
 * reported.  They are, however, most likely included in the rejected
 * shares.</p>
 */
public class Xmrig
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param name    The name.
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Xmrig(
            final String name,
            final String apiIp,
            final int apiPort) {
        super(
                name,
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
                        Response.class);
        addPool(
                response,
                statsBuilder);
        addRig(
                response,
                statsBuilder);
    }

    /**
     * Adds a {@link Gpu} to the provided {@link Rig.Builder builder}.
     *
     * @param health     The {@link Response.Health}.
     * @param index      The index.
     * @param rigBuilder The builder.
     */
    private void addGpuFromHealth(
            final Response.Health health,
            final int index,
            final Rig.Builder rigBuilder) {
        rigBuilder.addGpu(
                new Gpu.Builder()
                        .setName(health.name)
                        .setIndex(index)
                        // No bus in API
                        .setBus(0)
                        .setTemp(health.temp)
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(1)
                                        .addSpeed(health.fan)
                                        .setSpeedUnits("%")
                                        .build())
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(health.clock)
                                        .setMemFreq(health.memClock)
                                        .build())
                        .build());
    }

    /**
     * Adds a {@link Gpu} to the provided {@link Rig.Builder builder}.
     *
     * @param index      The index.
     * @param rigBuilder The builder.
     */
    private void addGpuFromThread(
            final int index,
            final Rig.Builder rigBuilder) {
        rigBuilder
                .addGpu(
                        new Gpu.Builder()
                                .setName("GPU " + index)
                                .setIndex(index)
                                // No bus in API
                                .setBus(0)
                                // No temp in API
                                .setTemp(0)
                                // No fans in API
                                .setFans(
                                        new FanInfo.Builder()
                                                .setCount(0)
                                                .setSpeedUnits("%")
                                                .build())
                                // No freq info in API
                                .setFreqInfo(
                                        new FreqInfo.Builder()
                                                .setFreq(0)
                                                .setMemFreq(0)
                                                .build())
                                .build());
    }

    /**
     * Adds a {@link Gpu} to the provided {@link Rig.Builder builder}.
     *
     * @param healths    The threads.
     * @param rigBuilder The builder.
     */
    private void addGpusFromHealths(
            final List<Response.Health> healths,
            final Rig.Builder rigBuilder) {
        for (int i = 0; i < healths.size(); i++) {
            addGpuFromHealth(
                    healths.get(i),
                    i,
                    rigBuilder);
        }
    }

    /**
     * Adds a {@link Gpu} to the provided {@link Rig.Builder builder}.
     *
     * @param threads    The threads.
     * @param rigBuilder The builder.
     */
    private void addGpusFromThreads(
            final List<List<Integer>> threads,
            final Rig.Builder rigBuilder) {
        for (int i = 0; i < threads.size(); i++) {
            addGpuFromThread(
                    i,
                    rigBuilder);
        }
    }

    /**
     * Adds a {@link Pool} from the {@link Response}.
     *
     * @param response     The {@link Response}.
     * @param statsBuilder The {@link MinerStats.Builder builder} to update.
     */
    private void addPool(
            final Response response,
            final MinerStats.Builder statsBuilder) {
        final Response.Connection connection = response.connection;
        final Response.Results results = response.results;
        statsBuilder
                .addPool(
                        new Pool.Builder()
                                .setName(
                                        PoolUtils.sanitizeUrl(
                                                connection.pool))
                                .setStatus(true, connection.uptime > 0)
                                .setPriority(0)
                                .setCounts(
                                        results.sharesGood,
                                        results.sharesTotal - results.sharesGood,
                                        0)
                                .build());
    }

    /**
     * Adds a {@link Rig} from the {@link Response}.
     *
     * @param response     The {@link Response}.
     * @param statsBuilder The {@link MinerStats.Builder builder} to update.
     */
    private void addRig(
            final Response response,
            final MinerStats.Builder statsBuilder) {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setName("xmrig_" + response.ua.split(" ")[0])
                        .setHashRate(response.hashrate.totals.get(0));
        if (response.health != null) {
            addGpusFromHealths(
                    response.health,
                    rigBuilder);
        } else {
            addGpusFromThreads(
                    response.hashrate.threads,
                    rigBuilder);
        }
        statsBuilder.addRig(rigBuilder.build());
    }
}