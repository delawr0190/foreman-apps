package mn.foreman.excavator.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The following model provides a model of a response from excavator:
 *
 * <pre>
 *     {"id":1,"method":"subscribe.info","params":[]}
 * </pre>
 *
 * <p>The expected format of that response is:</p>
 *
 * <pre>
 * {
 *   "id":1,
 *   "address":"nhmp.eu.nicehash.com:3200",
 *   "login":"34HKWdzLxWBduUfJE9JxaFhoXnfC6gmePG.test2:x",
 *   "connected":true,
 *   "server_status":"Subscribed"
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscribe
        implements Response {

    /** The address. */
    @JsonProperty("address")
    public String address;

    /** Whether or not connected. */
    @JsonProperty("connected")
    public boolean connected;

    /** The ID. */
    @JsonProperty("id")
    public int id;

    /** The login. */
    @JsonProperty("login")
    public String login;

    /** The server status. */
    @JsonProperty("server_status")
    public String serverStatus;
}
