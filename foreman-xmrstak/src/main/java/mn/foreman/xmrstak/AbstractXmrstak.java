package mn.foreman.xmrstak;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.util.PoolUtils;
import mn.foreman.xmrstak.json.Response;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * An {@link AbstractXmrstak} provides an abstract implementation of an xmrstak
 * miner.
 */
public abstract class AbstractXmrstak
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    AbstractXmrstak(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    /**
     * Adds the devices from the provided {@link Response}.
     *
     * @param statsBuilder The builder.
     * @param response     The response.
     */
    protected abstract void addDevices(
            MinerStats.Builder statsBuilder,
            Response response);

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Response response =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/api.json",
                        new TypeReference<Response>() {
                        });
        addPool(
                statsBuilder,
                response);
        addDevices(
                statsBuilder,
                response);
    }

    /**
     * Adds a {@link Pool} from the {@link Response}.
     *
     * @param statsBuilder The {@link MinerStats.Builder builder} to update.
     * @param response     The {@link Response}.
     */
    private void addPool(
            final MinerStats.Builder statsBuilder,
            final Response response) {
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
}
