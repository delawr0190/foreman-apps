package mn.foreman.obelisk.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The following model provides an {@link Info} model object of a response from
 * an obelisk:
 *
 * <pre>
 *     POST /api/info
 * </pre>
 *
 * <p>The expected format of that object is:</p>
 *
 * <pre>
 * {
 *   "macAddress": "80:00:27:2e:bc:69",
 *   "ipAddress": "192.168.1.123",
 *   "model": "SC1" ,
 *   "vendor": "Obelisk"
 * }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Info {

    /** The model. */
    @JsonProperty("model")
    public String model;
}
