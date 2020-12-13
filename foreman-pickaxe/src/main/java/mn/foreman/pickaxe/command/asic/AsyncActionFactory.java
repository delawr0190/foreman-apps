package mn.foreman.pickaxe.command.asic;

import mn.foreman.model.AsicAction;
import mn.foreman.model.AsyncAsicAction;
import mn.foreman.model.MinerFactory;
import mn.foreman.model.MinerID;
import mn.foreman.model.cache.StatsCache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/** A factory for making new {@link AsyncAsicAction actions}. */
class AsyncActionFactory {

    /**
     * Creates a new async action with defaults.
     *
     * @param executor          The executor.
     * @param blacklist         The blacklist.
     * @param statsCache        The stats cache.
     * @param minerFactory      The factory.
     * @param completableAction The action.
     * @param paramHook         The param hook.
     *
     * @return The new async action.
     */
    static AsyncAsicAction toAsync(
            final ScheduledExecutorService executor,
            final Set<MinerID> blacklist,
            final StatsCache statsCache,
            final MinerFactory minerFactory,
            final AsicAction.CompletableAction completableAction,
            final BiFunction<
                    Map<String, Object>,
                    Map<String, Object>,
                    Map<String, Object>> paramHook) {
        return new AsyncAsicAction(
                2,
                2,
                TimeUnit.MINUTES,
                executor,
                minerFactory,
                completableAction,
                blacklist,
                statsCache,
                paramHook);
    }

    /**
     * Creates a new async action with defaults.
     *
     * @param executor          The executor.
     * @param blacklist         The blacklist.
     * @param statsCache        The stats cache.
     * @param minerFactory      The factory.
     * @param completableAction The action.
     *
     * @return The new async action.
     */
    static AsyncAsicAction toAsync(
            final ScheduledExecutorService executor,
            final Set<MinerID> blacklist,
            final StatsCache statsCache,
            final MinerFactory minerFactory,
            final AsicAction.CompletableAction completableAction) {
        return toAsync(
                executor,
                blacklist,
                statsCache,
                minerFactory,
                completableAction,
                (originalParams, newParams) -> newParams);
    }
}
