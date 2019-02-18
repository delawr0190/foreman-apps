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

            final String pickaxeId = configuration.getPickaxeId();

            if ((pickaxeId == null) || pickaxeId.isEmpty()) {
                // Auto-generate a pickaxe ID - must be the first run
                configuration =
                        new YmlConfiguration(
                                configuration.getForemanApiUrl(),
                                configuration.getForemanConfigUrl(),
                                configuration.getForemanNicehashUrl(),
                                configuration.getApiKey(),
                                configuration.getClientId(),
                                UUID.randomUUID().toString());
                writeConfiguration(
                        objectMapper,
                        configFile,
                        configuration);
            }

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