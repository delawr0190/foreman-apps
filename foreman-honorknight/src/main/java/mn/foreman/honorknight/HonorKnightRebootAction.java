package mn.foreman.honorknight;

import mn.foreman.model.AsicAction;

import java.util.Map;

/**
 * A {@link HonorKnightRebootAction} provides an {@link AsicAction.CompletableAction}
 * implementation that will reboot a honorknight device.
 */
public class HonorKnightRebootAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        return HonorKnightQuery.query(
                ip,
                port,
                "/api/reboot",
                args);
    }
}
