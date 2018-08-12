package mn.foreman.xmrig.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a model of a response from xmrig:
 *
 * <pre>
 * GET /
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "id": "83de12bb926e280b",
 *   "worker_id": "DESKTOP-P3EFAF7",
 *   "version": "2.6.1",
 *   "kind": "nvidia",
 *   "ua": "XMRig/2.6.1 (Windows NT 10.0; Win64; x64) libuv/1.19.2 CUDA/9.10
 * msvc/2015",
 *   "cpu": {
 *     "brand": "Intel(R) Core(TM) i7-7700K CPU @ 4.20GHz",
 *     "aes": true,
 *     "x64": true,
 *     "sockets": 1
 *   },
 *   "algo": "cryptonight",
 *   "hugepages": false,
 *   "donate_level": 5,
 *   "hashrate": {
 *     "total": [
 *       3522.59,
 *       3526.79,
 *       0.0
 *     ],
 *     "highest": 3557.57,
 *     "threads": [
 *       [
 *         477.26,
 *         479.49,
 *         0.0
 *       ],
 *       [
 *         433.49,
 *         439.47,
 *         0.0
 *       ],
 *       [
 *         506.3,
 *         504.16,
 *         0.0
 *       ],
 *       [
 *         471.32,
 *         468.27,
 *         0.0
 *       ],
 *       [
 *         449.21,
 *         444.38,
 *         0.0
 *       ],
 *       [
 *         449.26,
 *         446.61,
 *         0.0
 *       ],
 *       [
 *         449.4,
 *         446.04,
 *         0.0
 *       ],
 *       [
 *         286.31,
 *         298.34,
 *         0.0
 *       ]
 *     ]
 *   },
 *   "health": [
 *     {
 *       "name": "GeForce GTX 1070 Ti",
 *       "clock": 1961,
 *       "mem_clock": 4158,
 *       "power": 93,
 *       "temp": 54,
 *       "fan": 53
 *     },
 *     {
 *       "name": "GeForce GTX 1070 Ti",
 *       "clock": 1936,
 *       "mem_clock": 4158,
 *       "power": 76,
 *       "temp": 50,
 *       "fan": 50
 *     },
 *     {
 *       "name": "GeForce GTX 1070 Ti",
 *       "clock": 1936,
 *       "mem_clock": 4158,
 *       "power": 77,
 *       "temp": 50,
 *       "fan": 50
 *     },
 *     {
 *       "name": "GeForce GTX 1070 Ti",
 *       "clock": 1936,
 *       "mem_clock": 4158,
 *       "power": 76,
 *       "temp": 50,
 *       "fan": 50
 *     },
 *     {
 *       "name": "GeForce GTX 1070 Ti",
 *       "clock": 1936,
 *       "mem_clock": 4158,
 *       "power": 75,
 *       "temp": 51,
 *       "fan": 51
 *     },
 *     {
 *       "name": "GeForce GTX 1070 Ti",
 *       "clock": 1949,
 *       "mem_clock": 4158,
 *       "power": 77,
 *       "temp": 52,
 *       "fan": 52
 *     },
 *     {
 *       "name": "GeForce GTX 1070 Ti",
 *       "clock": 1936,
 *       "mem_clock": 4158,
 *       "power": 76,
 *       "temp": 52,
 *       "fan": 52
 *     },
 *     {
 *       "name": "GeForce GTX 1070 Ti",
 *       "clock": 1949,
 *       "mem_clock": 4158,
 *       "power": 53,
 *       "temp": 50,
 *       "fan": 50
 *     }
 *   ],
 *   "results": {
 *     "diff_current": 80761,
 *     "shares_good": 4,
 *     "shares_total": 4,
 *     "avg_time": 19,
 *     "hashes_total": 207331,
 *     "best": [
 *       717724,
 *       93840,
 *       91003,
 *       49818,
 *       0,
 *       0,
 *       0,
 *       0,
 *       0,
 *       0
 *     ],
 *     "error_log": []
 *   },
 *   "connection": {
 *     "pool": "pool.supportxmr.com:7777",
 *     "uptime": 79,
 *     "ping": 98,
 *     "failures": 0,
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

    /** The health (only present in xmrig-nvidia). */
    @JsonProperty("health")
    public List<Health> health;

    /** The results. */
    @JsonProperty("results")
    public Results results;

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

    /** Provides a model representation of the {@link Health} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Health {

        /** The clock. */
        @JsonProperty("clock")
        public int clock;

        /** The fan percentage. */
        @JsonProperty("fan")
        public int fan;

        /** The memory clock. */
        @JsonProperty("mem_clock")
        public int memClock;

        /** The name. */
        @JsonProperty("name")
        public String name;

        /** The temp. */
        @JsonProperty("temp")
        public int temp;
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