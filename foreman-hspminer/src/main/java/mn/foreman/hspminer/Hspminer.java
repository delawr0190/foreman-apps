package mn.foreman.hspminer;

import mn.foreman.hspminer.json.Stats;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.List;

/**
 * <h1>Overview</h1>
 *
 * A {@link Hspminer} represents a remote hspminer instance.
 *
 * <p>This class relies on the hspminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <pre>
 *     GET /api/v1
 * </pre>
 */
public class Hspminer
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    Hspminer(
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
                        "/api/v1",
                        new TypeReference<Stats>() {
                        });
        final List<Stats.Miner> miners = stats.miners;
        Validate.notEmpty(
                miners,
                "miners cannot be empty");
        final Stats.Miner miner = miners.get(0);
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(
                                PoolUtils.sanitizeUrl(
                                        miner.pool))
                        .setStatus(
                                true,
                                true)
                        .setPriority(0)
                        .setCounts(
                                miner.accepted,
                                miner.rejected,
                                0)
                        .build());

        final BigDecimal multiplier = new BigDecimal((double) 1 / 60);

        final Rig.Builder rigBuilder =
                new Rig.Builder()
                        .setHashRate(miner.devices
                                .stream()
                                .map(device -> device.hashrate.multiply(multiplier))
                                .reduce(BigDecimal.ZERO, BigDecimal::add));
        miner.devices.forEach(
                device ->
                        addGpu(
                                rigBuilder,
                                device));
        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Adds a GPU from the provided {@link Stats.Device device}.
     *
     * @param rigBuilder The builder to update.
     * @param device     The {@link Stats.Device}.
     */
    private static void addGpu(
            final Rig.Builder rigBuilder,
            final Stats.Device device) {
        rigBuilder.addGpu(
                new Gpu.Builder()
                        .setIndex(device.id)
                        .setBus(device.pci.split(":")[0])
                        .setName(device.name)
                        .setTemp(device.temp)
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(1)
                                        .addSpeed(device.fan)
                                        .setSpeedUnits("%")
                                        .build())
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(device.core)
                                        .setMemFreq(device.mem)
                                        .build())
                        .build());
    }
}