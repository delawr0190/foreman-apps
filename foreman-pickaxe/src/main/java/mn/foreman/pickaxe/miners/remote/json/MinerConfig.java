package mn.foreman.pickaxe.miners.remote.json;

import mn.foreman.pickaxe.miners.remote.ApiType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A {@link MinerConfig} provides a model object representation of a FOREMAN
 * dashboard miner configuration.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    /** The chisel configuration. */
    @JsonProperty("chisel")
    public ChiselConfig chisel;

    /** The parameters. */
    @JsonProperty("params")
    public List<Param> params;

    /** The raw stats that have been whitelisted. */
    @JsonProperty("statsWhitelist")
    public List<String> statsWhitelist;

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "apiIp=%s, " +
                        "apiPort=%d, " +
                        "apiType=%s, " +
                        "params=[%s], " +
                        "chisel=%s, " +
                        "statesWhitelist=%s " +
                        "]",
                getClass().getSimpleName(),
                this.apiIp,
                this.apiPort,
                this.apiType,
                this.params,
                this.chisel,
                this.statsWhitelist);
    }

    /** A chisel configuration. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChiselConfig {

        /** The port where chisel is listening. */
        @JsonProperty("apiPort")
        public int apiPort;

        @Override
        public String toString() {
            return String.format("%s [ apiPort=%d ]",
                    getClass().getSimpleName(),
                    this.apiPort);
        }
    }

    /** A miner configuration parameter. */
    public static class Param {

        /** The key. */
        @JsonProperty("key")
        public String key;

        /** The value. */
        @JsonProperty("value")
        public Object value;

        @Override
        public String toString() {
            return String.format("%s [ key=%s, value=%s ]",
                    getClass().getSimpleName(),
                    this.key,
                    this.value);
        }
    }
}
