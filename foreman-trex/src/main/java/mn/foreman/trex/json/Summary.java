package mn.foreman.trex.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a {@link Summary} model object of a response
 * from t-rex:
 *
 * <pre>
 *     GET /summary
 * </pre>
 *
 * <p>The expected format of that object is either:</p>
 *
 * <pre>
 * {
 *   "accepted_count": 44,
 *   "active_pool": {
 *     "retries": 0,
 *     "url": "stratum+tcp://blockmasters.co:4553",
 *     "user": "DMbUXFZfpHpzEGjHMfJivjgNXswmrsdaqB"
 *   },
 *   "algorithm": "lyra2z",
 *   "api": "1.0",
 *   "cuda": "9.20",
 *   "description": "T-Rex NVIDIA GPU miner",
 *   "difficulty": 53.78282292371265,
 *   "gpu_total": 8,
 *   "gpus": [
 *     {
 *       "device_id": 0,
 *       "gpu_id": 0,
 *       "hashrate": 3059637,
 *       "intensity": 20
 *     },
 *     {
 *       "device_id": 1,
 *       "gpu_id": 1,
 *       "hashrate": 3036386,
 *       "intensity": 20
 *     },
 *     {
 *       "device_id": 2,
 *       "gpu_id": 2,
 *       "hashrate": 3033945,
 *       "intensity": 20
 *     },
 *     {
 *       "device_id": 3,
 *       "gpu_id": 3,
 *       "hashrate": 3039842,
 *       "intensity": 20
 *     },
 *     {
 *       "device_id": 4,
 *       "gpu_id": 4,
 *       "hashrate": 3036523,
 *       "intensity": 20
 *     },
 *     {
 *       "device_id": 5,
 *       "gpu_id": 5,
 *       "hashrate": 3074756,
 *       "intensity": 20
 *     },
 *     {
 *       "device_id": 6,
 *       "gpu_id": 6,
 *       "hashrate": 3053700,
 *       "intensity": 20
 *     },
 *     {
 *       "device_id": 7,
 *       "gpu_id": 7,
 *       "hashrate": 2966131,
 *       "intensity": 20
 *     }
 *   ],
 *   "hashrate": 24300920,
 *   "name": "t-rex",
 *   "os": "win",
 *   "rejected_count": 0,
 *   "ts": 1533090180,
 *   "uptime": 290,
 *   "version": "0.5.6"
 * }
 * </pre>
 *
 * OR:
 *
 * <pre>
 * {
 *   "accepted_count": 10,
 *   "active_pool": {
 *     "difficulty": 8,
 *     "ping": 140,
 *     "retries": 0,
 *     "url": "stratum+tcp://us.ravenminer.com:4567",
 *     "user": "xxxx"
 *   },
 *   "algorithm": "x16r",
 *   "api": "1.1",
 *   "cuda": "9.20",
 *   "description": "T-Rex NVIDIA GPU miner",
 *   "difficulty": 32129.671888325327,
 *   "gpu_total": 8,
 *   "gpus": [
 *     {
 *       "HW": 98971.9378238342,
 *       "device_id": 0,
 *       "fan_speed": 56,
 *       "gpu_id": 0,
 *       "hashrate": 19101584,
 *       "hashrate_day": 0,
 *       "hashrate_hour": 0,
 *       "hashrate_minute": 19257829,
 *       "intensity": 20,
 *       "name": "GeForce GTX 1070 Ti",
 *       "power": 193,
 *       "temperature": 57,
 *       "vendor": "EVGA"
 *     },
 *     {
 *       "HW": 96907.94329896907,
 *       "device_id": 1,
 *       "fan_speed": 52,
 *       "gpu_id": 1,
 *       "hashrate": 18800141,
 *       "hashrate_day": 0,
 *       "hashrate_hour": 0,
 *       "hashrate_minute": 18770078,
 *       "intensity": 20,
 *       "name": "GeForce GTX 1070 Ti",
 *       "power": 194,
 *       "temperature": 53,
 *       "vendor": "EVGA"
 *     },
 *     {
 *       "HW": 97651.11917098446,
 *       "device_id": 2,
 *       "fan_speed": 56,
 *       "gpu_id": 2,
 *       "hashrate": 18846666,
 *       "hashrate_day": 0,
 *       "hashrate_hour": 0,
 *       "hashrate_minute": 18902532,
 *       "intensity": 20,
 *       "name": "GeForce GTX 1070 Ti",
 *       "power": 193,
 *       "temperature": 57,
 *       "vendor": "EVGA"
 *     },
 *     {
 *       "HW": 95317.5,
 *       "device_id": 3,
 *       "fan_speed": 53,
 *       "gpu_id": 3,
 *       "hashrate": 18872865,
 *       "hashrate_day": 0,
 *       "hashrate_hour": 0,
 *       "hashrate_minute": 18925389,
 *       "intensity": 20,
 *       "name": "GeForce GTX 1070 Ti",
 *       "power": 198,
 *       "temperature": 55,
 *       "vendor": "EVGA"
 *     },
 *     {
 *       "HW": 100180.74074074074,
 *       "device_id": 4,
 *       "fan_speed": 55,
 *       "gpu_id": 4,
 *       "hashrate": 18934160,
 *       "hashrate_day": 0,
 *       "hashrate_hour": 0,
 *       "hashrate_minute": 19111002,
 *       "intensity": 20,
 *       "name": "GeForce GTX 1070 Ti",
 *       "power": 189,
 *       "temperature": 56,
 *       "vendor": "EVGA"
 *     },
 *     {
 *       "HW": 99529.24736842106,
 *       "device_id": 5,
 *       "fan_speed": 56,
 *       "gpu_id": 5,
 *       "hashrate": 18910557,
 *       "hashrate_day": 0,
 *       "hashrate_hour": 0,
 *       "hashrate_minute": 18905498,
 *       "intensity": 20,
 *       "name": "GeForce GTX 1070 Ti",
 *       "power": 190,
 *       "temperature": 57,
 *       "vendor": "EVGA"
 *     },
 *     {
 *       "HW": 97652.86082474227,
 *       "device_id": 6,
 *       "fan_speed": 53,
 *       "gpu_id": 6,
 *       "hashrate": 18944655,
 *       "hashrate_day": 0,
 *       "hashrate_hour": 0,
 *       "hashrate_minute": 18968503,
 *       "intensity": 20,
 *       "name": "GeForce GTX 1070 Ti",
 *       "power": 194,
 *       "temperature": 54,
 *       "vendor": "EVGA"
 *     },
 *     {
 *       "HW": 100744.32044198895,
 *       "device_id": 7,
 *       "fan_speed": 57,
 *       "gpu_id": 7,
 *       "hashrate": 18234722,
 *       "hashrate_day": 0,
 *       "hashrate_hour": 0,
 *       "hashrate_minute": 18167319,
 *       "intensity": 20,
 *       "name": "GeForce GTX 1070 Ti",
 *       "power": 181,
 *       "temperature": 58,
 *       "vendor": "EVGA"
 *     }
 *   ],
 *   "hashrate": 150645350,
 *   "hashrate_day": 0,
 *   "hashrate_hour": 0,
 *   "hashrate_minute": 151008150,
 *   "name": "t-rex",
 *   "os": "win",
 *   "rejected_count": 0,
 *   "ts": 1535896052,
 *   "uptime": 31,
 *   "version": "0.6.3"
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Summary {

    /** The pool accept count. */
    @JsonProperty("accepted_count")
    public int acceptCount;

    /** The active pool. */
    @JsonProperty("active_pool")
    public ActivePool activePool;

    /** The number of GPUs. */
    @JsonProperty("gpu_total")
    public int gpuTotal;

    /** The gpus. */
    @JsonProperty("gpus")
    public List<Gpu> gpus;

    /** The hash rate. */
    @JsonProperty("hashrate")
    public BigDecimal hashRate;

    /** The rejected count. */
    @JsonProperty("rejected_count")
    public int rejectedCount;

    /** The uptime. */
    @JsonProperty("uptime")
    public int uptime;

    /** A model object representing the active_pool JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ActivePool {

        /** The URL. */
        @JsonProperty("url")
        public String url;
    }

    /** A model object representing the gpu JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Gpu {

        /** The device ID. */
        @JsonProperty("device_id")
        public int deviceId;

        /** The hash rate. */
        @JsonProperty("hashrate")
        public BigDecimal hashRate;

        /** The fan speed. */
        @JsonProperty("fan_speed")
        public int fanSpeed;

        /** The name. */
        @JsonProperty("name")
        public String name;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;
    }
}