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

    /** The Foreman API URL. */
    private final String foremanApiUrl;

    /** The Foreman autominer API URL. */
    private final String foremanAutominerUrl;

    /** The claymore multipliers URL. */
    private final String foremanClaymoreMultipliersUrl;

    /** The Foreman config API URL. */
    private final String foremanConfigUrl;

    /** The Foreman nicehash API URL. */
    private final String foremanNicehashUrl;

    /** The pickaxe identifier. */
    private final String pickaxeId;

    /**
     * Constructor.
     *
     * @param foremanApiUrl                 The Foreman API URL.
     * @param foremanConfigUrl              The Foreman config URL.
     * @param foremanNicehashUrl            The Foreman nicehash URL.
     * @param foremanAutominerUrl           The Foreman autominer URL.
     * @param foremanClaymoreMultipliersUrl The claymore multipliers URL.
     * @param apiKey                        The API key.
     * @param clientId                      The client ID.
     * @param pickaxeId                     The pickaxe ID.
     */
    public YmlConfiguration(
            @JsonProperty("foremanApiUrl") final String foremanApiUrl,
            @JsonProperty("foremanConfigUrl") final String foremanConfigUrl,
            @JsonProperty("foremanNicehashUrl") final String foremanNicehashUrl,
            @JsonProperty("foremanAutominerUrl") final String foremanAutominerUrl,
            @JsonProperty("foremanClaymoreMultipliersUrl") final String foremanClaymoreMultipliersUrl,
            @JsonProperty("apiKey") final String apiKey,
            @JsonProperty("clientId") final String clientId,
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
        this.foremanNicehashUrl = foremanNicehashUrl;
        this.foremanAutominerUrl = foremanAutominerUrl;
        this.foremanClaymoreMultipliersUrl = foremanClaymoreMultipliersUrl;
        this.apiKey = apiKey;
        this.clientId = clientId;
        this.pickaxeId = pickaxeId;
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
    public String getForemanApiUrl() {
        return this.foremanApiUrl;
    }

    @Override
    public String getForemanAutominerUrl() {
        return this.foremanAutominerUrl;
    }

    @Override
    public String getForemanClaymoreMultipliersUrl() {
        return foremanClaymoreMultipliersUrl;
    }

    @Override
    public String getForemanConfigUrl() {
        return this.foremanConfigUrl;
    }

    @Override
    public String getForemanNicehashUrl() {
        return this.foremanNicehashUrl;
    }

    @Override
    public String getPickaxeId() {
        return this.pickaxeId;
    }
}