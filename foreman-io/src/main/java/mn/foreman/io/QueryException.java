package mn.foreman.io;

/** A {@link QueryException} represents a failed HTTP operation. */
public class QueryException
        extends Exception {

    /**
     * Constructor.
     *
     * @param message The message.
     */
    public QueryException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message The message.
     * @param cause   The cause.
     */
    public QueryException(
            final String message,
            final Throwable cause) {
        super(
                message,
                cause);
    }

    /**
     * Constructor.
     *
     * @param cause The cause.
     */
    public QueryException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message            The message.
     * @param cause              The cause.
     * @param enableSuppression  Whether or not to enable suppression.
     * @param writableStackTrace Whether or not the stack is writable.
     */
    protected QueryException(
            final String message,
            final Throwable cause,
            final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(
                message,
                cause,
                enableSuppression,
                writableStackTrace);
    }
}
