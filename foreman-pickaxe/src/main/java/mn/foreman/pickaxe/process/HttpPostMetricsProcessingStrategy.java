package mn.foreman.pickaxe.process;

import mn.foreman.model.MetricsReport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * An {@link HttpPostMetricsProcessingStrategy} provides a mechanism for
 * uploading {@link MetricsReport metrics} to FOREMAN through the FOREMAN API.
 */
public class HttpPostMetricsProcessingStrategy
        implements MetricsProcessingStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(HttpPostMetricsProcessingStrategy.class);

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
        final ObjectMapper objectMapper =
                new ObjectMapper()
                        .registerModule(new JavaTimeModule());

        try {
            final String json =
                    objectMapper.writeValueAsString(metricsReport);

            LOG.debug("{} generated {}", metricsReport, json);

            final URL url = new URL(this.url);
            final HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("x-api-key", this.apiKey);

            final OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(json);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            final int code = connection.getResponseCode();
            if (code != HttpURLConnection.HTTP_CREATED) {
                LOG.warn("Received a bad response: code({})", code);
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
