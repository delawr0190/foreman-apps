package mn.foreman.antminer;

import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** Utilities for interacting with a bOS miner. */
class BraiinsUtils {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(BraiinsUtils.class);

    /** The default SSH port. */
    private static final int SSH_PORT = 22;

    /**
     * Queries a bOS miner, performing a login operation first to obtain a
     * session cookie.
     *
     * @param ip             The ip.
     * @param port           The port.
     * @param username       The username.
     * @param password       The password.
     * @param uri            The uri.
     * @param isGet          Whether or not a GET is being performed.
     * @param urlParams      The URL parameters.
     * @param requestContent The request content (json), if present.
     * @param callback       The callback for processing the response.
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
            final String requestContent,
            final BiConsumer<Integer, String> callback)
            throws MinerException {
        final CookieStore cookieStore = new BasicCookieStore();
        final RequestConfig requestConfig =
                RequestConfig
                        .custom()
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .setConnectTimeout((int) TimeUnit.MILLISECONDS.toMillis(100))
                        .setSocketTimeout((int) TimeUnit.SECONDS.toMillis(1))
                        .build();

        // Login first
        final AtomicBoolean loggedIn = new AtomicBoolean(false);
        query(
                ip,
                port,
                "/cgi-bin/luci/admin/miner/overview",
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
                requestContent,
                requestConfig,
                cookieStore,
                (statusCode, data) ->
                        loggedIn.set(data != null && !data.isEmpty()));
        if (loggedIn.get()) {
            query(
                    ip,
                    port,
                    uri,
                    isGet,
                    urlParams,
                    requestContent,
                    requestConfig,
                    cookieStore,
                    callback);
        } else {
            throw new MinerException("Failed to obtain config data");
        }
    }

    /**
     * Runs a command over SSH.
     *
     * @param ip       The IP.
     * @param username The username.
     * @param password The password.
     * @param command  The command.
     * @param response The response callback.
     *
     * @throws MinerException on failure.
     */
    static void runMinerCommand(
            final String ip,
            final String username,
            final String password,
            final String command,
            final Consumer<String> response)
            throws MinerException {
        Session session = null;
        Channel channel = null;

        try {
            final Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            final JSch jsch = new JSch();
            session =
                    jsch.getSession(
                            username,
                            ip,
                            SSH_PORT);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();

            final ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream();

            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(byteArrayOutputStream);

            final InputStream in = channel.getInputStream();
            channel.connect();

            final StringBuilder outBuff = new StringBuilder();

            while (true) {
                for (int c; ((c = in.read()) >= 0); ) {
                    outBuff.append((char) c);
                }

                if (channel.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    break;
                }
            }

            response.accept(outBuff.toString());

            LOG.info("SSH channel closed (status={})",
                    channel.getExitStatus());
        } catch (final Exception e) {
            throw new MinerException("Failed to connect to miner SSH");
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    /**
     * Queries a bOS miner, performing a login operation first to obtain a
     * session cookie.
     *
     * @param ip             The ip.
     * @param port           The port.
     * @param uri            The uri.
     * @param isGet          Whether or not a GET is being performed.
     * @param urlParams      The URL parameters.
     * @param requestContent The request content (json), if present.
     * @param requestConfig  The request config.
     * @param cookieStore    The cookie store.
     * @param callback       The callback for processing the response.
     *
     * @throws MinerException on failure.
     */
    private static void query(
            final String ip,
            final int port,
            final String uri,
            final boolean isGet,
            final List<Map<String, Object>> urlParams,
            final String requestContent,
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
                } else if (requestContent != null) {
                    post.setEntity(
                            new StringEntity(
                                    requestContent,
                                    ContentType.APPLICATION_JSON));
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
