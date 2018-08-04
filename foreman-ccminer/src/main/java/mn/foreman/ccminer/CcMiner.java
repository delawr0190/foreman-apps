package mn.foreman.ccminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;
import mn.foreman.util.PoolUtils;

import com.google.common.base.Splitter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

/**
 * A {@link CcMiner} represents a remove ccminer instance.
 *
 * <p>This class relies on the ccminer-api being enabled and configured to allow
 * the server that this application is running on to access it.  If this
 * applications is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries for:</p>
 *
 * <ul>
 *     <li>summary|</li>
 *     <li>pool|</li>
 *     <li>hwinfo|</li>
 * </ul>
 */
public class CcMiner
        extends AbstractMiner {

    /** The separator between regions. */
    private static final String REGION_SEPARATOR = "\\|";

    /** The separator between key -> value pairs. */
    private static final String VALUE_SEPARATOR = ";";

    /**
     * Constructor.
     *
     * @param name    The name.
     * @param apiIp   The api IP.
     * @param apiPort The api port.
     */
    CcMiner(
            final String name,
            final String apiIp,
            final int apiPort) {
        super(
                name,
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Rig.Builder rigBuilder =
                new Rig.Builder();

        getSummary(rigBuilder);
        getHwInfo(rigBuilder);
        getPools(statsBuilder);

        statsBuilder.addRig(rigBuilder.build());
    }

    /**
     * Utility method to split up the provided {@link String}, delineated by
     * {@link #VALUE_SEPARATOR}, into key -> value pairs.
     *
     * @param value The value to split.
     *
     * @return The key -> value pairs.
     */
    private static Map<String, String> split(final String value) {
        return Splitter.on(VALUE_SEPARATOR)
                .withKeyValueSeparator("=")
                .split(value.replace("|", ""));
    }

    /**
     * Adds a {@link Gpu} to the {@link Rig} using the provided values.
     *
     * @param values  The values.
     * @param builder The builder.
     */
    private void addGpu(
            final Map<String, String> values,
            final Rig.Builder builder) {
        builder.addGpu(
                new Gpu.Builder()
                        .setName(values.get("CARD"))
                        .setIndex(values.get("GPU"))
                        .setBus(values.get("BUS"))
                        .setTemp(values.get("TEMP"))
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(1)
                                        .addSpeed(values.get("FAN"))
                                        .setSpeedUnits("%")
                                        .build())
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(values.get("FREQ"))
                                        .setMemFreq(values.get("MEMFREQ"))
                                        .build())
                        .build());
    }

    /**
     * Queries for hardware info and extracts the {@link Gpu GPUs} in the {@link
     * Rig}, adding them to the provided builder.
     *
     * @param builder The builder to update.
     *
     * @throws MinerException on failure to query.
     */
    private void getHwInfo(final Rig.Builder builder)
            throws MinerException {
        final String hwInfo =
                Query.delimiterQuery(
                        this.apiIp,
                        this.apiPort,
                        "hwinfo|");
        Arrays
                .stream(hwInfo.split(REGION_SEPARATOR))
                .filter((info) -> info.contains("GPU"))
                .map(CcMiner::split)
                .filter((info) -> info.containsKey("GPU"))
                .forEach((info) ->
                        addGpu(
                                info,
                                builder));
    }

    /**
     * Queries for pool info and adds them to the provided builder.
     *
     * @param builder The builder.
     *
     * @throws MinerException on failure to query.
     */
    private void getPools(final MinerStats.Builder builder)
            throws MinerException {
        final String pool =
                Query.delimiterQuery(
                        this.apiIp,
                        this.apiPort,
                        "pool|");
        final Map<String, String> values = split(pool);
        builder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(values.get("URL")))
                        .setPriority(1)
                        .setStatus(
                                true,
                                Integer.parseInt(values.get("UPTIME")) > 0)
                        .setCounts(
                                values.get("ACC"),
                                values.get("REJ"),
                                values.get("STALE"))
                        .build());
    }

    /**
     * Queries for summary info and adds it to the provided builder.
     *
     * @param rigBuilder The builder.
     *
     * @throws MinerException on failure to query.
     */
    private void getSummary(final Rig.Builder rigBuilder)
            throws MinerException {
        final String summary =
                Query.delimiterQuery(
                        this.apiIp,
                        this.apiPort,
                        "summary|");
        final Map<String, String> values = split(summary);
        final BigDecimal hashRate =
                new BigDecimal(values.get("KHS"))
                        .multiply(new BigDecimal(1000));
        rigBuilder
                .setName("ccminer_" + values.get("NAME"))
                .setHashRate(hashRate);
    }
}