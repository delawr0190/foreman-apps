package mn.foreman.multminer;

import mn.foreman.io.Query;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** Utilities for querying a multminer. */
public class MultMinerQuery {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(MultMinerQuery.class);

    /**
     * Adds the key and value to the destination.
     *
     * @param key   The key.
     * @param value The value.
     * @param dest  The destination.
     */
    public static void add(
            final String key,
            final String value,
            final List<Map<String, Object>> dest) {
        dest.add(
                ImmutableMap.of(
                        "key",
                        key,
                        "value",
                        value));
    }

    /**
     * Updates the multminer instance, leveraging the provided {@link Consumer}
     * to add content based on the action that's being performed.
     *
     * @param ip           The ip.
     * @param port         The port.
     * @param act          The act.
     * @param contentAdder The content enricher.
     * @param delay        The delayer.
     *
     * @return Whether or not the update was successful.
     *
     * @throws MinerException on failure.
     */
    public static boolean query(
            final String ip,
            final int port,
            final String act,
            final Consumer<List<Map<String, Object>>> contentAdder,
            final Supplier<Boolean> delay) throws MinerException {
        boolean success;

        final List<Map<String, Object>> content = new LinkedList<>();
        add(
                "act",
                act,
                content);
        contentAdder.accept(content);

        try {
            final AtomicReference<Integer> statusCode = new AtomicReference<>();
            Query.post(
                    ip,
                    port,
                    "/index.csp",
                    content,
                    (code, s) -> {
                        LOG.debug(
                                "Received {} - response {}",
                                code,
                                statusCode);
                        statusCode.set(code);
                    });
            final Integer code = statusCode.get();
            success = (code != null && code == HttpStatus.SC_OK);
        } catch (final Exception e) {
            throw new MinerException(e);
        }

        // Wait, if desired, until proceeding
        return success && delay.get();
    }
}
