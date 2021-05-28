package mn.foreman.goldshell;

import mn.foreman.api.model.Network;
import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.model.ApplicationConfiguration;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/** Changes Goldshell network settings. */
public class GoldshellNetworkAction
        extends AbstractNetworkAction {

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /**
     * Constructor.
     *
     * @param configuration The configuration.
     */
    public GoldshellNetworkAction(final ApplicationConfiguration configuration) {
        this.configuration = configuration;
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
                this.configuration);
    }
}
