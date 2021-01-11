package mn.foreman.antminer;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/** A strategy that performs an antminer factory reset. */
public class BraiinsFactoryResetAction
        implements AsicAction.CompletableAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(BraiinsFactoryResetAction.class);

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws MinerException {
        BraiinsUtils.runMinerCommand(
                ip,
                parameters.getOrDefault("username", "").toString(),
                parameters.getOrDefault("password", "").toString(),
                "miner factory_reset",
                s -> {
                });
        LOG.info("Factory reset has been started");
        return true;
    }
}
