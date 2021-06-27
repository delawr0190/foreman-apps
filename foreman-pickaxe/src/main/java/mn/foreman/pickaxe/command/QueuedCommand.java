package mn.foreman.pickaxe.command;

import mn.foreman.api.model.Command;

import lombok.Builder;
import lombok.Data;

/** A command that's queued. */
@Data
@Builder
public class QueuedCommand {

    /** The command. */
    private final Command command;

    /** The command id. */
    private final String commandId;
}
