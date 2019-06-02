package mn.foreman.swarm;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.swarm.response.Stats;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.List;

/**
 * A {@link Swarm} represents a remote swarm instance.
 *
 * <p>This class relies on the swarm tcp api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * applications is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries for:</p>
 *
 * <ul>
 * <li>stats</li>
 * </ul>
 */
public class Swarm
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The api IP.
     * @param apiPort The api port.
     */
    Swarm(
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
                Query.jsonQuery(
                        this.apiIp,
                        this.apiPort,
                        "stats",
                        new TypeReference<Stats>() {
                        });

        // Add pool
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(stats.stratum))
                        .setPriority(0)
                        .setStatus(true, true)
                        .setCounts(
                                stats.accepted,
                                stats.rejected,
                                0)
                        .build());

        // Add rig
        statsBuilder.addRig(toRig(stats));
    }

    /**
     * Converts the provided {@link Stats} to a {@link Rig}.
     *
     * @param stats The stats.
     *
     * @return The new {@link Rig}.
     *
     * @throws MinerException on failure to create a {@link Rig}.
     */
    private static Rig toRig(final Stats stats) throws MinerException {
        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(stats.hsu.toHs(stats.gpuTotal))
                        .addAttribute(
                                "uptime",
                                Integer.toString(stats.uptime))
                        .addAttribute(
                                "algo",
                                stats.algo);

        final List<BigDecimal> gpus = stats.gpus;
        final List<Integer> fans = stats.fans;
        final List<Integer> temps = stats.temps;

        if ((gpus.size() == fans.size()) &&
                (gpus.size() == temps.size())) {
            for (int i = 0; i < stats.gpus.size(); i++) {
                rigBuilder.addGpu(
                        new Gpu.Builder()
                                .setName("GPU " + i)
                                .setIndex(i)
                                .setBus(0)
                                .setTemp(temps.get(i))
                                .setFreqInfo(
                                        new FreqInfo.Builder()
                                                .setFreq(0)
                                                .setMemFreq(0)
                                                .build())
                                .setFans(
                                        new FanInfo.Builder()
                                                .setCount(1)
                                                .addSpeed(fans.get(i))
                                                .setSpeedUnits("%")
                                                .build())
                                .build());
            }
        } else {
            throw new MinerException(
                    "Gpu, temps, and fans aren't the same size");
        }

        return rigBuilder.build();
    }
}