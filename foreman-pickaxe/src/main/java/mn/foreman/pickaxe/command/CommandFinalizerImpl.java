package mn.foreman.pickaxe.command;

import mn.foreman.api.ForemanApi;
import mn.foreman.api.endpoints.pickaxe.Pickaxe;
import mn.foreman.api.model.*;

import java.util.LinkedList;
import java.util.List;

/** Completes batches of commands. */
public class CommandFinalizerImpl
        implements CommandFinalizer {

    /** The Foreman API. */
    private final ForemanApi foremanApi;

    /**
     * Constructor.
     *
     * @param foremanApi The Foreman API.
     */
    public CommandFinalizerImpl(final ForemanApi foremanApi) {
        this.foremanApi = foremanApi;
    }

    @Override
    public void finish(final List<QueuedCommand> queuedCommands) {
        final List<CommandStartBatch.BatchedStart> batchedStarts =
                new LinkedList<>();
        final List<CommandUpdateBatch.BatchedUpdate> batchedUpdates =
                new LinkedList<>();
        final List<CommandDoneBatch.BatchedDone> batchedCompletions =
                new LinkedList<>();
        partition(
                queuedCommands,
                batchedStarts,
                batchedUpdates,
                batchedCompletions);

        final Pickaxe pickaxe =
                this.foremanApi.pickaxe();

        if (!batchedStarts.isEmpty()) {
            pickaxe
                    .commandStartedBatch(
                            CommandStartBatch
                                    .builder()
                                    .commands(batchedStarts)
                                    .build());
        }
        if (!batchedUpdates.isEmpty()) {
            pickaxe
                    .commandUpdateBatch(
                            CommandUpdateBatch
                                    .builder()
                                    .commands(batchedUpdates)
                                    .build());
        }
        if (!batchedCompletions.isEmpty()) {
            pickaxe
                    .commandDoneBatch(
                            CommandDoneBatch
                                    .builder()
                                    .commands(batchedCompletions)
                                    .build());
        }
    }

    /**
     * Partitions the provided commands.
     *
     * @param queuedCommands     The commands to partition.
     * @param batchedStarts      The starts.
     * @param batchedUpdates     The updates.
     * @param batchedCompletions The completions.
     */
    private void partition(
            final List<QueuedCommand> queuedCommands,
            final List<CommandStartBatch.BatchedStart> batchedStarts,
            final List<CommandUpdateBatch.BatchedUpdate> batchedUpdates,
            final List<CommandDoneBatch.BatchedDone> batchedCompletions) {
        for (final QueuedCommand queuedCommand : queuedCommands) {
            final Command command = queuedCommand.getCommand();
            if (command instanceof CommandStart) {
                batchedStarts.add(
                        CommandStartBatch.BatchedStart
                                .builder()
                                .commandId(queuedCommand.getCommandId())
                                .start((CommandStart) command)
                                .build());
            } else if (command instanceof CommandUpdate) {
                batchedUpdates.add(
                        CommandUpdateBatch.BatchedUpdate
                                .builder()
                                .commandId(queuedCommand.getCommandId())
                                .update((CommandUpdate) command)
                                .build());
            } else if (command instanceof CommandDone) {
                batchedCompletions.add(
                        CommandDoneBatch.BatchedDone
                                .builder()
                                .commandId(queuedCommand.getCommandId())
                                .done((CommandDone) command)
                                .build());
            }
        }
    }
}
