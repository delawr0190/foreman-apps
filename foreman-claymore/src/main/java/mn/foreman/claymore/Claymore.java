package mn.foreman.claymore;

import mn.foreman.claymore.json.Response;
import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.model.Miner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Overview</h1>
 *
 * A {@link Claymore} represents a remote claymore instance.
 *
 * <p>This class relies on the claymore-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries "miner_getstat1" via JSON RPC.</p>
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
 *
 * <h2>Pools</h2>
 *
 * <h3>Connection status</h3>
 *
 * <p>The API doesn't expose information related to when a connection to a pool
 * has been broken.  Therefore, the {@link Pool} will always appear to be
 * connected. Shares, however, will not increase, which can be used to infer a
 * problem.</p>
 */
public class Claymore
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Claymore.class);

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
    Claymore(
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
    public MinerStats getStats() {
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
     * Adds a {@link Pool} using the provided parameters.
     *
     * @param pools          The pools.
     * @param totalShares    The total shares.
     * @param rejectedShares The rejected shares.
     * @param invalidShares  The invalid shares.
     * @param builder        The builder to update.
     */
    private static void addPools(
            final String[] pools,
            final String[] totalShares,
            final String[] rejectedShares,
            final String[] invalidShares,
            final MinerStats.Builder builder) {
        for (int i = 0; i < pools.length; i++) {
            builder.addPool(
                    new Pool.Builder()
                            .setName(pools[i])
                            .setStatus(true, true)
                            .setPriority(0)
                            .setCounts(
                                    toInt(totalShares[i]) - toInt(rejectedShares[i]),
                                    toInt(rejectedShares[i]),
                                    toInt(invalidShares[i]))
                            .build());
        }
    }

    /**
     * Adds a {@link Rig} using the provided parameters.
     *
     * @param version     The miner version.
     * @param ethHashRate The ETH hash rate.
     * @param dcrHashRate The DCR hash rate.
     * @param temps       The temperatures.
     * @param fans        The fans.
     * @param builder     The builder to update.
     */
    private static void addRig(
            final String version,
            final String ethHashRate,
            final String dcrHashRate,
            final List<String> temps,
            final List<String> fans,
            final MinerStats.Builder builder) {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setName("claymore_" + version)
                        .setHashRate(toHashRate(ethHashRate, dcrHashRate));
        for (int i = 0; i < temps.size(); i++) {
            rigBuilder
                    .addGpu(
                            new Gpu.Builder()
                                    .setName("GPU " + i)
                                    .setIndex(Integer.toString(i))
                                    // Bus is not exposed in claymore
                                    .setBus("0")
                                    .setTemp(temps.get(i))
                                    .setFans(
                                            new FanInfo.Builder()
                                                    .setCount(1)
                                                    .addSpeed(fans.get(i))
                                                    .setSpeedUnits("%")
                                                    .build())
                                    // No frequencies in claymore
                                    .setFreqInfo(
                                            new FreqInfo.Builder()
                                                    .setFreq("0")
                                                    .setMemFreq("0")
                                                    .build())
                                    .build());
        }
        builder.addRig(rigBuilder.build());
    }

    /**
     * Combines the two hash rates into a {@link BigDecimal}.
     *
     * @param ethRate The ETH rate.
     * @param dcrRate The DCR rate.
     *
     * @return The hash rate.
     */
    private static BigDecimal toHashRate(
            final String ethRate,
            final String dcrRate) {
        return new BigDecimal(ethRate)
                .add(new BigDecimal(dcrRate));
    }

    /**
     * Converts the provided {@link String} to an int.
     *
     * @param value The value to convert.
     *
     * @return The int.
     */
    private static int toInt(final String value) {
        if (!"off".equals(value)) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    /**
     * Queries the API and updates the provided builder with the parsed
     * response.
     *
     * @param builder The builder to update.
     */
    private void getStats(final MinerStats.Builder builder) {
        query().ifPresent((response) -> {
            final List<String> results = response.result;
            final String minerVersion = results.get(0);

            final String[] ethRateAndShares = results.get(2).split(";");
            final String ethHashRate = ethRateAndShares[0];
            final String ethTotalShares = ethRateAndShares[1];
            final String ethRejectedShares = ethRateAndShares[2];

            final String[] dcrRateAndShares = results.get(4).split(";");
            final String dcrHashRate = dcrRateAndShares[0];
            final String dcrTotalShares = dcrRateAndShares[1];
            final String dcrRejectedShares = dcrRateAndShares[2];

            final List<String> temps = new LinkedList<>();
            final List<String> fans = new LinkedList<>();
            final String[] tempsAndFans = results.get(6).split(";");
            for (int i = 0; i < tempsAndFans.length / 2; i += 2) {
                temps.add(tempsAndFans[i]);
                fans.add(tempsAndFans[i + 1]);
            }

            final String[] pools = results.get(7).split(";");
            final String[] shares = results.get(8).split(";");

            addPools(
                    pools,
                    new String[]{ethTotalShares, dcrTotalShares},
                    new String[]{ethRejectedShares, dcrRejectedShares},
                    new String[]{shares[1], shares[3]},
                    builder);
            addRig(
                    minerVersion,
                    ethHashRate,
                    dcrHashRate,
                    temps,
                    fans,
                    builder);
        });
    }

    /**
     * Generates a JSON RPC command.
     *
     * @return The command.
     */
    private String makeCommand() {
        return String.format(
                "{\"id\":%d,\"jsonrpc\":\"%s\",\"method\":\"%s\"%s}",
                0,
                "2.0",
                "miner_getstat1",
                this.apiPassword != null
                        ? "\"apiPassword\":\"" + this.apiPassword + "\""
                        : "");
    }

    /**
     * Queries the API.
     *
     * @return The response.
     */
    private Optional<Response> query() {
        Response response = null;

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
                        toResponse(
                                request.getResponse());
            } catch (IOException ioe) {
                LOG.warn("Exception occurred while querying", ioe);
            }
        }

        return Optional.ofNullable(response);
    }

    /**
     * Parses the response into a {@link Response}.
     *
     * @param response The response to parse.
     *
     * @return The {@link Response}.
     *
     * @throws IOException on failure to parse.
     */
    private Response toResponse(final String response)
            throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(
                DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
        return objectMapper.readValue(
                response,
                Response.class);
    }
}