package mn.foreman.api;

import mn.foreman.model.command.CommandDone;
import mn.foreman.model.command.CommandStart;
import mn.foreman.model.command.CommandUpdate;
import mn.foreman.model.command.Commands;

import java.util.Optional;

/**
 * A {@link Pickaxe} provides a handler for interacting with the
 * <code>/api/pickaxe</code> Foreman API endpoint.
 */
public interface Pickaxe {

    /**
     * Sends a command completion to the Foreman API.
     *
     * @param done      The completed command.
     * @param commandId The referencing command ID.
     *
     * @return The command response.
     */
    Optional<CommandDone.Response> commandDone(
            CommandDone done,
            String commandId);

    /**
     * Sends a command started to the Foreman API.
     *
     * @param start The started command.
     *
     * @return The command response.
     */
    Optional<CommandStart.Response> commandStarted(
            CommandStart start);

    /**
     * Sends a command update to the Foreman API.
     *
     * @param update    The updated command.
     * @param commandId The referencing command ID.
     *
     * @return The command response.
     */
    Optional<CommandUpdate.Response> commandUpdate(
            CommandUpdate update,
            String commandId);

    /**
     * Queries for pending commands to be executed.
     *
     * @return The commands to execute, if any.
     */
    Optional<Commands> getCommands();
}
