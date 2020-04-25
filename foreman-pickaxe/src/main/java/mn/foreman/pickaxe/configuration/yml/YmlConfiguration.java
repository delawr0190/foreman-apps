package mn.foreman.pickaxe.configuration.yml;

import mn.foreman.pickaxe.configuration.Configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

/** A YML-based {@link Configuration} parser. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class YmlConfiguration
        implements Configuration {

    /** The user's API key. */
    private final String apiKey;

    /** The client ID. */
    private final String clientId;

    /** Whether or not command and control is enabled. */
    private final boolean control;

    /** The pickaxe identifier. */
    private final String pickaxeId;

    /**
     * Constructor.
     *
     * @param apiKey    The API key.
     * @param clientId  The client ID.
     * @param pickaxeId The pickaxe ID.
     * @param control   Whether or not command and control is enabled.
     */
    public YmlConfiguration(
            @JsonProperty("apiKey") final String apiKey,
            @JsonProperty("clientId") final String clientId,
            @JsonProperty("pickaxeId") final String pickaxeId,
            @JsonProperty("control") final boolean control) {
        Validate.notEmpty(
                apiKey,
                "apiKey cannot be empty");
        this.apiKey = apiKey;
        this.clientId = clientId;
        this.pickaxeId = pickaxeId;
        this.control = control;
    }

    @Override
    public String getApiKey() {
        return this.apiKey;
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public String getPickaxeId() {
        return this.pickaxeId;
    }

    @Override
    public boolean isControl() {
        return this.control;
    }
}