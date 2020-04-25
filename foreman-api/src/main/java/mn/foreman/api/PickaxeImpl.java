package mn.foreman.api;

import mn.foreman.model.command.CommandDone;
import mn.foreman.model.command.CommandStart;
import mn.foreman.model.command.CommandUpdate;
import mn.foreman.model.command.Commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/** A simple {@link Pickaxe} implementation. */
public class PickaxeImpl
        implements Pickaxe {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(PickaxeImpl.class);

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The pickaxe ID. */
    private final String pickaxeId;

    /** The web utilities. */
    private final WebUtil webUtil;

    /**
     * Constructor.
     *
     * @param pickaxeId    The pickaxe ID.
     * @param objectMapper The mapper.
     * @param webUtil      The web utilities.
     */
    public PickaxeImpl(
            final String pickaxeId,
            final ObjectMapper objectMapper,
            final WebUtil webUtil) {
        this.pickaxeId = pickaxeId;
        this.objectMapper = objectMapper;
        this.webUtil = webUtil;
    }

    @Override
    public Optional<CommandDone.Response> commandDone(
            final CommandDone done,
            final String commandId) {
        Optional<CommandDone.Response> result = Optional.empty();
        try {
            final Optional<String> response =
                    this.webUtil.post(
                            String.format(
                                    "/api/pickaxe/%s/command/%s/done",
                                    this.pickaxeId,
                                    commandId),
                            this.objectMapper.writeValueAsString(done));
            if (response.isPresent()) {
                result =
                        fromJson(
                                response.get(),
                                CommandDone.Response.class);
            }
        } catch (final JsonProcessingException e) {
            LOG.warn("Exception occurred while parsing json", e);
        }
        return result;
    }

    @Override
    public Optional<CommandStart.Response> commandStarted(
            final CommandStart start) {
        final Optional<String> response =
                this.webUtil.post(
                        String.format(
                                "/api/pickaxe/%s/command/%s/start",
                                this.pickaxeId,
                                start.id));
        if (response.isPresent()) {
            return fromJson(
                    response.get(),
                    CommandStart.Response.class);
        }
        return Optional.empty();
    }

    @Override
    public Optional<CommandUpdate.Response> commandUpdate(
            final CommandUpdate update,
            final String commandId) {
        Optional<CommandUpdate.Response> result = Optional.empty();
        try {
            final Optional<String> response =
                    this.webUtil.post(
                            String.format(
                                    "/api/pickaxe/%s/command/%s/update",
                                    this.pickaxeId,
                                    commandId),
                            this.objectMapper.writeValueAsString(update));
            if (response.isPresent()) {
                result =
                        fromJson(
                                response.get(),
                                CommandUpdate.Response.class);
            }
        } catch (final JsonProcessingException e) {
            LOG.warn("Exception occurred while parsing json", e);
        }
        return result;
    }

    @Override
    public Optional<Commands> getCommands() {
        final Optional<String> response =
                this.webUtil.get(
                        String.format(
                                "/api/pickaxe/%s/commands",
                                this.pickaxeId));
        if (response.isPresent()) {
            return fromJson(
                    response.get(),
                    Commands.class);
        }
        return Optional.empty();
    }

    /**
     * Converts the provided json to the {@link T}.
     *
     * @param json  The json.
     * @param clazz The {@link Class}.
     * @param <T>   The response type.
     *
     * @return The response object.
     */
    private <T> Optional<T> fromJson(
            final String json,
            final Class<T> clazz) {
        try {
            return Optional.of(
                    this.objectMapper.readValue(
                            json,
                            clazz));
        } catch (final IOException e) {
            LOG.warn("Exception occurred while parsing response");
        }
        return Optional.empty();
    }
}
