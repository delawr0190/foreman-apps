package mn.foreman.srbminer.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a model of a response from srbminer:
 *
 * <pre>
 *     GET /
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "rig_name": "SRBMiner-Rig",
 *   "cryptonight_type": "normalv7",
 *   "mining_time": 10,
 *   "total_devices": 1,
 *   "total_threads": 2,
 *   "hashrate_total_now": 0,
 *   "hashrate_total_1min": 0,
 *   "hashrate_total_5min": 0,
 *   "hashrate_total_30min": 0,
 *   "hashrate_total_max": 0,
 *   "pool": {
 *     "pool": "xmr-eu1.nanopool.org:14444",
 *     "difficulty": 120001,
 *     "time_connected": "2018-07-29 22:12:41",
 *     "uptime": 10,
 *     "latency": 0,
 *     "last_job_received": 5
 *   },
 *   "shares": {
 *     "total": 0,
 *     "accepted": 0,
 *     "accepted_stale": 0,
 *     "rejected": 0,
 *     "avg_find_time": 0
 *   },
 *   "devices": [
 *     {
 *       "device": "GPU0",
 *       "device_id": 0,
 *       "model": "Radeon RX 560 Series",
 *       "bus_id": 2,
 *       "kernel_id": 1,
 *       "hashrate": 0,
 *       "core_clock": 1187,
 *       "memory_clock": 1500,
 *       "temperature": 44,
 *       "fan_speed_rpm": 1538
 *     }
 *   ]
 * }
 * </pre>
 */
public class Response {

    /** The cryptonight type. */
    @JsonProperty("cryptonight_type")
    public String cryptonightType;

    /** The devices. */
    @JsonProperty("devices")
    public List<Device> devices;

    /** The current hash rate. */
    @JsonProperty("hashrate_total_now")
    public BigDecimal hashRate;

    /** The 1 min hash rate. */
    @JsonProperty("hashrate_total_1min")
    public BigDecimal hashRate1Min;

    /** The 30 min hash rate. */
    @JsonProperty("hashrate_total_30min")
    public BigDecimal hashRate30Min;

    /** The 5 min hash rate. */
    @JsonProperty("hashrate_total_5min")
    public BigDecimal hashRate5Min;

    /** The max hash rate. */
    @JsonProperty("hashrate_total_max")
    public BigDecimal hashRateMax;

    /** The mining time. */
    @JsonProperty("mining_time")
    public int miningTime;

    /** The pool. */
    @JsonProperty("pool")
    public Pool pool;

    /** The rig name. */
    @JsonProperty("rig_name")
    public String rigName;

    /** The shares. */
    @JsonProperty("shares")
    public Shares shares;

    /** The total devices. */
    @JsonProperty("total_devices")
    public int totalDevices;

    /** The total threads. */
    @JsonProperty("total_threads")
    public int totalThreads;

    /** A model object of the JSON device object. */
    public static class Device {

        /** The bus id. */
        @JsonProperty("bus_id")
        public int busId;

        /** The core clock. */
        @JsonProperty("core_clock")
        public int coreClock;

        /** The device. */
        @JsonProperty("device")
        public String device;

        /** The device id. */
        @JsonProperty("device_id")
        public int deviceId;

        /** The fan speed (RPMs). */
        @JsonProperty("fan_speed_rpm")
        public int fanSpeedRpm;

        /** The hash rate. */
        @JsonProperty("hashrate")
        public long hashRate;

        /** The kernel id. */
        @JsonProperty("kernel_id")
        public int kernelId;

        /** The memory clock. */
        @JsonProperty("memory_clock")
        public int memoryClock;

        /** The model. */
        @JsonProperty("model")
        public String model;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;
    }

    /** A model object of the JSON pool object. */
    public static class Pool {

        /** The difficulty. */
        @JsonProperty("difficulty")
        public long difficulty;

        /** The last job received. */
        @JsonProperty("last_job_received")
        public int lastJobReceived;

        /** The latency. */
        @JsonProperty("latency")
        public int latency;

        /** The pool. */
        @JsonProperty("pool")
        public String pool;

        /** The time connected. */
        @JsonProperty("time_connected")
        public String timeConnected;

        /** The uptime. */
        @JsonProperty("uptime")
        public int uptime;
    }

    /** A model object representation of the shares JSON object. */
    public static class Shares {

        /** The total accepted. */
        @JsonProperty("accepted")
        public int accepted;

        /** The total stale. */
        @JsonProperty("accepted_stale")
        public int acceptedStale;

        /** The average share find time. */
        @JsonProperty("avg_find_time")
        public int avgFindTime;

        /** The total rejected. */
        @JsonProperty("rejected")
        public int rejected;

        /** The total shares. */
        @JsonProperty("total")
        public int total;
    }
}