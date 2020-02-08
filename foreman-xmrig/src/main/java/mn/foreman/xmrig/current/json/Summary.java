package mn.foreman.xmrig.current.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a model of a response from xmrig:
 *
 * <pre>
 * GET /1/summary
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *     "id": "bbcf7f6893360fb3",
 *     "worker_id": "LAPTOP-I9PPS5BU",
 *     "uptime": 71,
 *     "restricted": true,
 *     "resources": {
 *         "memory": {
 *             "free": 6621306880,
 *             "total": 17047015424,
 *             "resident_set_memory": 2334564352
 *         },
 *         "load_average": [0.0, 0.0, 0.0],
 *         "hardware_concurrency": 12
 *     },
 *     "features": ["api", "asm", "http", "hwloc", "tls", "opencl", "cuda"],
 *     "results": {
 *         "diff_current": 1000225,
 *         "shares_good": 0,
 *         "shares_total": 0,
 *         "avg_time": 0,
 *         "hashes_total": 0,
 *         "best": [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
 *         "error_log": []
 *     },
 *     "algo": "rx/0",
 *     "connection": {
 *         "pool": "donate.v2.xmrig.com:3333",
 *         "ip": "178.128.242.134",
 *         "uptime": 71,
 *         "ping": 0,
 *         "failures": 0,
 *         "tls": null,
 *         "tls-fingerprint": null,
 *         "error_log": []
 *     },
 *     "version": "5.5.3",
 *     "kind": "miner",
 *     "ua": "XMRig/5.5.3 (Windows NT 10.0; Win64; x64) libuv/1.34.0 msvc/2019",
 *     "cpu": {
 *         "brand": "Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz",
 *         "aes": true,
 *         "avx2": true,
 *         "x64": true,
 *         "l2": 1572864,
 *         "l3": 9437184,
 *         "cores": 6,
 *         "threads": 12,
 *         "packages": 1,
 *         "nodes": 1,
 *         "backend": "hwloc/2.1.0",
 *         "assembly": "intel"
 *     },
 *     "donate_level": 5,
 *     "paused": false,
 *     "algorithms": ["cn/1", "cn/2", "cn/r", "cn/fast", "cn/half", "cn/xao", "cn/rto", "cn/rwz", "cn/zls", "cn/double", "cn/gpu", "cn-lite/1", "cn-heavy/0", "cn-heavy/tube", "cn-heavy/xhv", "cn-pico", "cn-pico/tlo", "rx/0", "rx/wow", "rx/loki", "rx/arq", "rx/sfx", "argon2/chukwa", "argon2/wrkz"],
 *     "hashrate": {
 *         "total": [899.09, 953.1, null],
 *         "highest": 1100.45,
 *         "threads": [
 *             [147.4, 162.68, null],
 *             [144.15, 164.59, null],
 *             [147.93, 166.77, null],
 *             [153.16, 172.54, null],
 *             [148.21, 162.76, null],
 *             [158.22, 123.74, null]
 *         ]
 *     },
 *     "hugepages": false
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Summary {

    /** The connection. */
    @JsonProperty("connection")
    public Connection connection;

    /** The hash rate. */
    @JsonProperty("hashrate")
    public HashRate hashrate;

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