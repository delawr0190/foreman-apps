package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** A {@link AsicAction.CompletableAction} for changing power modes. */
public abstract class AbstractPowerModeAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws NotAuthenticatedException, MinerException {
        return doChange(
                ip,
                port,
                args,
                PowerMode
                        .forMode(args.getOrDefault("mode", "").toString())
                        .orElseThrow(() -> new MinerException("Invalid power mode")));
    }

    /**
     * Performs the change.
     *
     * @param ip         The ip.
     * @param port       The port.
     * @param parameters The parameters.
     * @param mode       The mode.
     *
     * @return Whether or not the mode was changed.
     *
     * @throws MinerException on failure.
     */
    protected abstract boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final PowerMode mode) throws MinerException;

    /** An enumeration of all of the known power mode states. */
    protected enum PowerMode {

        /** Sleeping. */
        SLEEPING("sleeping"),

        /** Low power mode. */
        LOW("low"),

        /** Normal power mode. */
        NORMAL("normal"),

        /** High power mode. */
        HIGH("high");

        /** All of the supported power modes. */
        private static final ConcurrentMap<String, PowerMode> VALUES =
                new ConcurrentHashMap<>();

        static {
            for (final PowerMode powerMode : values()) {
                VALUES.put(powerMode.mode, powerMode);
            }
        }

        /** The mode. */
        private final String mode;

        /**
         * Constructor.
         *
         * @param mode The mode.
         */
        PowerMode(final String mode) {
            this.mode = mode;
        }

        /**
         * Converts the provided mode to a {@link PowerMode}.
         *
         * @param mode The mode.
         *
         * @return The new mode.
         */
        public static Optional<PowerMode> forMode(final String mode) {
            return Optional.ofNullable(VALUES.get(mode));
        }
    }
}
