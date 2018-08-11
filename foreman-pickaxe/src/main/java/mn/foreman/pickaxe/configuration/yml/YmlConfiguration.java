package mn.foreman.pickaxe.configuration.yml;

import mn.foreman.pickaxe.configuration.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

/** A YML-based {@link Configuration} parser. */
public class YmlConfiguration
        implements Configuration {

    /** The user's API key. */
    private final String apiKey;

    /** The FOREMAN API URL. */
    private final String foremanApiUrl;

    /** The FOREMAN config API URL. */
    private final String foremanConfigUrl;

    /**
     * Constructor.
     *
     * <p>Note: intentionally hidden (built via JACKSON).</p>
     *
     * @param foremanApiUrl    The FOREMAN API URL.
     * @param foremanConfigUrl The FOREMAN config URL.
     * @param apiKey           The API key.
     */
    private YmlConfiguration(
            @JsonProperty("foremanApiUrl") final String foremanApiUrl,
            @JsonProperty("foremanConfigUrl") final String foremanConfigUrl,
            @JsonProperty("apiKey") final String apiKey) {
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
}