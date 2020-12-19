package mn.foreman.obelisk;

import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.model.Network;
import mn.foreman.model.error.MinerException;
import mn.foreman.obelisk.json.Dashboard;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An {@link ObeliskNetworkAction} provides an {@link AbstractNetworkAction}
 * implementation that will change the network settings in use by an obelisk
 * device.
 */
public class ObeliskNetworkAction
        extends AbstractNetworkAction {

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network)
            throws MinerException {
        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");
            final ObjectMapper objectMapper =
                    new ObjectMapper();

            final AtomicReference<String> hostname =
                    new AtomicReference<>();

            ObeliskQuery.runSessionQuery(
                    ObeliskQuery.Context
                            .<mn.foreman.obelisk.json.Network>builder()
                            .apiIp(ip)
                            .apiPort(port)
                            .uri("/api/config/network")
                            .method("GET")
                            .username(username)
                            .password(password)
                            .rawResponseCallback(s -> {
                            })
                            .responseClass(mn.foreman.obelisk.json.Network.class)
                            .responseCallback(net -> hostname.set(net.hostname))
                            .build());

            final String currentHostname = hostname.get();
            if (currentHostname != null) {
                final Map<String, String> networkConfig = new HashMap<>();
                networkConfig.put(
                        "dhcpEnabled",
                        Boolean.toString(false));
                networkConfig.put(
                        "hostname",
                        network.hostname != null && !network.hostname.isEmpty()
                                ? network.hostname
                                : currentHostname);
                networkConfig.put(
                        "ipAddress",
                        network.ipAddress);
                networkConfig.put(
                        "subnetMask",
                        network.netmask);
                networkConfig.put(
                        "gateway",
                        network.gateway);
                networkConfig.put(
                        "dnsServer",
                        network.dns);

                ObeliskQuery.runSessionQuery(
                        ObeliskQuery.Context
                                .<Dashboard>builder()
                                .apiIp(ip)
                                .apiPort(port)
                                .uri("/api/config/network")
                                .method("POST")
                                .username(username)
                                .password(password)
                                .content(objectMapper.writeValueAsString(networkConfig))
                                .rawResponseCallback(s -> {
                                })
                                .build());
            } else {
                throw new MinerException("Failed to obtain current hostname");
            }
        } catch (final Exception e) {
            throw new MinerException(e);
        }
        return true;
    }
}
