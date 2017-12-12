package mn.foreman.pickaxe.configuration;

import java.io.IOException;

/**
 * A common interface to factories that able to provide PICKAXE configurations.
 */
public interface ConfigurationFactory {

    /**
     * Loads the configuration from the provided file.
     *
     * @param path The path to the configuration file.
     *
     * @return The {@link Configuration}.
     */
    Configuration load(String path)
            throws IOException;
}