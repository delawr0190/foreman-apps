package mn.foreman.model.error;

/**
 * An {@link EmptySiteException} provides an {@link Exception} that represents
 * no miner being present at the queried IP and port.
 */
public class EmptySiteException
        extends Exception {

    /** Constructor. */
    public EmptySiteException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message The message.
     */
    public EmptySiteException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message The message.
     * @param cause   The cause.
     */
    public EmptySiteException(
            final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause
     */
    public EmptySiteException(
            final Throwable cause) {
        super(cause);
    }
}
