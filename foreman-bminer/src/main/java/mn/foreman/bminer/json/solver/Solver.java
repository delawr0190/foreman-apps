package mn.foreman.bminer.json.solver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The following model provides a model sub-object of a response from bminer:
 *
 * <pre>
 *     GET /api/v1/status/solver
 * </pre>
 *
 * <p>The expected format of that sub-object response is:</p>
 *
 * <pre>
 * {
 *   "algorithm": "ethash",
 *   "speed_info": {
 *     "hash_rate": 30828134.4
 *   }
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Solver {

    /** The speed info. */
    @JsonProperty("speed_info")
    public SpeedInfo speedInfo;
}