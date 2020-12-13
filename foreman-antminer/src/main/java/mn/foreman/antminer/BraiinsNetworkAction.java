package mn.foreman.antminer;

import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.model.Network;
import mn.foreman.model.error.MinerException;

import java.util.Map;

/**
 * An {@link AbstractChangePoolsAction} implementation that will change the
 * network on an Antminer running bOS.
 */
public class BraiinsNetworkAction
        extends AbstractNetworkAction {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network)
            throws MinerException {
        throw new MinerException("bOS network changes not supported");
    }
}
