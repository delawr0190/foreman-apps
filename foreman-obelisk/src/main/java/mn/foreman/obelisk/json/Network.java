package mn.foreman.obelisk.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The following model provides an {@link Network} model object of a response
 * from an obelisk.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Network {

    /** The hostname. */
    @JsonProperty("hostname")
    public String hostname;
}
