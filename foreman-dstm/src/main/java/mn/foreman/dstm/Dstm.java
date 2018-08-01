package mn.foreman.dstm;

import mn.foreman.dstm.json.Response;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Overview</h1>
 *
 * A {@link Dstm} represents a remote dstm instance.
 *
 * <p>This class relies on the dstm-api being enabled and configured to allow
 * the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries "getstat" via JSON RPC.</p>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>GPU frequency info isn't exposed via the API.  Therefore, it can't be
 * reported.</p>
 *
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares are not directly reported.  They are most likely included in
 * the reported rejected shares.</p>
 */
public class Dstm
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Dstm.class);

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
    Dstm(
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
                response.results,
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
     * Converts the provided {@link Response.Result} to a {@link Gpu} and adds
     * it to the provided builder.
     *
     * @param result     The result.
     * @param rigBuilder The builder.
     */
    private void addGpu(
            final Response.Result result,
            final Rig.Builder rigBuilder) {
        rigBuilder
                .addGpu(
                        new Gpu.Builder()
                                .setName(result.gpuName)
                                .setIndex(result.gpuPciDeviceId)
                                .setBus(result.gpuPciBusId)
                                .setTemp(result.temperature)
                                .setFreqInfo(
                                        // No freq info in API
                                        new FreqInfo.Builder()
                                                .setFreq(0)
                                                .setMemFreq(0)
                                                .build())
                                .setFans(
                                        new FanInfo.Builder()
                                                .setCount(1)
                                                .addSpeed(result.fanSpeed)
                                                .setSpeedUnits("%")
                                                .build())
                                .build());
    }

    /**
     * Adds the {@link Pool} in the {@link Response} to the provided builder.
     *
     * @param response     The response.
     * @param statsBuilder The builder.
     */
    private void addPool(
            final Response response,
            final MinerStats.Builder statsBuilder) {
        final long totalAccepted =
                response.results
                        .stream()
                        .mapToInt((result) -> result.acceptedShares)
                        .sum();
        final long totalRejected =
                response.results
                        .stream()
                        .mapToInt((result) -> result.rejectedShares)
                        .sum();
        statsBuilder
                .addPool(
                        new Pool.Builder()
                                .setName(response.server)
                                .setPriority(0)
                                .setStatus(
                                        true,
                                        response.connectionTime > 0)
                                .setCounts(
                                        totalAccepted,
                                        totalRejected,
                                        0)
                                .build());
    }

    /**
     * Converts the provided {@link Response.Result} to a {@link Rig} and adds
     * it to the provided builder.
     *
     * @param results      The results.
     * @param statsBuilder The builder to update.
     */
    private void addRig(
            final List<Response.Result> results,
            final MinerStats.Builder statsBuilder) {
        final double hashRate =
                results
                        .stream()
                        .mapToDouble((result) -> result.solutionRate)
                        .sum();
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setName("dstm")
                        .setHashRate(new BigDecimal(hashRate));
        results
                .forEach(
                        (result) ->
                                addGpu(result, rigBuilder));
        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Generates a JSON RPC command.
     *
     * @return The command.
     */
    private String makeCommand() {
        return String.format(
                "{\"id\":%d,\"method\":\"%s\"}\n",
                1,
                "getstat");
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
                final ObjectMapper objectMapper =
                        new ObjectMapper();
                response =
                        objectMapper.readValue(
                                request.getResponse(),
                                Response.class);
            } catch (IOException ioe) {
                LOG.warn("Exception occurred while querying", ioe);
                throw new MinerException(ioe);
            }
        } else {
            throw new MinerException("Failed to obtain a response");
        }

        return response;
    }
}
