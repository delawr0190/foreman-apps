package mn.foreman.pickaxe.command;

import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/** Processes the starts that were issued by Foreman. */
public class CommandProcessorImpl
        implements CommandProcessor {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(CommandProcessorImpl.class);

    /** The callback. */
    private final CommandCompletionCallback commandCompletionCallback;

    /** The factory for making strategies. */
    private final StrategyFactory strategyFactory;

    /**
     * Constructor.
     *
     * @param commandCompletionCallback The callback.
     * @param strategyFactory           The factory for making strategies.
     */
    public CommandProcessorImpl(
            final CommandCompletionCallback commandCompletionCallback,
            final StrategyFactory strategyFactory) {
        this.commandCompletionCallback = commandCompletionCallback;
        this.strategyFactory = strategyFactory;
    }

    @Override
    public void runCommand(final CommandStart command) {
        LOG.debug("Processing command {}", command);
        final String action = command.command;

        final Optional<CommandStrategy> strategy =
                this.strategyFactory.forType(action);
        if (strategy.isPresent()) {
            runKnownCommand(
                    strategy.get(),
                    command);
        } else {
            LOG.warn("Unknown command type: {}", action);
        }
    }

    /**
     * Runs a known command.
     *
     * @param strategy The strategy to run.
     * @param start    The start that was issued.
     */
    private void runKnownCommand(
            final CommandStrategy strategy,
            final CommandStart start) {
        final CommandDone.CommandDoneBuilder builder =
                CommandDone
                        .builder()
                        .command(start.command);
        try {
            this.commandCompletionCallback.start(
                    start.id,
                    start);
            strategy.runCommand(
                    start,
                    this.commandCompletionCallback,
                    builder);
        } catch (final Exception me) {
            LOG.warn("Exception occurred while processing command", me);
        }
    }
}