package mn.foreman.avalon;

import mn.foreman.api.model.Network;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractNetworkAction;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An {@link AbstractNetworkAction} implementation that will change the network
 * settings on an Avalon miner.
 */
public class AvalonNetworkAction
        extends AbstractNetworkAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AvalonNetworkAction.class);

    /** The action to run when performing a reboot. */
    private final AvalonRebootAction rebootAction;

    /**
     * Constructor.
     *
     * @param rebootAction The action to run when performing a reboot.
     */
    public AvalonNetworkAction(final AvalonRebootAction rebootAction) {
        this.rebootAction = rebootAction;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network) {
        final AtomicBoolean success = new AtomicBoolean(false);

        try {
            Query.post(
                    ip,
                    port,
                    "/network.cgi",
                    toParams(network),
                    (code, s1) -> {
                        if (code == HttpStatus.SC_OK) {
                            // Miner needs to be rebooted after the config has
                            // been changed
                            success.set(
                                    this.rebootAction.run(
                                            ip,
                                            port,
                                            parameters));
                        }
                    });
        } catch (final Exception e) {
            LOG.warn("Exception occurred", e);
        }

        return success.get();
    }

    /**
     * Creates the network params.
     *
     * @param network The network.
     *
     * @return The new params.
     */
    private static List<Map<String, Object>> toParams(final Network network) {
        final List<Map<String, Object>> newParams = new LinkedList<>();
        newParams.add(
                toValueMap(
                        "protocol",
                        "1"));
        newParams.add(
                toValueMap(
                        "ip",
                        network.ipAddress));
        newParams.add(
                toValueMap(
                        "mask",
                        network.netmask));
        newParams.add(
                toValueMap(
                        "gateway",
                        network.gateway));
        newParams.add(
                toValueMap(
                        "dns",
                        network.dns));
        newParams.add(
                toValueMap(
                        "dnsbak",
                        network.gateway));
        return newParams;
    }

    /**
     * Creates a value map.
     *
     * @param key   The key.
     * @param value The value.
     *
     * @return The map.
     */
    private static Map<String, Object> toValueMap(
            final String key,
            final Object value) {
        return ImmutableMap.of(
                "key",
                key,
                "value",
                value);
    }
}
