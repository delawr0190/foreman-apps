package mn.foreman.xmrstak.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a model of a response from xmrstak:
 *
 * <pre>
 * GET /api.json
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "version": "xmr-stak/2.4.7/c5f0505d/master/win/nvidia-amd-cpu/20",
 *   "hashrate": {
 *     "threads": [
 *       [
 *         322.8,
 *         321.6,
 *         null
 *       ],
 *       [
 *         13.4,
 *         13.6,
 *         null
 *       ],
 *       [
 *         13.4,
 *         13.6,
 *         null
 *       ],
 *       [
 *         13.4,
 *         13.6,
 *         null
 *       ],
 *       [
 *         13.8,
 *         14,
 *         null
 *       ]
 *     ],
 *     "total": [
 *       376.8,
 *       376.6,
 *       null
 *     ],
 *     "highest": 385.5
 *   },
 *   "results": {
 *     "diff_current": 120001,
 *     "shares_good": 0,
 *     "shares_total": 1,
 *     "avg_time": 272,
 *     "hashes_total": 0,
 *     "best": [
 *       0,
 *       0,
 *       0,
 *       0,
 *       0,
 *       0,
 *       0,
 *       0,
 *       0,
 *       0
 *     ],
 *     "error_log": [
 *       {
 *         "count": 1,
 *         "last_seen": 1533769528,
 *         "text": "Low difficulty share"
 *       }
 *     ]
 *   },
 *   "connection": {
 *     "pool": "xmr-us-east1.nanopool.org:14444",
 *     "uptime": 272,
 *     "ping": 0,
 *     "error_log": []
 *   }
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    /** The connection. */
    @JsonProperty("connection")
    public Connection connection;

    /** The hash rate. */
    @JsonProperty("hashrate")
    public HashRate hashrate;

    /** The results. */
    @JsonProperty("results")
    public Results results;

    /** The version. */
    @JsonProperty("version")
    public String version;

    /** Provides a model representation of the {@link Connection} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Connection {

        /** The pool. */
        @JsonProperty("pool")
        public String pool;

        /** The uptime. */
        @JsonProperty("uptime")
        public long uptime;
    }

    /** Provides a model representation of the {@link HashRate} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HashRate {

        /** The thread rates. */
        @JsonProperty("threads")
        public List<List<Integer>> threads;

        /** The totals. */
        @JsonProperty("total")
        public List<BigDecimal> totals;
    }

    /** Provides a model representation of the {@link Results} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Results {

        /** The total number of good shares. */
        @JsonProperty("shares_good")
        public int sharesGood;

        /** The total number of shares. */
        @JsonProperty("shares_total")
        public int sharesTotal;
    }
}