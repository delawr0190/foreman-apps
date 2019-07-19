package mn.foreman.sgminer.response;

import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;

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

    /** The {@link CgMinerCommand#DEVS} strategy. */
    private final ResponseStrategy devsResponseStrategy;

    /** The {@link CgMinerCommand#POOLS} strategy. */
    private final ResponseStrategy poolResponseStrategy;

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
    }

    @Override
    public void processPools(
            final MinerStats.Builder builder,
            final CgMinerResponse response) {
        this.poolResponseStrategy.processResponse(
                builder,
                response);
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
}
