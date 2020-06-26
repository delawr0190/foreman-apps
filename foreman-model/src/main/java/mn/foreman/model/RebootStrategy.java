package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;

/** A {@link RebootStrategy} provides a strategy for rebooting a miner. */
public interface RebootStrategy {

    /**
     * Reboots the miner.
     *
     * @param ip         The miner IP.
     * @param port       The miner port.
     * @param parameters The parameters.
     *
     * @return Whether or not the reboot was successful.
     *
     * @throws NotAuthenticatedException on failure to authenticate
     * @throws MinerException            on unexpected error
     */
    boolean reboot(
            String ip,
            int port,
            Map<String, Object> parameters)
            throws NotAuthenticatedException, MinerException;
}