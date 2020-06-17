package mn.foreman.model.error;

/**
 * A {@link NotAuthenticatedException} provides an exception to indicate that
 * authentication failed while communicating with an API.
 */
public class NotAuthenticatedException
        extends Exception {

    /** Constructor. */
    public NotAuthenticatedException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message The message.
     */
    public NotAuthenticatedException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message The message.
     * @param cause   The cause.
     */
    public NotAuthenticatedException(
            final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause The cause.
     */
    public NotAuthenticatedException(final Throwable cause) {
        super(cause);
    }
}
