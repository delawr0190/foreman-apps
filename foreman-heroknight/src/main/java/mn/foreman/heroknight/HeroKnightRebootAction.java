package mn.foreman.heroknight;

import mn.foreman.model.AsicAction;

import java.util.Map;

/**
 * A {@link HeroKnightRebootAction} provides an {@link AsicAction.CompletableAction}
 * implementation that will reboot a heroknight device.
 */
public class HeroKnightRebootAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        return HeroKnightQuery.query(
                ip,
                port,
                "/api/reboot",
                args);
    }
}
