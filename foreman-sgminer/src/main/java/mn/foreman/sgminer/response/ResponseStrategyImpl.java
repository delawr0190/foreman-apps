package mn.foreman.sgminer.response;

import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.Rig;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A {@link ResponseStrategyImpl} provides a {@link SgminerResponseStrategy}
 * that collects the hardware errors from a {@link CgMinerCommand#DEVS DEVS}
 * commands and uses the hardware errors in a TRM response to reduce the pool
 * rejected shares.
 */
public class ResponseStrategyImpl
        implements SgminerResponseStrategy {

    /** An unknown algorithm. */
    private static final String UNKNOWN_ALGORITHM = "";

    /** The {@link CgMinerCommand#DEVS} strategy. */
    private final ResponseStrategy devsResponseStrategy;

    /** The {@link CgMinerCommand#POOLS} strategy. */
    private final ResponseStrategy poolResponseStrategy;

    /** The algorithm. */
    private String algorithm = UNKNOWN_ALGORITHM;

    /** The hardware errors. */
    private int hardwareErrors = 0;

    /**
     * Constructor.
     *
     * @param poolResponseStrategy The {@link CgMinerCommand#POOLS} strategy.
     * @param devsResponseStrategy The {@link CgMinerCommand#DEVS} strategy.
     */
    public ResponseStrategyImpl(
            final ResponseStrategy poolResponseStrategy,
            final ResponseStrategy devsResponseStrategy) {
        this.poolResponseStrategy = poolResponseStrategy;
        this.devsResponseStrategy = devsResponseStrategy;
    }

    @Override
    public void processDevs(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        if (isTrm(response)) {
            this.hardwareErrors =
                    response.getValues()
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().equals("DEVS"))
                            .map(Map.Entry::getValue)
                            .flatMap(List::stream)
                            .mapToInt(map -> Integer.parseInt(
                                    map.getOrDefault("Hardware Errors", "0")))
                            .sum();
        }
        this.devsResponseStrategy.processResponse(
                builder,
                response);
        enrichStats(builder);
    }

    @Override
    public void processPools(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        if (isTrm(response)) {
            this.algorithm =
                    response.getValues()
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().equals("POOLS"))
                            .map(Map.Entry::getValue)
                            .flatMap(List::stream)
                            .filter(map -> map.containsKey("Algorithm"))
                            .map(map -> map.get("Algorithm"))
                            .findFirst()
                            .orElse(UNKNOWN_ALGORITHM);
        }
        this.poolResponseStrategy.processResponse(
                builder,
                response);
        enrichStats(builder);
    }

    /**
     * Checks to see if the {@link CgMinerResponse response} is from
     * TeamRedMiner.
     *
     * @param response The response.
     *
     * @return Whether or not TRM.
     */
    private static boolean isTrm(final CgMinerResponse response) {
        return response.getStatus()
                .stream()
                .anyMatch(map ->
                        map
                                .getOrDefault("Description", "N/A")
                                .contains("TeamRedMiner"));
    }

    /**
     * Enriches the stats with values that have been found.
     *
     * @param builder The stats to enrich.
     */
    private void enrichStats(final MinerStats.Builder builder) {
        builder.setRigs(
                builder.getRigs()
                        .stream()
                        .map(this::toRig)
                        .collect(Collectors.toList()));
        builder.setPools(
                builder.getPools()
                        .stream()
                        .map(pool ->
                                new Pool.Builder()
                                        .setName(pool.getName())
                                        .setPriority(pool.getPriority())
                                        .setCounts(
                                                pool.getAccepted(),
                                                pool.getRejected() + this.hardwareErrors,
                                                pool.getStale())
                                        .setStatus(
                                                pool.getEnabled(),
                                                pool.getStatus())
                                        .build())
                        .collect(Collectors.toList()));
    }

    /**
     * Enriches the {@link Rig} with the algorithm, if one was found.
     *
     * @param rig The {@link Rig}.
     *
     * @return The enriched {@link Rig}.
     */
    private Rig toRig(final Rig rig) {
        final Rig.Builder builder =
                new Rig.Builder()
                        .setHashRate(rig.getHashRate())
                        .addGpus(rig.getGpus())
                        .addAttributes(rig.getAttributes());
        if (this.algorithm != null &&
                !UNKNOWN_ALGORITHM.equals(this.algorithm)) {
            builder.addAttribute(
                    "gpu_algo",
                    this.algorithm);
        }
        return builder.build();
    }
}