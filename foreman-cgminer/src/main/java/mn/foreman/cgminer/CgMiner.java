package mn.foreman.cgminer;

import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.model.AbstractBuilder;
import mn.foreman.model.AbstractMiner;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
     * A {@link Map} of every {@link CgMinerRequest} to a {@link
     * ResponsePatchingStrategy} to sanitize the JSON.
     */
    private final Map<CgMinerRequest, ResponsePatchingStrategy>
            patchingStrategies;

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
        this.patchingStrategies = new HashMap<>(builder.patchingStrategies);
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
     * Fixes invalid JSON formatting present in some forks of cgminer.
     *
     * @param json             The JSON to patch.
     * @param patchingStrategy The strategy to use for patching the response.
     *
     * @return The patched JSON.
     *
     * @throws IOException on failure to patch JSON.
     */
    private static String patchJson(
            final String json,
            final ResponsePatchingStrategy patchingStrategy)
            throws IOException {
        String goodJson = json;
        final int errorObjectEnd = json.indexOf("}{");
        if (errorObjectEnd >= 0) {
            final int errorObjectStart =
                    json.lastIndexOf(
                            "{",
                            errorObjectEnd);
            goodJson =
                    json.substring(0, errorObjectStart) +
                            json.substring(
                                    errorObjectEnd + 1,
                                    json.length());
        }

        try {
            // Remove id
            final ObjectMapper objectMapper =
                    new ObjectMapper();
            final Map<String, Object> parsed =
                    objectMapper.readValue(
                            goodJson,
                            new TypeReference<Map<String, Object>>() {
                            });
            parsed.remove("id");
            goodJson = objectMapper.writeValueAsString(parsed);
        } catch (final IOException e) {
            // Ignore
        }

        return patchingStrategy.patch(goodJson);
    }

    /**
     * Converts the response map to a {@link CgMinerResponse}.
     *
     * @param response The map.
     *
     * @return The response.
     */
    private static CgMinerResponse toResponse(
            final Map<String, List<Map<String, String>>> response) {
        final CgMinerResponse.Builder builder =
                new CgMinerResponse.Builder();
        response.get("STATUS").forEach(builder::addStatus);
        response.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().equals("STATUS"))
                .forEach(
                        entry ->
                                entry.getValue().forEach(
                                        value ->
                                                builder.addValues(
                                                        entry.getKey(),
                                                        value)));
        return builder.build();
    }

    /**
     * Connects to the IP and port provided and sends the {@link CgMinerRequest}
     * as bytes to the interface.
     *
     * <p>This function also waits and reads the entire response until the
     * socket is closed.</p>
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
        CgMinerResponse response = null;

        try {
            final ObjectMapper objectMapper = new ObjectMapper();

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
                final String responseString =
                        patchJson(
                                apiRequest.getResponse(),
                                this.patchingStrategies.get(request));

                final Map<String, List<Map<String, String>>> responseMap =
                        objectMapper.readValue(
                                responseString,
                                new TypeReference<Map<String, List<Map<String, String>>>>() {
                                });
                if (!responseMap.isEmpty()) {
                    response = toResponse(responseMap);
                }
            } else {
                LOG.warn("No response received from cgminer");
            }
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while querying {}:{}",
                    this.apiIp,
                    this.apiPort,
                    ioe);
        }

        if (response == null) {
            throw new MinerException("Failed to obtain a response");
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
         * A map of every request to a patching strategy to sanitize the
         * response.
         */
        private Map<CgMinerRequest, ResponsePatchingStrategy>
                patchingStrategies = new ConcurrentHashMap<>();

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
            return addRequest(
                    request,
                    strategy,
                    new NullPatchingStrategy());
        }

        /**
         * Adds the request and the strategies to use for processing the
         * response.
         *
         * @param request          The request.
         * @param responseStrategy The response strategy.
         * @param patchingStrategy The patching strategy.
         *
         * @return This builder instance.
         */
        public Builder addRequest(
                final CgMinerRequest request,
                final ResponseStrategy responseStrategy,
                final ResponsePatchingStrategy patchingStrategy) {
            this.requests.put(request, responseStrategy);
            this.patchingStrategies.put(request, patchingStrategy);
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