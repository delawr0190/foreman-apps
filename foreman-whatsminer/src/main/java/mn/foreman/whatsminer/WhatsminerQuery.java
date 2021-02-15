package mn.foreman.whatsminer;

import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Data;
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
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** Utilities for interacting with a whatsminer. */
public class WhatsminerQuery {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(WhatsminerQuery.class);

    /**
     * Queries a Whatsminer, performing a login operation first to obtain a
     * session cookie.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     * @param queries  The queries.
     *
     * @throws MinerException on failure.
     */
    public static void query(
            final String ip,
            final int port,
            final String username,
            final String password,
            final List<Query> queries)
            throws MinerException {
        // Test hook
        if (port == 8080 || port == 8081) {
            doQuery(
                    ip,
                    port,
                    username,
                    password,
                    queries);
        } else {
            try {
                doQuery(
                        ip,
                        443,
                        username,
                        password,
                        queries);
            } catch (final MinerException me) {
                doQuery(
                        ip,
                        80,
                        username,
                        password,
                        queries);
            }
        }
    }

    /**
     * Queries a Whatsminer, performing a login operation first to obtain a
     * session cookie.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     * @param queries  The queries.
     *
     * @throws MinerException on failure.
     */
    private static void doQuery(
            final String ip,
            final int port,
            final String username,
            final String password,
            final List<Query> queries)
            throws MinerException {
        final CookieStore cookieStore = new BasicCookieStore();
        final RequestConfig requestConfig =
                RequestConfig
                        .custom()
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .setConnectTimeout((int) TimeUnit.MILLISECONDS.toMillis(400))
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
                false,
                null,
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
                maps -> {
                },
                requestConfig,
                cookieStore,
                (statusCode, data) -> {
                    LOG.debug("Code: {}", statusCode);
                    loggedIn.set(statusCode != HttpStatus.SC_FORBIDDEN);
                },
                e -> {
                });
        if (loggedIn.get()) {
            for (final Query query : queries) {
                query(
                        ip,
                        port,
                        query.uri,
                        query.isGet,
                        query.isMultipartForm,
                        query.boundary,
                        query.urlParams,
                        query.paramEnricher,
                        requestConfig,
                        cookieStore,
                        query.callback,
                        query.timeout);
            }
        } else {
            throw new MinerException("Failed to obtain config data");
        }
    }


    /**
     * Queries a Whatsminer miner, performing a login operation first to obtain
     * a session cookie.
     *
     * @param ip              The ip.
     * @param port            The port.
     * @param uri             The uri.
     * @param isGet           Whether or not a GET is being performed.
     * @param isMultipartForm Whether or not a multipart form.
     * @param boundary        The form boundary.
     * @param urlParams       The URL parameters.
     * @param paramEnricher   The enricher.
     * @param requestConfig   The request config.
     * @param cookieStore     The cookie store.
     * @param callback        The callback for processing the response.
     * @param timeout         The timeout callback.
     *
     * @throws MinerException on failure.
     */
    private static void query(
            final String ip,
            final int port,
            final String uri,
            final boolean isGet,
            final boolean isMultipartForm,
            final String boundary,
            final List<Map<String, Object>> urlParams,
            final Consumer<List<Map<String, Object>>> paramEnricher,
            final RequestConfig requestConfig,
            final CookieStore cookieStore,
            final BiConsumer<Integer, String> callback,
            final Consumer<SocketTimeoutException> timeout)
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
                if (paramEnricher != null) {
                    paramEnricher.accept(urlParams);
                }
                if (isMultipartForm) {
                    final MultipartEntityBuilder builder =
                            MultipartEntityBuilder.create();
                    urlParams.forEach(param ->
                            builder.addTextBody(
                                    param.get("key").toString(),
                                    param.get("value").toString()));
                    if (boundary != null) {
                        builder.setBoundary(boundary);
                    }
                    post.setEntity(builder.build());
                } else {
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
                }
                httpRequest = post;
            }

            try {
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
            } catch (final SocketTimeoutException ste) {
                if (timeout != null) {
                    timeout.accept(ste);
                }
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

    /** A query to run against a WhatsMiner. */
    @Data
    @Builder
    public static class Query {

        /** The boundary. */
        private final String boundary;

        /** The callback. */
        private final BiConsumer<Integer, String> callback;

        /** Whether or not a GET. */
        private final boolean isGet;

        /** If a POST, whether or not a multipart form. */
        private final boolean isMultipartForm;

        /** The param enricher. */
        private final Consumer<List<Map<String, Object>>> paramEnricher;

        /** The timeout callback. */
        private final Consumer<SocketTimeoutException> timeout;

        /** The URI. */
        private final String uri;

        /** The params. */
        private final List<Map<String, Object>> urlParams;
    }
}