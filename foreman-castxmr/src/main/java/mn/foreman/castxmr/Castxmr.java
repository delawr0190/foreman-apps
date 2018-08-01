package mn.foreman.castxmr;

import mn.foreman.castxmr.json.Response;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Castxmr.class);

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
    Castxmr(
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

        addResponse(
                statsBuilder,
                query());

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
                        .setName(response.pool.server)
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
                        .setName("castxmr")
                        .setHashRate(response.hashRate);
        response.devices.forEach(
                (device) ->
                        addGpu(
                                rigBuilder,
                                device));
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
            final ObjectMapper objectMapper =
                    new ObjectMapper();
            try {
                response =
                        objectMapper.readValue(
                                request.getResponse(),
                                Response.class);
            } catch (final IOException ioe) {
                throw new MinerException(ioe);
            }
        } else {
            throw new MinerException("Failed to obtain a response");
        }

        return response;
    }
}