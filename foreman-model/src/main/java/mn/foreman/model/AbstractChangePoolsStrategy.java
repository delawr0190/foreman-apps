package mn.foreman.model;

import mn.foreman.model.error.MinerException;

import java.util.List;
import java.util.Map;

/**
 * A common, base {@link mn.foreman.model.ChangePoolsStrategy} that requires
 * that there be at least 3 pools.
 */
public abstract class AbstractChangePoolsStrategy
        implements ChangePoolsStrategy {

    @Override
    public boolean change(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        if (pools.size() != 3) {
            throw new MinerException("3 pools are required");
        }

        return doChange(
                ip,
                port,
                parameters,
                pools);
    }

    /**
     * Performs the change.
     *
     * @param ip         The ip.
     * @param port       The port.
     * @param parameters The parameters.
     * @param pools      The pools.
     *
     * @return Whether or not the pools were changed.
     *
     * @throws MinerException on failure.
     */
    protected abstract boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools) throws MinerException;
}
