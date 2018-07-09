package mn.foreman.xmrig;

import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.model.Miner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.xmrig.json.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Xmrig.class);

    /** The API IP. */
    private final String apiIp;

    /** The API port. */
    private final int apiPort;

    /** The miner name. */
    private final String name;

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
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.isTrue(
                apiPort > 0,
                "apiPort must be > 0");
        this.name = name;
        this.apiIp = apiIp;
        this.apiPort = apiPort;
    }

    @Override
    public MinerStats getStats()
            throws MinerException {
        LOG.debug("Obtaining stats from {}-{}:{}",
                this.name,
                this.apiIp,
                this.apiPort);

        final MinerStats.Builder statsBuilder =
                new MinerStats.Builder()
                        .setApiIp(this.apiIp)
                        .setApiPort(this.apiPort)
                        .setName(this.name);

        final Response response = query();
        addPool(
                response,
                statsBuilder);
        addRig(
                response,
                statsBuilder);

        return statsBuilder.build();
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
                                                .setCount(1)
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
                                .setName(connection.pool)
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
                        .setName("xmrig")
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

    /**
     * Queries the API.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    private Response query()
            throws MinerException {
        Response response;

        final ApiRequest request =
                new ApiRequestImpl(
                        this.apiIp,
                        this.apiPort,
                        "/");

        final Connection connection =
                ConnectionFactory.createRestConnection(
                        request);
        connection.query();

        if (request.waitForCompletion(
                10,
                TimeUnit.SECONDS)) {
            final ObjectMapper objectMapper =
                    new ObjectMapper();
            try {
                response =
                        objectMapper.readValue(
                                request.getResponse(),
                                Response.class);
            } catch (final IOException ioe) {
                LOG.warn("Exception occurred while querying", ioe);
                throw new MinerException(ioe);
            }
        } else {
            throw new MinerException("Failed to obtain a response");
        }

        return response;
    }
}