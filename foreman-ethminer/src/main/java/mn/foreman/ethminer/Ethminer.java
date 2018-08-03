package mn.foreman.ethminer;

import mn.foreman.ethminer.json.Response;
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
import mn.foreman.util.PoolUtils;

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
 * An {@link Ethminer} represents a remote ethminer instance.
 *
 * <p>This class relies on the ethminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries "miner_getstathr" via JSON RPC.</p>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>GPU bus ID isn't exposed via the API.  Therefore, it can't be
 * reported.</p>
 *
 * <p>The GPU name, as well, isn't exposed via the API.  Therefore, an
 * artificial name is generated that matches the format: "GPU &lt;id&gt;"</p>
 *
 * <p>Also, the {@link FreqInfo} for each GPU isn't exposed.  The values that
 * are reported to Foreman are 0.</p>
 */
public class Ethminer
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Ethminer.class);

    /** The API IP. */
    private final String apiIp;

    /** The API password. */
    private final String apiPassword;

    /** The API port. */
    private final int apiPort;

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name        The name.
     * @param apiIp       The API IP.
     * @param apiPort     The API port.
     * @param apiPassword The API password.
     */
    Ethminer(
            final String name,
            final String apiIp,
            final int apiPort,
            final String apiPassword) {
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
        this.apiPassword = apiPassword;
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

        getStats(statsBuilder);

        return statsBuilder.build();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ name=%s, apiIp=%s, apiPort=%d, apiPassword=%s ]",
                getClass().getSimpleName(),
                this.name,
                this.apiIp,
                this.apiPort,
                this.apiPassword);
    }

    /**
     * Adds the {@link Pool} from the {@link Response.Result}.
     *
     * @param builder The builder.
     * @param result  The result.
     */
    private static void addPool(
            final MinerStats.Builder builder,
            final Response.Result result) {
        builder
                .addPool(
                        new Pool.Builder()
                                .setName(
                                        PoolUtils.sanitizeUrl(
                                                result.poolAddress))
                                .setStatus(
                                        true,
                                        (Integer.parseInt(result.runtime) > 0))
                                .setPriority(0)
                                .setCounts(
                                        result.ethShares,
                                        result.ethRejected,
                                        result.ethInvalid)
                                .build());
    }

    /**
     * Adds the {@link Rig} from the {@link Response.Result}.
     *
     * @param builder The builder.
     * @param result  The result.
     *
     * @throws MinerException on failure to parse the rig.
     */
    private static void addRig(
            final MinerStats.Builder builder,
            final Response.Result result)
            throws MinerException {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setName("ethminer_" + result.version)
                        .setHashRate(new BigDecimal(result.ethHashRate));
        final List<Integer> temperatures = result.temperatures;
        final List<Integer> fans = result.fanPercentages;
        if (temperatures.size() == fans.size()) {
            for (int i = 0; i < temperatures.size(); i++) {
                rigBuilder
                        .addGpu(
                                new Gpu.Builder()
                                        .setName("GPU " + i)
                                        .setIndex(Integer.toString(i))
                                        // Bus is not exposed in ethminer
                                        .setBus("0")
                                        .setTemp(temperatures.get(i))
                                        .setFans(
                                                new FanInfo.Builder()
                                                        .setCount(1)
                                                        .addSpeed(fans.get(i))
                                                        .setSpeedUnits("%")
                                                        .build())
                                        // No frequencies in ethminer
                                        .setFreqInfo(
                                                new FreqInfo.Builder()
                                                        .setFreq("0")
                                                        .setMemFreq("0")
                                                        .build())
                                        .build());
            }
        } else {
            throw new MinerException("Temps and fans should be the same size");
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
        final Response.Result result = response.result;
        addPool(
                builder,
                result);
        addRig(
                builder,
                result);
    }

    /**
     * Generates a JSON RPC command.
     *
     * @return The command.
     */
    private String makeCommand() {
        final String password =
                String.format(
                        ",\"params\": {\"psw\": \"%s\"}",
                        this.apiPassword);
        return String.format(
                "{\"id\":%d,\"jsonrpc\":\"%s\",\"method\":\"%s\"%s}\n",
                1,
                "2.0",
                "miner_getstathr",
                (this.apiPassword != null && !this.apiPassword.isEmpty())
                        ? password
                        : "");
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
                        makeCommand());
        final Connection connection =
                ConnectionFactory.createJsonConnection(
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
                LOG.warn("Exception occurred while querying", ioe);
                throw new MinerException(ioe);
            }
        } else {
            throw new MinerException("Failed to obtain a response");
        }

        return response;
    }
}