package mn.foreman.aixin;

import mn.foreman.io.Query;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class AixinQuery {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AixinQuery.class);

    protected static boolean query(
            final String ip,
            final String uri,
            final Map<String, Object> parameters) {
        return query(
                ip,
                uri,
                parameters,
                null,
                "{}");
    }

    protected static boolean query(
            final String ip,
            final String uri,
            final Map<String, Object> parameters,
            final List<Map<String, Object>> content) {
        return query(
                ip,
                uri,
                parameters,
                content,
                null);
    }

    protected static boolean query(
            final String ip,
            final String uri,
            final Map<String, Object> parameters,
            final List<Map<String, Object>> content,
            final String payload) {
        final AtomicBoolean success = new AtomicBoolean(false);
        try {
            Query.post(
                    ip,
                    parameters.containsKey("testPort")
                            ? Integer.parseInt(parameters.get("testPort").toString())
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
