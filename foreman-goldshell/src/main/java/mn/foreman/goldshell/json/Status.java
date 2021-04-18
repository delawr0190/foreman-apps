package mn.foreman.goldshell.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A response from <code>/mcb/status</code>. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {

    /** The model. */
    @JsonProperty("model")
    public String model;
}
