package mn.foreman.bminer.json.stratum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * The following model provides a model object of a response from bminer:
 *
 * <pre>
 *     GET /api/v1/status/stratum
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "blake2s": {
 *     "failover_uris": [
 *       {
 *         "name": "blake2s://DDXKDhq73GRM3hjh6uee57fJ3LS2ctNtyi.my:c=XVG,d=92@blake2s.mine.zpool.ca:5766",
 *         "active": true
 *       }
 *     ],
 *     "accepted_shares": 1,
 *     "rejected_shares": 0,
 *     "accepted_share_rate": 0.02,
 *     "rejected_share_rate": 0
 *   },
 *   "ethash": {
 *     "failover_uris": [
 *       {
 *         "name": "ethproxy://0xb76d43eAaB2e905028a7f0F3aF13C7A84c477B9f.w@guangdong-pool.ethfans.org:3333/",
 *         "active": true
 *       }
 *     ],
 *     "accepted_shares": 2,
 *     "rejected_shares": 0,
 *     "accepted_share_rate": 0.01,
 *     "rejected_share_rate": 0
 *   }
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stratums {

    /** The stratums. */
    @JsonProperty("stratums")
    public Map<String, Stratum> stratums;
}