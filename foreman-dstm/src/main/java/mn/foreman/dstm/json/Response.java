package mn.foreman.dstm.json;

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
 *   "id": 1,
 *   "result": [
 *     {
 *       "gpu_id": 0,
 *       "gpu_name": "string",
 *       "gpu_pci_bus_id": 0,
 *       "gpu_pci_device_id": 0,
 *       "gpu_uuid": "string",
 *       "temperature": 0,
 *       "fan_speed": 0,
 *       "sol_ps": 0.00,
 *       "avg_sol_ps": 0.00,
 *       "sol_pw": 0.00,
 *       "avg_sol_pw": 0.00,
 *       "power_usage": 0.00,
 *       "avg_power_usage": 0.00,
 *       "accepted_shares": 0,
 *       "rejected_shares": 0,
 *       "latency": 0
 *     }
 *   ],
 *   "uptime": 0,
 *   "contime": 0,
 *   "server": "string",
 *   "port": 0000,
 *   "user": "string",
 *   "version": "string",
 *   "error": null
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The connection time. */
    @JsonProperty("contime")
    public long connectionTime;

    /** The port. */
    @JsonProperty("port")
    public int port;

    /** The results. */
    @JsonProperty("result")
    public List<Result> results;

    /** The server. */
    @JsonProperty("server")
    public String server;

    /** Provides a model representation of the {@link Result} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {

        /** The accepted shares. */
        @JsonProperty("accepted_shares")
        public int acceptedShares;

        /** The fan speed (percentage). */
        @JsonProperty("fan_speed")
        public int fanSpeed;

        /** The GPU ID. */
        @JsonProperty("gpu_id")
        public int gpuId;

        /** The GPU name. */
        @JsonProperty("gpu_name")
        public String gpuName;

        /** The GPU PCI bus ID. */
        @JsonProperty("gpu_pci_bus_id")
        public int gpuPciBusId;

        /** The rejected shares. */
        @JsonProperty("rejected_shares")
        public int rejectedShares;

        /** The solution rate. */
        @JsonProperty("sol_ps")
        public double solutionRate;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;
    }
}