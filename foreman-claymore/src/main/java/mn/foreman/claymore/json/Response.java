package mn.foreman.claymore.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The following model provides a model of a response from Claymore:
 *
 * <pre>
 *     {"id":0,"jsonrpc":"2.0","method":"miner_getstat1"}
 * </pre>
 *
 * <p>OR</p>
 *
 * <pre>
 *     {"id":0,"jsonrpc":"2.0","method":"miner_getstat1","psw":"<password></password>"}
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "error": null,
 *   "id": 0,
 *   "result": [
 *     "10.0 - ETH",
 *     "0",
 *     "0;0;0",
 *     "0",
 *     "0;0;0",
 *     "0",
 *     "52;47",
 *     "daggerhashimoto.eu.nicehash.com:3353;decred.eu.nicehash.com:3354",
 *     "0;0;0;0"
 *   ]
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The result. */
    @JsonProperty("result")
    public List<String> result;
}