package mn.foreman.nbminer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a model object of a response from nbminer:
 *
 * <pre>
 *     GET /api/v1/status
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "miner": {
 *     "devices": [
 *       {
 *         "core_clock": 1556,
 *         "core_utilization": 100,
 *         "fan": 36,
 *         "hashrate": 1499,
 *         "hashrate2": "23.0 M",
 *         "hashrate_raw": 1499,
 *         "hashrate2_raw": 23030000,
 *         "id": 0,
 *         "info": "GeForce GTX 1080 Ti 11178 MB",
 *         "power": 182,
 *         "temperature": 65
 *       },
 *       {
 *         "core_clock": 1518,
 *         "core_utilization": 100,
 *         "fan": 34,
 *         "hashrate": 1490,
 *         "id": 1,
 *         "info": "GeForce GTX 1080 Ti 11178 MB",
 *         "power": 172,
 *         "temperature": 62
 *       }
 *     ],
 *     "total_hashrate": 2989,
 *     "total_hashrate_raw": 2989,
 *     "total_hashrate2": "48.3 M",
 *     "total_hashrate2_raw": 48308746,
 *     "total_power_consume": 354
 *   },
 *   "start_time": 1532482659,
 *   "stratum": {
 *     "accepted_share_rate": 0.99,
 *     "accepted_shares": 99,
 *     "password": "",
 *     "rejected_share_rate": 0.01,
 *     "rejected_shares": 1,
 *     "url": "btm.pool.zhizh.com:3859",
 *     "use_ssl": false,
 *     "user": "bmxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.test",
 *     "difficulty": "0003ffff",
 *     "latency": 65
 *   },
 *   "version": "v10.0"
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The miner. */
    @JsonProperty("miner")
    public Miner miner;

    /** The stratum. */
    @JsonProperty("stratum")
    public Stratum stratum;

    /** A device object representation. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Device {

        /** The core clock. */
        @JsonProperty("core_clock")
        public int coreClock;

        /** The fan speed. */
        @JsonProperty("fan")
        public int fanSpeed;

        /** The ID. */
        @JsonProperty("id")
        public int id;

        /** The name. */
        @JsonProperty("info")
        public String name;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;
    }

    /** A miner object representation. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Miner {

        /** The devices. */
        @JsonProperty("devices")
        public List<Device> devices;

        /** The hashrate. */
        @JsonProperty("total_hashrate_raw")
        public BigDecimal hashrate;
    }

    /** A stratum object representation. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Stratum {

        /** The accepted shares. */
        @JsonProperty("accepted_shares")
        public int acceptedShares;

        /** The rejected shares. */
        @JsonProperty("rejected_shares")
        public int rejectedShares;

        /** The stratum url. */
        @JsonProperty("url")
        public String url;
    }
}