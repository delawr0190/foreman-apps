package mn.foreman.trex;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.trex.json.Summary;
import mn.foreman.util.PoolUtils;

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
        extends AbstractMiner {

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
        super(
                name,
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Summary summary =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/summary",
                        Summary.class);
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(
                                PoolUtils.sanitizeUrl(
                                        summary.activePool.url))
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
                        .setName("trex_" + summary.name)
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
        statsBuilder.addRig(rigBuilder.build());
    }
}