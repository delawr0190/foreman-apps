package mn.foreman.dragonmint;

import mn.foreman.model.RebootStrategy;
import mn.foreman.model.error.MinerException;

import java.util.Collections;
import java.util.Map;

/**
 * A {@link DragonmintRebootStrategy} provides a {@link RebootStrategy}
 * implementation that will reboot a dragonmint device.
 */
public class DragonmintRebootStrategy
        implements RebootStrategy {

    @Override
    public boolean reboot(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws MinerException {
        return DragonmintQuery.runQuery(
                ip,
                port,
                "/api/reboot",
                parameters,
                Collections.emptyList());
    }
}
