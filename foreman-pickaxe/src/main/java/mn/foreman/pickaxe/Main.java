package mn.foreman.pickaxe;

import mn.foreman.pickaxe.configuration.Configuration;
import mn.foreman.pickaxe.configuration.ConfigurationFactory;
import mn.foreman.pickaxe.configuration.yml.YmlConfigurationFactory;
import mn.foreman.pickaxe.run.RunMe;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/** The application entry point. */
public class Main {

    /** The config file option. */
    private static final String CONFIG_OPTION = "c";

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Main.class);

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     */
    public static void main(final String[] args) {
        final CommandLineParser parser = new DefaultParser();

        final Options options = new Options();
        options.addOption(CONFIG_OPTION, true, "Set config file path");

        try {
            final CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption(CONFIG_OPTION)) {
                final String config =
                        commandLine.getOptionValue(CONFIG_OPTION).trim();
                if (!config.isEmpty()) {
                    final ConfigurationFactory configurationFactory =
                            new YmlConfigurationFactory();
                    final Configuration configuration =
                            configurationFactory.load(config);

                    final RunMe runMe = new RunMe(configuration);
                    runMe.run();
                } else {
                    LOG.error("Missing config file!");
                }
            } else {
                LOG.error("Missing config file!");
            }
        } catch (final ParseException | IOException e) {
            LOG.error("Exception occurred while starting", e);
        }
    }
}