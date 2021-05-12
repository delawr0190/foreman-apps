package mn.foreman.blackminer;

import mn.foreman.blackminer.response.MinerStatus;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.MacStrategy;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.asic.Asic;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An {@link AbstractMiner} implementation that will query a Blackminer that's
 * only reachable via the web interface.
 */
public class BlackminerWeb
        extends AbstractMiner {

    /** The JSON mapper. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** The username. */
    private final String password;

    /** The password. */
    private final String username;

    /**
     * Constructor.
     *
     * @param apiIp       The API ip.
     * @param apiPort     The API port.
     * @param username    The username.
     * @param password    The password.
     * @param macStrategy The MAC strategy.
     */
    public BlackminerWeb(
            final String apiIp,
            final int apiPort,
            final String username,
            final String password,
            final MacStrategy macStrategy) {
        super(
                apiIp,
                apiPort,
                macStrategy);
        this.username = username;
        this.password = password;
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        try {
            final AtomicReference<MinerStatus> status = new AtomicReference<>();
            Query.digestGet(
                    this.apiIp,
                    this.apiPort,
                    "blackMiner Configuration",
                    "/cgi-bin/get_miner_status.cgi",
                    this.username,
                    this.password,
                    (integer, s) -> {
                        try {
                            s = s.replaceAll("(?m)^//.*", "");
                            status.set(
                                    OBJECT_MAPPER.readValue(
                                            s,
                                            MinerStatus.class));
                        } catch (final Exception e) {
                            // Ignore
                        }
                    });

            final MinerStatus minerStatus = status.get();
            if (minerStatus != null) {
                addPools(
                        minerStatus,
                        statsBuilder);
                addAsics(
                        minerStatus,
                        statsBuilder);
            }
        } catch (Exception e) {
            throw new MinerException(e);
        }
    }

    /**
     * Adds an {@link Asic} to the provided builder.
     *
     * @param minerStatus The status.
     * @param builder     The builder.
     */
    private static void addAsics(
            final MinerStatus minerStatus,
            final MinerStats.Builder builder) {
        final Asic.Builder asicBuilder =
                new Asic.Builder();
        asicBuilder.setPowerMode(Asic.PowerMode.NORMAL);
        asicBuilder.setBoards(minerStatus.devs.size());

        if (!minerStatus.devs.isEmpty()) {
            final String freq = minerStatus.devs.get(0).freq;

            final Optional<String> fanNumOpt =
                    getValue(freq, "fan_num");
            if (fanNumOpt.isPresent()) {
                final int fanNum = Integer.parseInt(fanNumOpt.get());

                final FanInfo.Builder fanBuilder =
                        new FanInfo.Builder()
                                .setCount(fanNum);
                for (int i = 1; i <= fanNum; i++) {
                    getValue(freq, "fan" + i).ifPresent(fanBuilder::addSpeed);
                }
                fanBuilder.setSpeedUnits("RPM");

                asicBuilder.setFanInfo(fanBuilder.build());
            }

            final Optional<String> tempNumOpt =
                    getValue(freq, "temp_num");
            if (tempNumOpt.isPresent()) {
                final int tempNum = Integer.parseInt(tempNumOpt.get());
                for (int i = 1; i <= tempNum; i++) {
                    getValue(freq, "temp" + i).ifPresent(asicBuilder::addTemp);
                }
            }

            asicBuilder.setHashRate(
                    new BigDecimal(minerStatus.summary.ghsav)
                            .multiply(new BigDecimal(1000 * 1000 * 1000)));
        }

        builder.addAsic(asicBuilder.build());
    }

    /**
     * Adds pools to the provided builder.
     *
     * @param minerStatus The status.
     * @param builder     The builder.
     */
    private static void addPools(
            final MinerStatus minerStatus,
            final MinerStats.Builder builder) {
        minerStatus
                .pools
                .stream()
                .filter(pool -> pool.url != null && !pool.url.isEmpty())
                .map(pool ->
                        new Pool.Builder()
                                .setName(PoolUtils.sanitizeUrl(pool.url))
                                .setWorker(pool.user)
                                .setCounts(
                                        minerStatus.summary.accepted,
                                        minerStatus.summary.rejected,
                                        minerStatus.summary.stale)
                                .setPriority(pool.priority)
                                .setStatus(
                                        true,
                                        "Alive".equals(pool.status))
                                .build())
                .forEach(builder::addPool);
    }

    /**
     * Extracts a value from an encoded key=value string.
     *
     * @param toExtract The string to examine.
     * @param key       The key to extract.
     *
     * @return The value.
     */
    private static Optional<String> getValue(
            final String toExtract,
            final String key) {
        final int start = toExtract.indexOf(key);
        if (start >= 0) {
            return Optional.of(
                    toExtract.substring(
                            // Start after key=
                            start + key.length() + 1,
                            // End after ,
                            toExtract.indexOf(",", start)));
        }
        return Optional.empty();
    }
}
