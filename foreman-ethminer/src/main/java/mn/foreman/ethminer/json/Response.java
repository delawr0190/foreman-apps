package mn.foreman.ethminer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The following model provides a model of a response from ethminer:
 *
 * <pre>
 *     {"id":0,"jsonrpc":"2.0","method":"miner_getstathr"}
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "id": 1,
 *   "jsonrpc": "2.0",
 *   "result": {
 *     "ethhashrate": 73056881,
 *     "ethhashrates": [
 *       14681287,
 *       14506510,
 *       14681287,
 *       14506510,
 *       0,
 *       14681287
 *     ],
 *     "ethinvalid": 0,
 *     "ethpoolsw": 0,
 *     "ethrejected": 0,
 *     "ethshares": 64,
 *     "fanpercentages": [
 *       90,
 *       90,
 *       90,
 *       90,
 *       100,
 *       90
 *     ],
 *     "pooladdrs": "eu1.ethermine.org:4444",
 *     "powerusages": [
 *       0.0,
 *       0.0,
 *       0.0,
 *       0.0,
 *       0.0,
 *       0.0
 *     ],
 *     "runtime": "59",
 *     "temperatures": [
 *       53,
 *       50,
 *       56,
 *       58,
 *       68,
 *       60
 *     ],
 *     "ispaused": [
 *       false,
 *       false,
 *       false,
 *       false,
 *       true,
 *       false
 *     ],
 *     "version": "ethminer-0.16.0.dev0+commit.41639944"
 *   }
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The result. */
    @JsonProperty("result")
    public Result result;

    /** A model object representing the result object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {

        /** The total hash rate. */
        @JsonProperty("ethhashrate")
        public int ethHashRate;

        /** The ETH invalid shares. */
        @JsonProperty("ethinvalid")
        public int ethInvalid;

        /** The ETH rejected shares. */
        @JsonProperty("ethrejected")
        public int ethRejected;

        /** The ETH shares. */
        @JsonProperty("ethshares")
        public int ethShares;

        /** The fan percentages. */
        @JsonProperty("fanpercentages")
        public List<Integer> fanPercentages;

        /** The pool address. */
        @JsonProperty("pooladdrs")
        public String poolAddress;

        /** The total runtime (minutes). */
        @JsonProperty("runtime")
        public String runtime;

        /** The GPU temperatures. */
        @JsonProperty("temperatures")
        public List<Integer> temperatures;

        /** The version. */
        @JsonProperty("version")
        public String version;
    }
}
