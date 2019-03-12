package mn.foreman.autominer;

import mn.foreman.autominer.json.Response;
import mn.foreman.claymore.ClaymoreType;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.Miner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <h1>Overview</h1>
 *
 * An {@link AutoMiner} represents a remote autominer instance.
 *
 * <p>This class relies on the autominer api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>The autominer API returns MRR group information in addition to the API
 * port that the currently running miner is using for API requests. This class
 * will first query autominer to determine what miner is running; it will then
 * query the miner directly to extract miner-specific metrics.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <ul>
 * <li>http://{@link #apiIp}:{@link #apiPort}/summary</li>
 * </ul>
 */
public class AutoMiner
        extends AbstractMiner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AutoMiner.class);

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    AutoMiner(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Response response =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/summary",
                        new TypeReference<Response>() {
                        });

        final Miner miner =
                toMiner(
                        response.gpuMiner,
                        this.apiIp,
                        response.gpuApiPort)
                        .orElseThrow(
                                () -> new MinerException(
                                        "Can't return stats for an unknown miner type"));
        MinerStats minerStats =
                miner.getStats();
        if (hasGpuStats(response)) {
            // AutoMiner returned GPU stats - use those in the stats being
            // built instead.  They're most likely more detailed
            minerStats =
                    mergeStats(
                            minerStats,
                            response.gpus);
        }

        statsBuilder
                .addPools(minerStats.getPools())
                .addAsics(minerStats.getAsics())
                .addRigs(
                        addAttributes(
                                response.gpuRigGroup,
                                response.startTime,
                                response.gpuAlgo,
                                minerStats.getRigs()));
    }

    /**
     * Adds attributes to every rig.
     *
     * @param mrrGroup  The MRR group.
     * @param startTime The start time.
     * @param gpuAlgo   The GPU algorithm.
     * @param rigs      The rigs.
     *
     * @return The new rigs with the MRR group set.
     */
    private static List<Rig> addAttributes(
            final int mrrGroup,
            final ZonedDateTime startTime,
            final String gpuAlgo,
            final List<Rig> rigs) {
        return rigs
                .stream()
                .map(rig ->
                        addAttributes(
                                mrrGroup,
                                startTime,
                                gpuAlgo,
                                rig))
                .collect(Collectors.toList());
    }

    /**
     * Adds attributes to the rig.
     *
     * @param mrrGroup  The MRR group.
     * @param startTime The start time.
     * @param gpuAlgo   The GPU algorithm.
     * @param rig       The rig.
     *
     * @return The new rig with the group.
     */
    private static Rig addAttributes(
            final int mrrGroup,
            final ZonedDateTime startTime,
            final String gpuAlgo,
            final Rig rig) {
        return new Rig.Builder()
                .setHashRate(rig.getHashRate())
                .addGpus(rig.getGpus())
                .addAttribute(
                        "mrr_group",
                        Integer.toString(mrrGroup))
                .addAttribute(
                        "start_time",
                        startTime.toString())
                .addAttribute(
                        "gpu_algo",
                        gpuAlgo)
                .build();
    }

    /**
     * Checks to see if the provided {@link Response} contains GPU stats. GPU
     * stats are considered present if any {@link Response.Gpu GPUs} exist with
     * a non-0 core clock.
     *
     * @param response The response.
     *
     * @return Whether or not stats are present.
     */
    private static boolean hasGpuStats(final Response response) {
        return response.gpus
                .stream()
                .anyMatch(gpu -> gpu.coreClock > 0);
    }

    /**
     * Merges two GPU stats together.
     *
     * @param autominerGpu The GPU stats returned from autominer.
     * @param minerGpu     The GPU stats returned from the miner API.
     *
     * @return The merged stats.
     */
    private static Gpu mergeGpu(
            final Response.Gpu autominerGpu,
            final Gpu minerGpu) {
        return new Gpu.Builder()
                .setName(minerGpu.getName())
                .setIndex(autominerGpu.id)
                .setBus(minerGpu.getBus())
                .setTemp(autominerGpu.temperature)
                .setFans(
                        new FanInfo.Builder()
                                .setCount(1)
                                .addSpeed(autominerGpu.fanSpeed)
                                .setSpeedUnits("%")
                                .build())
                .setFreqInfo(
                        new FreqInfo.Builder()
                                .setFreq(autominerGpu.coreClock)
                                .setMemFreq(autominerGpu.memoryClock)
                                .build())
                .build();
    }

    /**
     * Merges the rigs together.
     *
     * @param sourceRig     The source rig (miner returned).
     * @param autominerGpus The autominer GPUs.
     *
     * @return The new {@link Rig}.
     */
    private static Rig mergeRig(
            final Rig sourceRig,
            final List<Response.Gpu> autominerGpus) {
        final Rig newRig;
        final List<Gpu> minerGpus = sourceRig.getGpus();
        if (autominerGpus.size() == minerGpus.size()) {
            final Rig.Builder rigBuilder =
                    new Rig.Builder()
                            .setHashRate(sourceRig.getHashRate());
            for (int i = 0; i < autominerGpus.size(); i++) {
                rigBuilder.addGpu(
                        mergeGpu(
                                autominerGpus.get(i),
                                minerGpus.get(i)));
            }
            newRig = rigBuilder.build();
        } else {
            // Different number of GPUs returned from miner and autominer -
            // don't merge
            LOG.debug("AutoMiner returned {} GPUs, but miner returned {}",
                    autominerGpus.size(),
                    minerGpus.size());
            newRig = sourceRig;
        }
        return newRig;
    }

    /**
     * Makes a new {@link MinerStats} that's enriched with the GPU stats
     * returned from {@link AutoMiner} if they're present.
     *
     * @param sourceStats The source {@link MinerStats}.
     * @param gpus        The GPUs.
     *
     * @return The new stats.
     */
    private static MinerStats mergeStats(
            final MinerStats sourceStats,
            final List<Response.Gpu> gpus) {
        final MinerStats.Builder newStats =
                new MinerStats.Builder()
                        .setApiIp(sourceStats.getApiIp())
                        .setApiPort(sourceStats.getApiPort())
                        .addPools(sourceStats.getPools())
                        .addAsics(sourceStats.getAsics());
        sourceStats.getRigs().forEach(rig ->
                newStats.addRig(
                        mergeRig(
                                rig,
                                gpus)));
        return newStats.build();
    }

    /**
     * Creates a {@link Miner} to query from the provided {@link MinerType}.
     *
     * @param minerType The type.
     * @param apiIp     The API ip.
     * @param apiPort   The API port.
     *
     * @return The {@link Miner}.
     */
    private static Optional<Miner> toMiner(
            final MinerType minerType,
            final String apiIp,
            final int apiPort) {
        return minerType.getFactory().map(factory -> {
            final Map<String, String> attributes = new HashMap<>();
            attributes.put("apiIp", apiIp);
            attributes.put("apiPort", Integer.toString(apiPort));
            if (minerType == MinerType.PHOENIX) {
                attributes.put("type", ClaymoreType.ETH.name().toLowerCase());
            }
            return factory.create(attributes);
        });
    }
}