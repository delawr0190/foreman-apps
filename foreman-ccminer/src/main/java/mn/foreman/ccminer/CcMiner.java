package mn.foreman.ccminer;

import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.model.Miner;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;
import mn.foreman.model.miners.SpeedInfo;
import mn.foreman.model.miners.rig.FreqInfo;
import mn.foreman.model.miners.rig.Gpu;
import mn.foreman.model.miners.rig.Rig;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
 * <ul> <li>{@link CcMinerCommand#SUMMARY summary|}</li> <li>{@link
 * CcMinerCommand#POOL pool|}</li> <li>{@link CcMinerCommand#HWINFO
 * hwinfo|}</li> </ul>
 */
public class CcMiner
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(CcMiner.class);

    /** The separator between regions. */
    private static final String REGION_SEPARATOR = "|";

    /** The separator between key -> value pairs. */
    private static final String VALUE_SEPARATOR = ";";

    /** The API IP. */
    private final String apiIp;

    /** The API port. */
    private final int apiPort;

    /** The miner name. */
    private final String name;

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
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.isTrue(
                apiPort > 0,
                "apiPort must be > 0");
        this.name = name;
        this.apiIp = apiIp;
        this.apiPort = apiPort;
    }

    @Override
    public MinerStats getStats() {
        LOG.debug("Obtaining stats from {}-{}:{}",
                this.name,
                this.apiIp,
                this.apiPort);

        final MinerStats.Builder builder =
                new MinerStats.Builder()
                        .setApiIp(this.apiIp)
                        .setApiPort(this.apiPort)
                        .setName(this.name);

        final Rig.Builder rigBuilder =
                new Rig.Builder();
        getSummary(rigBuilder);
        getHwInfo(rigBuilder);
        getPools(builder);

        builder.addRig(rigBuilder.build());

        return builder.build();
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
                .split(value.replace(REGION_SEPARATOR, ""));
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
                                        .addSpeed(values.get("RPM"))
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
     */
    private void getHwInfo(final Rig.Builder builder) {
        query(CcMinerCommand.HWINFO)
                .ifPresent((hwInfo) ->
                        Arrays
                                .stream(hwInfo.split(REGION_SEPARATOR))
                                .filter((info) -> info.contains("GPU"))
                                .map(CcMiner::split)
                                .forEach((info) ->
                                        addGpu(
                                                info,
                                                builder)));
    }

    /**
     * Queries for pool info and adds them to the provided builder.
     *
     * @param builder The builder.
     */
    private void getPools(final MinerStats.Builder builder) {
        query(CcMinerCommand.POOL)
                .ifPresent((pool) -> {
                    final Map<String, String> values = split(pool);
                    builder.addPool(
                            new Pool.Builder()
                                    .setName(values.get("URL"))
                                    .setPriority(1)
                                    .setStatus(
                                            true,
                                            Integer.parseInt(values.get("UPTIME")) > 0)
                                    .setCounts(
                                            values.get("ACC"),
                                            values.get("REJ"),
                                            values.get("STALE"))
                                    .setDifficulty(values.get("DIFF"))
                                    .build());
                });
    }

    /**
     * Queries for summary info and adds it to the provided builder.
     *
     * @param rigBuilder The builder.
     */
    private void getSummary(final Rig.Builder rigBuilder) {
        query(CcMinerCommand.SUMMARY)
                .ifPresent((summary) -> {
                    final Map<String, String> values = split(summary);
                    final BigDecimal hashRate =
                            new BigDecimal(values.get("KHS"))
                                    .multiply(new BigDecimal(1000));
                    rigBuilder
                            .setName(values.get("NAME"))
                            .setSpeedInfo(
                                    new SpeedInfo.Builder()
                                            .setAvgHashRate(hashRate)
                                            .build());
                });
    }

    /**
     * Queries ccminer and returns the result.
     *
     * @param command The command to run.
     *
     * @return The result, if one exists.
     */
    private Optional<String> query(final CcMinerCommand command) {
        String response = null;

        final ApiRequest request =
                new ApiRequestImpl(
                        this.apiIp,
                        this.apiPort,
                        command.getCommand() + "|");
        final Connection connection =
                ConnectionFactory.createDelimiterConnection(
                        request);
        connection.query();

        if (request.waitForCompletion(
                10,
                TimeUnit.SECONDS)) {
            response = request.getResponse();
        }

        return Optional.ofNullable(response);
    }
}