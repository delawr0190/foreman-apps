package mn.foreman.nbminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.nbminer.json.Response;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * <h1>Overview</h1>
 *
 * A {@link Nbminer} represents a remote nbminer instance.
 *
 * <p>This class relies on the nbminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 * <li>http://{@link #apiIp}:{@link #apiPort}/api/v1/status</li>
 * </ul>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>GPU bus ID isn't exposed via the API.  Therefore, it can't be
 * reported.</p>
 */
public class Nbminer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Nbminer(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    protected void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Response response =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/api/v1/status",
                        new TypeReference<Response>() {
                        });

        // Add pool
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(
                                PoolUtils.sanitizeUrl(
                                        response.stratum.url))
                        .setPriority(0)
                        .setStatus(
                                true,
                                true)
                        .setCounts(
                                response.stratum.acceptedShares,
                                response.stratum.rejectedShares,
                                0)
                        .build());

        // Add rig
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(response.miner.hashrate);
        response.miner.devices
                .stream()
                .map(device ->
                        new Gpu.Builder()
                                .setName(device.name)
                                .setIndex(device.id)
                                .setBus(0)
                                .setTemp(device.temperature)
                                .setFans(
                                        new FanInfo.Builder()
                                                .setCount(1)
                                                .addSpeed(device.fanSpeed)
                                                .setSpeedUnits("%")
                                                .build())
                                .setFreqInfo(
                                        new FreqInfo.Builder()
                                                .setFreq(device.coreClock)
                                                .setMemFreq(0)
                                                .build())
                                .build())
                .forEach(rigBuilder::addGpu);
        statsBuilder.addRig(rigBuilder.build());
    }
}