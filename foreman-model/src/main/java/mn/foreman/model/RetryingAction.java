package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/** Continuously retries an action a fixed number of times until successful. */
public class RetryingAction
        implements AsicAction.CompletableAction {

    /** The backoff. */
    private final int backoff;

    /** The backoff units. */
    private final TimeUnit backoffUnits;

    /** The max count. */
    private final int count;

    /** The real. */
    private final AsicAction.CompletableAction real;

    /**
     * Constructor.
     *
     * @param count        The count.
     * @param backoff      The backoff.
     * @param backoffUnits The units.
     * @param real         The real.
     */
    public RetryingAction(
            final int count,
            final int backoff,
            final TimeUnit backoffUnits,
            final AsicAction.CompletableAction real) {
        this.count = count;
        this.backoff = backoff;
        this.backoffUnits = backoffUnits;
        this.real = real;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws NotAuthenticatedException, MinerException {
        int counter = 0;
        while (counter++ <= this.count) {
            try {
                if (this.real.run(ip, port, args)) {
                    return true;
                }
                this.backoffUnits.sleep(this.backoff);
            } catch (final Exception e) {
                // Ignore and go again
            }
        }
        return false;
    }
}
