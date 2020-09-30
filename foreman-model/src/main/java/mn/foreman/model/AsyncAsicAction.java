package mn.foreman.model;

import mn.foreman.model.cache.StatsCache;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;
import mn.foreman.model.miners.MinerStats;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * An {@link AsyncAsicAction} provides an {@link AsicAction} that will perform
 * an action against and ASIC and then only invoke the {@link
 * #completableAction} when the device has rebooted and is back up, running, and
 * mining.
 */
public class AsyncAsicAction
        implements AsicAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AsyncAsicAction.class);

    /** The blacklist. */
    private final Set<MinerID> blacklist;

    /** The action to perform. */
    private final CompletableAction completableAction;

    /** The delay. */
    private final long delay;

    /** The interval. */
    private final long interval;

    /** The factory for creating miners that can be used to obtain metrics. */
    private final MinerFactory minerFactory;

    /** The cache. */
    private final StatsCache statsCache;

    /** The thread pool. */
    private final ScheduledExecutorService threadPool;

    /** The units. */
    private final TimeUnit units;

    /**
     * The task that's checking if the miner has returned, running in {@link
     * #threadPool}.
     */
    private Future<?> task;

    /**
     * Constructor.
     *
     * @param delay             The delay.
     * @param interval          The interval.
     * @param units             The units.
     * @param threadPool        The thread pool.
     * @param minerFactory      The factory for creating a miner to use to
     *                          obtain stats.
     * @param completableAction The completable action.
     * @param blacklist         The blacklist.
     * @param statsCache        The stats cache.
     */
    public AsyncAsicAction(
            final long delay,
            final long interval,
            final TimeUnit units,
            final ScheduledExecutorService threadPool,
            final MinerFactory minerFactory,
            final CompletableAction completableAction,
            final Set<MinerID> blacklist,
            final StatsCache statsCache) {
        this.delay = delay;
        this.interval = interval;
        this.units = units;
        this.threadPool = threadPool;
        this.minerFactory = minerFactory;
        this.completableAction = completableAction;
        this.blacklist = blacklist;
        this.statsCache = statsCache;
    }

    @Override
    public void runAction(
            final String ip,
            final int port,
            final Map<String, Object> args,
            final CompletionCallback callback)
            throws NotAuthenticatedException, MinerException {
        if (this.completableAction.run(
                ip,
                port,
                args)) {
            final long deadlineInMillis =
                    System.currentTimeMillis() +
                            (Integer) args.get("deadlineMillis");
            final Map<String, Object> newParams =
                    toParams(args);
            final MinerID minerID =
                    new SimpleMinerID(
                            newParams.get("apiIp").toString(),
                            Integer.parseInt(
                                    newParams.get("apiPort").toString()));
            LOG.info("Blacklisting {} while waiting for a reboot", minerID);
            this.blacklist.add(minerID);
            this.statsCache.invalidate(minerID);

            final Miner miner = this.minerFactory.create(newParams);
            this.task =
                    this.threadPool.scheduleAtFixedRate(
                            () -> evaluateMiner(
                                    deadlineInMillis,
                                    miner,
                                    callback,
                                    minerID),
                            this.delay,
                            this.interval,
                            this.units);
        } else {
            // Didn't work
            callback.failed("Fail to perform action");
        }
    }

    /**
     * Creates params that miners typically need to be queried.
     *
     * @param params The parameters.
     *
     * @return The parameters.
     */
    private static Map<String, Object> toParams(final Map<String, Object> params) {
        return ImmutableMap.of(
                "apiIp",
                params.get("ip"),
                "apiPort",
                params.get("apiPort"),
                "username",
                params.getOrDefault("username", ""),
                "password",
                params.getOrDefault("password", ""));
    }

    /**
     * Checks to see if the miner has returned yet.
     *
     * @param deadlineInMillis The deadline.
     * @param miner            The miner.
     * @param doneCallback     The callback for when the reboot operation is
     *                         complete.
     * @param minerID          The miner ID.
     */
    private void evaluateMiner(
            final long deadlineInMillis,
            final Miner miner,
            final CompletionCallback doneCallback,
            final MinerID minerID) {
        final long now = System.currentTimeMillis();
        if (now < deadlineInMillis) {
            try {
                final MinerStats stats = miner.getStats();
                this.task.cancel(false);
                this.blacklist.remove(minerID);
                this.statsCache.add(
                        minerID,
                        stats);
                doneCallback.success();
            } catch (final Exception e) {
                // Miner isn't back yet - ignore
                LOG.info("Miner isn't back yet - waiting");
                this.blacklist.add(minerID);
            }
        } else {
            // Took too long to find the miner - abort
            this.task.cancel(false);
            doneCallback.failed("Miner never returned");
        }
    }
}
