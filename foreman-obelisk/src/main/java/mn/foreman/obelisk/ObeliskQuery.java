package mn.foreman.obelisk;

import mn.foreman.io.ApiRequest;
import mn.foreman.io.ApiRequestImpl;
import mn.foreman.io.Connection;
import mn.foreman.io.ConnectionFactory;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Data;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/** Utility methods to safely query an Obelisk with an established session. */
public class ObeliskQuery {

    /** The mapper for parsing json. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Adds stats for the provided obelisk generation.
     *
     * @param context The query context.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> void runSessionQuery(final Context<T> context)
            throws Exception {
        final CookieStore cookieStore = new BasicCookieStore();
        try {
            // Login first
            query(
                    context,
                    "/api/login",
                    "POST",
                    true,
                    OBJECT_MAPPER.writeValueAsString(
                            ImmutableMap.of(
                                    "username",
                                    context.getUsername(),
                                    "password",
                                    context.getPassword())),
                    cookieStore,
                    Object.class);

            // Run the query
            final Class<T> responseClass = context.getResponseClass();
            final Optional<T> responseOptional =
                    query(
                            context,
                            context.getUri(),
                            context.getMethod(),
                            false,
                            context.getContent(),
                            cookieStore,
                            responseClass);
            if (responseClass != null) {
                if (responseOptional.isPresent()) {
                    final T response = responseOptional.get();
                    final Consumer<T> responseCallback =
                            context.getResponseCallback();
                    responseCallback.accept(response);
                } else {
                    throw new MinerException("Failed to obtain response");
                }
            }
        } finally {
            // Logout
            if (!context.isReboot()) {
                // Only attempt to logout if the miner isn't rebooting
                query(
                        context,
                        "/api/logout",
                        "POST",
                        false,
                        null,
                        cookieStore,
                        Object.class);
            }
        }
    }

    /**
     * Queries an Obelisk.
     *
     * @param context     The request context.
     * @param uri         The API URI.
     * @param method      The method.
     * @param isLogin     Whether or not the request is a login attempt.
     * @param content     The content.
     * @param cookieStore The cookie store.
     * @param clazz       The class of the response.
     * @param <T>         The response type.
     *
     * @return The response, if present.
     *
     * @throws IOException    on failure to parse JSON.
     * @throws MinerException on failure to communicate with the miner.
     */
    private static <T, U> Optional<U> query(
            final Context<T> context,
            final String uri,
            final String method,
            final boolean isLogin,
            final String content,
            final CookieStore cookieStore,
            final Class<U> clazz)
            throws
            IOException,
            MinerException {
        U result = null;

        final ApiRequest apiRequest =
                new ApiRequestImpl(
                        context.getApiIp(),
                        context.getApiPort(),
                        uri,
                        Collections.emptyMap(),
                        content);

        final Connection connection =
                ConnectionFactory.createRestConnection(
                        apiRequest,
                        method,
                        5,
                        TimeUnit.SECONDS,
                        cookieStore);
        connection.query();

        if (apiRequest.waitForCompletion(
                5,
                TimeUnit.SECONDS)) {
            final String response = apiRequest.getResponse();
            final Consumer<String> callback =
                    context.getRawResponseCallback();
            if (callback != null) {
                callback.accept(response);
            }
            if (response != null && !response.isEmpty()) {
                result =
                        OBJECT_MAPPER.readValue(
                                response,
                                clazz);
            }
        } else {
            throw new MinerException("Failed to obtain response from obelisk");
        }

        if (isLogin && cookieStore.getCookies().stream().noneMatch(cookie -> cookie.getName().contains("sessionid"))) {
            throw new MinerException("Failed to login to obelisk");
        }

        return Optional.ofNullable(result);
    }

    /**
     * A {@link Context} provides all of the information needed to perform a
     * fully-authenticated query against an Obelisk.
     *
     * @param <T> The response type.
     */
    @Data
    @Builder
    public static class Context<T> {

        /** The API ip. */
        private final String apiIp;

        /** The API port. */
        private final int apiPort;

        /** Any content to be sent. */
        private final String content;

        /** Whether or not the query is a reboot. */
        private final boolean isReboot;

        /** The method. */
        private final String method;

        /** The password. */
        private final String password;

        /** The raw response callback. */
        private final Consumer<String> rawResponseCallback;

        /** The response callback. */
        private final Consumer<T> responseCallback;

        /** The response class. */
        private final Class<T> responseClass;

        /** The URI. */
        private final String uri;

        /** The username. */
        private final String username;
    }
}
