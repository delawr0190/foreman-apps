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
     * Returns the FOREMAN API URL.
     *
     * @return The FOREMAN API URL.
     */
    String getForemanApiUrl();

    /**
     * Returns the FOREMAN config URL.
     *
     * @return The FOREMAN config URL.
     */
    String getForemanConfigUrl();

    /**
     * Returns the pickaxe ID.
     *
     * @return The pickaxe ID.
     */
    String getPickaxeId();
}