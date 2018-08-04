package mn.foreman.ewbf.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The following model provides a model of a response from ewbf to the following
 * URL:
 *
 * <pre>
 *   GET /getstat
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "method": "getstat",
 *   "error": null,
 *   "start_time": 1530826989,
 *   "current_server": "zec-eu1.nanopool.org:6666",
 *   "available_servers": 1,
 *   "server_status": 2,
 *   "result": [
 *     {
 *       "gpuid": 0,
 *       "cudaid": 0,
 *       "busid": "0000:01:00.0",
 *       "name": "GeForce GTX 1070 Ti",
 *       "gpu_status": 2,
 *       "solver": 0,
 *       "temperature": 71,
 *       "gpu_power_usage": 188,
 *       "speed_sps": 508,
 *       "accepted_shares": 1,
 *       "rejected_shares": 0,
 *       "start_time": 1530826991
 *     },
 *     {
 *       "gpuid": 1,
 *       "cudaid": 1,
 *       "busid": "0000:02:00.0",
 *       "name": "GeForce GTX 1070 Ti",
 *       "gpu_status": 2,
 *       "solver": 0,
 *       "temperature": 67,
 *       "gpu_power_usage": 181,
 *       "speed_sps": 503,
 *       "accepted_shares": 0,
 *       "rejected_shares": 0,
 *       "start_time": 1530826991
 *     },
 *     {
 *       "gpuid": 2,
 *       "cudaid": 2,
 *       "busid": "0000:03:00.0",
 *       "name": "GeForce GTX 1070 Ti",
 *       "gpu_status": 2,
 *       "solver": 0,
 *       "temperature": 68,
 *       "gpu_power_usage": 174,
 *       "speed_sps": 498,
 *       "accepted_shares": 0,
 *       "rejected_shares": 0,
 *       "start_time": 1530826991
 *     },
 *     {
 *       "gpuid": 3,
 *       "cudaid": 3,
 *       "busid": "0000:04:00.0",
 *       "name": "GeForce GTX 1070 Ti",
 *       "gpu_status": 2,
 *       "solver": 0,
 *       "temperature": 68,
 *       "gpu_power_usage": 181,
 *       "speed_sps": 504,
 *       "accepted_shares": 0,
 *       "rejected_shares": 0,
 *       "start_time": 1530826991
 *     },
 *     {
 *       "gpuid": 4,
 *       "cudaid": 4,
 *       "busid": "0000:06:00.0",
 *       "name": "GeForce GTX 1070 Ti",
 *       "gpu_status": 2,
 *       "solver": 0,
 *       "temperature": 69,
 *       "gpu_power_usage": 186,
 *       "speed_sps": 510,
 *       "accepted_shares": 2,
 *       "rejected_shares": 0,
 *       "start_time": 1530826991
 *     },
 *     {
 *       "gpuid": 5,
 *       "cudaid": 5,
 *       "busid": "0000:09:00.0",
 *       "name": "GeForce GTX 1070 Ti",
 *       "gpu_status": 2,
 *       "solver": 0,
 *       "temperature": 69,
 *       "gpu_power_usage": 186,
 *       "speed_sps": 511,
 *       "accepted_shares": 1,
 *       "rejected_shares": 0,
 *       "start_time": 1530826991
 *     },
 *     {
 *       "gpuid": 6,
 *       "cudaid": 6,
 *       "busid": "0000:0A:00.0",
 *       "name": "GeForce GTX 1070 Ti",
 *       "gpu_status": 2,
 *       "solver": 0,
 *       "temperature": 69,
 *       "gpu_power_usage": 185,
 *       "speed_sps": 504,
 *       "accepted_shares": 0,
 *       "rejected_shares": 0,
 *       "start_time": 1530826991
 *     },
 *     {
 *       "gpuid": 7,
 *       "cudaid": 7,
 *       "busid": "0000:0B:00.0",
 *       "name": "GeForce GTX 1070 Ti",
 *       "gpu_status": 2,
 *       "solver": 0,
 *       "temperature": 66,
 *       "gpu_power_usage": 168,
 *       "speed_sps": 436,
 *       "accepted_shares": 1,
 *       "rejected_shares": 0,
 *       "start_time": 1530826992
 *     }
 *   ]
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stat {

    /** The current server. */
    @JsonProperty("current_server")
    public String currentServer;

    /** The results. */
    @JsonProperty("result")
    public List<Result> results;

    /** The server status. */
    @JsonProperty("server_status")
    public int serverStatus;

    /** Provides a model representation of the {@link Result} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {

        /** The accepted shares. */
        @JsonProperty("accepted_shares")
        public int acceptedShares;

        /** The bus ID. */
        @JsonProperty("busid")
        public String busId;

        /** The GPU ID. */
        @JsonProperty("gpuid")
        public int gpuId;

        /** The name. */
        @JsonProperty("name")
        public String name;

        /** The rejected shares. */
        @JsonProperty("rejected_shares")
        public int rejectedShares;

        /** The speed sps. */
        @JsonProperty("speed_sps")
        public int speedSps;

        @JsonProperty("temperature")
        public int temperature;
    }
}