package mn.foreman.antminer;

import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.AbstractPowerModeAction;
import mn.foreman.model.error.MinerException;

import java.util.Map;

/**
 * An {@link AbstractChangePoolsAction} implementation that will change the
 * power mode on an Antminer running bOS.
 */
public class BraiinsPowerModeAction
        extends AbstractPowerModeAction {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final PowerMode powerMode)
            throws MinerException {
        throw new MinerException("bOS power changes not supported");
    }
}
