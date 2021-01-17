package mn.foreman.pickaxe.command;

import mn.foreman.api.model.CommandStart;
import mn.foreman.model.error.MinerException;

/** Processes the provided command. */
public interface CommandProcessor {

    /**
     * Processes the command.
     *
     * @param command The command.
     *
     * @throws MinerException on failure to run.
     */
    void runCommand(CommandStart command)
            throws MinerException;
}
