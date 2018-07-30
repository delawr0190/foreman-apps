package mn.foreman.pickaxe.configuration;

import java.util.List;
import java.util.Map;

/**
 * Implementations of this interface are capable of providing a PICKAXE
 * configuration.
 */
public interface Configuration {

    /**
     * Returns the antminer configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getAntminerConfigs();

    /**
     * Returns the user's API key.
     *
     * @return The user's API key.
     */
    String getApiKey();

    /**
     * Returns the bminer configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getBminerConfigs();

    /**
     * Returns the ccminer configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getCcminerConfigs();

    /**
     * Returns the claymore configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getClaymoreConfigs();

    /**
     * Returns the dstm configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getDstmConfigs();

    /**
     * Returns the ethminer configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getEthminerConfigs();

    /**
     * Returns the ewbf configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getEwbfConfigs();

    /**
     * Returns the excavator configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getExcavatorConfigs();

    /**
     * Returns the FOREMAN API URL.
     *
     * @return The FOREMAN API URL.
     */
    String getForemanApiUrl();

    /**
     * Returns the phoenix configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getPhoenixConfigs();

    /**
     * Returns the poll frequency in seconds.
     *
     * @return The poll frequency in seconds.
     */
    int getPollFrequencyInSeconds();

    /**
     * Returns the srbminer configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getSrbminerConfigs();

    /**
     * Returns the xmrig configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getXmrigConfigs();

    /**
     * Returns the zenemy configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getZenemyConfigs();
}