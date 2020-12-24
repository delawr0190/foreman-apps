package mn.foreman.aixin;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import java.util.Map;

/**
 * A {@link AixinRebootAction} provides an {@link AsicAction.CompletableAction}
 * implementation that will reboot an aixin device.
 */
public class AixinRebootAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws
            NotAuthenticatedException,
            MinerException {
        return AixinQuery.query(
                ip,
                "/api/reboot",
                args);
    }
}
