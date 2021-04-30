package mn.foreman.avalon;

import mn.foreman.model.AsicAction;

import java.util.Map;

/**
 * A {@link AvalonRebootAction} provides an action implementation that will
 * perform a reboot on an avalon miner.
 */
public class AvalonRebootAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        return AvalonUtils.query(
                ip,
                port,
                "ascset|0,reboot,0",
                (s, request) -> request.connected());
    }
}
