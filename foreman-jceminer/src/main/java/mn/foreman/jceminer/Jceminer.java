package mn.foreman.jceminer;

import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.jceminer.json.Response;
import mn.foreman.model.Miner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Jceminer.class);

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
    Jceminer(
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

        addRig(
                query(),
                statsBuilder);

        return statsBuilder.build();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ name=%s, apiIp=%s, apiPort=%d ]",
                getClass().getSimpleName(),
                this.name,
                this.apiIp,
                this.apiPort);
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
                        .setName("GPU " + gpu.index)
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
     */
    private void addRig(
            final Response response,
            final MinerStats.Builder builder) {
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
                        .setName("jceminer_" + response.miner.version)
                        .setHashRate(response.hashrate.total);
        response.gpus.forEach(
                (gpu) ->
                        addGpu(
                                gpu,
                                rigBuilder));
        builder.addRig(rigBuilder.build());
    }

    /**
     * Queries the REST interface.
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
            // Got a response
            try {
                response =
                        new ObjectMapper().readValue(
                                request.getResponse(),
                                Response.class);
                if (response.gpus.size() == 0) {
                    throw new MinerException("Only GPU mining is supported");
                }
            } catch (final IOException ioe) {
                throw new MinerException(ioe);
            }
        } else {
            throw new MinerException("Failed to obtain a response");
        }

        return response;
    }
}