package mn.foreman.model;

/** A callback for performing actions once the action is done. */
public interface CompletionCallback {

    /**
     * Completes the callback.
     *
     * @param message The message.
     */
    void failed(String message);

    /** Completes the callback. */
    void success();
}