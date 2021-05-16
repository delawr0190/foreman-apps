package mn.foreman.ebang.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The admin info object. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminInfo {

    /** The feedback. */
    @JsonProperty("feedback")
    public Feedback feedback;

    /** The feedback. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feedback {

        /** The MAC address. */
        @JsonProperty("macaddr")
        public String mac;
    }
}
