package mn.foreman.openminer;

import mn.foreman.api.model.Pool;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.util.PoolUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A {@link OpenMinerChangePoolsAction} provides an {@link
 * AbstractChangePoolsAction} implementation that will change the pools in use
 * by an OpenMiner device.
 */
public class OpenMinerChangePoolsAction
        extends AbstractChangePoolsAction {

    /** The mapper for JSON. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** Constructor. */
    public OpenMinerChangePoolsAction() {
        super(1);
    }

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        final String username =
                parameters.getOrDefault("username", "").toString();
        final String password =
                parameters.getOrDefault("password", "").toString();

        // Test hook
        final int realPort =
                port != 8080 && port != 8081
                        ? 80
                        : port;

        final String authToken =
                OpenMinerUtils.getAuthToken(
                        ip,
                        realPort,
                        username,
                        password)
                        .orElseThrow(
                                () -> new MinerException("Failed to login"));
        final Map<String, Object> currentSettings =
                new HashMap<>(
                        Query.restQueryBearer(
                                ip,
                                realPort,
                                "/api/om/miner/settings",
                                authToken,
                                new TypeReference<Map<String, Object>>() {
                                },
                                1,
                                TimeUnit.SECONDS));

        final Pool pool = pools.get(0);
        final String[] host = PoolUtils.sanitizeUrl(pool.getUrl()).split(":");
        currentSettings.put("host", host[0]);
        currentSettings.put("port", Integer.parseInt(host[1]));
        currentSettings.put("user", pool.getUsername());
        currentSettings.put("password", pool.getPassword());

        final AtomicBoolean success = new AtomicBoolean(false);
        try {
            Query.restPost(
                    ip,
                    realPort,
                    "/api/om/miner/settings/apply",
                    authToken,
                    OBJECT_MAPPER.writeValueAsString(currentSettings),
                    new TypeReference<Map<String, String>>() {
                    },
                    5,
                    TimeUnit.SECONDS,
                    (integer, s) -> success.set(integer == HttpStatus.SC_OK));
        } catch (final Exception e) {
            throw new MinerException("Failed to change pools", e);
        }

        return success.get();
    }
}