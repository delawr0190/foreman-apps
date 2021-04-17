package mn.foreman.epic;

import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.error.MinerException;

import java.util.Map;

/**
 * A {@link EpicPasswordAction} provides an {@link AbstractPasswordAction}
 * implementation that will change the password.
 */
public class EpicPasswordAction
        extends AbstractPasswordAction {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword)
            throws MinerException {
        return EpicQuery.runQuery(
                ip,
                port,
                oldPassword,
                "/password",
                newPassword);
    }
}
