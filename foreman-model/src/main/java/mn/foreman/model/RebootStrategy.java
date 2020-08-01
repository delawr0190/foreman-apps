package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;

/** A {@link RebootStrategy} provides a strategy for rebooting a miner. */
public interface RebootStrategy {

    /**
     * Reboots the miner.
     *
     * @param ip           The miner IP.
     * @param port         The miner port.
     * @param parameters   The parameters.
     * @param doneCallback A callback for processing the status of the reboot
     *                     when it's done.
     *
     * @throws NotAuthenticatedException on failure to authenticate
     * @throws MinerException            on unexpected error
     */
    void reboot(
            String ip,
            int port,
            Map<String, Object> parameters,
            Callback doneCallback)
            throws NotAuthenticatedException, MinerException;

    /** A callback for performing actions once the reboot is done. */
    interface Callback {

        /**
         * Completes the callback.
         *
         * @param message The message.
         */
        void failed(String message);

        /** Completes the callback. */
        void success();
    }
}