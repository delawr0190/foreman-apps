package mn.foreman.ebang.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The status object. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CgminerStatus {

    /** The feedback. */
    @JsonProperty("feedback")
    public Feedback feedback;

    /** The feedback object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feedback {

        /** The accepted shares. */
        @JsonProperty("accepted")
        public int accepted;

        /** The average hash rate. */
        @JsonProperty("averfigure")
        public String hashRate;

        /** The pool. */
        @JsonProperty("cgminerstatus")
        public String pool;

        /** The rejected shares. */
        @JsonProperty("rejected")
        public int rejected;
    }
}
