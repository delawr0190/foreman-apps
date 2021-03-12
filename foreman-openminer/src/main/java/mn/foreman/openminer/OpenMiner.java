package mn.foreman.openminer;

import mn.foreman.model.AbstractMiner;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.openminer.response.Agg;
import mn.foreman.util.Flatten;
import mn.foreman.util.MrrUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** Queries for stats from a miner running OpenMiner. */
public class OpenMiner
        extends AbstractMiner {

    /** The password. */
    private final String password;

    /** The stats whitelist. */
    private final List<String> statsWhitelist;

    /** The username. */
    private final String username;

    /**
     * Constructor.
     *
     * @param apiIp          The API ip.
     * @param apiPort        The API port.
     * @param username       The username.
     * @param password       The password.
     * @param statsWhitelist The stats whitelist.
     * @param macStrategy    The mac strategy.
     */
    OpenMiner(
            final String apiIp,
            final int apiPort,
            final String username,
            final String password,
            final List<String> statsWhitelist,
            final MacStrategy macStrategy) {
        super(
                apiIp,
                apiPort,
                macStrategy);
        this.username = username;
        this.password = password;
        this.statsWhitelist = statsWhitelist;
    }

    @Override
    protected void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Map<String, Object> rawStats = new LinkedHashMap<>();
        final Agg stats = OpenMinerUtils.getStats(
                this.apiIp,
                this.apiPort,
                this.username,
                this.password,
                rawJson -> rawStats.putAll(
                        Flatten.flattenAndFilter(
                                rawJson,
                                this.statsWhitelist)));
        addAgg(
                stats,
                rawStats,
                statsBuilder);
    }

    @Override
    protected void addToEquals(
            final EqualsBuilder equalsBuilder,
            final AbstractMiner other) {
        final OpenMiner otherMiner = (OpenMiner) other;
        equalsBuilder
                .append(this.username, otherMiner.username)
                .append(this.password, otherMiner.password);
    }

    @Override
    protected void addToHashCode(final HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder
                .append(this.username)
                .append(this.password);
    }

    @Override
    protected String addToString() {
        return String.format(
                ", username=%s, password=%s",
                this.username,
                this.password);
    }

    /**
     * Extracts the number of active boards from the stats.
     *
     * @param agg The stats.
     *
     * @return The active boards.
     */
    private static long toBoards(final Agg agg) {
        return agg
                .slots
                .values()
                .stream()
                .map(slot -> slot.ghs)
                .map(BigDecimal::new)
                .filter(hashRate -> hashRate.compareTo(BigDecimal.ZERO) > 0)
                .count();
    }

    /**
     * Extracts the fans from the stats.
     *
     * @param agg The stats.
     *
     * @return The fans.
     */
    private static FanInfo toFanInfo(final Agg agg) {
        return new FanInfo.Builder()
                .setCount(agg.fans.size())
                .addIntSpeeds(agg.fans)
                .setSpeedUnits("RPM")
                .build();
    }

    /**
     * Extracts the hash rate from the stats.
     *
     * @param agg The stats.
     *
     * @return The hash rate.
     */
    private static BigDecimal toHashRate(final Agg agg) {
        return agg
                .slots
                .values()
                .stream()
                .map(slot -> slot.ghs)
                .map(BigDecimal::new)
                .map(hashRate -> hashRate.multiply(BigDecimal.valueOf(Math.pow(1000, 3))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Extracts the temps from the stats.
     *
     * @param agg The stats.
     *
     * @return The temps.
     */
    private static List<Integer> toTemps(final Agg agg) {
        final List<Integer> temps = new LinkedList<>();
        for (final Agg.Slot slot : agg.slots.values()) {
            temps.add(slot.temp0);
            temps.add(slot.temp1);
        }
        return temps;
    }

    /**
     * Adds the stats to the builder.
     *
     * @param agg          The stats.
     * @param rawStats     The raw returned stats.
     * @param statsBuilder The builder.
     */
    private void addAgg(
            final Agg agg,
            final Map<String, Object> rawStats,
            final MinerStats.Builder statsBuilder) {
        final Agg.Pool.Shares shares = agg.pool.intervals.get("0");
        statsBuilder
                .addPool(
                        new Pool.Builder()
                                .setName(agg.pool.host + ":" + agg.pool.port)
                                .setWorker(agg.pool.username)
                                .setPriority(0)
                                .setStatus(true, true)
                                .setCounts(
                                        shares.accepted,
                                        shares.rejected,
                                        shares.stale)
                                .build())
                .addAsic(
                        new Asic.Builder()
                                .setHashRate(toHashRate(agg))
                                .setBoards(toBoards(agg))
                                .setFanInfo(toFanInfo(agg))
                                .addIntTemps(toTemps(agg))
                                .setPowerMode(agg.power.state.contains("FULL")
                                        ? Asic.PowerMode.NORMAL
                                        : Asic.PowerMode.LOW)
                                .addRawStats(rawStats)
                                .setMrrRigId(
                                        MrrUtils.getRigId(
                                                agg.pool.host,
                                                agg.pool.username))
                                .build());
    }
}
