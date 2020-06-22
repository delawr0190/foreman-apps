package mn.foreman.multminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractChangePoolsStrategy;
import mn.foreman.model.ChangePoolsStrategy;
import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A {@link MultMinerChangePoolsStrategy} provides a {@link ChangePoolsStrategy}
 * implementation that will change the pools in use by a multminer device.
 */
public class MultMinerChangePoolsStrategy
        extends AbstractChangePoolsStrategy {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        boolean success;

        final List<Map<String, Object>> content = new LinkedList<>();
        for (int i = 0; i < pools.size(); i++) {
            addPool(
                    pools.get(i),
                    i,
                    content);
        }

        try {
            final AtomicReference<Integer> statusCode = new AtomicReference<>();
            Query.post(
                    ip,
                    port,
                    "/index.csp?act=pol",
                    content,
                    (code, s) -> {
                        statusCode.set(code);
                    });
            final Integer code = statusCode.get();
            success = (code != null && code == HttpStatus.SC_OK);
        } catch (final Exception e) {
            throw new MinerException(e);
        }

        return success;
    }

    /**
     * Adds the key and value to the destination.
     *
     * @param key   The key.
     * @param value The value.
     * @param dest  The destination.
     */
    private static void add(
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
     * Adds the provided pool.
     *
     * @param pool  The pool.
     * @param index The index.
     * @param dest  The destination.
     */
    private static void addPool(
            final Pool pool,
            final int index,
            final List<Map<String, Object>> dest) {
        add(
                String.format(
                        "p%durl",
                        index),
                String.format(
                        "-o%s %s",
                        (index == 0 ? "" : index),
                        pool.getUrl()),
                dest);
        add(
                String.format(
                        "p%duser",
                        index),
                String.format(
                        "-u%s %s",
                        (index == 0 ? "" : index),
                        pool.getUsername()),
                dest);
        add(
                String.format(
                        "p%dpwd",
                        index),
                String.format(
                        "-p%s %s",
                        (index == 0 ? "" : index),
                        pool.getPassword()),
                dest);
    }
}
