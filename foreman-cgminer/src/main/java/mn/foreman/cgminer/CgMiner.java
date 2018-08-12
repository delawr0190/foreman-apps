package mn.foreman.cgminer;

import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.cgminer.response.CgMinerStatusCode;
import mn.foreman.cgminer.response.CgMinerStatusSection;
import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.model.AbstractBuilder;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * A {@link CgMiner} represents a remote cgminer instance.
 *
 * <p>This class relies on the cgminer-api being enabled and configured to allow
 * the server that this application is running on to access it.</p>
 *
 * <p>As of right now, only {@link CgMinerCommand#VERSION}, {@link
 * CgMinerCommand#STATS} and {@link CgMinerCommand#POOLS} are polled.  These
 * commands proved to be the most common across all cgminer forks without
 * requiring users to enable additional remote commands (the objective is to
 * never require users to re-configure their miners just for monitoring
 * purposes.  Make use of what we have by default).</p>
 */
public class CgMiner
        extends AbstractMiner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(CgMiner.class);

    /**
     * A {@link Map} of every {@link CgMinerRequest} to send and the {@link
     * ResponseStrategy} to use for each.
     */
    private final Map<CgMinerRequest, ResponseStrategy> requests;

    /**
     * Constructor.
     *
     * @param builder The builder.
     */
    CgMiner(final Builder builder) {
        super(
                builder.apiIp,
                Integer.parseInt(builder.apiPort));
        this.requests = new HashMap<>(builder.requests);
    }

    @Override
    protected void addStats(
            final MinerStats.Builder statsBuilder)
            throws MinerException {
        for (final Map.Entry<CgMinerRequest, ResponseStrategy> entry :
                this.requests.entrySet()) {
            final CgMinerRequest request = entry.getKey();
            final CgMinerResponse response = query(request);

            final ResponseStrategy strategy = entry.getValue();
            strategy.processResponse(
                    statsBuilder,
                    response);
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
     *
     * @throws MinerException on failure to query.
     */
    private CgMinerResponse query(
            final CgMinerRequest request)
            throws MinerException {
        CgMinerResponse response;
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

            final ApiRequest apiRequest =
                    new ApiRequestImpl(
                            this.apiIp,
                            this.apiPort,
                            message);

            final Connection connection =
                    ConnectionFactory.createJsonConnection(
                            apiRequest);
            connection.query();

            if (apiRequest.waitForCompletion(
                    10,
                    TimeUnit.SECONDS)) {
                String responseString = apiRequest.getResponse();

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
                throw new MinerException("Failed to obtain a response");
            }
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while querying {}:{}",
                    this.apiIp,
                    this.apiPort,
                    ioe);
            throw new MinerException(ioe);
        }

        return response;
    }

    /** A builder for creating new {@link CgMiner CgMiners}. */
    public static class Builder
            extends AbstractBuilder<CgMiner> {

        /** The API IP. */
        private String apiIp;

        /** The API port. */
        private String apiPort;

        /**
         * A {@link Map} of each {@link CgMinerRequest} to the {@link
         * ResponseStrategy} to use for processing the response.
         */
        private Map<CgMinerRequest, ResponseStrategy> requests =
                new ConcurrentHashMap<>();

        /**
         * Adds the strategy to use for the provided request to be performed.
         *
         * @param request  The request.
         * @param strategy The strategy.
         *
         * @return This builder instance.
         */
        public Builder addRequest(
                final CgMinerRequest request,
                final ResponseStrategy strategy) {
            this.requests.put(request, strategy);
            return this;
        }

        @Override
        public CgMiner build() {
            return new CgMiner(this);
        }

        /**
         * Sets the API IP.
         *
         * @param apiIp The API IP.
         *
         * @return This builder instance.
         */
        public Builder setApiIp(final String apiIp) {
            this.apiIp = apiIp;
            return this;
        }

        /**
         * Sets the API port.
         *
         * @param apiPort The API port.
         *
         * @return This builder instance.
         */
        public Builder setApiPort(final String apiPort) {
            this.apiPort = apiPort;
            return this;
        }
    }
}