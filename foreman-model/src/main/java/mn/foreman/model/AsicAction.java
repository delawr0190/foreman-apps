package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;

/**
 * An {@link AsicAction} provides an adapter mechanism to invoke specific
 * actions against an ASIC.
 */
public interface AsicAction {

    /**
     * Runs an action on an ASIC.
     *
     * @param ip       The IP.
     * @param port     The port.
     * @param args     The arguments.
     * @param callback The callback.
     *
     * @throws NotAuthenticatedException on not authenticated.
     * @throws MinerException            on unexpected exception.
     */
    void runAction(
            String ip,
            int port,
            Map<String, Object> args,
            CompletionCallback callback)
            throws NotAuthenticatedException, MinerException;

    /** A marker interface for the action to be performed. */
    interface CompletableAction {

        /**
         * Runs the action.
         *
         * @param ip   The ip.
         * @param port The port.
         * @param args The arguments.
         *
         * @return whether or not the action was successful.
         *
         * @throws NotAuthenticatedException on failure to authenticate.
         * @throws MinerException            on unexpected error.
         */
        boolean run(
                String ip,
                int port,
                Map<String, Object> args)
                throws NotAuthenticatedException, MinerException;
    }
}
