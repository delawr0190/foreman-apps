package mn.foreman.cgminer;

import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.cgminer.response.CgMinerPoolStatus;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.cgminer.response.CgMinerStatusCode;
import mn.foreman.cgminer.response.CgMinerStatusSection;
import mn.foreman.model.Miner;
import mn.foreman.model.miners.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link CgMiner} represents a cgminer controlled crypto farm.
 *
 * <p>This class relies on the cgminer-api being enabled and configured to allow
 * the server that this application is running on to access it.</p>
 *
 * <p>As of right now, only {@link CgMinerCommand#VERSION}, {@link
 * CgMinerCommand#STATS} and {@link CgMinerCommand#POOLS} are polled.
 * Originally, it was intended that either the 'devs' or 'ascs' commands be used
 * to obtain ASIC metrics.  That wasn't possible in the November build of
 * bmminer for an Antminer S9, so 'stats' was preferred instead.</p>
 *
 * <p>The objective is to never require users to re-configure their miners just
 * for monitoring purposes.  Make use of what we have by default.</p>
 */
public class CgMiner
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(CgMiner.class);

    /** The API IP. */
    private final String apiIp;

    /** The API port. */
    private final int apiPort;

    /** The {@link ConnectionFactory} for creating connections to the API. */
    private final ConnectionFactory connectionFactory;

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name              The miner name.
     * @param apiIp             The API IP.
     * @param apiPort           The API port.
     * @param connectionFactory The {@link ConnectionFactory} for creating
     *                          connections to the API.
     */
    public CgMiner(
            final String name,
            final String apiIp,
            final int apiPort,
            final ConnectionFactory connectionFactory) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, apiPort,
                "apiPort must be positive");
        Validate.notNull(
                connectionFactory,
                "connectionFactory cannot be null");
        this.name = name;
        this.apiIp = apiIp;
        this.apiPort = apiPort;
        this.connectionFactory = connectionFactory;
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
        addPoolStats(builder);
        addAsicStats(builder);

        final MinerStats minerStats =
                builder.build();

        LOG.debug("Obtained stats: {}", minerStats);

        return minerStats;
    }

    /**
     * Utility method to convert the provided values, which contain per-ASIC
     * metrics, to a {@link Asic} and adds it to the provided builder.
     *
     * @param builder The builder to update.
     * @param type    The type.
     * @param values  The asic values.
     */
    private static void addAsicStats(
            final MinerStats.Builder builder,
            final String type,
            final Map<String, String> values) {

        final Asic.Builder asicBuilder =
                new Asic.Builder()
                        .setName(type);

        // Speed
        final BigDecimal avgHashRate =
                new BigDecimal(values.get("GHS av"))
                        .multiply(new BigDecimal(Math.pow(1000, 3)));
        final BigDecimal avgHashRate5s =
                new BigDecimal(values.get("GHS 5s"))
                        .multiply(new BigDecimal(Math.pow(1000, 3)));
        asicBuilder
                .setSpeedInfo(
                        new SpeedInfo.Builder()
                                .setAvgHashRate(avgHashRate)
                                .setAvgHashRateFiveSecs(avgHashRate5s)
                                .build());

        // Fangs
        final FanInfo.Builder fanBuilder =
                new FanInfo.Builder()
                        .setCount(values.get("fan_num"));
        for (int i = 1; i <= 8; i++) {
            fanBuilder.addSpeed(values.get("fan" + i));
        }
        asicBuilder.setFanInfo(fanBuilder.build());

        // Temps
        final List<String> tempPrefixes =
                Arrays.asList(
                        "temp",
                        "temp2_",
                        "temp3_");
        for (final String prefix : tempPrefixes) {
            for (int i = 1; i <= 16; i++) {
                asicBuilder.addTemp(values.get(prefix + i));
            }
        }

        // Errors
        boolean hasErrors = false;
        for (int i = 1; i <= 16; i++) {
            hasErrors =
                    (hasErrors || values.get("chain_acs" + i).contains("x"));
        }
        asicBuilder.hasErrors(hasErrors);

        builder.addAsic(asicBuilder.build());
    }

    /**
     * Utility method to convert the provided values, which contain per-pool
     * metrics, to a {@link Pool} and adds it to the provided builder.
     *
     * @param builder The builder to update.
     * @param values  The pool values.
     */
    private static void addPoolStats(
            final MinerStats.Builder builder,
            final Map<String, String> values) {
        final CgMinerPoolStatus status =
                CgMinerPoolStatus.forValue(values.get("Status"));
        builder.addPool(
                new Pool.Builder()
                        .setName(values.get("URL"))
                        .setPriority(values.get("Priority"))
                        .setStatus(
                                status.isEnabled(),
                                status.isUp())
                        .setCounts(
                                values.get("Accepted"),
                                values.get("Rejected"),
                                values.get("Stale"))
                        .build());
    }

    /**
     * Queries for and adds ASIC metrics to the provided builder.
     *
     * <p>Unfortunately, this code was implemented to the November build of
     * bmminer for an Antminer S9, which didn't support the 'devs' command.
     * So...ASIC metrics will be extracted from 'stats' instead.</p>
     *
     * @param builder The builder to update.
     */
    private void addAsicStats(
            final MinerStats.Builder builder) {
        final CgMinerResponse versionResponse = query(
                new CgMinerRequest.Builder()
                        .setCommand(CgMinerCommand.VERSION)
                        .build());
        final CgMinerResponse statsResponse = query(
                new CgMinerRequest.Builder()
                        .setCommand(CgMinerCommand.STATS)
                        .build());

        final String type;
        if (versionResponse.hasValues()) {
            final Map<String, String> versionValues =
                    versionResponse.getValues().get(0);
            type = versionValues.get("Type");
        } else {
            type = "cgminer_" + this.apiIp + ":" + this.apiPort;
        }

        if (statsResponse.hasValues()) {
            final List<Map<String, String>> values = statsResponse.getValues();
            values.forEach(
                    value -> addAsicStats(builder, type, value));
        } else {
            LOG.debug("No ACICs founds");
        }
    }

    /**
     * Queries for and adds pool metrics to the provided builder.
     *
     * @param builder The builder to update.
     */
    private void addPoolStats(
            final MinerStats.Builder builder) {
        final CgMinerResponse response = query(
                new CgMinerRequest.Builder()
                        .setCommand(CgMinerCommand.POOLS)
                        .build());

        if (response.hasValues()) {
            final List<Map<String, String>> values = response.getValues();
            values.forEach(value -> addPoolStats(builder, value));
        } else {
            LOG.debug("No pools found");
        }
    }

    /**
     * Connects to the IP and port provided and sends the {@link CgMinerRequest}
     * as bytes to the interface.
     *
     * <p>This function also waits and reads the entire response until the
     * socket is closed.</p>
     *
     * <p>Note: if an exception occurs throughout this function, a {@link
     * CgMinerResponse} will be returned with a {@link CgMinerStatusCode#FATAL}
     * set and the reason will reside in the {@link CgMinerStatusSection#description}.</p>
     *
     * @param request The request to send.
     *
     * @return The {@link CgMinerResponse}.
     */
    private CgMinerResponse query(
            final CgMinerRequest request) {
        CgMinerResponse response = null;
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(
                    DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);

            final String message =
                    objectMapper.writeValueAsString(request);
            LOG.debug("Sending message ({}) to {}:{}",
                    message,
                    this.apiIp,
                    this.apiPort);

            final Connection connection =
                    this.connectionFactory.create(
                            this.apiIp,
                            this.apiPort);

            final Optional<String> responseJson =
                    connection.query(message);
            if (responseJson.isPresent()) {
                String responseString = responseJson.get();

                // Some forks of cgminer return invalid JSON, so prune the
                // invalid object out
                final int errorObjectEnd = responseString.indexOf("}{");
                if (errorObjectEnd >= 0) {
                    final int errorObjectStart =
                            responseString.lastIndexOf("{", errorObjectEnd);
                    responseString =
                            responseString.substring(0, errorObjectStart) +
                                    responseString.substring(
                                            errorObjectEnd + 1,
                                            responseString.length());
                }

                response =
                        objectMapper.readValue(
                                responseString,
                                CgMinerResponse.class);

                LOG.debug("Read response: {}", response);
            } else {
                LOG.warn("No response received from cgminer");
            }
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while querying {}:{}",
                    this.apiIp,
                    this.apiPort,
                    ioe);
        } finally {
            if (response == null) {
                final CgMinerStatusSection statusSection =
                        new CgMinerStatusSection.Builder()
                                .setStatusCode(
                                        CgMinerStatusCode.FATAL)
                                .setDescription(
                                        "Failed to connect")
                                .build();
                response =
                        new CgMinerResponse.Builder()
                                .setStatusSection(statusSection)
                                .build();
            }
        }

        return response;
    }
}