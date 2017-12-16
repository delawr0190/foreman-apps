package mn.foreman.pickaxe;

import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.configuration.ConfigurationFactory;
import mn.foreman.pickaxe.configuration.yml.YmlConfigurationFactory;
import mn.foreman.pickaxe.run.RunMe;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

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
            final String configFile = optionSet.valueOf(configSpec);

            final ConfigurationFactory configurationFactory =
                    new YmlConfigurationFactory();
            final Configuration configuration =
                    configurationFactory.load(configFile);

            final RunMe runMe = new RunMe(configuration);
            runMe.run();
        } catch (final IOException ioe) {
            LOG.error("Missing config file!");
        }
    }
}