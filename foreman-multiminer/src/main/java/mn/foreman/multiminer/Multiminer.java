package mn.foreman.multiminer;

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
import java.util.Map;

/**
 * A {@link Multiminer} represents a remote multiminer instance.
 *
 * <p>This class relies on the multiminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * applications is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries for:</p>
 *
 * <ul>
 * <li>summary|</li>
 * </ul>
 *
 * <h2>Limitations</h2>
 *
 * <p>multiminer has a very basic API.  Therefore, device information is not
 * available.</p>
 */
public class Multiminer
        extends AbstractMiner {

    /** The separator between key -> value pairs. */
    private static final String VALUE_SEPARATOR = ";";

    /**
     * Constructor.
     *
     * @param apiIp   The api IP.
     * @param apiPort The api port.
     */
    Multiminer(
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

        final String summary =
                Query.delimiterQuery(
                        this.apiIp,
                        this.apiPort,
                        "summary|");

        final Map<String, String> values = split(summary);

        // Add pool
        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(values.get("URL")))
                        .setPriority(0)
                        .setStatus(
                                true,
                                Integer.parseInt(values.get("UPTIME")) > 0)
                        .setCounts(
                                values.get("ACC"),
                                values.get("REJ"),
                                "0")
                        .build());
        rigBuilder.setHashRate(new BigDecimal(values.get("HS")));

        for (int i = 0; i < Integer.parseInt(values.get("CPUS")); i++) {
            rigBuilder.addGpu(
                    new Gpu.Builder()
                            .setIndex(i)
                            .setBus(0)
                            .setName("Mining Device " + i)
                            .setFans(
                                    new FanInfo.Builder()
                                            .setCount(0)
                                            .setSpeedUnits("RPM")
                                            .build())
                            .setFreqInfo(
                                    new FreqInfo.Builder()
                                            .setFreq(0)
                                            .setMemFreq(0)
                                            .build())
                            .build());
        }
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
}