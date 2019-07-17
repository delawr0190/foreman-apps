package mn.foreman.pickaxe;

import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.configuration.yml.YmlConfiguration;
import mn.foreman.pickaxe.run.RunMe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

/** The application entry point. */
public class Main {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Main.class);

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     */
    public static void main(final String[] args) {
        final OptionParser optionParser = new OptionParser();

        // Help
        optionParser
                .acceptsAll(Arrays.asList("h", "?"), "Shows help")
                .forHelp();

        // Config file
        final OptionSpec<String> configSpec =
                optionParser
                        .acceptsAll(
                                Arrays.asList("c", "config"),
                                "Sets the configuration file")
                        .withRequiredArg()
                        .ofType(String.class)
                        .required();

        final OptionSet optionSet = optionParser.parse(args);

        try {
            final String configFilePath = optionSet.valueOf(configSpec);
            final File configFile = new File(configFilePath);

            final ObjectMapper objectMapper =
                    new ObjectMapper(
                            new YAMLFactory());

            Configuration configuration =
                    readConfiguration(
                            objectMapper,
                            configFile);

            // Add nicehash URL if missing
            configuration =
                    updateConfiguration(
                            configuration,
                            Configuration::getForemanNicehashUrl,
                            configuration.getForemanApiUrl(),
                            configuration.getForemanConfigUrl(),
                            "https://dashboard.foreman.mn/api/nicehashv2",
                            configuration.getForemanAutominerUrl(),
                            configuration.getForemanClaymoreMultipliersUrl(),
                            configuration.getApiKey(),
                            configuration.getClientId(),
                            configuration.getPickaxeId(),
                            objectMapper,
                            configFile,
                            true);

            // Add autominer URL if missing
            configuration =
                    updateConfiguration(
                            configuration,
                            Configuration::getForemanAutominerUrl,
                            configuration.getForemanApiUrl(),
                            configuration.getForemanConfigUrl(),
                            configuration.getForemanNicehashUrl(),
                            "https://dashboard.foreman.mn/api/autominer",
                            configuration.getForemanClaymoreMultipliersUrl(),
                            configuration.getApiKey(),
                            configuration.getClientId(),
                            configuration.getPickaxeId(),
                            objectMapper,
                            configFile,
                            false);

            // Add claymore URL if missing
            configuration =
                    updateConfiguration(
                            configuration,
                            Configuration::getForemanAutominerUrl,
                            configuration.getForemanApiUrl(),
                            configuration.getForemanConfigUrl(),
                            configuration.getForemanNicehashUrl(),
                            configuration.getForemanAutominerUrl(),
                            "https://dashboard.foreman.mn/api/claymore",
                            configuration.getApiKey(),
                            configuration.getClientId(),
                            configuration.getPickaxeId(),
                            objectMapper,
                            configFile,
                            false);

            // Add pickaxe ID if missing
            configuration =
                    updateConfiguration(
                            configuration,
                            Configuration::getPickaxeId,
                            configuration.getForemanApiUrl(),
                            configuration.getForemanConfigUrl(),
                            configuration.getForemanNicehashUrl(),
                            configuration.getForemanAutominerUrl(),
                            configuration.getForemanClaymoreMultipliersUrl(),
                            configuration.getApiKey(),
                            configuration.getClientId(),
                            UUID.randomUUID().toString(),
                            objectMapper,
                            configFile,
                            false);

            final RunMe runMe = new RunMe(configuration);
            runMe.run();
        } catch (final IOException ioe) {
            LOG.error("Invalid configuration!", ioe);
        }
    }

    /**
     * Reads the {@link Configuration} from the {@link File}.
     *
     * @param objectMapper The {@link ObjectMapper} to use for parsing the
     *                     configuration file.
     * @param configFile   The configuration file.
     *
     * @return The {@link Configuration}.
     *
     * @throws IOException on failure to read the {@link Configuration}.
     */
    private static Configuration readConfiguration(
            final ObjectMapper objectMapper,
            final File configFile)
            throws IOException {
        return objectMapper.readValue(
                configFile,
                YmlConfiguration.class);
    }

    /**
     * Updates the configuration if a value is missing.
     *
     * @param configuration         The {@link Configuration}.
     * @param getter                The getter.
     * @param apiUrl                The API URL.
     * @param configUrl             The config URL.
     * @param nicehashUrl           The nicehash URL.
     * @param autominerUrl          The autominer URL.
     * @param claymoreMultiplierUrl The claymore multiplier URL.
     * @param apiKey                The API key.
     * @param clientID              The client ID.
     * @param pickaxeId             The pickaxe ID.
     * @param objectMapper          The {@link ObjectMapper}.
     * @param configFile            The config {@link File}.
     * @param forceWrite            Whether or not the value should always be
     *                              written.
     *
     * @return The updated {@link Configuration}.
     *
     * @throws IOException on failure to write.
     */
    private static Configuration updateConfiguration(
            final Configuration configuration,
            final Function<Configuration, String> getter,
            final String apiUrl,
            final String configUrl,
            final String nicehashUrl,
            final String autominerUrl,
            final String claymoreMultiplierUrl,
            final String apiKey,
            final String clientID,
            final String pickaxeId,
            final ObjectMapper objectMapper,
            final File configFile,
            final boolean forceWrite)
            throws IOException {
        Configuration newConfiguration = configuration;
        final String value = getter.apply(configuration);
        if ((value == null) || (value.isEmpty()) || forceWrite) {
            newConfiguration =
                    new YmlConfiguration(
                            apiUrl,
                            configUrl,
                            nicehashUrl,
                            autominerUrl,
                            claymoreMultiplierUrl,
                            apiKey,
                            clientID,
                            pickaxeId);
            writeConfiguration(
                    objectMapper,
                    configFile,
                    newConfiguration);
        }
        return newConfiguration;
    }

    /**
     * Writes the {@link Configuration} out to the {@link File}.
     *
     * @param objectMapper  The {@link ObjectMapper} to use for writing the
     *                      file.
     * @param configFile    The configuration file.
     * @param configuration The {@link Configuration}.
     *
     * @throws IOException on failure to write the {@link Configuration}.
     */
    private static void writeConfiguration(
            final ObjectMapper objectMapper,
            final File configFile,
            final Configuration configuration)
            throws IOException {
        final String config =
                objectMapper.writeValueAsString(
                        configuration);
        FileUtils.writeByteArrayToFile(
                configFile,
                config.getBytes(),
                false);
    }
}