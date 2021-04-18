package mn.foreman.goldshell;

import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link GoldshellFactoryResetAction} provides an {@link
 * AbstractChangePoolsAction} implementation that will factory reset a goldshell
 * device.
 */
public class GoldshellFactoryResetAction
        implements AsicAction.CompletableAction {

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout units. */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout units.
     */
    public GoldshellFactoryResetAction(
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
        return GoldshellQuery.runPut(
                ip,
                port,
                "/mcb/facrst",
                null,
                this.socketTimeout,
                this.socketTimeoutUnits);
    }
}