package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.BlinkStrategy;
import mn.foreman.util.ParamUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A {@link BlinkStrategy} for blinking LEDs against a miner running stock
 * Antminer firmware.
 */
public class StockBlinkStrategy
        implements BlinkStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(StockBlinkStrategy.class);

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm                    The realm.
     * @param applicationConfiguration The configuration.
     */
    public StockBlinkStrategy(
            final String realm,
            final ApplicationConfiguration applicationConfiguration) {
        this.realm = realm;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public boolean startBlink(
            final String ip,
            final int port,
            final Map<String, Object> parameters) {
        return runBlinkAction(
                ip,
                port,
                parameters,
                this::startBlinkNew,
                this::startBlinkOld);
    }

    @Override
    public boolean stopBlink(
            final String ip,
            final int port,
            final Map<String, Object> parameters) {
        return runBlinkAction(
                ip,
                port,
                parameters,
                this::stopBlinkNew,
                this::stopBlinkOld);
    }

    /**
     * Determines the miner generation (new API or old) and starts or stops LED
     * blinking depending on the provided actions.
     *
     * @param ip         The ip.
     * @param port       The port.
     * @param parameters The parameters.
     * @param newAction  The action to run for newer models.
     * @param oldAction  The action to run for older models.
     *
     * @return Whether or not the action was successful.
     */
    private boolean runBlinkAction(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final BlinkLedAction newAction,
            final BlinkLedAction oldAction) {
        boolean success = false;
        final String username =
                parameters.getOrDefault("username", "root").toString();
        final String password =
                parameters.getOrDefault("password", "root").toString();
        try {
            if (AntminerUtils.isNewGen(
                    ip,
                    port,
                    this.realm,
                    username,
                    password,
                    this.applicationConfiguration.getReadSocketTimeout())) {
                success =
                        newAction.run(
                                ip,
                                port,
                                username,
                                password);
            } else {
                success =
                        oldAction.run(
                                ip,
                                port,
                                username,
                                password);
            }
        } catch (final Exception e) {
            LOG.warn("Exception occurred while blinking", e);
        }
        return success;
    }

    /**
     * Runs a start/stop blinking against against a newer miner model.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     * @param blink    Whether or not to start or stop LED blinking.
     *
     * @return Whether or not the action was successful.
     *
     * @throws Exception on failure.
     */
    private boolean runNew(
            final String ip,
            final int port,
            final String username,
            final String password,
            final boolean blink) throws Exception {
        final AtomicBoolean status = new AtomicBoolean(false);
        Query.digestPost(
                ip,
                port,
                this.realm,
                "/cgi-bin/blink.cgi",
                username,
                password,
                null,
                "{\"blink\":" + blink + "}",
                (integer, s) -> status.set(s.contains("B")),
                this.applicationConfiguration.getWriteSocketTimeout());
        return status.get();
    }

    /**
     * Runs a start/stop blinking against against an older miner model.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     * @param action   The action.
     *
     * @return Whether or not the action was successful.
     */
    private boolean runOld(
            final String ip,
            final int port,
            final String username,
            final String password,
            final String action) {
        try {
            Query.digestPost(
                    ip,
                    port,
                    this.realm,
                    "/cgi-bin/blink.cgi",
                    username,
                    password,
                    Collections.singletonList(
                            ParamUtils.toParam(
                                    "action",
                                    action)),
                    null,
                    (integer, s) -> {
                    },
                    this.applicationConfiguration.getWriteSocketTimeout());
        } catch (final Exception e) {
            // Ignore
        }
        return true;
    }

    /**
     * Starts new miner model LED blinking.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     *
     * @return Whether or not the action was successful.
     *
     * @throws Exception on failure.
     */
    private boolean startBlinkNew(
            final String ip,
            final int port,
            final String username,
            final String password) throws Exception {
        return runNew(
                ip,
                port,
                username,
                password,
                true);
    }

    /**
     * Starts older miner model LED blinking.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     *
     * @return Whether or not the action was successful.
     */
    private boolean startBlinkOld(
            final String ip,
            final int port,
            final String username,
            final String password) {
        return runOld(
                ip,
                port,
                username,
                password,
                "startBlink");
    }

    /**
     * Stops new miner model LED blinking.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     *
     * @return Whether or not the action was successful.
     *
     * @throws Exception on failure.
     */
    private boolean stopBlinkNew(
            final String ip,
            final int port,
            final String username,
            final String password) throws Exception {
        return runNew(
                ip,
                port,
                username,
                password,
                false);
    }

    /**
     * Stops older miner model LED blinking.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     *
     * @return Whether or not the action was successful.
     */
    private boolean stopBlinkOld(
            final String ip,
            final int port,
            final String username,
            final String password) {
        return runOld(
                ip,
                port,
                username,
                password,
                "stopBlink");
    }

    /**
     * Provides a functional interface for abstracting away the version
     * checking.
     */
    @FunctionalInterface
    private interface BlinkLedAction {

        /**
         * Runs the action.
         *
         * @param ip       The ip.
         * @param port     The port.
         * @param username The username.
         * @param password The password.
         *
         * @return The action result.
         *
         * @throws Exception on failure.
         */
        boolean run(
                String ip,
                int port,
                String username,
                String password)
                throws Exception;
    }
}
