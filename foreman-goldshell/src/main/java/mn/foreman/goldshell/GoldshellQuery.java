package mn.foreman.goldshell;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/** Utilities for querying a goldshell. */
public class GoldshellQuery {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(GoldshellQuery.class);

    /** The mapper for json. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Runs a PUT query.
     *
     * @param ip                 The IP.
     * @param port               The port.
     * @param uri                The URI.
     * @param param              The param.
     * @param reference          The response type.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     * @param <T>                The response type.
     *
     * @return The response object.
     */
    public static <T> Optional<T> runGet(
            final String ip,
            final int port,
            final String uri,
            final Object param,
            final TypeReference<T> reference,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        final AtomicReference<String> response = new AtomicReference<>();
        if (run(
                ip,
                port,
                uri,
                param,
                response::set,
                false,
                socketTimeout,
                socketTimeoutUnits)) {
            final String responseString = response.get();
            if (responseString != null) {
                try {
                    return Optional.ofNullable(
                            OBJECT_MAPPER.readValue(
                                    response.get(),
                                    reference));
                } catch (final IOException e) {
                    LOG.warn("Exception occurred while querying", e);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Runs a PUT query.
     *
     * @param ip                 The IP.
     * @param port               The port.
     * @param uri                The URI.
     * @param param              The param.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     *
     * @return Whether or not the query was successful.
     */
    public static boolean runPut(
            final String ip,
            final int port,
            final String uri,
            final Object param,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        return run(
                ip,
                port,
                uri,
                param,
                s -> {
                },
                true,
                socketTimeout,
                socketTimeoutUnits);
    }

    /**
     * Runs an API query.
     *
     * @param ip                 The IP.
     * @param port               The port.
     * @param uri                The URI.
     * @param param              The param.
     * @param callback           The response callback.
     * @param isPut              Whether or not a PUT operation.
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     *
     * @return Whether or not the call was successful.
     */
    private static boolean run(
            final String ip,
            final int port,
            final String uri,
            final Object param,
            final Consumer<String> callback,
            final boolean isPut,
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits) {
        boolean success = false;

        final int socketTimeoutMillis =
                (int) socketTimeoutUnits.toMillis(socketTimeout);

        final RequestConfig requestConfig =
                RequestConfig.custom()
                        .setConnectTimeout(socketTimeoutMillis)
                        .setConnectionRequestTimeout(socketTimeoutMillis)
                        .setSocketTimeout(socketTimeoutMillis)
                        .build();

        try (final CloseableHttpClient httpClient =
                     HttpClients.custom()
                             .setDefaultRequestConfig(requestConfig)
                             .disableAutomaticRetries()
                             .build()) {

            final String url =
                    String.format(
                            "http://%s:%d%s",
                            ip,
                            port,
                            uri);

            final HttpRequestBase requestBase;
            if (isPut) {
                final HttpPut httpPut = new HttpPut(url);
                if (param != null) {
                    final String json =
                            OBJECT_MAPPER.writeValueAsString(param);
                    final StringEntity stringEntity =
                            new StringEntity(json);
                    httpPut.setEntity(stringEntity);
                }
                requestBase = httpPut;
            } else {
                requestBase = new HttpGet(url);
            }

            requestBase.setHeader(
                    "Content-Type",
                    "application/json");
            requestBase.addHeader(
                    "Accept",
                    "application/json");

            try (final CloseableHttpResponse httpResponse =
                         httpClient.execute(requestBase)) {
                final int statusCode =
                        httpResponse
                                .getStatusLine()
                                .getStatusCode();
                if (statusCode == HttpStatus.SC_CREATED || statusCode == HttpStatus.SC_OK) {
                    final HttpEntity responseEntity = httpResponse.getEntity();
                    if (responseEntity != null) {
                        final String responseBody =
                                EntityUtils.toString(responseEntity);
                        callback.accept(responseBody);
                    }
                    success = true;
                }
            } catch (final IOException ioe) {
                LOG.warn("Exception occurred while performing op", ioe);
            }
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while performing op", ioe);
        }

        return success;
    }
}
