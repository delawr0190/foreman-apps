package mn.foreman.whatsminer.latest.error;

/**
 * An exception covering when the miner indicates that the API is not fully
 * enabled.
 */
public class PermissionDeniedException
        extends Exception {

    /** Constructor. */
    public PermissionDeniedException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message The message.
     */
    public PermissionDeniedException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message The message.
     * @param cause   The cause.
     */
    public PermissionDeniedException(
            final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause The cause.
     */
    public PermissionDeniedException(final Throwable cause) {
        super(cause);
    }

}
