package mn.foreman.goldshell;

import mn.foreman.api.model.Pool;
import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.ApplicationConfiguration;

import com.google.common.collect.ImmutableMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A {@link GoldshellChangePoolsAction} provides an {@link
 * AbstractChangePoolsAction} implementation that will change the pools in use
 * by a goldshell device.
 */
public class GoldshellChangePoolsAction
        extends AbstractChangePoolsAction {

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /**
     * Constructor.
     *
     * @param configuration The configuration.
     */
    public GoldshellChangePoolsAction(
            final ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools) {
        return GoldshellQuery.runPut(
                ip,
                port,
                "/mcb/pools",
                toPools(pools),
                this.configuration);
    }

    /**
     * Converts the provided pool to a Goldshell-formatted pool.
     *
     * @param pool     The pool.
     * @param priority The priority.
     *
     * @return The pool json.
     */
    private static Map<String, Object> toPool(
            final Pool pool,
            final int priority) {
        return ImmutableMap.<String, Object>builder()
                .put(
                        "url",
                        pool.getUrl())
                .put(
                        "legal",
                        true)
                .put(
                        "active",
                        false)
                .put(
                        "dragid",
                        priority)
                .put(
                        "user",
                        pool.getUsername())
                .put(
                        "pool-priority",
                        priority)
                .put(
                        "pass",
                        pool.getPassword())
                .build();
    }

    /**
     * Converts the provided pools to a pools array.
     *
     * @param pools The pools.
     *
     * @return The pools array.
     */
    private static List<Map<String, Object>> toPools(final List<Pool> pools) {
        final List<Map<String, Object>> newPools = new LinkedList<>();
        for (int i = 0; i < pools.size(); i++) {
            newPools.add(
                    toPool(
                            pools.get(i),
                            i));
        }
        return newPools;
    }
}
