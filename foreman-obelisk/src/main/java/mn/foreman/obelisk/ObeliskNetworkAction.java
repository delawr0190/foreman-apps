package mn.foreman.obelisk;

import mn.foreman.api.model.Network;
import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An {@link ObeliskNetworkAction} provides an {@link AbstractNetworkAction}
 * implementation that will change the network settings in use by an obelisk
 * device.
 */
public class ObeliskNetworkAction
        extends AbstractNetworkAction {

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     * @param objectMapper       The json mapper.
     */
    public ObeliskNetworkAction(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final ObjectMapper objectMapper) {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network)
            throws MinerException {
        boolean result;
        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");

            final AtomicReference<String> hostname =
                    new AtomicReference<>();

            ObeliskQuery.runSessionQuery(
                    ip,
                    port,
                    "/api/config/network",
                    false,
                    username,
                    password,
                    this.socketTimeout,
                    this.socketTimeoutUnits,
                    Collections.emptyList(),
                    (code, body) ->
                            this.objectMapper.readValue(
                                    body,
                                    mn.foreman.obelisk.json.Network.class),
                    null,
                    network1 -> hostname.set(network1.hostname),
                    new HashMap<>());

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

                result =
                        ObeliskQuery.runSessionQuery(
                                ip,
                                port,
                                "/api/config/network",
                                true,
                                username,
                                password,
                                this.socketTimeout,
                                this.socketTimeoutUnits,
                                Collections.emptyList(),
                                null,
                                this.objectMapper.writeValueAsString(networkConfig),
                                s -> {
                                },
                                new HashMap<>());
            } else {
                throw new MinerException("Failed to obtain current hostname");
            }
        } catch (final Exception e) {
            throw new MinerException(e);
        }
        return result;
    }
}
