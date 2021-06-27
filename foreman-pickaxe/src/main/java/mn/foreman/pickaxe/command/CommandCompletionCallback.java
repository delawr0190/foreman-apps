package mn.foreman.pickaxe.command;

import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.CommandUpdate;

/**
 * Provides a callback mechanism for post-processing of commands and their
 * lifecycle.
 */
public interface CommandCompletionCallback {

    /**
     * Processes the provided completion.
     *
     * @param commandId The command.
     * @param done      The completion.
     */
    void done(
            String commandId,
            CommandDone done);

    /**
     * Processes the provided start.
     *
     * @param commandId The command.
     * @param start     The start.
     */
    void start(
            String commandId,
            CommandStart start);

    /**
     * Processes the provided update.
     *
     * @param commandId The command.
     * @param update    The update.
     */
    void update(
            String commandId,
            CommandUpdate update);
}
