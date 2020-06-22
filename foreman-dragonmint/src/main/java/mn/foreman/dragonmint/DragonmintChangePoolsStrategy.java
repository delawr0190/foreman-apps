package mn.foreman.dragonmint;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractChangePoolsStrategy;
import mn.foreman.model.ChangePoolsStrategy;
import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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
        boolean success;

        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");

            final List<Map<String, Object>> content = new LinkedList<>();
            addParams(pools.get(0), 1, content);
            addParams(pools.get(1), 2, content);
            addParams(pools.get(2), 3, content);

            final AtomicReference<String> responseRef =
                    new AtomicReference<>();
            Query.restQuery(
                    ip,
                    port,
                    "/api/updatePools",
                    username,
                    password,
                    content,
                    responseRef::set);

            final String response = responseRef.get();
            success = response != null && response.contains("true");
        } catch (final IOException | URISyntaxException e) {
            throw new MinerException(e);
        }

        return success;
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
                        "Username" + index,
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
