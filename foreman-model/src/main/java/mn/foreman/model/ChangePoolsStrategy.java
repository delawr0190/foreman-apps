package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.List;
import java.util.Map;

/**
 * A {@link ChangePoolsStrategy} provides a strategy for changing pools on a
 * running miner.
 */
public interface ChangePoolsStrategy {

    /**
     * Changes the pools.
     *
     * @param ip         The miner IP.
     * @param port       The miner port.
     * @param parameters The parameters.
     * @param pools      The new pools.
     *
     * @return Whether or not the change was successful.
     *
     * @throws NotAuthenticatedException on failure to authenticate
     * @throws MinerException            on unexpected error
     */
    boolean change(
            String ip,
            int port,
            Map<String, Object> parameters,
            List<Pool> pools)
            throws NotAuthenticatedException, MinerException;
}