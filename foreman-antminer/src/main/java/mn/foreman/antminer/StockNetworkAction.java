package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.model.Network;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A {@link StockNetworkAction} provides a mechanism for setting the network
 * configuration of a miner running Antminer-like firmware.
 */
public class StockNetworkAction
        extends AbstractNetworkAction {

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
        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");

            final String previousHostname =
                    AntminerUtils.getConf(
                            ip,
                            port,
                            this.realm,
                            "/cgi-bin/get_system_info.cgi",
                            username,
                            password)
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().contains("hostname"))
                            .map(entry -> entry.getValue().toString())
                            .findFirst()
                            .orElseThrow(() -> new MinerException("Failed to obtain previous hostname"));
            final List<Map<String, Object>> config = new LinkedList<>();
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
                            network.hostname != null && !network.hostname.isEmpty()
                                    ? network.hostname
                                    : previousHostname));
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

            Query.digestPost(
                    ip,
                    port,
                    this.realm,
                    "/cgi-bin/set_miner_conf.cgi",
                    username,
                    password,
                    config,
                    null,
                    (integer, s) -> {
                    });
        } catch (final MinerException me) {
            throw me;
        } catch (final Exception e) {
            throw new MinerException(
                    String.format(
                            "Failed to obtain a response from miner at %s:%d",
                            ip,
                            port));
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