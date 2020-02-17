package mn.foreman.trex;

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

import org.apache.commons.lang3.StringUtils;

/**
 * <h1>Overview</h1>
 *
 * A {@link TrexJson} represents a remote t-rex instance.
 *
 * <p>This class relies on the trex api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>The t-rex API, at the time of this writing, was very new and lacked
 * detailed information outside of basic pool statistics and hash rate.</p>
 *
 * <p>This class currently queries either the HTTP API endpoint or the
 * telnet endpoint depending on the {@link ApiStrategy} that's provided.</p>
 *
 * <h1>Limitations</h1>
 *
 * <h2>GPUs</h2>
 *
 * <p>Clocks are not exposed via the API. Therefore, they aren't reported to
 * Foreman.</p>
 *
 * <h2>Pools</h2>
 *
 * <h3>Stale shares</h3>
 *
 * <p>Stale shares are not directly reported.  They are most likely included in
 * the reported rejected shares.</p>
 */
public class TrexJson
        extends AbstractMiner {

    /** The strategy to use for obtaining a {@link Summary} from trex. */
    private final ApiStrategy apiStrategy;

    /**
     * Constructor.
     *
     * @param apiIp       The API IP.
     * @param apiPort     The API port.
     * @param apiStrategy The API strategy.
     */
    TrexJson(
            final String apiIp,
            final int apiPort,
            final ApiStrategy apiStrategy) {
        super(
                apiIp,
                apiPort);
        this.apiStrategy = apiStrategy;
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        try {
            final Summary summary =
                    this.apiStrategy.getSummary(
                            this.apiIp,
                            this.apiPort);
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
                            .setHashRate(summary.hashRate);
            summary.gpus.forEach(
                    (gpu) ->
                            addGpu(
                                    rigBuilder,
                                    gpu));
            statsBuilder.addRig(rigBuilder.build());
        } catch (final Exception e) {
            throw new MinerException(
                    "Possibly not a trex or miner is down",
                    e);
        }
    }

    /**
     * Adds a GPU from the provided {@link Summary.Gpu GPU}.
     *
     * @param rigBuilder The builder to update.
     * @param gpu        The {@link Summary.Gpu}.
     */
    private static void addGpu(
            final Rig.Builder rigBuilder,
            final Summary.Gpu gpu) {
        final Gpu.Builder gpuBuilder =
                new Gpu.Builder()
                        .setIndex(gpu.deviceId)
                        .setBus(0)
                        .setName(
                                ((gpu.name != null) && (!StringUtils.isBlank(gpu.name)))
                                        ? gpu.name
                                        : "GPU " + gpu.deviceId)
                        .setTemp(gpu.temperature)
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(0)
                                        .setMemFreq(0)
                                        .build());
        if (gpu.fanSpeed > 0) {
            gpuBuilder.setFans(
                    new FanInfo.Builder()
                            .setCount(1)
                            .addSpeed(gpu.fanSpeed)
                            .setSpeedUnits("%")
                            .build());
        } else {
            // No fans in this version of the API
            gpuBuilder.setFans(
                    new FanInfo.Builder()
                            .setCount(0)
                            .setSpeedUnits("%")
                            .build());
        }
        rigBuilder.addGpu(gpuBuilder.build());
    }
}