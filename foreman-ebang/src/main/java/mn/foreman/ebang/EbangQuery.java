package mn.foreman.ebang;

import mn.foreman.io.HttpRequestBuilder;
import mn.foreman.io.QueryException;
import mn.foreman.util.ParamUtils;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/** Utility methods to safely query an Ebang. */
public class EbangQuery {

    /**
     * Adds stats for the provided obelisk generation.
     *
     * @throws QueryException on failure to query.
     */
    public static <T> Optional<T> query(
            final String apiIp,
            final int apiPort,
            final String username,
            final String password,
            final String uri,
            final String content,
            final List<Map<String, Object>> params,
            final HttpRequestBuilder.ResponseTransformer<T> transformer,
            final BiConsumer<Integer, String> rawCallback,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits)
            throws QueryException {
        Optional<T> result = Optional.empty();

        final CookieStore cookieStore = new BasicCookieStore();

        // Login first
        final AtomicBoolean loggedIn = new AtomicBoolean(false);
        new HttpRequestBuilder<>()
                // Test hook
                .scheme(
                        apiPort == 8080 || apiPort == 8081
                                ? "http"
                                : "https")
                .ip(apiIp)
                // Test hook
                .port(
                        apiPort == 8080 || apiPort == 8081
                                ? apiPort
                                : 443)
                .uri("/user/login")
                .cookieStore(cookieStore)
                .socketTimeout(
                        socketTimeout,
                        socketTimeoutUnits)
                .validator((code, s) -> {
                    loggedIn.set(
                            cookieStore
                                    .getCookies()
                                    .stream()
                                    .anyMatch(cookie -> cookie.getName().contains("http-session")));
                    return loggedIn.get();
                })
                .rawCallback((code, body) -> {
                })
                .postWithResponse(
                        Arrays.asList(
                                ParamUtils.toParam(
                                        "username",
                                        username),
                                ParamUtils.toParam(
                                        "word",
                                        password),
                                ParamUtils.toParam(
                                        "yuyan",
                                        1),
                                ParamUtils.toParam(
                                        "login",
                                        "Login"),
                                ParamUtils.toParam(
                                        "get_password",
                                        "")));
        if (loggedIn.get()) {
            final HttpRequestBuilder<T> builder =
                    new HttpRequestBuilder<T>()
                            .ip(apiIp)
                            .port(apiPort)
                            .uri(uri)
                            .cookieStore(cookieStore)
                            .socketTimeout(
                                    socketTimeout,
                                    socketTimeoutUnits)
                            .validator((code, s) -> s.contains("feedback"))
                            .rawCallback(rawCallback)
                            .responseTransformer(transformer);
            if (content != null) {
                result = builder.postJsonWithResponse(content);
            } else if (params != null) {
                result = builder.postWithResponse(params);
            }
        }

        return result;
    }
}
