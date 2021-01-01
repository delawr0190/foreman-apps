package mn.foreman.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/** Utilities for parsing json. */
public class JsonUtils {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(JsonUtils.class);

    /**
     * Converts the provided json to the {@link T}.
     *
     * @param json          The json.
     * @param objectMapper  The mapper.
     * @param typeReference The type reference.
     * @param <T>           The response type.
     *
     * @return The response object.
     */
    public static <T> Optional<T> fromJson(
            final String json,
            final ObjectMapper objectMapper,
            final TypeReference<T> typeReference) {
        try {
            return Optional.of(
                    objectMapper.readValue(
                            json,
                            typeReference));
        } catch (final IOException e) {
            LOG.warn("Exception occurred while parsing response", e);
        }
        return Optional.empty();
    }
}
