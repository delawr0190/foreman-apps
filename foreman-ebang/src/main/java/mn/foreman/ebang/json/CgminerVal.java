package mn.foreman.ebang.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The cgminer configuration. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CgminerVal {

    /** The feedback object. */
    @JsonProperty("feedback")
    public Feedback feedback;

    /** The feedback object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feedback {

        /** The pool 1 URL. */
        @JsonProperty("Mip1")
        public String pool1Url;

        /** The pool 1 worker. */
        @JsonProperty("Mwork1")
        public String pool1Worker;

        /** The pool 2 URL. */
        @JsonProperty("Mip2")
        public String pool2Url;

        /** The pool 2 worker. */
        @JsonProperty("Mwork2")
        public String pool2Worker;

        /** The pool 3 URL. */
        @JsonProperty("Mip3")
        public String pool3Url;

        /** The pool 3 worker. */
        @JsonProperty("Mwork3")
        public String pool3Worker;
    }
}
