package mn.foreman.model.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * A {@link CommandStart} represents a command to be performed against miners.
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommandStart {

    /** The arguments for the command. */
    @JsonProperty("args")
    private final Map<String, String> args;

    /** The command. */
    @JsonProperty("command")
    private final String command;

    /** The command ID. */
    @JsonProperty("id")
    private final String id;
}
