package mn.foreman.model.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * A {@link CommandStart} represents a command to be performed against miners.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommandStart {

    /** The arguments for the command. */
    @JsonProperty("args")
    public Map<String, String> args;

    /** The command. */
    @JsonProperty("command")
    public String command;

    /** The command ID. */
    @JsonProperty("id")
    public String id;

    /** The response. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {

        /** The command status. */
        @JsonProperty("status")
        public String status;
    }
}
