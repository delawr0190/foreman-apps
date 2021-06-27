package mn.foreman.pickaxe.command;

import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;

/**
 * A {@link CommandStrategy} provides a mechanism for processing known
 * commands.
 */
public interface CommandStrategy {

    /**
     * Runs the provided command.
     *
     * @param start                     The command.
     * @param commandCompletionCallback The callback.
     * @param builder                   The builder for done messages.
     */
    void runCommand(
            CommandStart start,
            CommandCompletionCallback commandCompletionCallback,
            CommandDone.CommandDoneBuilder builder);
}
