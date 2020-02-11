package mn.foreman.xmrig.current.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/** The /2/backends response object types. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Backend {

    /** Whether or not the backend is enabled. */
    @JsonProperty("enabled")
    public boolean enabled;

    /** The threads. */
    @JsonProperty("threads")
    public List<Thread> threads;

    /** The backend type. */
    @JsonProperty("type")
    public String type;

    /** A thread. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Thread {

        /** The bus ID. */
        @JsonProperty("bus_id")
        public String busId;

        /** The hash rates. */
        @JsonProperty("hashrate")
        public List<BigDecimal> hashrates;

        /** The device health. */
        @JsonProperty("health")
        public Health health;

        /** The device index. */
        @JsonProperty("index")
        public int index;

        /** The device name. */
        @JsonProperty("name")
        public String name;

        /** Cuda device health. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Health {

            /** The core clock rate. */
            @JsonProperty("clock")
            public int clock;

            /** The fan speeds. */
            @JsonProperty("fan_speed")
            public List<Integer> fanSpeeds;

            /** The memory clock rate. */
            @JsonProperty("mem_clock")
            public int memory;

            /** The temperature. */
            @JsonProperty("temperature")
            public int temperature;
        }
    }
}
