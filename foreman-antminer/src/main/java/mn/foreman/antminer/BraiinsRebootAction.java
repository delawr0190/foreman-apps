package mn.foreman.antminer;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * A {@link BraiinsRebootAction} provides an action implementation that will
 * perform a reboot on a bOS miner over SSH.
 */
public class BraiinsRebootAction
        implements AsicAction.CompletableAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(BraiinsRebootAction.class);

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws MinerException {
        BraiinsUtils.runMinerCommand(
                ip,
                args.getOrDefault("username", "").toString(),
                args.getOrDefault("password", "").toString(),
                "reboot",
                s -> {
                });
        LOG.info("Miner has rebooted");
        return true;
    }
}
