package mn.foreman.bminer.json.solver;

import com.fasterxml.jackson.annotation.JsonAlias;
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
 *   "hash_rate": 30828134.4
 * }
 * </pre>
 *
 * <p>OR</p>
 *
 * <pre>
 * {
 *   "nonce_rate": 359.27,
 *   "solution_rate": 677.53
 * }
 * </pre>
 *
 * <p>Both the solution rate and the hash rate are placed into {@link
 * #hashRate}.</p>
 */
public class SpeedInfo {

    /** The hash rate (for equihash, this is the solution rate). */
    @JsonProperty("hash_rate")
    @JsonAlias("solution_rate")
    public double hashRate;
}