package mn.foreman.dragonmint;

import mn.foreman.dragonmint.json.Summary;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.Flatten;
import mn.foreman.util.MrrUtils;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.*;

/**
 * <h1>Overview</h1>
 *
 * A {@link Dragonmint} represents a remote dragonmint instance.
 *
 * <p>This class relies on the dragonmint-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * application is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries:</p>
 *
 * <pre>
 *     POST http://{@link #apiIp}:{@link #apiPort}/api/summary
 * </pre>
 */
public class Dragonmint
        extends AbstractMiner {

    /** The API password. */
    private final String password;

    /** The stats whitelist. */
    private final List<String> statsWhitelist;

    /** The API username. */
    private final String username;

    /**
     * Constructor.
     *
     * @param apiIp          The API IP.
     * @param apiPort        The API port.
     * @param username       The username.
     * @param password       The password.
     * @param statsWhitelist The stats whitelist.
     */
    Dragonmint(
            final String apiIp,
            final int apiPort,
            final String username,
            final String password,
            final List<String> statsWhitelist) {
        super(
                apiIp,
                apiPort);
        this.username = username;
        this.password = password;
        this.statsWhitelist = new ArrayList<>(statsWhitelist);
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Map<String, Object> rawStats = new LinkedHashMap<>();
        final Summary summary =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/api/summary",
                        this.username,
                        this.password,
                        new TypeReference<Summary>() {
                        },
                        rawJson -> rawStats.putAll(
                                Flatten.flattenAndFilter(
                                        rawJson,
                                        this.statsWhitelist)));
        addPools(
                statsBuilder,
                summary.pools);
        addAsics(
                statsBuilder,
                summary.devs,
                summary.hardware,
                summary
                        .pools
                        .stream()
                        .map(pool -> MrrUtils.getRigId(pool.url, pool.user))
                        .filter(Objects::nonNull)
                        .findAny()
                        .orElse(""),
                rawStats);
    }

    @Override
    protected void addToEquals(
            final EqualsBuilder equalsBuilder,
            final AbstractMiner other) {
        final Dragonmint otherMiner = (Dragonmint) other;
        equalsBuilder
                .append(this.username, otherMiner.username)
                .append(this.password, otherMiner.password);
    }

    @Override
    protected void addToHashCode(
            final HashCodeBuilder hashCodeBuilder) {
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
     * Adds an ASIC from the provided devices and hardware.
     *
     * @param statsBuilder The stats builder.
     * @param devs         The devices.
     * @param hardware     The hardware information.
     * @param mrrRigId     The rig ID.
     * @param rawStats     The raw stats.
     */
    private static void addAsics(
            final MinerStats.Builder statsBuilder,
            final List<Summary.Dev> devs,
            final Summary.Hardware hardware,
            final String mrrRigId,
            final Map<String, Object> rawStats) {
        final Asic.Builder asicBuilder =
                new Asic.Builder();
        asicBuilder
                .setHashRate(toHashRate(devs))
                .setFanInfo(
                        new FanInfo.Builder()
                                .setCount(1)
                                .addSpeed(hardware.fanSpeed)
                                .setSpeedUnits("%")
                                .build());
        for (final Summary.Dev dev : devs) {
            asicBuilder.addTemp(dev.temperature);
        }
        // Context data
        asicBuilder
                .setMrrRigId(mrrRigId)
                .addRawStats(rawStats);
        statsBuilder.addAsic(asicBuilder.build());
    }

    /**
     * Adds the {@link Pool}.
     *
     * @param statsBuilder The builder to update.
     * @param pool         The {@link Pool}.
     */
    private static void addPool(
            final MinerStats.Builder statsBuilder,
            final Summary.Pool pool) {
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(pool.url))
                        .setPriority(pool.priority)
                        .setStatus(
                                !"Disabled".equals(pool.status),
                                "Alive".equals(pool.status))
                        .setCounts(
                                pool.accepted,
                                pool.rejected,
                                pool.stale)
                        .build());
    }

    /**
     * Adds the {@link Pool Pools}.
     *
     * @param statsBuilder The builder to update.
     * @param pools        The {@link Pool Pools}.
     */
    private static void addPools(
            final MinerStats.Builder statsBuilder,
            final List<Summary.Pool> pools) {
        pools.forEach(
                pool ->
                        addPool(
                                statsBuilder,
                                pool));
    }

    /**
     * Adds up the hash rates from the provided devices.
     *
     * @param devs The devices.
     *
     * @return The hash rate.
     */
    private static BigDecimal toHashRate(
            final List<Summary.Dev> devs) {
        return devs
                .stream()
                .map((dev) -> dev.hashRate)
                .map((value) ->
                        value.multiply(
                                new BigDecimal(1000 * 1000)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}