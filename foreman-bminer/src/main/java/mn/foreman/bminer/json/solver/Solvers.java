package mn.foreman.bminer.json.solver;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
 * [
 *   {
 *     "algorithm": "ethash",
 *     "speed_info": {
 *       "hash_rate": 30828134.4
 *     }
 *   },
 *   {
 *     "algorithm": "blake2s",
 *     "speed_info": {
 *       "hash_rate": 1778500208.17
 *     }
 *   }
 * ]
 * </pre>
 */
public class Solvers {

    /** The solvers. */
    @JsonProperty("solvers")
    public List<Solver> solvers;
}