package mn.foreman.bminer.json.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * The following model provides a model object of a response from bminer:
 *
 * <pre>
 *     GET /api/v1/status/device
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "0": {
 *     "temperature": 83,
 *     "power": 199,
 *     "fan_speed": 74,
 *     "global_memory_used": 4385,
 *     "utilization": {
 *       "gpu": 100,
 *       "memory": 73
 *     },
 *     "clocks": {
 *       "core": 1809,
 *       "memory": 5005
 *     }
 *   }
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Devices {

    /** All of the {@link Device devices}. */
    @JsonProperty("devices")
    public Map<String, Device> devices;
}