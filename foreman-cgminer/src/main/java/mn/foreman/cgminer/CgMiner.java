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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;
import java.util.Map;
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

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name    The miner name.
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    public CgMiner(
            final String name,
            final String apiIp,
            final int apiPort) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, apiPort,
                "apiPort must be positive");
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
     * Connects to the IP and port provided and sends the {@link CgMinerRequest}
     * as bytes to the interface.
     *
     * <p>This function also waits and reads the entire response until the
     * socket is closed.</p>
     *
     * <p>Note: if an exception occurs throughout this function, a {@link
     * CgMinerResponse} will be returned with a {@link CgMinerStatusCode#FATAL}
     * set and the exception will reside in the {@link CgMinerStatusSection#description}.</p>
     *
     * @param request The request to send.
     * @param ip      The IP.
     * @param port    The port.
     *
     * @return The {@link CgMinerResponse}.
     */
    private static CgMinerResponse query(
            final CgMinerRequest request,
            final String ip,
            final int port) {
        CgMinerResponse response;

        try (final Socket socket =
                     new Socket(ip, port);
             final InputStreamReader inputStreamReader =
                     new InputStreamReader(socket.getInputStream());
             final BufferedReader bufferedReader =
                     new BufferedReader(inputStreamReader);
             final OutputStreamWriter outputStream =
                     new OutputStreamWriter(
                             socket.getOutputStream(), "UTF-8")) {

            final ObjectMapper objectMapper =
                    new ObjectMapper();

            final String message =
                    objectMapper.writeValueAsString(request);
            LOG.debug("Sending message ({}) to {}:{}",
                    message,
                    ip,
                    port);

            outputStream.write(message, 0, message.length());
            outputStream.flush();

            final char[] buffer = new char[2048];

            final StringBuilder stringBuilder = new StringBuilder();

            int read;
            while ((read =
                    bufferedReader.read(buffer, 0, buffer.length)) > 0) {
                stringBuilder.append(buffer, 0, read);
            }

            response =
                    objectMapper.readValue(
                            stringBuilder.toString().trim(),
                            CgMinerResponse.class);

            LOG.debug("Read response: {}", response);
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while querying", ioe);
            response =
                    new CgMinerResponse.Builder()
                            .setStatusSection(
                                    new CgMinerStatusSection.Builder()
                                            .setStatusCode(
                                                    CgMinerStatusCode.FATAL)
                                            .setDescription(
                                                    ioe.getLocalizedMessage())
                                            .build())
                            .build();
        }

        return response;
    }

    /**
     * Queries for and adds ASIC metrics to the provided builder.
     *
     * @param builder The builder to update.
     */
    private void addAsicStats(
            final MinerStats.Builder builder) {
        final CgMinerResponse response =
                query(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.DEVS)
                                .build(),
                        this.apiIp,
                        this.apiPort);

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
        final CgMinerResponse response =
                query(
                        new CgMinerRequest.Builder()
                                .setCommand(CgMinerCommand.POOLS)
                                .build(),
                        this.apiIp,
                        this.apiPort);

        if (response.hasValues()) {
            final List<Map<String, String>> values = response.getValues();
            values.forEach(value -> addPoolStats(builder, value));
        } else {
            LOG.debug("No pools found");
        }
    }
}