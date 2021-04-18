package mn.foreman.goldshell;

import mn.foreman.goldshell.json.Cgminer;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.Flatten;
import mn.foreman.util.MrrUtils;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * An {@link AbstractMiner} implementation that will query a Goldshell miner.
 */
public class Goldshell
        extends AbstractMiner {

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout units. */
    private final TimeUnit socketTimeoutUnits;

    /** The stats whitelist. */
    private final List<String> statsWhitelist;

    /**
     * Constructor.
     *
     * @param apiIp              The IP.
     * @param apiPort            The port.
     * @param statsWhitelist     The stats whitelist.
     * @param macStrategy        The mac strategy.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout units.
     */
    Goldshell(
            final String apiIp,
            final int apiPort,
            final List<String> statsWhitelist,
            final MacStrategy macStrategy,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        super(
                apiIp,
                apiPort,
                macStrategy);
        this.statsWhitelist = new ArrayList<>(statsWhitelist);
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Map<String, Object> rawStats = new LinkedHashMap<>();
        final Cgminer cgminer =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/mcb/cgminer?cgminercmd=devs",
                        "GET",
                        new TypeReference<Cgminer>() {
                        },
                        this.socketTimeout,
                        this.socketTimeoutUnits,
                        rawJson -> rawStats.putAll(
                                Flatten.flattenAndFilter(
                                        rawJson,
                                        this.statsWhitelist)));
        final List<mn.foreman.goldshell.json.Pool> pools =
                Query.restQuery(
                        this.apiIp,
                        this.apiPort,
                        "/mcb/pools",
                        "GET",
                        new TypeReference<List<mn.foreman.goldshell.json.Pool>>() {
                        },
                        this.socketTimeout,
                        this.socketTimeoutUnits,
                        rawJson -> rawStats.putAll(
                                Flatten.flattenAndFilter(
                                        rawJson,
                                        this.statsWhitelist)));

        addPools(
                statsBuilder,
                pools,
                cgminer);
        addAsics(
                statsBuilder,
                cgminer,
                pools,
                rawStats);
    }

    /**
     * Adds an ASIC from the provided devices and hardware.
     *
     * @param statsBuilder The stats builder.
     * @param cgminer      The stats.
     * @param pools        The pools.
     * @param rawStats     The raw stats.
     */
    private static void addAsics(
            final MinerStats.Builder statsBuilder,
            final Cgminer cgminer,
            final List<mn.foreman.goldshell.json.Pool> pools,
            final Map<String, Object> rawStats) {
        BigDecimal hashRate = BigDecimal.ZERO;

        int effectiveBoards = 0;

        final List<String> temps = new LinkedList<>();
        final FanInfo.Builder fanBuilder = new FanInfo.Builder();

        final BigDecimal multiplier = new BigDecimal(1000 * 1000);
        for (final Cgminer.Data data : cgminer.datas) {
            if (data.status == 0) {
                effectiveBoards++;
            }

            hashRate = hashRate.add(data.hashrate.multiply(multiplier));

            // Parse out the fan speeds
            if (!fanBuilder.hasSpeeds()) {
                int count = 0;
                for (final String fan : data.fans.split("/")) {
                    count++;
                    fanBuilder.addSpeed(
                            fan.
                                    replace(
                                            "rpm",
                                            "")
                                    .trim());
                }

                fanBuilder
                        .setCount(count)
                        .setSpeedUnits("RPM");
            }

            // Parse out the temps
            for (final String temp : data.temp.split("/")) {
                final String[] tempRegions = temp.trim().split(" ");
                if (tempRegions.length > 0) {
                    temps.add(tempRegions[0].trim());
                }
            }
        }

        final Asic.Builder asicBuilder =
                new Asic.Builder()
                        .setHashRate(hashRate)
                        .setBoards(effectiveBoards)
                        .setFanInfo(fanBuilder.build())
                        .addTemps(temps)
                        .addRawStats(rawStats);

        if (!pools.isEmpty()) {
            final mn.foreman.goldshell.json.Pool pool = pools.get(0);
            asicBuilder.setMrrRigId(MrrUtils.getRigId(pool.url, pool.user));
        }

        statsBuilder.addAsic(asicBuilder.build());
    }

    /**
     * Adds the {@link mn.foreman.goldshell.json.Pool pools}.
     *
     * @param statsBuilder The builder to update.
     * @param pools        The pools.
     * @param cgminer      The stats.
     */
    private static void addPools(
            final MinerStats.Builder statsBuilder,
            final List<mn.foreman.goldshell.json.Pool> pools,
            final Cgminer cgminer) {
        final int totalAccepted =
                cgminer
                        .datas
                        .stream()
                        .mapToInt(data -> data.accepted)
                        .sum();
        final int totalRejected =
                cgminer
                        .datas
                        .stream()
                        .mapToInt(data -> data.rejected)
                        .sum();

        for (final mn.foreman.goldshell.json.Pool pool : pools) {
            statsBuilder.addPool(
                    new Pool.Builder()
                            .setName(PoolUtils.sanitizeUrl(pool.url))
                            .setWorker(pool.user)
                            .setPriority(pool.priority)
                            .setStatus(
                                    true,
                                    true)
                            .setCounts(
                                    pool.active ? totalAccepted : 0,
                                    pool.active ? totalRejected : 0,
                                    0)
                            .build());
        }
    }
}
