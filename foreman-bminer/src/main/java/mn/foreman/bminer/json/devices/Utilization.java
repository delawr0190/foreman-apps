package mn.foreman.bminer.json.devices;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The following model provides a {@link Device} sub-model object of a response
 * from bminer:
 *
 * <pre>
 *     GET /api/v1/status/device
 * </pre>
 *
 * <p>The expected format of that sub-object is:</p>
 *
 * <pre>
 * {
 *   "gpu": 100,
 *   "memory": 73
 * }
 * </pre>
 */
public class Utilization {

    /** The GPU utilization. */
    @JsonProperty("gpu")
    public int gpu;

    /** The memory utilization. */
    @JsonProperty("memory")
    public int memory;
}