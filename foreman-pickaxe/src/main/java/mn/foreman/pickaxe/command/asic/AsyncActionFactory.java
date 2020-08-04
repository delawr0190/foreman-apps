package mn.foreman.pickaxe.command.asic;

import mn.foreman.model.AsicAction;
import mn.foreman.model.AsyncAsicAction;
import mn.foreman.model.MinerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** A factory for making new {@link AsyncAsicAction actions}. */
class AsyncActionFactory {

    /**
     * Creates a new async action with defaults.
     *
     * @param executor          The exceturo.
     * @param minerFactory      The factory.
     * @param completableAction The action.
     *
     * @return The new async action.
     */
    static AsyncAsicAction toAsync(
            final ScheduledThreadPoolExecutor executor,
            final MinerFactory minerFactory,
            final AsicAction.CompletableAction completableAction) {
        return new AsyncAsicAction(
                30,
                30,
                TimeUnit.SECONDS,
                executor,
                minerFactory,
                completableAction);
    }
}
