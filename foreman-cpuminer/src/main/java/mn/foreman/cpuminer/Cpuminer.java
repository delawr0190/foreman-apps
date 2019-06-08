package mn.foreman.cpuminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.cpu.Cpu;
import mn.foreman.util.PoolUtils;

import com.google.common.base.Splitter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A {@link Cpuminer} represents a remote cpuminer instance.
 *
 * <p>This class relies on the cpuminer-api being enabled and configured to
 * allow the server that this application is running on to access it.  If this
 * applications is running on the rig server, only localhost connections need to
 * be allowed.</p>
 *
 * <p>This class currently queries for:</p>
 *
 * <ul>
 * <li>summary</li>
 * <li>threads</li>
 * </ul>
 */
public class Cpuminer
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
    Cpuminer(
            final String apiIp,
            final int apiPort) {
        super(
                apiIp,
                apiPort);
    }

    @Override
    public void addStats(final MinerStats.Builder statsBuilder)
            throws MinerException {
        final Map<String, String> summaryValues =
                getResponse("summary").get(0);
        final List<Map<String, String>> threadValues =
                getResponse("threads");

        statsBuilder.addPool(
                new Pool.Builder()
                        .setName(PoolUtils.sanitizeUrl(summaryValues.get("URL")))
                        .setPriority(0)
                        .setStatus(
                                true,
                                true)
                        .setCounts(
                                summaryValues.get("ACC"),
                                summaryValues.get("REJ"),
                                "0")
                        .build());

        final Cpu.Builder builder =
                new Cpu.Builder()
                        .setName("CPU 0")
                        .setTemp(summaryValues.get("TEMP"))
                        .setFanSpeed(summaryValues.get("FAN"))
                        .setFrequency(summaryValues.get("FREQ"));

        for (final Map<String, String> thread : threadValues) {
            builder.addThread(
                    new BigDecimal(thread.get("kH/s"))
                            .multiply(new BigDecimal(1000)));
        }
        statsBuilder.addCpu(builder.build());
    }

    /**
     * Utility method to split up the provided {@link String}, delineated by
     * {@link #VALUE_SEPARATOR}, into key -> value pairs.
     *
     * @param value The value to split.
     *
     * @return The key -> value pairs.
     */
    @SuppressWarnings("UnstableApiUsage")
    private static List<Map<String, String>> split(final String value) {
        final List<Map<String, String>> values = new LinkedList<>();
        final String[] regions = value.split(REGION_SEPARATOR);
        for (final String region : regions) {
            values.add(
                    Splitter.on(VALUE_SEPARATOR)
                            .withKeyValueSeparator("=")
                            .split(
                                    region
                                            .trim()
                                            .replace(REGION_SEPARATOR, "")));
        }
        return values;
    }

    /**
     * Queries for info.
     *
     * @param command The command.
     *
     * @return The values.
     *
     * @throws MinerException on failure to query.
     */
    private List<Map<String, String>> getResponse(final String command)
            throws MinerException {
        return split(
                Query.delimiterQuery(
                        this.apiIp,
                        this.apiPort,
                        command));
    }
}