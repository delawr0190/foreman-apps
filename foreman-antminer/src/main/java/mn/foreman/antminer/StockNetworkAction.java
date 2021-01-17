package mn.foreman.antminer;

import mn.foreman.api.model.Network;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A {@link StockNetworkAction} provides a mechanism for setting the network
 * configuration of a miner running Antminer-like firmware.
 */
public class StockNetworkAction
        extends AbstractNetworkAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(StockNetworkAction.class);

    /** Mapper for reading and writing JSON. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** The key. */
    private final String key;

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm The realm.
     * @param key   The key.
     */
    public StockNetworkAction(
            final String realm,
            final String key) {
        this.realm = realm;
        this.key = key;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network)
            throws MinerException {
        final String username =
                (String) parameters.getOrDefault("username", "");
        final String password =
                (String) parameters.getOrDefault("password", "");

        List<Map<String, Object>> config = null;
        String payload = null;

        try {
            final Map<String, Object> systemInfo =
                    AntminerUtils.getConf(
                            ip,
                            port,
                            this.realm,
                            "/cgi-bin/get_system_info.cgi",
                            username,
                            password);
            final String previousHostname =
                    systemInfo
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().contains("hostname"))
                            .map(entry -> entry.getValue().toString())
                            .findFirst()
                            .orElseThrow(() -> new MinerException("Failed to obtain previous hostname"));

            final String newHostname =
                    network.hostname != null && !network.hostname.isEmpty()
                            ? network.hostname
                            : previousHostname;

            if (systemInfo.getOrDefault(
                    "minertype",
                    "").toString().contains("S19")) {
                // New miner with new firmware
                final Map<String, Object> json =
                        ImmutableMap.<String, Object>builder()
                                .put(
                                        "ipHost",
                                        newHostname)
                                .put(
                                        "ipPro",
                                        2)
                                .put(
                                        "ipAddress",
                                        network.ipAddress)
                                .put(
                                        "ipSub",
                                        network.netmask)
                                .put(
                                        "ipGateway",
                                        network.gateway)
                                .put(
                                        "ipDns",
                                        network.dns)
                                .build();
                payload = OBJECT_MAPPER.writeValueAsString(json);
            } else {
                config = new LinkedList<>();
                config.add(
                        toConfig(
                                toKey(
                                        "_%s_conf_nettype",
                                        this.key),
                                "Static"));
                config.add(
                        toConfig(
                                toKey(
                                        "_%s_conf_hostname",
                                        this.key),
                                newHostname));
                config.add(
                        toConfig(
                                toKey(
                                        "_%s_conf_ipaddress",
                                        this.key),
                                network.ipAddress));
                config.add(
                        toConfig(
                                toKey(
                                        "_%s_conf_netmask",
                                        this.key),
                                network.netmask));
                config.add(
                        toConfig(
                                toKey(
                                        "_%s_conf_gateway",
                                        this.key),
                                network.gateway));
                config.add(
                        toConfig(
                                toKey(
                                        "_%s_conf_dnsservers",
                                        this.key),
                                network.dns));
            }
        } catch (final Exception e) {
            throw new MinerException(
                    String.format(
                            "Failed to obtain a response from miner at %s:%d",
                            ip,
                            port),
                    e);
        }

        final AtomicReference<String> errorMessage = new AtomicReference<>();

        try {
            Query.digestPost(
                    ip,
                    port,
                    this.realm,
                    "/cgi-bin/set_network_conf.cgi",
                    username,
                    password,
                    config,
                    payload,
                    (integer, s) -> {
                        if (s != null && s.contains("error")) {
                            errorMessage.set(s);
                        }
                    });
        } catch (final Exception e) {
            // Miner disconnects when the IP is changed
            LOG.info("Exception occurred while updating settings", e);
        }

        final String error = errorMessage.get();
        if (error != null && !error.isEmpty()) {
            throw new MinerException(error);
        }

        return true;
    }

    /**
     * Creates a configuration param with the provided values.
     *
     * @param key   The key.
     * @param value The value.
     *
     * @return The param.
     */
    private static Map<String, Object> toConfig(
            final String key,
            final String value) {
        return ImmutableMap.of(
                "key",
                key,
                "value",
                value);
    }

    /**
     * Creates a key from the provided pattern.
     *
     * @param pattern The pattern.
     * @param key     The key.
     *
     * @return The new key.
     */
    private static String toKey(
            final String pattern,
            final String key) {
        return String.format(pattern, key);
    }
}