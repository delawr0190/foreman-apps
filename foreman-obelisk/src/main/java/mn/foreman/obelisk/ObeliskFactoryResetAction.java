package mn.foreman.obelisk;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/** Reboots an obelisk. */
public class ObeliskFactoryResetAction
        implements AsicAction.CompletableAction {

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     */
    public ObeliskFactoryResetAction(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws MinerException {
        boolean result;
        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");
            result =
                    ObeliskQuery.runSessionQuery(
                            ip,
                            port,
                            "/api/action/resetMinerConfig",
                            true,
                            username,
                            password,
                            this.socketTimeout,
                            this.socketTimeoutUnits,
                            Collections.emptyList(),
                            (code, body) -> null,
                            null,
                            o -> {
                            },
                            new HashMap<>());
        } catch (final Exception e) {
            throw new MinerException(e);
        }
        return result;
    }
}