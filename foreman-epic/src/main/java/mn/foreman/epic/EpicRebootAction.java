package mn.foreman.epic;

import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import java.util.Map;

/** Reboots an epic miner. */
public class EpicRebootAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws MinerException {
        return EpicQuery.runQuery(
                ip,
                port,
                parameters.getOrDefault("password", "letmein").toString(),
                "/reboot",
                0);
    }
}
