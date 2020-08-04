package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
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

    /** The action to perform. */
    private final CompletableAction completableAction;

    /** The delay. */
    private final long delay;

    /** The interval. */
    private final long interval;

    /** The factory for creating miners that can be used to obtain metrics. */
    private final MinerFactory minerFactory;

    /** The thread pool. */
    private final ScheduledThreadPoolExecutor threadPool;

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
     */
    public AsyncAsicAction(
            final long delay,
            final long interval,
            final TimeUnit units,
            final ScheduledThreadPoolExecutor threadPool,
            final MinerFactory minerFactory,
            final CompletableAction completableAction) {
        this.delay = delay;
        this.interval = interval;
        this.units = units;
        this.threadPool = threadPool;
        this.minerFactory = minerFactory;
        this.completableAction = completableAction;
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
            final Map<String, String> newParams =
                    toParams(args);
            this.task =
                    this.threadPool.scheduleAtFixedRate(
                            () -> evaluateMiner(
                                    deadlineInMillis,
                                    newParams,
                                    callback),
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
    private static Map<String, String> toParams(final Map<String, Object> params) {
        return ImmutableMap.of(
                "apiIp",
                params.get("ip").toString(),
                "apiPort",
                params.get("apiPort").toString(),
                "username",
                params.getOrDefault("username", "").toString(),
                "password",
                params.getOrDefault("password", "").toString());
    }

    /**
     * Checks to see if the miner has returned yet.
     *
     * @param deadlineInMillis The deadline.
     * @param params           The parameters.
     * @param doneCallback     The callback for when the reboot operation is
     *                         complete.
     */
    private void evaluateMiner(
            final long deadlineInMillis,
            final Map<String, String> params,
            final CompletionCallback doneCallback) {
        final long now = System.currentTimeMillis();
        if (now < deadlineInMillis) {
            final Miner miner = this.minerFactory.create(params);
            try {
                miner.getStats();
                this.task.cancel(false);
                doneCallback.success();
            } catch (final Exception e) {
                // Miner isn't back yet - ignore
                LOG.info("Miner isn't back yet - waiting");
            }
        } else {
            // Took too long to find the miner - abort
            this.task.cancel(false);
            doneCallback.failed("Miner never returned");
        }
    }
}
