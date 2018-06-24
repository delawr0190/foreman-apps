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

    /** The bminer configurations. */
    private final List<Map<String, String>> bminerConfigs;

    /** The ccminer configurations. */
    private final List<Map<String, String>> ccminerConfigs;

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
     * @param bminerConfigs          The bminer configs.
     * @param ccminerConfigs         The ccminer configs.
     * @param pollFrequencyInSeconds How frequently to poll, in seconds.
     */
    private YmlConfiguration(
            @JsonProperty("foremanApiUrl") final String foremanApiUrl,
            @JsonProperty("apiKey") final String apiKey,
            @JsonProperty("antminers") final List<Map<String, String>> antConfigs,
            @JsonProperty("bminers") final List<Map<String, String>> bminerConfigs,
            @JsonProperty("ccminers") final List<Map<String, String>> ccminerConfigs,
            @JsonProperty("pollFrequencyInSeconds") int pollFrequencyInSeconds) {
        Validate.notEmpty(
                foremanApiUrl,
                "foremanApiUrl cannot be empty");
        Validate.notEmpty(
                apiKey,
                "apiKey cannot be empty");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, pollFrequencyInSeconds,
                "pollFrequencyInSeconds must be positive");
        this.foremanApiUrl = foremanApiUrl;
        this.apiKey = apiKey;
        this.antConfigs = toConfigs(antConfigs);
        this.bminerConfigs = toConfigs(bminerConfigs);
        this.ccminerConfigs = toConfigs(ccminerConfigs);
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
    public List<Map<String, String>> getBminerConfigs() {
        return Collections.unmodifiableList(this.bminerConfigs);
    }

    @Override
    public List<Map<String, String>> getCcminerConfigs() {
        return Collections.unmodifiableList(this.ccminerConfigs);
    }

    @Override
    public String getForemanApiUrl() {
        return this.foremanApiUrl;
    }

    @Override
    public int getPollFrequencyInSeconds() {
        return this.pollFrequencyInSeconds;
    }

    /**
     * Safe-copies the provided configuration.
     *
     * @param configs The configurations.
     *
     * @return The safe copy.
     */
    private static List<Map<String, String>> toConfigs(
            final List<Map<String, String>> configs) {
        return (configs != null
                ? new ArrayList<>(configs)
                : Collections.emptyList());
    }
}