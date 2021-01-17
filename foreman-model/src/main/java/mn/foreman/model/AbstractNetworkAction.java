package mn.foreman.model;

import mn.foreman.api.model.Network;
import mn.foreman.model.error.MinerException;

import java.util.Map;

/**
 * A {@link AsicAction.CompletableAction} implementation that parses network
 * configurations.
 */
public abstract class AbstractNetworkAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws MinerException {
        return doChange(
                ip,
                port,
                parameters,
                toNetwork(parameters));
    }

    /**
     * Performs the change.
     *
     * @param ip         The ip.
     * @param port       The port.
     * @param parameters The parameters.
     * @param network    The network.
     *
     * @return Whether or not the network was changed.
     *
     * @throws MinerException on failure.
     */
    protected abstract boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network) throws MinerException;

    /**
     * Converts the provided command args to a new network.
     *
     * @param args The arguments to inspect.
     *
     * @return The new network.
     */
    @SuppressWarnings("unchecked")
    private static Network toNetwork(final Map<String, Object> args) {
        final Map<String, String> network =
                (Map<String, String>) args.get("network");
        return Network
                .builder()
                .hostname(network.getOrDefault("hostname", ""))
                .gateway(network.getOrDefault("gateway", ""))
                .netmask(network.getOrDefault("netmask", ""))
                .ipAddress(network.getOrDefault("ipAddress", ""))
                .dns(network.getOrDefault("dns", ""))
                .build();
    }
}
