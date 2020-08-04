package mn.foreman.dragonmint;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import java.util.Collections;
import java.util.Map;

/** Reboots a dragonmint miner. */
public class DragonmintRebootAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
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
