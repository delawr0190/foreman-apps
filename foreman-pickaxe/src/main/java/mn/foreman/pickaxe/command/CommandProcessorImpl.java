package mn.foreman.pickaxe.command;

import mn.foreman.api.ForemanApi;
import mn.foreman.model.command.CommandDone;
import mn.foreman.model.command.CommandStart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/** Processes the starts that were issued by Foreman. */
public class CommandProcessorImpl
        implements CommandProcessor {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(CommandProcessorImpl.class);

    /** The api handler. */
    private final ForemanApi foremanApi;

    /**
     * Constructor.
     *
     * @param foremanApi The API handler.
     */
    public CommandProcessorImpl(final ForemanApi foremanApi) {
        this.foremanApi = foremanApi;
    }

    @Override
    public void runCommand(final CommandStart command) {
        LOG.debug("Processing command {}", command);
        final String action = command.command;

        final Optional<Command> commandType =
                Command.forName(action);
        if (commandType.isPresent()) {
            runKnownCommand(
                    commandType.get(),
                    command);
        } else {
            LOG.warn("Unknown command type: {}", action);
        }
    }

    /**
     * Runs a known command.
     *
     * @param command The command to run.
     * @param start   The start that was issued.
     */
    private void runKnownCommand(
            final Command command,
            final CommandStart start) {
        final CommandDone.CommandDoneBuilder builder =
                CommandDone
                        .builder()
                        .command(start.command);
        try {
            if (this.foremanApi
                    .pickaxe()
                    .commandStarted(start)
                    .isPresent()) {
                command
                        .getStrategy()
                        .runCommand(
                                start,
                                this.foremanApi,
                                builder,
                                new CommandCallback(start));
            }
        } catch (final Exception me) {
            LOG.warn("Exception occurred while processing command", me);
        }
    }

    /** A callback for when the command is completed. */
    private class CommandCallback
            implements CommandStrategy.Callback {

        /** The start command. */
        private final CommandStart start;

        /**
         * Constructor.
         *
         * @param start The start command.
         */
        CommandCallback(final CommandStart start) {
            this.start = start;
        }

        @Override
        public void done(final CommandDone done) {
            CommandProcessorImpl.this.foremanApi
                    .pickaxe()
                    .commandDone(
                            done,
                            this.start.id);
        }
    }
}