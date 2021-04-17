package mn.foreman.epic.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a {@link Summary} model object of a response
 * from epic.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Summary {

    /** The fans. */
    @JsonProperty("Fans")
    public Fans fans;

    /** The hash boards. */
    @JsonProperty("HBs")
    public List<Hashboard> hashboards;

    /** The hostname. */
    @JsonProperty("Hostname")
    public String hostname;

    /** The power mode. */
    @JsonProperty("Preset")
    public String preset;

    /** The session. */
    @JsonProperty("Session")
    public Session session;

    /** The stratum. */
    @JsonProperty("Stratum")
    public Stratum stratum;

    /** The fans. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Fans {

        /** The fan speed. */
        @JsonProperty("Fans Speed")
        public int fanSpeed;
    }

    /** The hashboard. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hashboard {

        /** The temperature. */
        @JsonProperty("Temperature")
        public int temp;
    }

    /** The session. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Session {

        /** The accepted shares. */
        @JsonProperty("Accepted")
        public int accepted;

        /** The active boards. */
        @JsonProperty("Active HBs")
        public int activeBoards;

        /** The hash rate. */
        @JsonProperty("Average MHs")
        public BigDecimal hashrateMhs;

        /** The rejected shares. */
        @JsonProperty("Rejected")
        public int rejected;
    }

    /** The stratum. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Stratum {

        /** The pool. */
        @JsonProperty("Current Pool")
        public String pool;

        /** The user. */
        @JsonProperty("Current User")
        public String user;
    }
}