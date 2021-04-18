package mn.foreman.goldshell.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A pool model object. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pool {

    /** Whether or not the pool is active. */
    @JsonProperty("active")
    public boolean active;

    /** The pass. */
    @JsonProperty("pass")
    public String pass;

    /** The priority. */
    @JsonProperty("pool-priority")
    public int priority;

    /** The active url. */
    @JsonProperty("url")
    public String url;

    /** The user. */
    @JsonProperty("user")
    public String user;
}
