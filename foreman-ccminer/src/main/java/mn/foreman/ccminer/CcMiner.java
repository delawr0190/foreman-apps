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
 * <p>This class relies on the ccminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * applications is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries for:</p>
 *
 * <ul>
 * <li>summary|</li>
 * <li>pool|</li>
 * <li>hwinfo|</li>
 * </ul>
 *
 * <p>In the event that the "hwinfo" command isn't available, "gpus" will be
 * used as an alternative with information stubbed out.</p>
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
     * @param apiIp   The api IP.
     * @param apiPort The api port.
     */
    CcMiner(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Rig.Builder rigBuilder =
                new Rig.Builder();

        final Map<String, String> summaryValues =
                getSummary(rigBuilder);
        try {
            getHwInfo(rigBuilder);
        } catch (final MinerException me) {
            // No hwinfo available on the API, so try gpus as a last resort
            getGpus(rigBuilder);
        }
        getPools(
                summaryValues,
                statsBuilder);

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
                .split(value.trim().replace("|", ""));
    }

    /**
     * Utility method to convert a frequency to a valid value.
     *
     * @param value The value to convert.
     *
     * @return The frequency.
     */
    private static int toFreqInt(final String value) {
        int freq = 0;
        if (value != null && !value.isEmpty()) {
            freq = Integer.parseInt(value);
        }

        // Some ccminer APIs report frequencies in Hz while others are MHz.
        // Convert to MHz if any seem overly high.
        if (freq / 1000 > 100) {
            freq /= 1000;
        }

        return freq;
    }

    /**
     * Adds a {@link Gpu} to the {@link Rig} using the provided values.
     *
     * @param values  The values.
     * @param builder The builder.
     */
    private void addFullGpu(
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
                                        .setFreq(
                                                toFreqInt(
                                                        values.get("FREQ")))
                                        .setMemFreq(
                                                toFreqInt(
                                                        values.get("MEMFREQ")))
                                        .build())
                        .build());
    }

    /**
     * Adds a {@link Gpu} to the {@link Rig} using the provided values.
     *
     * @param values  The values.
     * @param builder The builder.
     */
    private void addStubbedGpu(
            final Map<String, String> values,
            final Rig.Builder builder) {
        builder.addGpu(
                new Gpu.Builder()
                        .setName("GPU " + values.get("GPU"))
                        // Can't determine the index
                        .setIndex(0)
                        // Can't determine the bus
                        .setBus(0)
                        // Can't determine the temp
                        .setTemp(0)
                        // Can't determine the fans
                        .setFans(
                                new FanInfo.Builder()
                                        .setCount(0)
                                        .setSpeedUnits("%")
                                        .build())
                        // Can't determine the frequency info
                        .setFreqInfo(
                                new FreqInfo.Builder()
                                        .setFreq(0)
                                        .setMemFreq(0)
                                        .build())
                        .build());
    }

    /**
     * Queries for GPUs and extracts the {@link Gpu GPUs} in the {@link Rig},
     * adding them to the provided builder.
     *
     * @param builder The builder to update.
     *
     * @throws MinerException on failure to query.
     */
    private void getGpus(final Rig.Builder builder)
            throws MinerException {
        final String gpus =
                Query.delimiterQuery(
                        this.apiIp,
                        this.apiPort,
                        "gpus|");
        Arrays
                .stream(gpus.split(REGION_SEPARATOR))
                .filter((info) -> info.contains("GPU"))
                .map(CcMiner::split)
                .filter((info) -> info.containsKey("GPU"))
                .forEach((info) ->
                        addStubbedGpu(
                                info,
                                builder));
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
                        addFullGpu(
                                info,
                                builder));
    }

    /**
     * Queries for pool info and adds them to the provided builder.
     *
     * @param summaryValues The summary values.
     * @param builder       The builder.
     *
     * @throws MinerException on failure to query.
     */
    private void getPools(
            final Map<String, String> summaryValues,
            final MinerStats.Builder builder)
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
                        .setPriority(0)
                        .setStatus(
                                true,
                                Integer.parseInt(values.get("UPTIME")) > 0)
                        .setCounts(
                                // Some ccminers put the shares in 'summary'
                                values.getOrDefault(
                                        "ACC",
                                        summaryValues.getOrDefault(
                                                "ACC",
                                                "0")),
                                values.getOrDefault(
                                        "REJ",
                                        summaryValues.getOrDefault(
                                                "REJ",
                                                "0")),
                                values.getOrDefault(
                                        "STALE",
                                        summaryValues.getOrDefault(
                                                "STALE",
                                                "0")))
                        .build());
    }

    /**
     * Queries for summary info and adds it to the provided builder.
     *
     * @param rigBuilder The builder.
     *
     * @return The summary values.
     *
     * @throws MinerException on failure to query.
     */
    private Map<String, String> getSummary(final Rig.Builder rigBuilder)
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
        rigBuilder.setHashRate(hashRate);
        return values;
    }
}