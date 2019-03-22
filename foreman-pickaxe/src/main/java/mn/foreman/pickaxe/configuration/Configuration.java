package mn.foreman.pickaxe.configuration;

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
     * Returns the user's client ID.
     *
     * @return The user's client ID.
     */
    String getClientId();

    /**
     * Returns the Foreman API URL.
     *
     * @return The Foreman API URL.
     */
    String getForemanApiUrl();

    /**
     * Returns the Foreman autominer URL.
     *
     * @return The Foreman autominer URL.
     */
    String getForemanAutominerUrl();

    /**
     * Returns the Foreman config URL.
     *
     * @return The Foreman config URL.
     */
    String getForemanConfigUrl();

    /**
     * Returns the Foreman nicehash URL.
     *
     * @return The Foreman nicehash URL.
     */
    String getForemanNicehashUrl();

    /**
     * Returns the pickaxe ID.
     *
     * @return The pickaxe ID.
     */
    String getPickaxeId();
}