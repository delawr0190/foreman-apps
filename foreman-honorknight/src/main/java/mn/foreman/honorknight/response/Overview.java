package mn.foreman.honorknight.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** An overview model object. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Overview {

    /** The miner info. */
    @JsonProperty("minerInfo")
    public MinerInfo minerInfo;

    /** The miner info object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MinerInfo {

        /** The mac address. */
        @JsonProperty("macAddress")
        public String mac;

        /** The model. */
        @JsonProperty("model")
        public String model;
    }
}
