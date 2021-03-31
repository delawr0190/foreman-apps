package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;

/** A {@link BlinkStrategy} provides a mechanism for controlling LED blinking. */
public interface BlinkStrategy {

    /**
     * Starts the LED blinking.
     *
     * @param ip         The IP.
     * @param port       The port.
     * @param parameters The parameters.
     *
     * @return Whether or not the LEDs started blinking.
     *
     * @throws NotAuthenticatedException on failure to authenticate.
     * @throws MinerException            on failure.
     */
    boolean startBlink(
            String ip,
            int port,
            Map<String, Object> parameters)
            throws NotAuthenticatedException, MinerException;

    /**
     * Stops the LED from blinking
     *
     * @param ip         The IP.
     * @param port       The port.
     * @param parameters The parameters.
     *
     * @return Whether or not the LEDs stopped blinking.
     *
     * @throws NotAuthenticatedException on failure to authenticate.
     * @throws MinerException            on failure.
     */
    boolean stopBlink(
            String ip,
            int port,
            Map<String, Object> parameters)
            throws NotAuthenticatedException, MinerException;
}