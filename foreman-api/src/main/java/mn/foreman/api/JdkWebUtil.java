package mn.foreman.api;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/** A {@link WebUtil} implementation that uses native JDK libraries. */
public class JdkWebUtil
        implements WebUtil {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(JdkWebUtil.class);

    /** No request content. */
    private static final String NO_CONTENT = "";

    /** How long to wait on socket operations before disconnecting. */
    private static final int SOCKET_TIMEOUT =
            (int) TimeUnit.SECONDS.toMillis(60);

    /** The API token. */
    private final String apiToken;

    /** The configuration. */
    private final String foremanUrl;

    /**
     * Constructor.
     *
     * @param foremanUrl The Foreman API url.
     * @param apiToken   The API token.
     */
    public JdkWebUtil(
            final String foremanUrl,
            final String apiToken) {
        this.foremanUrl = foremanUrl;
        this.apiToken = apiToken;
    }

    @Override
    public Optional<String> get(final String uri) {
        return get(
                uri,
                Collections.emptyMap());
    }

    @Override
    public Optional<String> get(
            final String uri,
            final boolean auth) {
        return get(
                uri,
                auth,
                Collections.emptyMap());
    }

    @Override
    public Optional<String> get(
            final String uri,
            final Map<String, String> params) {
        return get(
                uri,
                true,
                params);
    }

    @Override
    public Optional<String> post(final String uri) {
        return post(
                uri,
                NO_CONTENT);
    }

    @Override
    public Optional<String> post(
            final String uri,
            final String body) {
        final HttpPost httpPost =
                new HttpPost(
                        String.format(
                                "%s%s",
                                this.foremanUrl,
                                uri));
        return writeableOp(
                uri,
                body,
                httpPost);
    }

    @Override
    public Optional<String> put(
            final String uri,
            final String body) {
        final HttpPut httpPut =
                new HttpPut(
                        String.format(
                                "%s%s",
                                this.foremanUrl,
                                uri));
        return writeableOp(
                uri,
                body,
                httpPut);
    }

    /**
     * Generates a parameter string.
     *
     * @param params The parameters.
     *
     * @return The query params.
     *
     * @throws UnsupportedEncodingException on failure.
     */
    private static String getParamsString(final Map<String, String> params)
            throws UnsupportedEncodingException {
        final StringBuilder result = new StringBuilder();

        for (final Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        final String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    /**
     * Performs a GET operation, with auth, if necessary.
     *
     * @param uri    The URI.
     * @param auth   Whether or not to auth.
     * @param params The parameters.
     *
     * @return The response.
     */
    private Optional<String> get(
            final String uri,
            final boolean auth,
            final Map<String, String> params) {
        String response = null;
        try {
            final URL url =
                    new URL(
                            String.format(
                                    "%s%s%s",
                                    this.foremanUrl,
                                    uri,
                                    !params.isEmpty()
                                            ? "?" + getParamsString(params)
                                            : ""));

            LOG.debug("Querying {}{}", this.foremanUrl, uri);

            final HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            if (auth) {
                connection.setRequestProperty(
                        "Authorization",
                        "Token " + this.apiToken);
            }
            connection.setConnectTimeout(SOCKET_TIMEOUT);
            connection.setReadTimeout(SOCKET_TIMEOUT);

            final int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                try (final InputStreamReader inputStreamReader =
                             new InputStreamReader(
                                     connection.getInputStream());
                     final BufferedReader reader =
                             new BufferedReader(
                                     inputStreamReader)) {
                    response = IOUtils.toString(reader);
                    LOG.debug("Received response: {}", response);
                }
            } else {
                LOG.warn("Failed to obtain commands: {}", code);
            }
        } catch (final Exception e) {
            LOG.warn("Exception occurred during get", e);
        }

        return Optional.ofNullable(response);
    }

    /**
     * Runs the provided entity request.
     *
     * @param uri         The URI.
     * @param body        The body.
     * @param requestBase The request base.
     *
     * @return The response content.
     */
    private Optional<String> writeableOp(
            final String uri,
            final String body,
            final HttpEntityEnclosingRequestBase requestBase) {
        String response = null;

        final RequestConfig requestConfig =
                RequestConfig.custom()
                        .setConnectTimeout(SOCKET_TIMEOUT)
                        .setConnectionRequestTimeout(SOCKET_TIMEOUT)
                        .setSocketTimeout(SOCKET_TIMEOUT)
                        .build();

        try (final CloseableHttpClient httpClient =
                     HttpClients.custom()
                             .setDefaultRequestConfig(requestConfig)
                             .disableAutomaticRetries()
                             .build()) {

            final StringEntity stringEntity =
                    new StringEntity(body);

            LOG.debug("Querying {}{} with {}",
                    this.foremanUrl,
                    uri,
                    body);

            requestBase.setEntity(stringEntity);
            requestBase.setHeader(
                    "Content-Type",
                    "application/json");
            requestBase.setHeader(
                    "Authorization",
                    "Token " + this.apiToken);

            try (final CloseableHttpResponse httpResponse =
                         httpClient.execute(requestBase)) {
                final int statusCode =
                        httpResponse
                                .getStatusLine()
                                .getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    response =
                            EntityUtils.toString(
                                    httpResponse.getEntity(),
                                    StandardCharsets.UTF_8);
                }
            } catch (final IOException ioe) {
                LOG.warn("Exception occurred while posting", ioe);
            }
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while posting", ioe);
        }

        return Optional.ofNullable(response);
    }
}
