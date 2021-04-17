package mn.foreman.epic;

import mn.foreman.epic.json.Summary;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** An {@link AbstractMiner} implementation that will query an Epic miner. */
public class Epic
        extends AbstractMiner {

    /** The stats whitelist. */
    private final List<String> statsWhitelist;

    /**
     * Constructor.
     *
     * @param apiIp          The IP.
     * @param apiPort        The port.
     * @param statsWhitelist The stats whitelist.
     * @param macStrategy    The mac strategy.
     */
    Epic(
            final String apiIp,
            final int apiPort,
            final List<String> statsWhitelist,
            final MacStrategy macStrategy) {
        super(
                apiIp,
                apiPort,
                macStrategy);
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
                        "/summary",
                        new TypeReference<Summary>() {
                        },
                        rawJson -> rawStats.putAll(
                                Flatten.flattenAndFilter(
                                        rawJson,
                                        this.statsWhitelist)));
        addPool(
                statsBuilder,
                summary.stratum,
                summary.session);
        addAsics(
                statsBuilder,
                summary.hashboards,
                summary.stratum,
                summary.session,
                summary.preset,
                summary.fans,
                rawStats);
    }

    /**
     * Adds an ASIC from the provided devices and hardware.
     *
     * @param statsBuilder The stats builder.
     * @param hashboards   The hash boards.
     * @param session      The session.
     * @param powerMode    The power mode.
     * @param fans         The fans.
     * @param rawStats     The raw stats.
     */
    private static void addAsics(
            final MinerStats.Builder statsBuilder,
            final List<Summary.Hashboard> hashboards,
            final Summary.Stratum stratum,
            final Summary.Session session,
            final String powerMode,
            final Summary.Fans fans,
            final Map<String, Object> rawStats) {
        final Asic.Builder asicBuilder =
                new Asic.Builder();
        asicBuilder
                .setHashRate(
                        session.hashrateMhs.multiply(
                                new BigDecimal(1000 * 1000)))
                .setBoards(session.activeBoards)
                .setFanInfo(
                        new FanInfo.Builder()
                                .setCount(1)
                                .addSpeed(fans.fanSpeed)
                                .setSpeedUnits("%")
                                .build());
        hashboards
                .stream()
                .mapToInt(board -> board.temp)
                .forEach(asicBuilder::addTemp);

        // Context data
        asicBuilder
                .setMrrRigId(MrrUtils.getRigId(stratum.pool, stratum.user))
                .setPowerMode(
                        "Efficiency".equals(powerMode)
                                ? Asic.PowerMode.LOW
                                : Asic.PowerMode.NORMAL)
                .addRawStats(rawStats);
        statsBuilder.addAsic(asicBuilder.build());
    }

    /**
     * Adds the {@link Pool}.
     *
     * @param statsBuilder The builder to update.
     * @param stratum      The stratum.
     * @param session      The session.
     */
    private static void addPool(
            final MinerStats.Builder statsBuilder,
            final Summary.Stratum stratum,
            final Summary.Session session) {
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(stratum.pool))
                        .setWorker(stratum.user)
                        .setPriority(0)
                        .setStatus(
                                true,
                                true)
                        .setCounts(
                                session.accepted,
                                session.rejected,
                                0)
                        .build());
    }
}
