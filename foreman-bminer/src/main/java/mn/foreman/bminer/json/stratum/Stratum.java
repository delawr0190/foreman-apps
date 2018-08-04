package mn.foreman.bminer.json.stratum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
 *   "failover_uris": [
 *     {
 *       "name": "blake2s://DDXKDhq73GRM3hjh6uee57fJ3LS2ctNtyi.my:c=XVG,d=92@blake2s.mine.zpool.ca:5766",
 *       "active": true
 *     }
 *   ],
 *   "accepted_shares": 1,
 *   "rejected_shares": 0,
 *   "accepted_share_rate": 0.02,
 *   "rejected_share_rate": 0
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stratum {

    /** The accepted shares. */
    @JsonProperty("accepted_shares")
    public int acceptedShares;

    /** The failure URIs. */
    @JsonProperty("failover_uris")
    public List<Failover> failovers;

    /** The rejected shares. */
    @JsonProperty("rejected_shares")
    public int rejectedShares;
}