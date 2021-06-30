package mn.foreman.avalon;

import mn.foreman.api.model.Network;
import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.AsicAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * An {@link AbstractNetworkAction} implementation that will change the network
 * settings on an Avalon miner.
 */
public class AvalonNetworkAction
        extends AbstractNetworkAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AvalonNetworkAction.class);

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /** The action to run when performing a reboot. */
    private final AsicAction.CompletableAction rebootAction;

    /**
     * Constructor.
     *
     * @param rebootAction             The action to run when performing a
     *                                 reboot.
     * @param applicationConfiguration The configuration.
     */
    public AvalonNetworkAction(
            final AsicAction.CompletableAction rebootAction,
            final ApplicationConfiguration applicationConfiguration) {
        this.rebootAction = rebootAction;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network) {
        final ApplicationConfiguration.SocketConfig socketConfig =
                this.applicationConfiguration.getWriteSocketTimeout();
        boolean success =
                AvalonUtils.query(
                        ip,
                        port,
                        String.format(
                                "ascset|0,ip,s,%s,%s,%s",
                                network.ipAddress,
                                network.netmask,
                                network.gateway),
                        (s, request) -> request.connected(),
                        socketConfig);
        if (success) {
            success =
                    AvalonUtils.query(
                            ip,
                            port,
                            String.format(
                                    "ascset|0,dns,%s,%s",
                                    network.dns,
                                    network.dns),
                            (s, request) -> request.connected(),
                            socketConfig);
        }
        if (success) {
            try {
                success =
                        this.rebootAction.run(
                                ip,
                                port,
                                parameters);
            } catch (final Exception e) {
                LOG.warn("Exception occurred", e);
            }
        }

        return success;
    }
}
