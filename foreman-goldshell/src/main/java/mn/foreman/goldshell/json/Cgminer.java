package mn.foreman.goldshell.json;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/** A response from <code>/mcb/cgminer??cgminercmd=devs</code>. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cgminer {

    /** The data. */
    @JsonProperty("data")
    public List<Data> datas;

    /** The data object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        /** The accepted shares. */
        @JsonProperty("accepted")
        public int accepted;

        /** The fan speed. */
        @JsonProperty("fanspeed")
        public String fans;

        /** The hashrate. */
        @JsonProperty("hashrate")
        public BigDecimal hashrate;

        /** The rejected shares. */
        @JsonProperty("rejected")
        public int rejected;

        /** The miner status. */
        @JsonProperty("minerstatus")
        public int status;

        /** The temp. */
        @JsonProperty("temp")
        @JsonAlias("r_temp")
        public String temp;
    }
}
