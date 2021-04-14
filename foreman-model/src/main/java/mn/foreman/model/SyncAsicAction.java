package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/** An {@link AsicAction} that will complete immediately. */
public class SyncAsicAction
        implements AsicAction {

    /** The action to complete. */
    private final CompletableAction completableAction;

    /** The delay. */
    private final int delay;

    /** The delay units. */
    private final TimeUnit delayUnits;

    /**
     * Constructor.
     *
     * @param completableAction The action to complete.
     * @param delay             The delay.
     * @param delayUnits        The delay units.
     */
    public SyncAsicAction(
            final CompletableAction completableAction,
            final int delay,
            final TimeUnit delayUnits) {
        this.completableAction = completableAction;
        this.delay = delay;
        this.delayUnits = delayUnits;
    }

    /**
     * Constructor.
     *
     * @param completableAction The action to complete.
     */
    public SyncAsicAction(final CompletableAction completableAction) {
        this(
                completableAction,
                0,
                TimeUnit.SECONDS);
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
            try {
                this.delayUnits.sleep(this.delay);
            } catch (final InterruptedException e) {
                // Ignore
            }
            callback.success();
        } else {
            callback.failed("Failed to perform action");
        }
    }
}