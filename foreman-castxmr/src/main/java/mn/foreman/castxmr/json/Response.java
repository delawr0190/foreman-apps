package mn.foreman.castxmr.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a {@link Response} model object of a response
 * from castxmr:
 *
 * <pre>
 *     GET /
 * </pre>
 *
 * <p>The expected format of that object is:</p>
 *
 * <pre>
 * {
 *   "total_hash_rate": 1857957,
 *   "total_hash_rate_avg": 1812193,
 *   "pool": {
 *     "server": "pool.supportxmr.com:7777",
 *     "status": "connected",
 *     "online": 15,
 *     "offline": 0,
 *     "reconnects": 0,
 *     "time_connected": "2017-12-13 16:23:40",
 *     "time_disconnected": "2017-12-13 16:23:40"
 *   },
 *   "job": {
 *     "job_number": 3,
 *     "difficulty": 25000,
 *     "running": 11,
 *     "job_time_avg": 1.50
 *   },
 *   "shares": {
 *     "num_accepted": 2,
 *     "num_rejected": 0,
 *     "num_invalid": 0,
 *     "num_network_fail": 0,
 *     "num_outdated": 0,
 *     "search_time_avg": 5.00
 *   },
 *   "devices": [
 *     {
 *       "device": "GPU0",
 *       "device_id": 0,
 *       "hash_rate": 1857957,
 *       "hash_rate_avg": 1812193,
 *       "gpu_temperature": 40,
 *       "gpu_fan_rpm": 3690
 *     },
 *     {
 *       "device": "GPU1",
 *       "device_id": 1,
 *       "hash_rate": 1857957,
 *       "hash_rate_avg": 1812193,
 *       "gpu_temperature": 41,
 *       "gpu_fan_rpm": 3691
 *     },
 *     {
 *       "device": "GPU2",
 *       "device_id": 2,
 *       "hash_rate": 1857957,
 *       "hash_rate_avg": 1812193,
 *       "gpu_temperature": 42,
 *       "gpu_fan_rpm": 3692
 *     },
 *     {
 *       "device": "GPU3",
 *       "device_id": 3,
 *       "hash_rate": 1857957,
 *       "hash_rate_avg": 1812193,
 *       "gpu_temperature": 43,
 *       "gpu_fan_rpm": 3693
 *     },
 *     {
 *       "device": "GPU4",
 *       "device_id": 4,
 *       "hash_rate": 1857957,
 *       "hash_rate_avg": 1812193,
 *       "gpu_temperature": 44,
 *       "gpu_fan_rpm": 3694
 *     },
 *     {
 *       "device": "GPU5",
 *       "device_id": 5,
 *       "hash_rate": 1857957,
 *       "hash_rate_avg": 1812193,
 *       "gpu_temperature": 45,
 *       "gpu_fan_rpm": 3695
 *     }
 *   ]
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The devices. */
    @JsonProperty("devices")
    public List<Device> devices;

    /** The hash rate. */
    @JsonProperty("total_hash_rate")
    public BigDecimal hashRate;

    /** The pool. */
    @JsonProperty("pool")
    public Pool pool;

    /** The shares. */
    @JsonProperty("shares")
    public Shares shares;

    /** A model object representing the device JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Device {

        /** The device. */
        @JsonProperty("device")
        public String device;

        /** The device ID. */
        @JsonProperty("device_id")
        public int deviceId;

        /** The fan RPM. */
        @JsonProperty("gpu_fan_rpm")
        public int fanRpm;

        /** The GPU temperatures. */
        @JsonProperty("gpu_temperature")
        public int temperature;
    }

    /** A model object representing the pool JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pool {

        /** The server. */
        @JsonProperty("server")
        public String server;

        /** The status. */
        @JsonProperty("status")
        public String status;
    }

    /** A model object representing the shares JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Shares {

        /** The number of accepted shares. */
        @JsonProperty("num_accepted")
        public int accepted;

        /** The number of invalid shares. */
        @JsonProperty("num_invalid")
        public int invalid;

        /** The number of rejected shares. */
        @JsonProperty("num_rejected")
        public int rejected;
    }
}