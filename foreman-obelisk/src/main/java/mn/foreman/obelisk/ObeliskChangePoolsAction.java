package mn.foreman.obelisk;

import mn.foreman.api.model.Pool;
import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * An {@link ObeliskChangePoolsAction} provides an {@link
 * AbstractChangePoolsAction} implementation that will change the pools in use
 * by an obelisk device.
 */
public class ObeliskChangePoolsAction
        extends AbstractChangePoolsAction {

    /** The mapper for JSON. */
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
     * @param objectMapper       The mapper.
     */
    public ObeliskChangePoolsAction(
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
            final List<Pool> pools)
            throws MinerException {
        boolean result;
        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");

            final List<Map<String, String>> newPools = new LinkedList<>();
            pools.forEach(pool ->
                    addPool(
                            pool,
                            newPools));

            result =
                    ObeliskQuery.runSessionQuery(
                            ip,
                            port,
                            "/api/config/pools",
                            true,
                            username,
                            password,
                            this.socketTimeout,
                            this.socketTimeoutUnits,
                            Collections.emptyList(),
                            (code, body) -> null,
                            this.objectMapper.writeValueAsString(newPools),
                            o -> {
                            },
                            new HashMap<>());
        } catch (final Exception e) {
            throw new MinerException(e);
        }
        return result;
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
