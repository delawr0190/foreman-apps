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
import org.apache.http.Header;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/** Utility methods to safely query an Obelisk with an established session. */
public class ObeliskQuery {

    /**
     * Adds stats for the provided obelisk generation.
     *
     * @param context The query context.
     *
     * @throws MinerException on failure to query.
     */
    static <T> void runSessionQuery(final Context<T> context)
            throws Exception {
        final AtomicReference<String> sessionId =
                new AtomicReference<>();
        final ObjectMapper objectMapper =
                new ObjectMapper();
        try {
            // Login first
            query(
                    context,
                    "/api/login",
                    "POST",
                    true,
                    objectMapper.writeValueAsString(
                            ImmutableMap.of(
                                    "username",
                                    context.getUsername(),
                                    "password",
                                    context.getPassword())),
                    sessionId,
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
                            sessionId,
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
                        sessionId,
                        Object.class);
            }
        }
    }

    /**
     * Processes all of the obelisk response headers.
     *
     * @param headers   The Obelisk response headers.
     * @param sessionId The session ID.
     */
    private static void processHeaders(
            final Map<String, String> headers,
            final AtomicReference<String> sessionId) {
        // Update the session cookie
        for (final Map.Entry<String, String> entry : headers.entrySet()) {
            if ("set-cookie".equals(entry.getKey().toLowerCase())) {
                final String cookie = entry.getValue();
                if (cookie.contains("sessionid")) {
                    sessionId.set(
                            cookie
                                    .split(";")[0]
                                    .replace("sessionid=", ""));
                }
            }
        }
    }

    /**
     * Queries an Obelisk.
     *
     * @param context   The request context.
     * @param uri       The API URI.
     * @param method    The method.
     * @param isLogin   Whether or not the request is a login attempt.
     * @param content   The content.
     * @param sessionId The session ID.
     * @param clazz     The class of the response.
     * @param <T>       The response type.
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
            final AtomicReference<String> sessionId,
            final Class<U> clazz)
            throws
            IOException,
            MinerException {
        U result = null;

        final ObjectMapper objectMapper =
                new ObjectMapper();

        final ApiRequest apiRequest =
                new ApiRequestImpl(
                        context.getApiIp(),
                        context.getApiPort(),
                        uri,
                        toCookies(sessionId),
                        content);

        final Map<String, String> headerMap = new HashMap<>();

        final Connection connection =
                ConnectionFactory.createRestConnection(
                        apiRequest,
                        method,
                        5,
                        TimeUnit.SECONDS,
                        headers -> {
                            for (final Header header : headers) {
                                headerMap.put(
                                        header.getName(),
                                        header.getValue());
                            }
                        });
        connection.query();

        if (apiRequest.waitForCompletion(
                5,
                TimeUnit.SECONDS)) {
            processHeaders(
                    headerMap,
                    sessionId);
            final String response = apiRequest.getResponse();
            final Consumer<String> callback =
                    context.getRawResponseCallback();
            if (callback != null) {
                callback.accept(response);
            }
            if (response != null && !response.isEmpty()) {
                result =
                        objectMapper.readValue(
                                response,
                                clazz);
            }
        } else {
            throw new MinerException("Failed to obtain response from obelisk");
        }

        if (isLogin && sessionId.get() == null) {
            throw new MinerException("Failed to login to obelisk");
        }

        return Optional.ofNullable(result);
    }

    /**
     * Creates an HTTP cookie map.
     *
     * @param sessionId The session ID.
     *
     * @return The cookies.
     */
    private static Map<String, String> toCookies(
            final AtomicReference<String> sessionId) {
        final String id = sessionId.get();
        return id != null
                ? ImmutableMap.of("Cookie", "sessionid=" + id)
                : Collections.emptyMap();
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
