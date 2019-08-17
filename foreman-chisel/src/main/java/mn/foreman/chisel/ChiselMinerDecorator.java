package mn.foreman.chisel;

import mn.foreman.chisel.json.GpuInfo;
import mn.foreman.io.Query;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link ChiselMinerDecorator} provides a {@link Miner} decorator that will
 * add GPU-specific metrics to a {@link MinerStats} that was returned from a
 * {@link Miner}.
 *
 * <p>This class provides a mechanism for adding metrics to {@link MinerStats}
 * that aren't present on all APIs.</p>
 */
public class ChiselMinerDecorator
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(ChiselMinerDecorator.class);

    /** The IP where chisel is listening. */
    private final String chiselIp;

    /** The port where chisel is listening. */
    private final int chiselPort;

    /** The real {@link Miner}. */
    private final Miner realMiner;

    /**
     * Constructor.
     *
     * @param chiselIp   The chisel IP address.
     * @param chiselPort The chisel port.
     * @param realMiner  The real {@link Miner}.
     */
    public ChiselMinerDecorator(
            final String chiselIp,
            final int chiselPort,
            final Miner realMiner) {
        Validate.notNull(
                chiselIp,
                "chiselIp cannot be null");
        Validate.notEmpty(
                chiselIp,
                "chiselIp cannot be empty");
        Validate.isTrue(
                chiselPort > 0,
                "chiselPort must be > 0");
        Validate.notNull(
                realMiner,
                "realMiner cannot be null");
        this.realMiner = realMiner;
        this.chiselIp = chiselIp;
        this.chiselPort = chiselPort;
    }

    @Override
    public boolean equals(final Object other) {
        boolean equals = false;
        if (this == other) {
            equals = true;
        } else if ((other != null) && (getClass() == other.getClass())) {
            equals = this.realMiner.equals(other);
        }
        return equals;
    }

    @Override
    public MinerID getMinerID() {
        return this.realMiner.getMinerID();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Overridden to query the {@link #realMiner} and enrich the response
     * with information obtained from chisel.</p>
     */
    @Override
    public MinerStats getStats()
            throws MinerException {
        MinerStats minerStats =
                this.realMiner.getStats();
        try {
            final List<GpuInfo> gpus =
                    Query.restQuery(
                            this.chiselIp,
                            this.chiselPort,
                            "/stats/gpus",
                            new TypeReference<List<GpuInfo>>() {
                            });
            minerStats =
                    enrichStats(
                            minerStats,
                            gpus);
        } catch (final MinerException me) {
            // Don't let a failed chisel response prevent basic metrics from
            // going to the dashboard
            LOG.info("Exception occurred while querying chisel - ignoring",
                    me);
        }
        return minerStats;
    }

    @Override
    public int hashCode() {
        return this.realMiner.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [ " +
                this.realMiner.toString() + " ]";
    }

    /**
     * Adds a {@link Gpu} from the {@link GpuInfo}.
     *
     * @param gpuInfo    The info.
     * @param rigBuilder The builder.
     */
    private static void addGpu(
            final GpuInfo gpuInfo,
            final Rig.Builder rigBuilder) {
        rigBuilder.addGpu(
                new Gpu.Builder()
                        .setIndex(gpuInfo.id)
                        .setBus(gpuInfo.busId)
                        .setName(gpuInfo.name)
                        .setTemp(gpuInfo.temp)
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(1)
                                        .addSpeed(gpuInfo.fan)
                                        .setSpeedUnits("%")
                                        .build())
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(gpuInfo.clocks.core)
                                        .setMemFreq(gpuInfo.clocks.memory)
                                        .build())
                        .build());
    }

    /**
     * Enriches the {@link Rig} with metrics provided by chisel.
     *
     * @param rig  The {@link Rig}.
     * @param gpus The {@link GpuInfo GPUs}.
     *
     * @return The enriched {@link Rig}.
     */
    private static Rig enrichRig(
            final Rig rig,
            final List<GpuInfo> gpus) {
        Rig newRig = rig;

        // Only enrich rigs that have the same number of GPUs in both the
        // actual rig and the chisel response
        final List<Gpu> apiGpus = rig.getGpus();
        if (gpus.size() == apiGpus.size()) {
            final Rig.Builder rigBuilder =
                    new Rig.Builder()
                            .setHashRate(rig.getHashRate());
            gpus.forEach(gpu ->
                    addGpu(
                            gpu,
                            rigBuilder));
            newRig = rigBuilder.build();
        } else {
            LOG.warn("Chisel returned more GPUs than the miner API - not " +
                            "enriching (chisel: {}, actual: {})",
                    gpus.size(),
                    apiGpus.size());
        }

        return newRig;
    }

    /**
     * Transforms the stats in the provided {@link MinerStats} to a new {@link
     * MinerStats} that contains additional metrics extracted from chisel.
     *
     * @param minerStats The stats to enrich.
     * @param gpus       The {@link GpuInfo GPUs}.
     *
     * @return The new stats.
     */
    private static MinerStats enrichStats(
            final MinerStats minerStats,
            final List<GpuInfo> gpus) {
        final MinerStats.Builder newStatsBuilder =
                new MinerStats.Builder()
                        .setApiIp(minerStats.getApiIp())
                        .setApiPort(minerStats.getApiPort());

        // Add the pools
        minerStats
                .getPools()
                .forEach(newStatsBuilder::addPool);

        // Enrich the rigs
        minerStats
                .getRigs()
                .stream()
                .map(rig ->
                        enrichRig(
                                rig,
                                gpus))
                .collect(Collectors.toList())
                .forEach(newStatsBuilder::addRig);

        return newStatsBuilder.build();
    }
}