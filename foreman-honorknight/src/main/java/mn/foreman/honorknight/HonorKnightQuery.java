package mn.foreman.honorknight;

import mn.foreman.honorknight.response.Overview;
import mn.foreman.io.Query;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/** Utilities for querying the honorknight API. */
public class HonorKnightQuery {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(HonorKnightQuery.class);

    /** Mapper for reading/writing json. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Obtains an honorknight overview.
     *
     * @param ip   The IP.
     * @param port The port.
     *
     * @return The overview.
     */
    protected static Optional<Overview> overview(
            final String ip,
            final int port) {
        final AtomicReference<Overview> overview = new AtomicReference<>();
        try {
            Query.post(
                    ip,
                    port,
                    "/api/overview",
                    null,
                    "{}",
                    (code, response) -> {
                        try {
                            overview.set(
                                    OBJECT_MAPPER.readValue(
                                            response,
                                            Overview.class));
                        } catch (final IOException ioe) {
                            LOG.warn("Exception while obtaining overview", ioe);
                        }
                    });
        } catch (final Exception e) {
            LOG.warn("Exception occurred", e);
        }
        return Optional.ofNullable(overview.get());
    }

    /**
     * Queries an honorknight with url-encoded params.
     *
     * @param ip         The IP.
     * @param port       The port.
     * @param uri        The URI.
     * @param parameters The parameters.
     * @param content    The content.
     *
     * @return Whether or not the query was successful.
     */
    protected static boolean query(
            final String ip,
            final int port,
            final String uri,
            final Map<String, Object> parameters,
            final List<Map<String, Object>> content) {
        return query(
                ip,
                port,
                uri,
                parameters,
                content,
                null);
    }

    /**
     * Queries an honorknight with empty json.
     *
     * @param ip         The IP.
     * @param port       The port.
     * @param uri        The URI.
     * @param parameters The parameters.
     *
     * @return Whether or not the query was successful.
     */
    protected static boolean query(
            final String ip,
            final int port,
            final String uri,
            final Map<String, Object> parameters) {
        return query(
                ip,
                port,
                uri,
                parameters,
                null,
                "{}");
    }

    /**
     * Queries an honorknight.
     *
     * @param ip         The IP.
     * @param port       The port.
     * @param uri        The URI.
     * @param parameters The parameters.
     * @param content    The content.
     * @param payload    The json payload.
     *
     * @return Whether or not the query was successful.
     */
    protected static boolean query(
            final String ip,
            final int port,
            final String uri,
            final Map<String, Object> parameters,
            final List<Map<String, Object>> content,
            final String payload) {
        final AtomicBoolean success = new AtomicBoolean(false);
        try {
            Query.post(
                    ip,
                    parameters.containsKey("test")
                            ? port
                            : 80,
                    uri,
                    content,
                    payload,
                    (code, response) ->
                            success.set(
                                    code == HttpStatus.SC_OK &&
                                            response.contains("true")));
        } catch (final Exception e) {
            LOG.warn("Exception occurred", e);
        }
        return success.get();
    }
}
