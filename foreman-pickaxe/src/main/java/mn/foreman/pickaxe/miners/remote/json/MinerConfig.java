package mn.foreman.pickaxe.miners.remote.json;

import mn.foreman.pickaxe.miners.remote.ApiType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A {@link MinerConfig} provides a model object representation of a FOREMAN
 * dashboard miner configuration.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MinerConfig {

    /** The API IP. */
    @JsonProperty("apiIp")
    public String apiIp;

    /** The API port. */
    @JsonProperty("apiPort")
    public int apiPort;

    /** The API type. */
    @JsonProperty("apiType")
    public ApiType apiType;

    /** The name. */
    @JsonProperty("name")
    public String name;

    /** The parameters. */
    @JsonProperty("params")
    public List<Param> params;

    /** A miner configuration parameter. */
    public static class Param {

        /** The key. */
        @JsonProperty("key")
        public String key;

        /** The value. */
        @JsonProperty("value")
        public String value;
    }
}
