package mn.foreman.ethminer.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The following model provides a model of a response from ethminer:
 *
 * <pre>
 *     {"id":0,"jsonrpc":"2.0","method":"miner_getstat1"}
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "id": 0,
 *   "jsonrpc": "2.0",
 *   "result": [
 *     "0.19.0-beta.0+nhfix",
 *     "4",
 *     "266708;13;0",
 *     "23117;23100;19731;22266;21328;23708;23559;23766;22556;20847;20973;21756",
 *     "0;0;0",
 *     "off;off;off;off;off;off;off;off;off;off;off;off",
 *     "49;100;56;100;56;100;57;100;56;100;51;100;51;100;50;85;59;0;54;95;52;100;52;100",
 *     "asia.ethash-hub.miningpoolhub.com:17020",
 *     "0;0;0;0"
 *   ]
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    /** The results. */
    @JsonProperty("result")
    public List<String> result;
}
