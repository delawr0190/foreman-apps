package mn.foreman.antminer.error;

/** Represents an invalid username/password combination. */
public class NotAuthorizedException
        extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message The message.
     */
    public NotAuthorizedException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message The message.
     * @param cause   The cause.
     */
    public NotAuthorizedException(
            final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause The cause.
     */
    public NotAuthorizedException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message            The message.
     * @param cause              The cause.
     * @param enableSuppression  Whether or not to enable suppression.
     * @param writableStackTrace Whether or not the stacktrace is writable.
     */
    protected NotAuthorizedException(
            final String message,
            final Throwable cause,
            final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
