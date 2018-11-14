package mn.foreman.gminer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The following model provides a model of a response from gminer to the
 * following URL:
 *
 * <pre>
 *   GET /stat
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 *
 *
 * {
 *   "uptime": 37,
 *   "server": "stratum:\/\/eu.btgpool.pro:1445",
 *   "user": "GZdx44gPVFX7GfeWXA3kyiuXecym3CWGHi.rig0",
 *   "devices": [
 *     {
 *       "gpu_id": 0,
 *       "cuda_id": 0,
 *       "bus_id": "0000:01:00.0",
 *       "name": "EVGA GeForce GTX 1070 Ti 8GB",
 *       "speed": 61,
 *       "accepted_shares": 0,
 *       "rejected_shares": 0,
 *       "temperature": 52,
 *       "temperature_limit": 90,
 *       "power_usage": 202
 *     },
 *     {
 *       "gpu_id": 1,
 *       "cuda_id": 1,
 *       "bus_id": "0000:02:00.0",
 *       "name": "EVGA GeForce GTX 1070 Ti 8GB",
 *       "speed": 65,
 *       "accepted_shares": 1,
 *       "rejected_shares": 0,
 *       "temperature": 48,
 *       "temperature_limit": 90,
 *       "power_usage": 202
 *     },
 *     {
 *       "gpu_id": 2,
 *       "cuda_id": 2,
 *       "bus_id": "0000:03:00.0",
 *       "name": "EVGA GeForce GTX 1070 Ti 8GB",
 *       "speed": 62,
 *       "accepted_shares": 2,
 *       "rejected_shares": 0,
 *       "temperature": 52,
 *       "temperature_limit": 90,
 *       "power_usage": 201
 *     },
 *     {
 *       "gpu_id": 3,
 *       "cuda_id": 3,
 *       "bus_id": "0000:04:00.0",
 *       "name": "EVGA GeForce GTX 1070 Ti 8GB",
 *       "speed": 65,
 *       "accepted_shares": 2,
 *       "rejected_shares": 0,
 *       "temperature": 51,
 *       "temperature_limit": 90,
 *       "power_usage": 201
 *     }
 *   ]
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stat {

    /** The devices. */
    @JsonProperty("devices")
    public List<Device> devices;

    /** The server. */
    @JsonProperty("server")
    public String server;

    /** Provides a model representation of the {@link Device} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Device {

        /** The accepted shares. */
        @JsonProperty("accepted_shares")
        public int acceptedShares;

        /** The bus ID. */
        @JsonProperty("bus_id")
        public String busId;

        /** The GPU ID. */
        @JsonProperty("gpu_id")
        public int gpuId;

        /** The name. */
        @JsonProperty("name")
        public String name;

        /** The rejected shares. */
        @JsonProperty("rejected_shares")
        public int rejectedShares;

        /** The speed. */
        @JsonProperty("speed")
        public int speed;

        /** The temperature. */
        @JsonProperty("temperature")
        public int temperature;
    }
}