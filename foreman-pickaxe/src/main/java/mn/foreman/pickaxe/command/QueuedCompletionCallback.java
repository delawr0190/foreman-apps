package mn.foreman.pickaxe.command;

import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.CommandUpdate;

import java.util.concurrent.BlockingQueue;

/** A callback for processing command completions. */
public class QueuedCompletionCallback
        implements CommandCompletionCallback {

    /** The commands. */
    private final BlockingQueue<QueuedCommand> commands;

    /**
     * Constructor.
     *
     * @param commands The commands.
     */
    public QueuedCompletionCallback(
            final BlockingQueue<QueuedCommand> commands) {
        this.commands = commands;
    }

    @Override
    public void done(
            final String commandId,
            final CommandDone done) {
        this.commands.add(
                QueuedCommand
                        .builder()
                        .commandId(commandId)
                        .command(done)
                        .build());
    }

    @Override
    public void start(
            final String commandId,
            final CommandStart start) {
        this.commands.add(
                QueuedCommand
                        .builder()
                        .commandId(commandId)
                        .command(start)
                        .build());
    }

    @Override
    public void update(
            final String commandId,
            final CommandUpdate update) {
        this.commands.add(
                QueuedCommand
                        .builder()
                        .commandId(commandId)
                        .command(update)
                        .build());
    }
}