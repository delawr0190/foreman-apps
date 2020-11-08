package mn.foreman.whatsminer;

import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;
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
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/** Utilities for interacting with a whatsminer. */
class WhatsminerQuery {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(WhatsminerQuery.class);

    /**
     * Queries a Whatsminer, performing a login operation first to obtain a
     * session cookie.
     *
     * @param ip        The ip.
     * @param port      The port.
     * @param username  The username.
     * @param password  The password.
     * @param uri       The uri.
     * @param isGet     Whether or not a GET is being performed.
     * @param urlParams The URL parameters.
     * @param callback  The callback for processing the response.
     *
     * @throws MinerException on failure.
     */
    static void query(
            final String ip,
            final int port,
            final String username,
            final String password,
            final String uri,
            final boolean isGet,
            final List<Map<String, Object>> urlParams,
            final BiConsumer<Integer, String> callback)
            throws MinerException {
        final CookieStore cookieStore = new BasicCookieStore();
        final RequestConfig requestConfig =
                RequestConfig
                        .custom()
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .setConnectTimeout((int) TimeUnit.SECONDS.toMillis(1))
                        .setSocketTimeout((int) TimeUnit.SECONDS.toMillis(5))
                        .setCircularRedirectsAllowed(true)
                        .build();

        // Login first
        final AtomicBoolean loggedIn = new AtomicBoolean(false);
        query(
                ip,
                port,
                "/cgi-bin/luci/",
                false,
                Arrays.asList(
                        ImmutableMap.of(
                                "key",
                                "luci_username",
                                "value",
                                username),
                        ImmutableMap.of(
                                "key",
                                "luci_password",
                                "value",
                                password)),
                requestConfig,
                cookieStore,
                (statusCode, data) -> {
                    LOG.debug("Code: {}", statusCode);
                    loggedIn.set(statusCode != HttpStatus.SC_FORBIDDEN);
                });
        if (loggedIn.get()) {
            query(
                    ip,
                    port,
                    uri,
                    isGet,
                    urlParams,
                    requestConfig,
                    cookieStore,
                    callback);
        } else {
            throw new MinerException("Failed to obtain config data");
        }
    }

    /**
     * Queries a Whatsminer miner, performing a login operation first to obtain
     * a session cookie.
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
                             .setDefaultRequestConfig(requestConfig)
                             .setDefaultCookieStore(cookieStore)
                             .disableAutomaticRetries()
                             .setSSLContext(
                                     new SSLContextBuilder()
                                             .loadTrustMaterial(null, TrustAllStrategy.INSTANCE)
                                             .build())
                             .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                             .setRedirectStrategy(new LaxRedirectStrategy())
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
        } catch (final Exception e) {
            throw new MinerException(e);
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
        if (port == 443) {
            return String.format("https://%s%s", ip, uri);
        }
        return String.format("http://%s:%d%s", ip, port, uri);
    }
}