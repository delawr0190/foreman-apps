package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A {@link AsicAction.CompletableAction} implementation that parses a blink
 * configuration.
 */
public class BlinkAction
        implements AsicAction {

    /** The strategy to use for blinking. */
    private final BlinkStrategy blinkStrategy;

    /** The thread pool for stopping the LEDs from blinking. */
    private final ScheduledExecutorService threadPool;

    /**
     * Constructor.
     *
     * @param threadPool    The thread pool.
     * @param blinkStrategy The strategy to use for blinking LEDs.
     */
    public BlinkAction(
            final ScheduledExecutorService threadPool,
            final BlinkStrategy blinkStrategy) {
        this.threadPool = threadPool;
        this.blinkStrategy = blinkStrategy;
    }

    @Override
    public void runAction(
            final String ip,
            final int port,
            final Map<String, Object> args,
            final CompletionCallback callback)
            throws NotAuthenticatedException, MinerException {
        final int duration =
                Integer.parseInt(
                        args.getOrDefault(
                                "blinkDurationInSeconds",
                                "60").toString());
        if (this.blinkStrategy.startBlink(
                ip,
                port,
                args)) {
            this.threadPool.schedule(
                    () ->
                            stopBlinking(
                                    ip,
                                    port,
                                    args,
                                    callback),
                    duration,
                    TimeUnit.SECONDS);
        } else {
            callback.failed("Failed to start LED blinking");
        }
    }

    /**
     * Stops the LEDs from blinking
     *
     * @param ip       The IP.
     * @param port     The port.
     * @param args     The args.
     * @param callback The completion callback.
     */
    private void stopBlinking(
            final String ip,
            final int port,
            final Map<String, Object> args,
            final CompletionCallback callback) {
        try {
            if (this.blinkStrategy.stopBlink(
                    ip,
                    port,
                    args)) {
                callback.success();
            } else {
                callback.failed("Failed to stop blinking");
            }
        } catch (final Exception e) {
            callback.failed("Error occurred while stopping");
        }
    }
}
