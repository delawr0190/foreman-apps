package mn.foreman.baikal;

import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/** Utilities for interacting with a Baikal miner. */
class BaikalUtils {

    /**
     * Queries a Baikal miner, performing a login operation first to obtain a
     * session cookie.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param password The password.
     * @param uri      The uri.
     * @param callback The callback for processing the response.
     *
     * @throws MinerException on failure.
     */
    static void query(
            final String ip,
            final int port,
            final String password,
            final String uri,
            final BiConsumer<Integer, String> callback)
            throws MinerException {
        final CookieStore cookieStore = new BasicCookieStore();
        final RequestConfig requestConfig =
                RequestConfig
                        .custom()
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .build();

        // Login first
        final AtomicBoolean loggedIn = new AtomicBoolean(false);
        query(
                ip,
                port,
                "/f_login.php",
                false,
                Collections.singletonList(
                        ImmutableMap.of(
                                "key",
                                "userPassword",
                                "value",
                                password)),
                requestConfig,
                cookieStore,
                (statusCode, data) -> loggedIn.set(data.contains("1")));
        if (loggedIn.get()) {
            try {
                query(
                        ip,
                        port,
                        uri,
                        true,
                        Collections.emptyList(),
                        requestConfig,
                        cookieStore,
                        callback);
            } catch (final Exception e) {
                throw new MinerException("Exception occurred while performing query");
            } finally {
                query(
                        ip,
                        port,
                        "/f_logout.php",
                        true,
                        Collections.emptyList(),
                        requestConfig,
                        cookieStore,
                        (integer, s) -> {
                            // Do nothing
                        });
            }
        } else {
            throw new MinerException("Failed to login");
        }
    }

    /**
     * Queries a Baikal miner, performing a login operation first to obtain a
     * session cookie.
     *
     * @param ip            The ip.
     * @param port          The port.
     * @param uri           The uri.
     * @param isGet         Whether or not a GET is being performed.
     * @param urlParams     The URL parameters.
     * @param requestConfig The request config.
     * @param cookieStore   The cookie store.
     * @param callback      The callback for processing the response.
     *
     * @throws MinerException on failure.
     */
    private static void query(
            final String ip,
            final int port,
            final String uri,
            final boolean isGet,
            final List<Map<String, Object>> urlParams,
            final RequestConfig requestConfig,
            final CookieStore cookieStore,
            final BiConsumer<Integer, String> callback)
            throws MinerException {
        try (final CloseableHttpClient client =
                     HttpClients
                             .custom()
                             .setRedirectStrategy(new LaxRedirectStrategy())
                             .setDefaultRequestConfig(requestConfig)
                             .setDefaultCookieStore(cookieStore)
                             .disableAutomaticRetries()
                             .build()) {
            final String url =
                    toUrl(
                            ip,
                            port,
                            uri);
            final HttpUriRequest httpRequest;
            if (isGet) {
                httpRequest = new HttpGet(url);
            } else {
                final HttpPost post = new HttpPost(url);
                if (!urlParams.isEmpty()) {
                    final List<NameValuePair> params = new ArrayList<>();
                    urlParams
                            .stream()
                            .map(map ->
                                    new BasicNameValuePair(
                                            map.get("key").toString(),
                                            map.get("value").toString()))
                            .forEach(params::add);
                    post.setEntity(
                            new UrlEncodedFormEntity(
                                    params,
                                    "UTF-8"));
                }
                httpRequest = post;
            }

            try (final CloseableHttpResponse response =
                         client.execute(httpRequest)) {
                final StatusLine statusLine =
                        response.getStatusLine();
                final String responseBody =
                        EntityUtils.toString(response.getEntity());
                callback.accept(
                        statusLine.getStatusCode(),
                        responseBody);
            }
        } catch (final IOException ioe) {
            throw new MinerException(ioe);
        }
    }

    /**
     * Creates a URL from the provided params.
     *
     * @param ip   The IP.
     * @param port The port.
     * @param uri  The URI.
     *
     * @return The new URL.
     */
    private static String toUrl(
            final String ip,
            final int port,
            final String uri) {
        return String.format(
                "http://%s:%d%s",
                ip,
                port,
                uri);
    }
}