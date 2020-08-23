package mn.foreman.baikal;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A {@link BaikalRebootAction} provides an action implementation that will
 * perform a reboot of a Baikal miner.
 */
public class BaikalRebootAction
        implements AsicAction.CompletableAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(BaikalRebootAction.class);

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args)
            throws MinerException {
        final AtomicBoolean success = new AtomicBoolean(false);

        BaikalUtils.query(
                ip,
                port,
                args.get("password").toString(),
                "/f_minerHardCtl.php?command=0",
                (integer, s) -> success.set(s.contains("true")));

        final boolean rebooted = success.get();
        if (rebooted) {
            LOG.info("Miner was successfully rebooted");
        } else {
            LOG.warn("Miner failed to reboot");
        }

        return rebooted;
    }
}
