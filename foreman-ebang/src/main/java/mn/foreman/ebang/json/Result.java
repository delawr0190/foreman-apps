package mn.foreman.ebang.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The status result. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    /** The status. */
    @JsonProperty("status")
    public int status;
}
