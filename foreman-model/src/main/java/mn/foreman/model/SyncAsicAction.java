package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;

/** An {@link AsicAction} that will complete immediately. */
public class SyncAsicAction
        implements AsicAction {

    /** The action to complete. */
    private final CompletableAction completableAction;

    /**
     * Constructor.
     *
     * @param completableAction The action to complete.
     */
    public SyncAsicAction(final CompletableAction completableAction) {
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
            callback.success();
        } else {
            callback.failed("Failed to perform action");
        }
    }
}