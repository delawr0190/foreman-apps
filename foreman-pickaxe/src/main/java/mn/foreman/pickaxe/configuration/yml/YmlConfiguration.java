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

    /** The FOREMAN API URL. */
    private final String foremanApiUrl;

    /** The FOREMAN config API URL. */
    private final String foremanConfigUrl;

    /** The pickaxe identifier. */
    private final String pickaxeId;

    /**
     * Constructor.
     *
     * @param foremanApiUrl    The FOREMAN API URL.
     * @param foremanConfigUrl The FOREMAN config URL.
     * @param apiKey           The API key.
     */
    public YmlConfiguration(
            @JsonProperty("foremanApiUrl") final String foremanApiUrl,
            @JsonProperty("foremanConfigUrl") final String foremanConfigUrl,
            @JsonProperty("apiKey") final String apiKey,
            @JsonProperty("pickaxeId") final String pickaxeId) {
        Validate.notEmpty(
                foremanApiUrl,
                "foremanApiUrl cannot be empty");
        Validate.notEmpty(
                foremanConfigUrl,
                "foremanConfigUrl cannot be empty");
        Validate.notEmpty(
                apiKey,
                "apiKey cannot be empty");
        this.foremanApiUrl = foremanApiUrl;
        this.foremanConfigUrl = foremanConfigUrl;
        this.apiKey = apiKey;
        this.pickaxeId = pickaxeId;
    }

    @Override
    public String getApiKey() {
        return this.apiKey;
    }

    @Override
    public String getForemanApiUrl() {
        return this.foremanApiUrl;
    }

    @Override
    public String getForemanConfigUrl() {
        return this.foremanConfigUrl;
    }

    @Override
    public String getPickaxeId() {
        return this.pickaxeId;
    }
}