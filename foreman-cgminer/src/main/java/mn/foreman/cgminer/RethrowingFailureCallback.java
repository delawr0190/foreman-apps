package mn.foreman.cgminer;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

/** Rethrows the caught exception. */
public class RethrowingFailureCallback
        implements RequestFailureCallback {

    @Override
    public void failed(
            final MinerStats.Builder builder,
            final MinerException minerException)
            throws MinerException {
        // Do nothing but rethrow
        throw minerException;
    }
}
