package mn.foreman.trex.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * The following model provides a {@link Summary} model object of a response
 * from t-rex:
 *
 * <pre>
 *     GET /summary
 * </pre>
 *
 * <p>The expected format of that object is:</p>
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

    /** The hash rate. */
    @JsonProperty("hashrate")
    public BigDecimal hashRate;

    /** The name. */
    @JsonProperty("name")
    public String name;

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
}