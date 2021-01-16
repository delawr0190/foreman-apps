package mn.foreman.api.ping;

/** Pings the dashboard to verify the credentials worked. */
public interface Ping {

    /**
     * Pings the dashboard to validate connectivity.
     *
     * @return Whether or not the dashboard was reachable.
     */
    boolean ping();

    /**
     * Pings the dashboard to validate auth against the client.
     *
     * @return The client.
     */
    boolean pingClient();
}
