package mn.foreman.dragonmint;

import mn.foreman.model.AbstractChangePoolsStrategy;
import mn.foreman.model.ChangePoolsStrategy;
import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A {@link DragonmintChangePoolsStrategy} provides a {@link
 * ChangePoolsStrategy} implementation that will change the pools in use by a
 * dragonmint device.
 */
public class DragonmintChangePoolsStrategy
        extends AbstractChangePoolsStrategy {

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        final List<Map<String, Object>> content = new LinkedList<>();
        addParams(pools.get(0), 1, content);
        addParams(pools.get(1), 2, content);
        addParams(pools.get(2), 3, content);

        return DragonmintQuery.runQuery(
                ip,
                port,
                "/api/updatePools",
                parameters,
                content);
    }

    /**
     * Adds parameters to the params.
     *
     * @param pool   The pool.
     * @param index  The index.
     * @param params The params.
     */
    private static void addParams(
            final Pool pool,
            final int index,
            final List<Map<String, Object>> params) {
        params.add(
                ImmutableMap.of(
                        "key",
                        "Pool" + index,
                        "value",
                        pool.getUrl()));
        params.add(
                ImmutableMap.of(
                        "key",
                        "UserName" + index,
                        "value",
                        pool.getUsername()));
        params.add(
                ImmutableMap.of(
                        "key",
                        "Password" + index,
                        "value",
                        pool.getPassword()));
    }
}
