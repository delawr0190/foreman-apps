package mn.foreman.model.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * A {@link CommandUpdate} represents an update to the Foreman dashboard from a
 * command that's being ran.
 */
@Data
@Builder
public class CommandUpdate {

    /** The command. */
    @JsonProperty("command")
    private final String command;

    /** The update. */
    @JsonProperty("update")
    private final Map<String, Object> update;

    /** The response. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {

        /** The command status. */
        @JsonProperty("status")
        public String status;
    }
}
