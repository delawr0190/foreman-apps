package mn.foreman.aixin;

import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.model.Network;
import mn.foreman.util.ParamUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A {@link AixinNetworkAction} provides an {@link AbstractNetworkAction}
 * implementation that will change the network in use by an aixin device.
 */
public class AixinNetworkAction
        extends AbstractNetworkAction {

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network) {
        final List<Map<String, Object>> content = new LinkedList<>();
        ParamUtils.addParam(
                "type",
                "static",
                content);
        ParamUtils.addParam(
                "ip",
                network.ipAddress,
                content);
        ParamUtils.addParam(
                "mask",
                network.netmask,
                content);
        ParamUtils.addParam(
                "gateway",
                network.gateway,
                content);
        ParamUtils.addParam(
                "dns",
                network.dns,
                content);
        return AixinQuery.query(
                ip,
                "/api/updateNetwork",
                parameters,
                content);
    }
}
