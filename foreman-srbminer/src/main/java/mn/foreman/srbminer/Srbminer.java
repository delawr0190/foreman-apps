package mn.foreman.srbminer;

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
import mn.foreman.srbminer.json.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
 *     <li>http://{@link #apiIp}:{@link #apiPort}/</li>
 * </ul>
 */
public class Srbminer
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Srbminer.class);

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
    Srbminer(
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
    public MinerStats getStats() throws MinerException {
        LOG.debug("Obtaining stats from {}-{}:{}",
                this.name,
                this.apiIp,
                this.apiPort);

        final MinerStats.Builder statsBuilder =
                new MinerStats.Builder()
                        .setApiIp(this.apiIp)
                        .setApiPort(this.apiPort)
                        .setName(this.name);

        getStats(statsBuilder);

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
                        .setName(pool.pool)
                        .setPriority(0)
                        .setStatus(
                                true,
                                pool.uptime > 0)
                        .setCounts(
                                shares.accepted,
                                shares.rejected,
                                shares.acceptedStale)
                        .build());
    }

    /**
     * Adds a rig to the provided builder.
     *
     * @param builder  The builder.
     * @param name     The name.
     * @param hashRate The hash rate.
     * @param devices  The devices.
     */
    private static void addRig(
            final MinerStats.Builder builder,
            final String name,
            final BigDecimal hashRate,
            final List<Response.Device> devices) {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setName(name)
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

    /**
     * Queries the API and updates the provided builder with the parsed
     * response.
     *
     * @param builder The builder to update.
     *
     * @throws MinerException on failure to query.
     */
    private void getStats(final MinerStats.Builder builder)
            throws MinerException {
        final Response response = query();
        addPool(
                builder,
                response.pool,
                response.shares);
        addRig(
                builder,
                response.rigName,
                response.hashRate,
                response.devices);
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
            try {
                response =
                        new ObjectMapper().readValue(
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