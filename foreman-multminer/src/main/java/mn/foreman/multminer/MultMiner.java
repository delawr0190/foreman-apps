package mn.foreman.multminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.multminer.response.Stats;
import mn.foreman.util.MrrUtils;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Iterables;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * A {@link MultMiner} provides a {@link AbstractMiner} implementation that can
 * query a remote multminer FPGA and return stats.
 */
public class MultMiner
        extends AbstractMiner {

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    MultMiner(
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
        final Stats stats =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/gst.csp?a=a",
                        new TypeReference<Stats>() {
                        });
        addPools(
                statsBuilder,
                stats.ms);
        addAsics(
                statsBuilder,
                stats,
                stats
                        .ms
                        .stream()
                        .filter(strings -> strings.size() >= 7)
                        .map(strings ->
                                MrrUtils.getRigId(
                                        strings.get(6),
                                        strings.get(2)))
                        .filter(Objects::nonNull)
                        .findAny()
                        .orElse(""));
    }

    /**
     * Adds an ASIC from the provided devices and hardware.
     *
     * @param statsBuilder The stats builder.
     * @param stats        The stats.
     * @param mrrRigId     The rig ID.
     */
    private static void addAsics(
            final MinerStats.Builder statsBuilder,
            final Stats stats,
            final String mrrRigId) {
        final Asic.Builder asicBuilder =
                new Asic.Builder();
        asicBuilder
                .setHashRate(toHashRate(stats))
                .setFanInfo(
                        new FanInfo.Builder()
                                .setCount(0)
                                .setSpeedUnits("%")
                                .build())
                .setPowerState(stats.mt.split(" ")[0]);
        stats
                .boards
                .stream()
                .filter(board -> board.chipNumber > 0)
                .map(board -> board.chips)
                .flatMap(List::stream)
                .map(chip -> Iterables.get(chip, 1, "0"))
                .forEach(asicBuilder::addTemp);
        asicBuilder.setMrrRigId(mrrRigId);
        statsBuilder.addAsic(asicBuilder.build());
    }

    /**
     * Adds the {@link Pool}.
     *
     * @param statsBuilder The builder to update.
     * @param pool         The {@link Pool}.
     *
     * @throws MinerException if the pool array was too short.
     */
    private static void addPool(
            final MinerStats.Builder statsBuilder,
            final List<String> pool)
            throws MinerException {
        if (pool.size() >= 7) {
            final String poolLine = pool.get(6);
            final String poolArgument = poolLine.split(" ")[0];
            final String priority =
                    poolArgument.length() == 2
                            ? "0"
                            : poolArgument.split("o")[1];

            statsBuilder.addPool(
                    new Pool.Builder()
                            .setName(PoolUtils.sanitizeUrl(poolLine))
                            .setPriority(priority)
                            .setStatus(
                                    true,
                                    isAlive(pool.get(0)))
                            .setCounts(
                                    pool.get(3),
                                    pool.get(4),
                                    "0")
                            .build());
        } else {
            throw new MinerException("Pool array was shorter than expected");
        }
    }

    /**
     * Adds the {@link Pool Pools}.
     *
     * @param statsBuilder The builder to update.
     * @param ms           The pools.
     */
    private static void addPools(
            final MinerStats.Builder statsBuilder,
            final List<List<String>> ms)
            throws MinerException {
        for (final List<String> pool : ms) {
            addPool(
                    statsBuilder,
                    pool);
        }
    }

    /**
     * Returns whether or not the pool is alive.
     *
     * @param poolStatus The pool status.
     *
     * @return Whether or not the pool is alive.
     */
    private static boolean isAlive(final String poolStatus) {
        return "working".equalsIgnoreCase(poolStatus) ||
                "connected".equalsIgnoreCase(poolStatus);
    }

    /**
     * Adds up the hash rates from the provided devices.
     *
     * @param stats The stats.
     *
     * @return The hash rate.
     */
    private static BigDecimal toHashRate(
            final Stats stats) {
        return stats
                .boards
                .stream()
                .filter(board -> board.chipNumber > 0)
                .map(board -> {
                    final BigDecimal boardRate =
                            board
                                    .chips
                                    .stream()
                                    .map(Iterables::getLast)
                                    .filter(chip -> chip.contains("|"))
                                    .map(chip -> chip.split(" ")[0])
                                    .map(rate -> rate.replace("GH/s", ""))
                                    .map(BigDecimal::new)
                                    .map(rate ->
                                            rate.multiply(
                                                    BigDecimal.valueOf(
                                                            Math.pow(
                                                                    1000,
                                                                    3))))
                                    .reduce(
                                            BigDecimal.ZERO,
                                            BigDecimal::add);
                    return boardRate.divide(
                            BigDecimal.valueOf(board.chipNumber),
                            RoundingMode.DOWN);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
