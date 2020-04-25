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
     * Returns the pickaxe ID.
     *
     * @return The pickaxe ID.
     */
    String getPickaxeId();

    /**
     * Whether or not command and control is enabled.
     *
     * @return Whether or not command and control is enabled.
     */
    boolean isControl();
}