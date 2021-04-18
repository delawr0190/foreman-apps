package mn.foreman.goldshell;

import mn.foreman.api.model.Network;
import mn.foreman.model.AbstractNetworkAction;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/** Changes Goldshell network settings. */
public class GoldshellNetworkAction
        extends AbstractNetworkAction {

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout units. */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout units.
     */
    public GoldshellNetworkAction(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network) {
        return GoldshellQuery.runPut(
                ip,
                port,
                "/mcb/ip",
                ImmutableMap.of(
                        "useDHCP",
                        false,
                        "useAuto",
                        true,
                        "setIP",
                        ImmutableMap.of(
                                "IPv4addr",
                                network.ipAddress,
                                "Subnetmask",
                                network.netmask,
                                "Router",
                                network.gateway)),
                this.socketTimeout,
                this.socketTimeoutUnits);
    }
}
