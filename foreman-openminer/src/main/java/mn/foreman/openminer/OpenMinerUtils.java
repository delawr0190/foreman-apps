package mn.foreman.openminer;

import mn.foreman.io.Query;
import mn.foreman.model.error.MinerException;
import mn.foreman.openminer.response.Agg;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/** Utilities for communicating with OpenMiner. */
public class OpenMinerUtils {

    /**
     * Returns the auth token that was obtained.
     *
     * @param apiIp    The API ip.
     * @param port     The API port.
     * @param username The username.
     * @param password The password.
     *
     * @return The token.
     *
     * @throws MinerException on failure.
     */
    public static Optional<String> getAuthToken(
            final String apiIp,
            final int port,
            final String username,
            final String password) throws MinerException {
        final AtomicReference<String> response = new AtomicReference<>();
        try {
            final Optional<Map<String, String>> result =
                    Query.restPost(
                            apiIp,
                            port,
                            "/api/login",
                            null,
                            "{\"email\":\"" + username + "\",\"password\":\"" + password + "\"}",
                            new TypeReference<Map<String, String>>() {
                            },
                            5,
                            TimeUnit.SECONDS,
                            (integer, s) -> {
                            });
            if (result.isPresent()) {
                final Map<String, String> content = result.get();
                if (content.containsKey("token")) {
                    response.set(content.get("token"));
                }
            }
        } catch (final Exception e) {
            throw new MinerException(e);
        }
        return Optional.ofNullable(response.get());
    }

    /**
     * Auths with the miner and gets stats.
     *
     * @param apiIp       The ip.
     * @param port        The port.
     * @param username    The username.
     * @param password    The password.
     * @param rawCallback The raw callback.
     *
     * @return The stats.
     *
     * @throws MinerException on failure.
     */
    public static Agg getStats(
            final String apiIp,
            final int port,
            final String username,
            final String password,
            final Consumer<String> rawCallback) throws MinerException {
        final String bearerToken =
                OpenMinerUtils.getAuthToken(
                        apiIp,
                        port,
                        username,
                        password).orElseThrow(
                        () -> new MinerException("Failed to login to OpenMiner"));
        return Query.restQueryBearer(
                apiIp,
                port,
                "/api/om/miner/stat/agg",
                bearerToken,
                new TypeReference<Agg>() {
                },
                1,
                TimeUnit.SECONDS,
                rawCallback);
    }
}
