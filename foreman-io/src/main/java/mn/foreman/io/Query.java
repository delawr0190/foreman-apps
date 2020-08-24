package mn.foreman.io;

import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** Provides utility methods for querying APIs. */
public class Query {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(Query.class);

    /**
     * Constructor.
     *
     * Note: intentionally hidden.
     */
    private Query() {
        // Do nothing
    }

    /**
     * Utility method to perform a query against a delimiter-based API.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     * @param command The command.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static String delimiterQuery(
            final String apiIp,
            final int apiPort,
            final String command)
            throws MinerException {
        return delimiterQuery(
                apiIp,
                apiPort,
                command,
                15,
                TimeUnit.SECONDS);
    }

    /**
     * Utility method to perform a query against a delimiter-based API.
     *
     * @param apiIp               The API IP.
     * @param apiPort             The API port.
     * @param command             The command.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static String delimiterQuery(
            final String apiIp,
            final int apiPort,
            final String command,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
            throws MinerException {
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        command);
        final Connection connection =
                ConnectionFactory.createDelimiterConnection(
                        request,
                        connectTimeout,
                        connectTimeoutUnits);
        connection.query();

        final boolean completed =
                request.waitForCompletion(
                        connectTimeout,
                        connectTimeoutUnits);
        final String response =
                request.getResponse();
        if (!completed || response == null) {
            throw new MinerException("Failed to obtain a response");
        }

        return response;
    }

    /**
     * Performs an HTTP GET operation against an API that requires digest auth.
     *
     * @param host              The host.
     * @param port              The port.
     * @param realm             The realm.
     * @param path              The path.
     * @param username          The digest auth username.
     * @param password          The digest auth password.
     * @param responseProcessor The response processor.
     *
     * @throws Exception on failure to connect.
     */
    public static void digestGet(
            final String host,
            final int port,
            final String realm,
            final String path,
            final String username,
            final String password,
            final BiConsumer<Integer, String> responseProcessor)
            throws Exception {
        doDigest(
                host,
                port,
                realm,
                path,
                username,
                password,
                false,
                null,
                responseProcessor);
    }

    /**
     * Performs an HTTP post operation.
     *
     * @param host              The host.
     * @param port              The port.
     * @param realm             The realm.
     * @param path              The path.
     * @param username          The username.
     * @param password          The password.
     * @param content           The content.
     * @param responseProcessor The response processor.
     *
     * @throws Exception on failure to connect.
     */
    public static void digestPost(
            final String host,
            final int port,
            final String realm,
            final String path,
            final String username,
            final String password,
            final List<Map<String, Object>> content,
            final BiConsumer<Integer, String> responseProcessor)
            throws Exception {
        doDigest(
                host,
                port,
                realm,
                path,
                username,
                password,
                true,
                content,
                responseProcessor);
    }

    /**
     * Utility method to perform a query against a JSON RPC API.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     * @param command The command.
     * @param type    The response class.
     * @param <T>     The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T jsonQuery(
            final String apiIp,
            final int apiPort,
            final String command,
            final TypeReference<T> type)
            throws MinerException {
        return jsonQuery(
                apiIp,
                apiPort,
                command,
                type,
                10,
                TimeUnit.SECONDS);
    }

    /**
     * Utility method to perform a query against a JSON RPC API.
     *
     * @param apiIp               The API IP.
     * @param apiPort             The API port.
     * @param command             The command.
     * @param type                The response class.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     * @param <T>                 The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T jsonQuery(
            final String apiIp,
            final int apiPort,
            final String command,
            final TypeReference<T> type,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
            throws MinerException {
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        command);
        final Connection connection =
                ConnectionFactory.createJsonConnection(
                        request,
                        connectTimeout,
                        connectTimeoutUnits);
        connection.query();

        return query(
                request,
                type,
                connectTimeout,
                connectTimeoutUnits);
    }

    /**
     * Performs a POST with content.
     *
     * @param host              The host.
     * @param port              The port.
     * @param path              The path.
     * @param content           The content.
     * @param responseProcessor The response processor.
     *
     * @throws Exception on failure.
     */
    public static void post(
            final String host,
            final int port,
            final String path,
            final List<Map<String, Object>> content,
            final BiConsumer<Integer, String> responseProcessor)
            throws Exception {
        doDigest(
                host,
                port,
                null,
                path,
                null,
                null,
                true,
                content,
                responseProcessor);
    }

