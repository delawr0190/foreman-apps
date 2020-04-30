package mn.foreman.obelisk.json;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/** A dashboard response object. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dashboard {

    /** The hashboards. */
    @JsonProperty("hashboardStatus")
    public List<Hashboard> hashboards;

    /** The pools. */
    @JsonProperty("poolStatus")
    public List<Pool> pools;

    /** The system information. */
    @JsonProperty("systemInfo")
    public List<SystemInfo> systemInfo;

    /** A hashboard response object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hashboard {

        /** The hash rate. */
        @JsonProperty("hashrate5min")
        @JsonAlias("mhsAvg")
        public BigDecimal hashrate;

        /** The first temp sensor. */
        @JsonProperty("intakeTemp")
        @JsonAlias("boardTemp")
        public String temp1;

        /** The second temp sensor. */
        @JsonProperty("exhaustTemp")
        @JsonAlias("chipTemp")
        public String temp2;
    }

    /** A pool response object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pool {

        /** The accepted shares. */
        @JsonProperty("accepted")
        public int accepted;

        /** The rejected shares. */
        @JsonProperty("rejected")
        public int rejected;

        /** The status. */
        @JsonProperty("status")
        public String status;

        /** The pool url. */
        @JsonProperty("url")
        public String url;
    }

    /** A system info response object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SystemInfo {

        /** The object name. */
        @JsonProperty("name")
        public String name;

        /** The object value. */
        @JsonProperty("value")
        public String value;
    }
}
