package mn.foreman.optiminer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

/**
 * The following model provides a {@link Stratum} model object of a response
 * from optiminer:
 *
 * <pre>
 *     GET /
 * </pre>
 *
 * <p>The expected format of that object is:</p>
 *
 * <pre>
 * {
 *   "iteration_rate" : {
 *     "GPU0" : {
 *       "5s" : 50,
 *       "60s" : 51.083333333333336
 *     },
 *     "Total" : {
 *       "5s" : 50,
 *       "60s" : 51.083333333333336
 *     }
 *   },
 *   "os" : "windows",
 *   "share" : {
 *     "accepted" : 2,
 *     "rejected" : 0
 *   },
 *   "solution_rate" : {
 *     "GPU0" : {
 *       "5s" : 86.799999999999997,
 *       "60s" : 95.400000000000006
 *     },
 *     "Total" : {
 *       "5s" : 86.799999999999997,
 *       "60s" : 95.400000000000006
 *     }
 *   },
 *   "stratum" : {
 *     "connected" : true,
 *     "connection_failures" : 0,
 *     "host" : "eu1-zcash.flypool.org",
 *     "port" : 3443,
 *     "target" : "0000aec33e1f671529a485cd7b900aec33e1f671529a485cd7b900aec33e1f67"
 *   },
 *   "uptime" : 272,
 *   "version" : "1.7.0"
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats {

    /** The pool shares. */
    @JsonProperty("share")
    public Share share;

    /** The solution rate. */
    @JsonProperty("solution_rate")
    public Map<String, Map<String, BigDecimal>> solutionRates;

    /** The stratum connection. */
    @JsonProperty("stratum")
    public Stratum stratum;

    /** A model object representation of the share JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Share {

        /** The total number of accepted shares. */
        @JsonProperty("accepted")
        public int accepted;

        /** The total number of rejected shares. */
        @JsonProperty("rejected")
        public int rejected;
    }

    /** A model object representation of the stratum JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Stratum {

        /** Whether or not connected to the stratum. */
        @JsonProperty("connected")
        public boolean connected;

        /** The host. */
        @JsonProperty("host")
        public String host;

        /** The port. */
        @JsonProperty("port")
        public int port;
    }
}