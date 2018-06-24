package mn.foreman.bminer.json.stratum;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The following model provides a model sub-object of a response from bminer:
 *
 * <pre>
 *     GET /api/v1/status/stratum
 * </pre>
 *
 * <p>The expected format of that sub-object response is:</p>
 *
 * <pre>
 * {
 *   "name": "blake2s://DDXKDhq73GRM3hjh6uee57fJ3LS2ctNtyi.my:c=XVG,d=92@blake2s.mine.zpool.ca:5766",
 *   "active": true
 * }
 * </pre>
 */
public class Failover {

    /** Whether or not the failover is active. */
    @JsonProperty("active")
    public boolean active;

    /** The name. */
    @JsonProperty("name")
    public String name;
}