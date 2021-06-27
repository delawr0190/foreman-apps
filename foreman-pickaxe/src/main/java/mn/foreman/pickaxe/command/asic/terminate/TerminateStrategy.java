package mn.foreman.pickaxe.command.asic.terminate;

import mn.foreman.api.model.CommandDone;
import mn.foreman.api.model.CommandStart;
import mn.foreman.api.model.DoneStatus;
import mn.foreman.pickaxe.command.CommandCompletionCallback;
import mn.foreman.pickaxe.command.CommandStrategy;

import com.google.common.collect.ImmutableMap;

/** A {@link TerminateStrategy} provides a mechanism for killing the JVM. */
public class TerminateStrategy
        implements CommandStrategy {

    @Override
    public void runCommand(
            final CommandStart start,
            final CommandCompletionCallback commandCompletionCallback,
            final CommandDone.CommandDoneBuilder builder) {
        commandCompletionCallback.done(
                start.id,
                builder
                        .result(
                                ImmutableMap.of(
                                        "terminate",
                                        "true"))
                        .status(
                                CommandDone.Status
                                        .builder()
                                        .type(DoneStatus.SUCCESS)
                                        .build())
                        .build());

        // Close the JVM
        System.exit(0);
    }
}
