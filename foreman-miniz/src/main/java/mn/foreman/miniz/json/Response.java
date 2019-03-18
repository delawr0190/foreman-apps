package mn.foreman.miniz.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The following model provides a model of a response from dstm:
 *
 * <pre>
 * {"id":1,"method":"getstat"}
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "id": 0,
 *   "method": "getstat",
 *   "error": null,
 *   "start_time": 0,
 *   "current_server": "us-btg.2miners.com:4040",
 *   "server_latency": 22.1,
 *   "available_servers": 1,
 *   "server_status": 1,
 *   "result": [
 *     {
 *       "gpuid": 0,
 *       "cudaid": 0,
 *       "busid": "busid",
 *       "name": "GeForce GTX 1050 Ti",
 *       "gpu_status": 2,
 *       "solver": -1,
 *       "temperature": 46,
 *       "gpu_fan_speed": 0,
 *       "gpu_power_usage": 0,
 *       "gpu_clock_core_max": 278,
 *       "gpu_clock_memory": 2504,
 *       "speed_sps": 0,
 *       "accepted_shares": 0,
 *       "rejected_shares": 0,
 *       "start_time": 0
 *     }
 *   ]
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The results. */
    @JsonProperty("result")
    public List<Result> results;

    /** The server. */
    @JsonProperty("current_server")
    public String server;

    /** Provides a model representation of the {@link Result} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {

        /** The accepted shares. */
        @JsonProperty("accepted_shares")
        public int acceptedShares;

        /** The core clock. */
        @JsonProperty("gpu_clock_core_max")
        public int coreClock;

        /** The fan speed (percentage). */
        @JsonProperty("gpu_fan_speed")
        public int fanSpeed;

        /** The GPU ID. */
        @JsonProperty("gpuid")
        public int gpuId;

        /** The GPU name. */
        @JsonProperty("name")
        public String gpuName;

        /** The memory clock. */
        @JsonProperty("gpu_clock_memory")
        public int memoryClock;

        /** The rejected shares. */
        @JsonProperty("rejected_shares")
        public int rejectedShares;

        /** The solution rate. */
        @JsonProperty("speed_sps")
        public double solutionRate;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;
    }
}