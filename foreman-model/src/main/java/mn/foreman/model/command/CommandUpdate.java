package mn.foreman.model.command;

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
}
