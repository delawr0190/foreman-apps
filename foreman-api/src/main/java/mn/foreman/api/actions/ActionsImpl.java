package mn.foreman.api.actions;

import mn.foreman.api.JsonUtils;
import mn.foreman.api.WebUtil;
import mn.foreman.model.Network;
import mn.foreman.model.Pool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** A simple {@link Actions} implementation. */
public class ActionsImpl
        implements Actions {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(ActionsImpl.class);

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The web utilities. */
    private final WebUtil webUtil;

    /**
     * Constructor.
     *
     * @param objectMapper The mapper.
     * @param webUtil      The web utilities.
     */
    public ActionsImpl(
            final ObjectMapper objectMapper,
            final WebUtil webUtil) {
        this.objectMapper = objectMapper;
        this.webUtil = webUtil;
    }

    @Override
    public Optional<Response> changeNetwork(
            final int minerId,
            final Network network) {
        return runAction(
                minerId,
                "network",
                network);
    }

    @Override
    public Optional<Response> changePools(
            final int minerId,
            final List<Pool> pools) {
        return runAction(
                minerId,
                "change-pools",
                pools
                        .stream()
                        .map(pool ->
                                ImmutableMap.of(
                                        "url",
                                        pool.getUrl(),
                                        "user",
                                        pool.getUsername(),
                                        "pass",
                                        pool.getPassword()))
                        .collect(Collectors.toList()));
    }

    @Override
    public Optional<StatusRunning> status(final int command) {
        Optional<StatusRunning> result = Optional.empty();
        final Optional<String> response =
                this.webUtil.get(
                        String.format(
                                "/api/actions/status/%d",
                                command));
        if (response.isPresent()) {
            result =
                    JsonUtils
                            .fromJson(
                                    response.get(),
                                    this.objectMapper,
                                    new TypeReference<StatusResult>() {
                                    })
                            .map(statusResult -> statusResult.status);
        }
        return result;
    }

    /**
     * Runs the provided action with the provided args as the payload.
     *
     * @param minerId The miner ID.
     * @param action  The action.
     * @param args    The args.
     *
     * @return The response.
     */
    private Optional<Response> runAction(
            final int minerId,
            final String action,
            final Object args) {
        Optional<Response> result = Optional.empty();
        try {
            final Optional<String> response =
                    this.webUtil.post(
                            String.format(
                                    "/api/actions/%s/%d",
                                    action,
                                    minerId),
                            this.objectMapper.writeValueAsString(args));
            if (response.isPresent()) {
                result =
                        JsonUtils.fromJson(
                                response.get(),
                                this.objectMapper,
                                new TypeReference<Response>() {
                                });
            }
        } catch (final JsonProcessingException e) {
            LOG.warn("Exception occurred while parsing json", e);
        }
        return result;
    }

    /** The status result. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class StatusResult {

        /** The status. */
        @JsonProperty("status")
        public StatusRunning status;
    }
}
