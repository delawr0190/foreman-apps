package mn.foreman.antminer;

import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.error.MinerException;

import java.util.Map;

/**
 * An {@link AbstractChangePoolsAction} implementation that will change the
 * password on an Antminer running bOS.
 */
public class BraiinsPasswordAction
        extends AbstractPasswordAction {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword)
            throws MinerException {
        throw new MinerException("bOS password changes not supported");
    }
}
