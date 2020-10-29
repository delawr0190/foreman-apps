package mn.foreman.dragonmint;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import java.util.Collections;
import java.util.Map;

/** Factory resets a dragonmint miner. */
public class DragonmintFactoryResetAction
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
                "/api/factoryReset",
                parameters,
                Collections.emptyList());
    }
}
