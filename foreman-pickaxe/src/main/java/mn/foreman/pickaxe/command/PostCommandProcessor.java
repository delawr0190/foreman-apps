package mn.foreman.pickaxe.command;

import mn.foreman.model.command.CommandStart;

/**
 * A callback mechanism for informing a listener that a command has been
 * completed.
 */
public interface PostCommandProcessor {

    /**
     * Notifies the processor of the following command being completed.
     *
     * @param start The start.
     */
    void completed(CommandStart start);
}
