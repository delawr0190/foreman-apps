package mn.foreman.pickaxe.process;

import mn.foreman.model.MetricsReport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.Validate;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * An {@link HttpPostMetricsProcessingStrategy} provides a mechanism for
 * uploading {@link MetricsReport metrics} to FOREMAN through the FOREMAN API.
 */
public class HttpPostMetricsProcessingStrategy
        implements MetricsProcessingStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(HttpPostMetricsProcessingStrategy.class);

    /** The socket timeout. */
    private static final int SOCKET_TIMEOUT =
            (int) TimeUnit.SECONDS.toMillis(20);

    /** The API key. */
    private final String apiKey;

    /** The URL. */
    private final String url;

    /**
     * Constructor.
     *
     * @param url    The URL.
     * @param apiKey The API key.
     */
    public HttpPostMetricsProcessingStrategy(
            final String url,
            final String apiKey) {
        Validate.notEmpty(
                url,
                "url cannot be empty");
        Validate.notEmpty(
                apiKey,
                "apiKey cannot be empty");
        this.apiKey = apiKey;
        this.url = url;
    }

    @Override
    public void process(final MetricsReport metricsReport) {
        final RequestConfig requestConfig =
                RequestConfig.custom()
                        .setConnectTimeout(SOCKET_TIMEOUT)
                        .setConnectionRequestTimeout(SOCKET_TIMEOUT)
                        .setSocketTimeout((int) TimeUnit.MINUTES.toMillis(1))
                        .build();

        try (final CloseableHttpClient httpClient =
                     HttpClients.custom()
                             .setDefaultRequestConfig(requestConfig)
                             .disableAutomaticRetries()
                             .build()) {
            final ObjectMapper objectMapper =
                    new ObjectMapper()
                            .registerModule(new JavaTimeModule());
            final String json =
                    objectMapper.writeValueAsString(metricsReport);
            LOG.debug("{} generated {}", metricsReport, json);

            final StringEntity stringEntity =
                    new StringEntity(json);

            final HttpPut httpPut =
                    new HttpPut(this.url);
            httpPut.setEntity(stringEntity);
            httpPut.setHeader(
                    "Content-Type",
                    "application/json");
            httpPut.setHeader(
                    "Authorization",
                    "Token " + this.apiKey);

            try (final CloseableHttpResponse httpResponse =
                         httpClient.execute(httpPut)) {
                final int statusCode =
                        httpResponse
                                .getStatusLine()
                                .getStatusCode();
                if (statusCode != HttpStatus.SC_CREATED) {
                    LOG.warn("Received a bad response from {}: code({})",
                            this.url,
                            statusCode);
                }
                LOG.debug("Metrics response content: {}",
                        EntityUtils.toString(httpResponse.getEntity()));
            } catch (final IOException ioe) {
                LOG.warn("Exception occurred while uploading metrics", ioe);
            }
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while uploading metrics", ioe);
        }
    }

    @Override
    public void processAll(final List<MetricsReport> metricsReports) {
        metricsReports.forEach(this::process);
    }
}