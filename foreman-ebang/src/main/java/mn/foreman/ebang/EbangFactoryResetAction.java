package mn.foreman.ebang;

import mn.foreman.model.AsicAction;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/** Factory resets an ebang miner. */
public class EbangFactoryResetAction
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
    public EbangFactoryResetAction(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> parameters) {
        final String username =
                (String) parameters.getOrDefault("username", "");
        final String password =
                (String) parameters.getOrDefault("password", "");

        final AtomicBoolean completed = new AtomicBoolean(false);

        try {
            EbangQuery.query(
                    ip,
                    port,
                    username,
                    password,
                    "/update/factory_restart",
                    null,
                    Collections.emptyList(),
                    (code, body) -> null,
                    (code, body) -> completed.set(true),
                    this.socketTimeout,
                    this.socketTimeoutUnits);
        } catch (final Exception e) {
            // Ignore
        }

        return completed.get();
    }
}
