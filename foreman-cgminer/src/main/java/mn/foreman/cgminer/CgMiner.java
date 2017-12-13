package mn.foreman.cgminer;

import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.cgminer.response.CgMinerStatusCode;
import mn.foreman.cgminer.response.CgMinerStatusSection;
import mn.foreman.model.Miner;
import mn.foreman.model.miners.Asic;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.Pool;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A {@link CgMiner} represents a cgminer controlled crypto farm.
 *
 * <p>This class relies on the cgminer-api being enabled and configured to allow
 * the server that this application is running on to access it.</p>
 *
 * <p>As of right now, only the {@link CgMinerCommand#DEVS} and {@link
 * CgMinerCommand#POOLS} stats are polled.</p>
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
        addAsicStats(builder);
        addPoolStats(builder);

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
     * @param values  The pool values.
     */
    private static void addAsicStats(
            final MinerStats.Builder builder,
            final Map<String, String> values) {
        builder.addAsic(
                new Asic.Builder()
                        .setName(values.get("ASC"))
                        .setTemperature(
                                new BigDecimal(values.get("Temperature")))
                        .build());
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
        builder.addPool(
                new Pool.Builder()
                        .setName(values.get("POOL"))
                        .setPriority(Integer.parseInt(values.get("Priority")))
                        .setEnabled("Alive".equals(values.get("Status")))
                        .build());
    }

    /**
     * Queries for and adds ASIC metrics to the provided builder.
     *
     * @param builder The builder to update.
     */
    private void addAsicStats(
            final MinerStats.Builder builder) {
        final CgMinerResponse response = query(
                new CgMinerRequest.Builder()
                        .setCommand(CgMinerCommand.DEVS)
                        .build());

        if (response.hasValues()) {
            final List<Map<String, String>> values = response.getValues();

            // Could contain GPU and PGA metrics.  Ignore all of those for now.
            // We only care about ASICs at this point in time.
            final Map<Boolean, List<Map<String, String>>> splitValues =
                    values.stream().collect(
                            Collectors.groupingBy(
                                    value -> value.containsKey("ASC")));
            final List<Map<String, String>> asicValues = splitValues.get(true);
            if ((asicValues != null) && (!asicValues.isEmpty())) {
                asicValues.forEach(
                        value -> addAsicStats(builder, value));
            } else {
                LOG.debug("No ASICs found");
            }
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
                response =
                        objectMapper.readValue(
                                responseJson.get(),
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