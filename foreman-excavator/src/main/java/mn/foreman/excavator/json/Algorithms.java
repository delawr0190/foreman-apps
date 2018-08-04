package mn.foreman.excavator.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a model of a response from excavator:
 *
 * <pre>
 * {"id":1,"method":"algorithm.list","params":[]}
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "algorithms": [
 *     {
 *       "algorithm_id": 20,
 *       "name": "daggerhashimoto",
 *       "speed": 9198932.692307692,
 *       "uptime": 19.18597412109375,
 *       "benchmark": false,
 *       "sent_shares": 0,
 *       "accepted_shares": 0,
 *       "rejected_shares": 0,
 *       "got_job": true,
 *       "received_jobs": 4,
 *       "current_job_difficulty": 0.5
 *     },
 *     {
 *       "algorithm_id": 21,
 *       "name": "decred",
 *       "speed": 5078989989.082617,
 *       "uptime": 19.18767738342285,
 *       "benchmark": false,
 *       "sent_shares": 1,
 *       "accepted_shares": 1,
 *       "rejected_shares": 0,
 *       "got_job": true,
 *       "received_jobs": 1,
 *       "current_job_difficulty": 4
 *     },
 *     {
 *       "algorithm_id": 24,
 *       "name": "equihash",
 *       "speed": 441.36426519101843,
 *       "uptime": 19.18423080444336,
 *       "benchmark": false,
 *       "sent_shares": 0,
 *       "accepted_shares": 0,
 *       "rejected_shares": 0,
 *       "got_job": true,
 *       "received_jobs": 2,
 *       "current_job_difficulty": 1024
 *     }
 *   ],
 *   "id": 1,
 *   "error": null
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Algorithms
        implements Response {

    /** The {@link Algorithm algorithms}. */
    @JsonProperty("algorithms")
    public List<Algorithm> algorithms;

    /** Provides a model representation of the {@link Algorithm} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Algorithm {

        /** The number of accepted shares. */
        @JsonProperty("accepted_shares")
        public int acceptedShares;

        /** The number of rejected shares. */
        @JsonProperty("rejected_shares")
        public int rejectedShares;

        /** The speed. */
        @JsonProperty("speed")
        public BigDecimal speed;
    }
}