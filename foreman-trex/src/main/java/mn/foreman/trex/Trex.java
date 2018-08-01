package mn.foreman.trex;

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
import mn.foreman.trex.json.Summary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Overview</h1>
 *
 * A {@link Trex} represents a remote t-rex instance.
 *
 * <p>This class relies on the trex-http-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>The t-rex API, at the time of this writing, was very new and lacked
 * detailed information outside of basic pool statistics and hash rate.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <pre>
 *     GET /summary
 * </pre>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>Model, temperature, clocks, and fans are not exposed via the API.
 * Therefore, they aren't reporting to Foreman.</p>
 *
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares are not directly reported.  They are most likely included in
 * the reported rejected shares.</p>
 */
public class Trex
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Trex.class);

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
    Trex(
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
        addSummary(statsBuilder);

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
     * Queries for and converts a summary response to a {@link Rig} and {@link
     * Pool}.
     *
     * @param builder The builder to update.
     *
     * @throws MinerException on failure to query the API.
     */
    private void addSummary(final MinerStats.Builder builder)
            throws MinerException {
        final Summary summary = query();
        builder.addPool(
                new Pool.Builder()
                        .setName(summary.activePool.url.split("//")[1])
                        .setStatus(
                                true,
                                summary.uptime > 0)
                        .setPriority(0)
                        .setCounts(
                                summary.acceptCount,
                                summary.rejectedCount,
                                0)
                        .build());

        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setName(summary.name)
                        .setHashRate(summary.hashRate);
        for (int i = 0; i < summary.gpuTotal; i++) {
            rigBuilder.addGpu(
                    new Gpu.Builder()
                            .setIndex(i)
                            .setBus(i)
                            .setName("GPU " + i)
                            .setFans(
                                    new FanInfo.Builder()
                                            .setCount(0)
                                            .setSpeedUnits("%")
                                            .build())
                            .setFreqInfo(
                                    new FreqInfo.Builder()
                                            .setFreq(0)
                                            .setMemFreq(0)
                                            .build())
                            .build());
        }
        builder.addRig(rigBuilder.build());
    }

    /**
     * Queries the REST interface.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    private Summary query()
            throws MinerException {
        Summary summary;

        final ApiRequest request =
                new ApiRequestImpl(
                        this.apiIp,
                        this.apiPort,
                        "/summary");

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
                summary =
                        objectMapper.readValue(
                                request.getResponse(),
                                Summary.class);
            } catch (final IOException ioe) {
                throw new MinerException(ioe);
            }
        } else {
            throw new MinerException("Failed to obtain a response");
        }

        return summary;
    }
}