package mn.foreman.pickaxe.command;

import java.util.List;

/** A finisher for commands that are ready to be published to Foreman. */
public interface CommandFinalizer {

    /**
     * Finishes the provided, queued commands.
     *
     * @param queuedCommands The commands.
     */
    void finish(List<QueuedCommand> queuedCommands);
}
