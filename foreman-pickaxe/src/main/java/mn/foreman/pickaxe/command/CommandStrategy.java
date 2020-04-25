package mn.foreman.pickaxe.command;

import mn.foreman.api.ForemanApi;
import mn.foreman.model.command.CommandDone;
import mn.foreman.model.command.CommandStart;

/**
 * A {@link CommandStrategy} provides a mechanism for processing known
 * commands.
 */
public interface CommandStrategy {

    /**
     * Runs the provided command.
     *
     * @param start      The command.
     * @param foremanApi The API to use for querying Foreman.
     * @param builder    The builder for done messages.
     */
    void runCommand(
            CommandStart start,
            ForemanApi foremanApi,
            CommandDone.CommandDoneBuilder builder);
}
