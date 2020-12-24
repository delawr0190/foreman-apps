package mn.foreman.heroknight;

import mn.foreman.io.Query;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class HeroKnightQuery {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(HeroKnightQuery.class);

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