    /**
     * Performs a rest query with basic auth.
     *
     * @param host              The host.
     * @param port              The port.
     * @param path              The path.
     * @param username          The username.
     * @param password          The password.
     * @param content           The content.
     * @param responseProcessor The processor for responses.
     *
     * @throws IOException        on failure.
     * @throws URISyntaxException on failure.
     */
    public static void restGetQuery(
            final String host,
            final int port,
            final String path,
            final String username,
            final String password,
            final List<Map<String, Object>> content,
            final Consumer<String> responseProcessor) throws IOException, URISyntaxException {
        runRestQuery(
                true,
                host,
                port,
                path,
                username,
                password,
                content,
                responseProcessor);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp                  The API IP.
     * @param apiPort                The API port.
     * @param uri                    The URI.
     * @param username               The username.
     * @param password               The password.
     * @param type                   The response class.
     * @param <T>                    The response type.
     * @param connectionTimeout      The timeout.
     * @param connectionTimeoutUnits The units.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T restQuery(
            final String apiIp,
            final int apiPort,
            final String uri,
            final String username,
            final String password,
            final TypeReference<T> type,
            final int connectionTimeout,
            final TimeUnit connectionTimeoutUnits)
            throws MinerException {
        final String auth =
                Base64.getEncoder().encodeToString(
                        (username + ":" + password).getBytes());
        return restQuery(
                apiIp,
                apiPort,
                uri,
                ImmutableMap.of(
                        "Authorization",
                        "Basic " + auth),
                "POST",
                type,
                connectionTimeout,
                connectionTimeoutUnits);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp    The API IP.
     * @param apiPort  The API port.
     * @param uri      The URI.
     * @param username The username.
     * @param password The password.
     * @param type     The response class.
     * @param <T>      The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T restQuery(
            final String apiIp,
            final int apiPort,
            final String uri,
            final String username,
            final String password,
            final TypeReference<T> type)
            throws MinerException {
        return restQuery(
                apiIp,
                apiPort,
                uri,
                username,
                password,
                type,
                10,
                TimeUnit.SECONDS);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     * @param uri     The URI.
     * @param command The command.
     * @param type    The response class.
     * @param <T>     The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T restQuery(
            final String apiIp,
            final int apiPort,
            final String uri,
            final String command,
            final TypeReference<T> type)
            throws MinerException {
        return restQuery(
                apiIp,
                apiPort,
                uri,
                command,
                type,
                10,
                TimeUnit.SECONDS);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp               The API IP.
     * @param apiPort             The API port.
     * @param uri                 The URI.
     * @param headers             The headers.
     * @param command             The command.
     * @param type                The response class.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     * @param <T>                 The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T restQuery(
            final String apiIp,
            final int apiPort,
            final String uri,
            final Map<String, String> headers,
            final String command,
            final TypeReference<T> type,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
            throws MinerException {
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        uri,
                        headers);

        final Connection connection =
                ConnectionFactory.createRestConnection(
                        request,
                        command,
                        connectTimeout,
                        connectTimeoutUnits);
        connection.query();

        return query(
                request,
                type,
                connectTimeout,
                connectTimeoutUnits);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     * @param uri     The URI.
     * @param type    The response class.
     * @param <T>     The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T restQuery(
            final String apiIp,
            final int apiPort,
            final String uri,
            final TypeReference<T> type)
            throws MinerException {
        return restQuery(
                apiIp,
                apiPort,
                uri,
                "GET",
                type,
                2,
                TimeUnit.SECONDS);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp               The API IP.
     * @param apiPort             The API port.
     * @param uri                 The URI.
     * @param command             The command.
     * @param type                The response class.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     * @param <T>                 The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T restQuery(
            final String apiIp,
            final int apiPort,
            final String uri,
            final String command,
            final TypeReference<T> type,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
            throws MinerException {
        final ApiRequest request =
                new ApiRequestImpl(
                        apiIp,
                        apiPort,
                        uri);

        final Connection connection =
                ConnectionFactory.createRestConnection(
                        request,
                        command,
                        connectTimeout,
                        connectTimeoutUnits);
        connection.query();

        return query(
                request,
                type,
                connectTimeout,
                connectTimeoutUnits);
    }

    /**
     * Performs a rest query with basic auth.
     *
     * @param host              The host.
     * @param port              The port.
     * @param path              The path.
     * @param username          The username.
     * @param password          The password.
     * @param content           The content.
     * @param responseProcessor The processor for responses.
     *
     * @throws IOException        on failure.
     * @throws URISyntaxException on failure.
     */
    public static void restQuery(
            final String host,
            final int port,
            final String path,
            final String username,
            final String password,
            final List<Map<String, Object>> content,
            final Consumer<String> responseProcessor) throws IOException, URISyntaxException {
        runRestQuery(
                false,
                host,
                port,
                path,
                username,
                password,
                content,
                responseProcessor);
    }

    /**
     * Utility method to perform a query against a REST API.
     *
     * @param apiIp               The API IP.
     * @param apiPort             The API port.
     * @param uri                 The URI.
     * @param token               The token.
     * @param type                The response class.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     * @param <T>                 The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    public static <T> T restQueryBearer(
            final String apiIp,
            final int apiPort,
            final String uri,
            final String token,
            final TypeReference<T> type,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
            throws MinerException {
        return restQuery(
                apiIp,
                apiPort,
                uri,
                ImmutableMap.of(
                        "Authorization",
                        "Bearer " + token),
                "GET",
                type,
                connectTimeout,
                connectTimeoutUnits);
    }

    /**
     * Runs a digest request.
     *
     * @param host              The host.
     * @param port              The port.
     * @param realm             The realm.
     * @param path              The path.
     * @param username          The username.
     * @param password          The password.
     * @param isPost            Whether or not the request is a post.
     * @param content           The content.
     * @param responseProcessor What to do with the response.
     *
     * @throws Exception on failure to connect.
     */
    private static void doDigest(
            final String host,
            final int port,
            final String realm,
            final String path,
            final String username,
            final String password,
            final boolean isPost,
            final List<Map<String, Object>> content,
            final BiConsumer<Integer, String> responseProcessor)
            throws Exception {
        final URI uri =
                new URI(
                        "http",
                        null,
                        host,
                        port,
                        path,
                        null,
                        null);
        final URL url = uri.toURL();

        final HttpHost targetHost =
                new HttpHost(
                        url.getHost(),
                        url.getPort(),
                        url.getProtocol());

        CloseableHttpClient httpClient = null;
        try {
            final HttpClientContext context = HttpClientContext.create();

            if (realm != null && username != null) {
                final CredentialsProvider credsProvider =
                        new BasicCredentialsProvider();
                credsProvider.setCredentials(
                        AuthScope.ANY,
                        new UsernamePasswordCredentials(
                                username,
                                password));
                final AuthCache authCache = new BasicAuthCache();
                final DigestScheme digestScheme = new DigestScheme();
                digestScheme.overrideParamter(
                        "realm",
                        realm);
                digestScheme.overrideParamter(
                        "nonce",
                        UUID
                                .randomUUID()
                                .toString()
                                .replace("-", ""));
                authCache.put(targetHost, digestScheme);

                httpClient =
                        HttpClients
                                .custom()
                                .setDefaultCredentialsProvider(credsProvider)
                                .disableAutomaticRetries()
                                .build();
                context.setAuthCache(authCache);
            } else {
                httpClient =
                        HttpClients
                                .custom()
                                .disableAutomaticRetries()
                                .build();
            }

            final HttpRequest httpRequest;
            if (!isPost) {
                httpRequest = new HttpGet(url.getPath());
            } else {
                final HttpPost httpPost = new HttpPost(url.getPath());
                if (content != null) {
                    final List<NameValuePair> params = new ArrayList<>();
                    content.forEach(entry ->
                            params.add(
                                    new BasicNameValuePair(
                                            entry.get("key").toString(),
                                            entry.get("value").toString())));
                    LOG.debug("Params for POST: {}", params);

                    final HttpEntity entity =
                            new UrlEncodedFormEntity(
                                    params,
                                    "UTF-8");
                    LOG.debug("Entity: {}", entity);
                    httpPost.setEntity(entity);
                }
                httpRequest = httpPost;
            }

            LOG.debug("Sending request: {}", httpRequest);

            try (final CloseableHttpResponse response =
                         httpClient.execute(
                                 targetHost,
                                 httpRequest,
                                 context)) {
                final StatusLine statusLine =
                        response.getStatusLine();
                final String responseBody =
                        EntityUtils.toString(response.getEntity());
                LOG.debug("Received digest API response: {}", responseBody);
                responseProcessor.accept(
                        statusLine.getStatusCode(),
                        responseBody);
            }
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
    }

    /**
     * Runs the query.
     *
     * @param request             The request.
     * @param type                The response class.
     * @param connectTimeout      The connection timeout.
     * @param connectTimeoutUnits The connection timeout units.
     * @param <T>                 The response type.
     *
     * @return The response.
     *
     * @throws MinerException on failure to query.
     */
    private static <T> T query(
            final ApiRequest request,
            final TypeReference<T> type,
            final int connectTimeout,
            final TimeUnit connectTimeoutUnits)
            throws MinerException {
        T response;
        if (request.waitForCompletion(
                connectTimeout,
                connectTimeoutUnits)) {
            final ObjectMapper objectMapper =
                    new ObjectMapper()
                            .registerModule(new JavaTimeModule());
            try {
                final String responseJson = request.getResponse();
                LOG.debug("Received API response: {}", responseJson);
                response =
                        objectMapper.readValue(
                                request.getResponse(),
                                type);
            } catch (final Exception e) {
                throw new MinerException(e);
            }
        } else {
            throw new MinerException("Failed to obtain a response");
        }
        return response;
    }

    /**
     * Performs a rest query with basic auth.
     *
     * @param isGet             Whether or not the request is a GET.
     * @param host              The host.
     * @param port              The port.
     * @param path              The path.
     * @param username          The username.
     * @param password          The password.
     * @param content           The content.
     * @param responseProcessor The processor for responses.
     *
     * @throws IOException        on failure.
     * @throws URISyntaxException on failure.
     */
    private static void runRestQuery(
            final boolean isGet,
            final String host,
            final int port,
            final String path,
            final String username,
            final String password,
            final List<Map<String, Object>> content,
            final Consumer<String> responseProcessor) throws IOException, URISyntaxException {
        final URI uri =
                new URI(
                        "http",
                        null,
                        host,
                        port,
                        path,
                        null,
                        null);
        final URL url = uri.toURL();

        final HttpHost targetHost =
                new HttpHost(
                        url.getHost(),
                        url.getPort(),
                        url.getProtocol());

        final CredentialsProvider provider = new BasicCredentialsProvider();
        final UsernamePasswordCredentials credentials =
                new UsernamePasswordCredentials(
                        username,
                        password);
        provider.setCredentials(
                AuthScope.ANY,
                credentials);

        final AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        final HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(provider);
        context.setAuthCache(authCache);

        final CloseableHttpClient httpClient =
                HttpClients
                        .custom()
                        .disableAutomaticRetries()
                        .setDefaultRequestConfig(
                                RequestConfig
                                        .custom()
                                        .setConnectTimeout((int) TimeUnit.SECONDS.toMillis(20))
                                        .setSocketTimeout((int) TimeUnit.SECONDS.toMillis(20))
                                        .build())
                        .build();

        final HttpRequest httpRequest;
        if (!isGet) {
            final HttpPost post = new HttpPost(url.getPath());
            final List<NameValuePair> params = new ArrayList<>();
            content.forEach(entry ->
                    params.add(
                            new BasicNameValuePair(
                                    entry.get("key").toString(),
                                    entry.get("value").toString())));
            post.setEntity(new UrlEncodedFormEntity(params));
            httpRequest = post;
        } else {
            httpRequest = new HttpGet(url.getPath());
        }

        try (final CloseableHttpResponse response =
                     httpClient.execute(
                             targetHost,
                             httpRequest,
                             context)) {
            final String responseBody =
                    EntityUtils.toString(response.getEntity());
            LOG.debug("Received API response: {}", responseBody);
            responseProcessor.accept(responseBody);
        }
    }
}
