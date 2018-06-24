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
 *   "core": 1759,
 *   "memory": 3504
 * }
 * </pre>
 */
public class Clocks {

    /** The core clock. */
    @JsonProperty("core")
    public int core;

    /** The memory clock. */
    @JsonProperty("memory")
    public int memory;
}
