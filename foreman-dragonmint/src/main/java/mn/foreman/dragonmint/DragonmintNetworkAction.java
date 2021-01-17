package mn.foreman.dragonmint;

import mn.foreman.api.model.Network;
import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A {@link DragonmintNetworkAction} provides an {@link AbstractNetworkAction}
 * implementation that will change the network settings.
 *
 * <p>Note: setting the hostname is not possible with the current DragonMint
 * API.</p>
 */
public class DragonmintNetworkAction
        extends AbstractNetworkAction {

    /** The mapper for creating JSON. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network)
            throws MinerException {
        final List<Map<String, Object>> content = new LinkedList<>();
        addParam(
                "dhcp",
                "static",
                content);
        addParam(
                "ipaddress",
                network.ipAddress,
                content);
        addParam(
                "netmask",
                network.netmask,
                content);
        addParam(
                "gateway",
                network.gateway,
                content);
        try {
            addParam(
                    "dns",
                    OBJECT_MAPPER.writeValueAsString(
                            Arrays.asList(
                                    network.dns,
                                    network.gateway)),
                    content);
        } catch (final JsonProcessingException jpe) {
            throw new MinerException("Invalid dns provided", jpe);
        }

        return DragonmintQuery.runQuery(
                ip,
                port,
                "/api/updateNetwork",
                parameters,
                content);
    }

    /**
     * Adds parameters to the params.
     *
     * @param key    The key.
     * @param value  The value.
     * @param params The params.
     */
    private static void addParam(
            final String key,
            final String value,
            final List<Map<String, Object>> params) {
        params.add(
                ImmutableMap.of(
                        "key",
                        key,
                        "value",
                        value));
    }
}
