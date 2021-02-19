package mn.foreman.whatsminer.latest;

/** Provides a mechanism to see the response from whatsminer. */
public interface ResponseCallback {

    /**
     * Processes the provided response.
     *
     * @param response The response.
     */
    void sawMsg(Object response);
}
