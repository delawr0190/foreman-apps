package mn.foreman.obelisk;

import mn.foreman.io.HttpRequestBuilder;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.obelisk.json.Dashboard;
import mn.foreman.obelisk.json.Info;
import mn.foreman.util.Flatten;
import mn.foreman.util.MrrUtils;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Overview</h1>
 *
 * A {@link Obelisk} represents a remote obelisk instance.
 *
 * <p>This class will query the <pre>/api/info</pre> and then interpret
 * the metrics according the the miner generation that's discovered.</p>
 */
public class Obelisk
        extends AbstractMiner {

    /** The mapper. */
    private final ObjectMapper objectMapper;

    /** The API password. */
    private final String password;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /** The stats whitelist. */
    private final List<String> statsWhitelist;

    /** The API username. */
    private final String username;

    /**
     * Constructor.
     *
     * @param apiIp              The API IP.
     * @param apiPort            The API port.
     * @param username           The username.
     * @param password           The password.
     * @param statsWhitelist     The stats whitelist.
     * @param macStrategy        The MAC strategy.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     * @param objectMapper       Json mapper.
     */
    Obelisk(
            final String apiIp,
            final int apiPort,
            final String username,
            final String password,
            final List<String> statsWhitelist,
            final MacStrategy macStrategy,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final ObjectMapper objectMapper) {
        super(
                apiIp,
                apiPort,
                macStrategy);
        this.username = username;
        this.password = password;
        this.statsWhitelist = new ArrayList<>(statsWhitelist);
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Map<String, Object> rawStats = new LinkedHashMap<>();
        final Info info =
                new HttpRequestBuilder<Info>()
                        .ip(this.apiIp)
                        .port(this.apiPort)
                        .uri(
                                "/api/info")
                        .socketTimeout(
                                this.socketTimeout,
                                this.socketTimeoutUnits)
                        .rawCallback((integer, s) ->
                                handleResponse(
                                        s,
                                        rawStats,
                                        this.statsWhitelist))
                        .responseTransformer((integer, s) ->
                                this.objectMapper.readValue(
                                        s,
                                        Info.class))
                        .get()
                        .orElseThrow(() -> new MinerException("Failed to obtain info"));
        final Optional<ObeliskType> type =
                ObeliskType.forType(
                        info.model);
        if (type.isPresent()) {
            final ObeliskType obeliskType =
                    type.get();
            try {
                addStatsForGen(
                        obeliskType.getGen(),
                        statsBuilder,
                        rawStats);
            } catch (final Exception e) {
                throw new MinerException(e);
            }
        } else {
            throw new MinerException("No obelisk found");
        }
    }

    @Override
    protected void addToEquals(
            final EqualsBuilder equalsBuilder,
            final AbstractMiner other) {
        final Obelisk otherMiner = (Obelisk) other;
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
     * @param dashboard    The dashboard stats.
     * @param gen          The generation.
     * @param mrrRigId     The MRR rig id.
     * @param rawStats     The raw stats.
     */
    private static void addAsics(
            final MinerStats.Builder statsBuilder,
            final Dashboard dashboard,
            final ObeliskGen gen,
            final String mrrRigId,
            final Map<String, Object> rawStats) {
        final Asic.Builder asicBuilder =
                new Asic.Builder();
        asicBuilder.setHashRate(toHashRate(dashboard));

        // Boards
        asicBuilder.setBoards(
                dashboard
                        .hashboards
                        .stream()
                        .filter(hashboard -> hashboard.hashrate.compareTo(BigDecimal.ZERO) > 0)
                        .count());

        // Fans
        final FanInfo.Builder fanBuilder =
                new FanInfo.Builder()
                        .setCount(gen.getNumFans())
                        .setSpeedUnits(gen.getFanSpeedUnits());
        for (final String fanKey : gen.getFanKeys()) {
            dashboard
                    .systemInfo
                    .stream()
                    .filter(info -> fanKey.equals(info.name))
                    .findFirst()
                    .ifPresent(info -> fanBuilder.addSpeed(info.value));
        }
        asicBuilder.setFanInfo(fanBuilder.build());

        // Temps
        dashboard
                .hashboards
                .forEach(board -> {
                    asicBuilder.addTemp(board.temp1);
                    asicBuilder.addTemp(board.temp2);
                });

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
            final Dashboard.Pool pool) {
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(pool.url))
                        .setWorker(pool.worker)
                        .setStatus(
                                !"Disabled".equals(pool.status),
                                "Alive".equals(pool.status))
                        .setCounts(
                                pool.accepted,
                                pool.rejected,
                                0)
                        .build());
    }

    /**
     * Adds the {@link Pool Pools}.
     *
     * @param statsBuilder The builder to update.
     * @param dashboard    The dashboard stats.
     */
    private static void addPools(
            final MinerStats.Builder statsBuilder,
            final Dashboard dashboard) {
        dashboard
                .pools
                .forEach(
                        pool ->
                                addPool(
                                        statsBuilder,
                                        pool));
    }

    /**
     * Adds up the hash rates from the provided devices.
     *
     * @param dashboard The dashboard stats.
     *
     * @return The hash rate.
     */
    private static BigDecimal toHashRate(
            final Dashboard dashboard) {
        return dashboard
                .hashboards
                .stream()
                .map(hashboard -> hashboard.hashrate)
                .map((value) ->
                        value.multiply(
                                new BigDecimal(1000 * 1000)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Adds stats for the provided obelisk generation.
     *
     * @param gen          The generation.
     * @param statsBuilder The stats.
     * @param rawStats     The raw stats.
     *
     * @throws MinerException on failure to query.
     */
    private void addStatsForGen(
            final ObeliskGen gen,
            final MinerStats.Builder statsBuilder,
            final Map<String, Object> rawStats)
            throws Exception {
        ObeliskQuery.runSessionQuery(
                this.apiIp,
                this.apiPort,
                "/api/status/dashboard",
                false,
                this.username,
                this.password,
                this.socketTimeout,
                this.socketTimeoutUnits,
                this.statsWhitelist,
                (code, body) ->
                        this.objectMapper.readValue(
                                body,
                                Dashboard.class),
                null,
                dashboard -> {
                    addPools(
                            statsBuilder,
                            dashboard);
                    addAsics(
                            statsBuilder,
                            dashboard,
                            gen,
                            dashboard
                                    .pools
                                    .stream()
                                    .map(pool -> MrrUtils.getRigId(pool.url, pool.worker))
                                    .filter(Objects::nonNull)
                                    .findFirst()
                                    .orElse(""),
                            rawStats);
                },
                rawStats);
    }

    /**
     * Handles the raw response.
     *
     * @param json      The raw response.
     * @param dest      The destination.
     * @param whitelist The stats key whitelist.
     */
    private void handleResponse(
            final String json,
            final Map<String, Object> dest,
            final List<String> whitelist) {
        if (json != null) {
            dest.putAll(
                    Flatten.flattenAndFilter(
                            json,
                            whitelist));
        }
    }
}