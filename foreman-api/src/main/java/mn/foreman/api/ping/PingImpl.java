package mn.foreman.api.ping;

import mn.foreman.api.WebUtil;

/** Pings the dashboard to test the provided API credentials. */
public class PingImpl
        implements Ping {

    /** The client ID. */
    private final String clientId;

    /** The web utils. */
    private final WebUtil webUtil;

    /**
     * Constructor.
     *
     * @param clientId The client ID.
     * @param webUtil  The web utility.
     */
    public PingImpl(
            final String clientId,
            final WebUtil webUtil) {
        this.clientId = clientId;
        this.webUtil = webUtil;
    }

    @Override
    public boolean ping() {
        return this.webUtil
                .get(
                        "/api/ping",
                        false)
                .map(s -> s.toLowerCase().contains("pong"))
                .orElse(false);
    }

    @Override
    public boolean pingClient() {
        return this.webUtil
                .get(
                        String.format(
                                "/api/ping/%s",
                                this.clientId))
                .map(s -> s.toLowerCase().contains("pong"))
                .orElse(false);
    }
}
