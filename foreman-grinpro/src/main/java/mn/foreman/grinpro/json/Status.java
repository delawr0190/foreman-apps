package mn.foreman.grinpro.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * The following model provides a model of a response from grinpro miner:
 *
 * <pre>
 *     GET /api/status
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "connectionAddress": "some.pool.com:4416",
 *   "connectionStatus": "Connected",
 *   "lastJob": "2019-02-20T16:18:48+01:00",
 *   "lastShare": "2019-02-20T16:18:48+01:00",
 *   "shares": {
 * 	   "found": 244,
 * 	   "submitted": 66,
 * 	   "accepted": 65,
 * 	   "tooLate": 1,
 * 	   "failedToValidate": 0
 *   },
 * 	 "workers": [
 *     {
 * 	     "id": 0,
 * 	     "status": "ONLINE",
 * 	     "graphsPerSecond": 3.19306469,
 * 	     "fidelity": 1.0042,
 * 	     "totalSols": 244,
 * 	     "lastSolution": "2019-02-20T16:18:48+01:00"
 *     }
 *   ]
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {

    /** The shares. */
    @JsonProperty("shares")
    public Shares shares;

    /** The stratum address. */
    @JsonProperty("connectionAddress")
    public String stratum;

    /** The stratum connection status. */
    @JsonProperty("connectionStatus")
    public String stratumStatus;

    /** The workers. */
    @JsonProperty("workers")
    public List<Worker> workers;

    /** A model object representation of the shares JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Shares {

        /** The accepted shares. */
        @JsonProperty("accepted")
        public int accepted;

        /** The rejected shares. */
        @JsonProperty("tooLate")
        public int rejected;
    }

    /** A model object representation of the worker JSON object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Worker {

        /** The graphs per second. */
        @JsonProperty("graphsPerSecond")
        public BigDecimal graphsPerSecond;

        /** The GPU id. */
        @JsonProperty("id")
        public int id;

        /** The GPU name. */
        @JsonProperty("gpuName")
        public String name;
    }
}