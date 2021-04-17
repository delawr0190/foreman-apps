package mn.foreman.epic;

import mn.foreman.model.AbstractPowerModeAction;
import mn.foreman.model.error.MinerException;

import java.util.Map;

/** Sets the epic power mode. */
public class EpicPowerModeAction
        extends AbstractPowerModeAction {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final PowerMode mode) throws MinerException {
        final String password =
                parameters.getOrDefault(
                        "password",
                        "letmein").toString();
        return EpicQuery.runQuery(
                ip,
                port,
                password,
                "/mode",
                mode == PowerMode.NORMAL
                        ? "normal"
                        : "efficiency");
    }
}
