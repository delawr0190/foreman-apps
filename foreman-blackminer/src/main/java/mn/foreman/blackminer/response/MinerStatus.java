package mn.foreman.blackminer.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** The status. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MinerStatus {

    /** The devices. */
    @JsonProperty("devs")
    public List<Dev> devs;

    /** The pools. */
    @JsonProperty("pools")
    public List<Pool> pools;

    /** The summary. */
    @JsonProperty("summary")
    public Summary summary;

    /** The dev object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dev {

        /** The freq. */
        @JsonProperty("freq")
        public String freq;
    }

    /** The pool. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pool {

        /** The accepted. */
        @JsonProperty("accepted")
        public String accepted;

        /** The priority. */
        @JsonProperty("priority")
        public String priority;

        /** The rejected. */
        @JsonProperty("rejected")
        public String rejected;

        /** The stale. */
        @JsonProperty("stale")
        public String stale;

        /** The status. */
        @JsonProperty("status")
        public String status;

        /** The URL. */
        @JsonProperty("url")
        public String url;

        /** The user. */
        @JsonProperty("user")
        public String user;
    }

    /** The summary. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Summary {

        /** The hash rate. */
        @JsonProperty("ghsav")
        public String ghsav;

    }
}