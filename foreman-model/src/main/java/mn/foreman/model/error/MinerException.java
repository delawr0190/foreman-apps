package mn.foreman.model.error;

/**
 * A {@link MinerException} represents an error that occurred while
 * communicating with a remote miner.
 */
public class MinerException extends Exception {

    /** Constructor. */
    public MinerException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message The message.
     */
    public MinerException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message The message.
     * @param cause   The cause.
     */
    public MinerException(
            final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause The cause.
     */
    public MinerException(final Throwable cause) {
        super(cause);
    }
}
