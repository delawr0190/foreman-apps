package mn.foreman.openminer.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Map;

/** The aggregated stats. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Agg {

    /** The fans. */
    @JsonProperty("fansRpm")
    public List<Integer> fans;

    /** The MAC address. */
    @JsonProperty("mac")
    public String mac;

    /** The pool. */
    @JsonProperty("pool")
    public Pool pool;

    /** The power config. */
    @JsonProperty("pwr")
    public Power power;

    /** The board slots. */
    @JsonProperty("slots")
    @JsonDeserialize(using = SlotDeserializer.class)
    public Map<String, Slot> slots;

    /** The connected pool. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pool {

        /** The host. */
        @JsonProperty("host")
        public String host;

        /** The intervals. */
        @JsonProperty("intervals")
        public Map<String, Shares> intervals;

        /** The port. */
        @JsonProperty("port")
        public String port;

        /** The username. */
        @JsonProperty("userName")
        public String username;

        /** The pool shares. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Shares {

            /** The accepted shares. */
            @JsonProperty("sharesAccepted")
            public String accepted;

            /** The rejected shares. */
            @JsonProperty("sharesRejected")
            public String rejected;

            /** The stale shares. */
            @JsonProperty("staleJobShares")
            public String stale;
        }
    }

    /** The power. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Power {

        /** The power state. */
        @JsonProperty("state")
        public String state;
    }

    /** A slot. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Slot {

        /** The hash rate. */
        @JsonProperty("ghs")
        public String ghs;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temp0;

        /** The temperature1. */
        @JsonProperty("temperature1")
        public int temp1;
    }
}
