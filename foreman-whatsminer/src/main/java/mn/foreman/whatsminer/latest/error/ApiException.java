package mn.foreman.whatsminer.latest.error;

/**
 * A {@link ApiException} represents an error that occurred while communicating
 * with a remote miner.
 */
public class ApiException
        extends Exception {

    /** Constructor. */
    public ApiException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message The message.
     */
    public ApiException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message The message.
     * @param cause   The cause.
     */
    public ApiException(
            final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause The cause.
     */
    public ApiException(final Throwable cause) {
        super(cause);
    }
}
