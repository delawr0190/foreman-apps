package mn.foreman.optiminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.optiminer.json.Stats;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * <h1>Overview</h1>
 *
 * A {@link Optiminer} represents a remote optiminer instance.
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
public class Optiminer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Optiminer(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Stats stats =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/",
                        new TypeReference<Stats>() {
                        });
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(
                                PoolUtils.sanitizeUrl(
                                        String.format(
                                                "%s:%d",
                                                stats.stratum.host,
                                                stats.stratum.port)))
                        .setStatus(
                                true,
                                stats.stratum.connected)
                        .setPriority(0)
                        .setCounts(
                                stats.share.accepted,
                                stats.share.rejected,
                                0)
                        .build());

        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(
                                stats.solutionRates.getOrDefault(
                                        "Total",
                                        new HashMap<>())
                                        .getOrDefault(
                                                "60s",
                                                BigDecimal.ZERO));
        for (int i = 0; i < 16; i++) {
            final String key = "GPU" + i;
            if (stats.solutionRates.containsKey(key)) {
                rigBuilder.addGpu(
                        new Gpu.Builder()
                                .setName(key)
                                .setIndex(i)
                                .setBus(0)
                                .setTemp(0)
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
        }
        statsBuilder.addRig(rigBuilder.build());
    }
}