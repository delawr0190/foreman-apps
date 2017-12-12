package mn.foreman.pickaxe.configuration;

import java.util.List;

/**
 * Implementations of this interface are capable of providing a PICKAXE
 * configuration.
 */
public interface Configuration {

    /**
     * Returns the user's API key.
     *
     * @return The user's API key.
     */
    String getApiKey();

    /**
     * Returns the cgminer configurations.
     *
     * @return The configurations.
     */
    List<CgMinerConfig> getCgminerConfigs();

    /**
     * Returns the FOREMAN API URL.
     *
     * @return The FOREMAN API URL.
     */
    String getForemanApiUrl();

    /**
     * Returns the poll frequency in seconds.
     *
     * @return The poll frequency in seconds.
     */
    int getPollFrequencyInSeconds();
}