package mn.foreman.pickaxe.configuration.yml;

import mn.foreman.pickaxe.configuration.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** A YML-based {@link Configuration} parser. */
public class YmlConfiguration
        implements Configuration {

    /** The antminer configurations. */
    private final List<Map<String, String>> antConfigs;

    /** The user's API key. */
    private final String apiKey;

    /** The FOREMAN API URL. */
    private final String foremanApiUrl;

    /** How frequently to poll, in seconds. */
    private final int pollFrequencyInSeconds;

    /**
     * Constructor.
     *
     * <p>Note: intentionally hidden (built via JACKSON).</p>
     *
     * @param foremanApiUrl          The FOREMAN API URL.
     * @param apiKey                 The API key.
     * @param antConfigs             The antminer configs.
     * @param pollFrequencyInSeconds How frequently to poll, in seconds.
     */
    private YmlConfiguration(
            @JsonProperty("foremanApiUrl") final String foremanApiUrl,
            @JsonProperty("apiKey") final String apiKey,
            @JsonProperty("antminers") final List<Map<String, String>> antConfigs,
            @JsonProperty("pollFrequencyInSeconds") int pollFrequencyInSeconds) {
        Validate.notEmpty(
                foremanApiUrl,
                "foremanApiUrl cannot be empty");
        Validate.notEmpty(
                apiKey,
                "apiKey cannot be empty");
        Validate.notNull(
                antConfigs,
                "antConfigs cannot be null");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, pollFrequencyInSeconds,
                "pollFrequencyInSeconds must be positive");
        this.foremanApiUrl = foremanApiUrl;
        this.apiKey = apiKey;
        this.antConfigs = new ArrayList<>(antConfigs);
        this.pollFrequencyInSeconds = pollFrequencyInSeconds;
    }

    @Override
    public List<Map<String, String>> getAntminerConfigs() {
        return Collections.unmodifiableList(this.antConfigs);
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
    public int getPollFrequencyInSeconds() {
        return this.pollFrequencyInSeconds;
    }
}