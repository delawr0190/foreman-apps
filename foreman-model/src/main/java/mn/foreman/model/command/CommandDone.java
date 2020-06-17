package mn.foreman.model.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * A {@link CommandDone} represents all of the information related to a Pickaxe
 * command finishing.
 */
@Data
@Builder
public class CommandDone {

    /** The command. */
    @JsonProperty("command")
    private final String command;

    /** The result. */
    @JsonProperty("result")
    private final Map<String, Object> result;

    /** The status code. */
    @JsonProperty("status")
    private final Status status;

    /** The response. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {

        /** The command status. */
        @JsonProperty("status")
        public String status;
    }

    /** The status. */
    @Data
    @Builder
    public static class Status {

        /** A detailed message. */
        @JsonProperty("details")
        public String details;

        /** The message. */
        @JsonProperty("message")
        public String message;

        /** The type. */
        @JsonProperty("type")
        public DoneStatus type;
    }
}
