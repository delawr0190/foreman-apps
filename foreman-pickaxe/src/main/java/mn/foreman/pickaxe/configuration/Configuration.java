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
     * Returns the baikal configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getBaikalConfigs();

    /**
     * Returns the bminer configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getBminerConfigs();

    /**
     * Returns the castxmr configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getCastxmrConfigs();

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
     * Returns the cryptodredge configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getCryptoDredgeConfigs();

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
     * Returns the jceminer configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getJceminerConfigs();

    /**
     * Returns the lolminer configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getLolminerConfigs();

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
     * Returns the sgminer configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getSgminerConfigs();

    /**
     * Returns the srbminer configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getSrbminerConfigs();

    /**
     * Returns the trex configurations.
     *
     * @return The configurations.
     */
    List<Map<String, String>> getTrexConfigs();

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