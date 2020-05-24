package mn.foreman.pickaxe.command;

import mn.foreman.api.ForemanApi;
import mn.foreman.model.command.CommandDone;
import mn.foreman.model.command.CommandStart;
import mn.foreman.model.command.DoneStatus;

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
                try {
                    command
                            .getStrategy()
                            .runCommand(
                                    start,
                                    this.foremanApi,
                                    builder);
                } finally {
                    this.foremanApi
                            .pickaxe()
                            .commandDone(
                                    builder.status(
                                            CommandDone.Status
                                                    .builder()
                                                    .type(DoneStatus.SUCCESS)
                                                    .build())
                                            .build(),
                                    start.id);
                }
            }
        } catch (final Exception me) {
            LOG.warn("Exception occurred while processing command", me);
        }
    }
}