package mn.foreman.bminer.json.solver;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * The following model provides a model object of a response from bminer:
 *
 * <pre>
 *     GET /api/v1/status/solver
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "0": {
 *     "solvers": [
 *       {
 *         "algorithm": "ethash",
 *         "speed_info": {
 *           "hash_rate": 30828134.4
 *         }
 *       },
 *       {
 *         "algorithm": "blake2s",
 *         "speed_info": {
 *           "hash_rate": 1778500208.17
 *         }
 *       }
 *     ]
 *   }
 * }
 * </pre>
 */
public class Devices {

    /** The devices. */
    @JsonProperty("devices")
    public Map<String, Solvers> devices;
}