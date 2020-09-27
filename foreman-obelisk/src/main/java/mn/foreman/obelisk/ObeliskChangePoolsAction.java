package mn.foreman.obelisk;

import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;
import mn.foreman.obelisk.json.Dashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * An {@link ObeliskChangePoolsAction} provides an {@link
 * AbstractChangePoolsAction} implementation that will change the pools in use
 * by an obelisk device.
 */
public class ObeliskChangePoolsAction
        extends AbstractChangePoolsAction {

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");
            final ObjectMapper objectMapper =
                    new ObjectMapper();

            final List<Map<String, String>> newPools = new LinkedList<>();
            pools.forEach(pool ->
                    addPool(
                            pool,
                            newPools));

            ObeliskQuery.runSessionQuery(
                    ObeliskQuery.Context
                            .<Dashboard>builder()
                            .apiIp(ip)
                            .apiPort(port)
                            .uri("/api/config/pools")
                            .method("POST")
                            .username(username)
                            .password(password)
                            .content(objectMapper.writeValueAsString(newPools))
                            .rawResponseCallback(s -> {
                            })
                            .build());
        } catch (final Exception e) {
            throw new MinerException(e);
        }
        return true;
    }

    /**
     * Adds a pool to the destination.
     *
     * @param pool The pool.
     * @param dest The destination.
     */
    private static void addPool(
            final Pool pool,
            final List<Map<String, String>> dest) {
        dest.add(
                ImmutableMap.of(
                        "url",
                        pool.getUrl(),
                        "worker",
                        pool.getUsername(),
                        "password",
                        pool.getPassword()));
    }
}
