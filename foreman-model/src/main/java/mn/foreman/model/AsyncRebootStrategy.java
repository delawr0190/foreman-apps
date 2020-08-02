package mn.foreman.model;

import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * An {@link AsyncRebootStrategy} provides a {@link RebootStrategy}
 * implementation that will start a miner reboot and then continuously check to
 * see if the miner has recovered in a separate thread pool.
 */
public abstract class AsyncRebootStrategy
        implements RebootStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AsyncRebootStrategy.class);

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
     * @param delay        The delay.
     * @param interval     The interval.
     * @param units        The units.
     * @param threadPool   The thread pool.
     * @param minerFactory The factory for creating a miner to use to obtain
     *                     stats.
     */
    public AsyncRebootStrategy(
            final long delay,
            final long interval,
            final TimeUnit units,
            final ScheduledThreadPoolExecutor threadPool,
            final MinerFactory minerFactory) {
        this.delay = delay;
        this.interval = interval;
        this.units = units;
        this.threadPool = threadPool;
        this.minerFactory = minerFactory;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Note: overridden to invoke the {@link Callback} in a separate
     * thread when the miner has returned.</p>
     */
    @Override
    public void reboot(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Callback doneCallback)
            throws MinerException {
        if (doReboot(
                ip,
                port,
                parameters)) {
            final long deadlineInMillis =
                    System.currentTimeMillis() +
                            (Integer) parameters.get("deadlineMillis");
            final Map<String, String> newParams =
                    toParams(parameters);
            this.task =
                    this.threadPool.scheduleAtFixedRate(
                            () -> evaluateMiner(
                                    deadlineInMillis,
                                    newParams,
                                    doneCallback),
                            this.delay,
                            this.interval,
                            this.units);
        } else {
            // Didn't reboot
            doneCallback.failed("Failed to trigger reboot");
        }
    }

    /**
     * Starts a reboot operation on a miner.
     *
     * @param ip         The ip.
     * @param port       The port.
     * @param parameters The parameters.
     *
     * @return Whether or not the reboot was successfully started.
     *
     * @throws MinerException on unexpected failure
     */
    protected abstract boolean doReboot(
            String ip,
            int port,
            Map<String, Object> parameters)
            throws MinerException;

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
                (String) params.get("ip"),
                "apiPort",
                ((Integer) params.get("apiPort")).toString(),
                "username",
                (String) params.getOrDefault("username", ""),
                "password",
                (String) params.getOrDefault("password", ""));
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
            final Callback doneCallback) {
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