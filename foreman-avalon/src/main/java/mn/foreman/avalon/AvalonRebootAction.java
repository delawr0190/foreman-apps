package mn.foreman.avalon;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.AsicAction;

import java.util.Map;

/**
 * A {@link AvalonRebootAction} provides an action implementation that will
 * perform a reboot on an avalon miner.
 */
public class AvalonRebootAction
        implements AsicAction.CompletableAction {

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Constructor.
     *
     * @param applicationConfiguration The configuration.
     */
    public AvalonRebootAction(final ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        return AvalonUtils.query(
                ip,
                port,
                "ascset|0,reboot,0",
                (s, request) -> request.connected(),
                this.applicationConfiguration.getWriteSocketTimeout());
    }
}
