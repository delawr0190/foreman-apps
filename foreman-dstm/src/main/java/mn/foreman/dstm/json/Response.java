package mn.foreman.dstm.json;

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
public class Response {

    /** The connection time. */
    @JsonProperty("contime")
    public long connectionTime;

    /** The error. */
    @JsonProperty("error")
    public String error;

    /** The ID. */
    @JsonProperty("id")
    public int id;

    /** The port. */
    @JsonProperty("port")
    public int port;

    /** The results. */
    @JsonProperty("result")
    public List<Result> results;

    /** The server. */
    @JsonProperty("server")
    public String server;

    /** The uptime. */
    @JsonProperty("uptime")
    public long uptime;

    /** The user. */
    @JsonProperty("user")
    public String user;

    /** The version. */
    @JsonProperty("version")
    public String version;

    /** Provides a model representation of the {@link Result} object. */
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

        /** The GPU PCI device ID. */
        @JsonProperty("gpu_pci_device_id")
        public int gpuPciDeviceId;

        /** The GPU UUID. */
        @JsonProperty("gpu_uuid")
        public String gpuUuid;

        /** The latency. */
        @JsonProperty("latency")
        public int latency;

        /** The power usage. */
        @JsonProperty("power_usage")
        public double powerUsage;

        /** The average power usage. */
        @JsonProperty("avg_power_usage")
        public double powerUsageAverage;

        /** The rejected shares. */
        @JsonProperty("rejected_shares")
        public int rejectedShares;

        /** The solution rate. */
        @JsonProperty("sol_ps")
        public double solutionRate;

        /** The solution rate (avg). */
        @JsonProperty("avg_sol_ps")
        public double solutionRateAverage;

        /** The solutions per watt. */
        @JsonProperty("sol_pw")
        public double solutionsPerWatt;

        /** The solutions per watt (avg). */
        @JsonProperty("avg_sol_pw")
        public double solutionsPerWattAverage;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;
    }
}