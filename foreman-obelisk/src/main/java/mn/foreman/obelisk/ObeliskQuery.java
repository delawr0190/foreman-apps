package mn.foreman.obelisk;

import mn.foreman.io.HttpRequestBuilder;
import mn.foreman.model.error.MinerException;
import mn.foreman.util.Flatten;

import com.google.common.collect.ImmutableMap;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/** Utility methods to safely query an Obelisk with an established session. */
public class ObeliskQuery {

    /**
     * Adds stats for the provided obelisk generation.
     *
     * @param ip                 The ip.
     * @param port               The port.
     * @param uri                The URI.
     * @param isPost             Whether or not a post.
     * @param username           The username.
     * @param password           The password.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     * @param whitelist          The stats whitelist.
     * @param transformer        The response transformer.
     * @param content            The body.
     * @param responseCallback   The response callback.
     * @param rawStats           The raw stats to update.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> boolean runSessionQuery(
            final String ip,
            final int port,
            final String uri,
            final boolean isPost,
            final String username,
            final String password,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final List<String> whitelist,
            final HttpRequestBuilder.ResponseTransformer<T> transformer,
            final String content,
            final Consumer<T> responseCallback,
            final Map<String, Object> rawStats)
            throws Exception {
        final CookieStore cookieStore = new BasicCookieStore();

        // Login first
        final boolean loggedIn =
                new HttpRequestBuilder<>()
                        .ip(ip)
                        .port(port)
                        .uri("/api/login")
                        .cookieStore(cookieStore)
                        .socketTimeout(
                                socketTimeout,
                                socketTimeoutUnits)
                        .validator((code, s) ->
                                cookieStore
                                        .getCookies()
                                        .stream()
                                        .anyMatch(cookie -> cookie.getName().contains("sessionid")))
                        .rawCallback((code, s) -> {
                        })
                        .postJsonNoResponse(
                                ImmutableMap.of(
                                        "username",
                                        username,
                                        "password",
                                        password));

        if (loggedIn) {
            final HttpRequestBuilder<T> builder =
                    new HttpRequestBuilder<T>()
                            .ip(ip)
                            .port(port)
                            .uri(uri)
                            .cookieStore(cookieStore)
                            .socketTimeout(
                                    socketTimeout,
                                    socketTimeoutUnits)
                            .validator((code, s) -> !s.isEmpty())
                            .rawCallback((code, s) ->
                                    rawStats.putAll(
                                            Flatten.flattenAndFilter(
                                                    s,
                                                    whitelist)))
                            .responseTransformer(transformer);
            if (isPost) {
                builder
                        .postJsonWithResponse(content)
                        .ifPresent(responseCallback);
            } else {
                builder
                        .get()
                        .ifPresent(responseCallback);
            }
        }

        return loggedIn;
    }
}
