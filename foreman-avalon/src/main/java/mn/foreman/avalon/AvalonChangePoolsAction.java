package mn.foreman.avalon;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.Pool;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An {@link AbstractChangePoolsAction} implementation that will change the
 * pools on an Avalon miner.
 */
public class AvalonChangePoolsAction
        extends AbstractChangePoolsAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AvalonChangePoolsAction.class);

    /** The mapper for parsing json. */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /** The action to run when performing a reboot. */
    private final AvalonRebootAction rebootAction;

    /**
     * Constructor.
     *
     * @param rebootAction The action to run when performing a reboot.
     */
    public AvalonChangePoolsAction(final AvalonRebootAction rebootAction) {
        this.rebootAction = rebootAction;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools) {
        final AtomicBoolean success = new AtomicBoolean(false);

        final String username = parameters.get("username").toString();
        final String password = parameters.get("password").toString();

        try {
            Query.restGetQuery(
                    ip,
                    port,
                    "/updatecgconf.cgi",
                    username,
                    password,
                    Collections.emptyList(),
                    s -> {
                        try {
                            final Map<String, Object> conf = extractConf(s);
                            Query.post(
                                    ip,
                                    port,
                                    "/cgconf.cgi",
                                    toParams(
                                            conf,
                                            pools),
                                    (code, s1) -> {
                                        if (code == HttpStatus.SC_OK) {
                                            // Miner needs to be rebooted after
                                            // the config has been changed
                                            success.set(
                                                    this.rebootAction.run(
                                                            ip,
                                                            port,
                                                            parameters));
                                        }
                                    });
                        } catch (final Exception e) {
                            LOG.warn("Exception occurred", e);
                        }
                    });
        } catch (final Exception e) {
            LOG.warn("Exception occurred", e);
        }

        return success.get();
    }

    /**
     * Adds a field to the dest and removes it from the old conf so we can copy
     * over whatever's remaining.
     *
     * @param key   The key.
     * @param value The value.
     * @param dest  The dest for the new params.
     * @param conf  The old conf.
     */
    private static void addField(
            final String key,
            final String value,
            final List<Map<String, Object>> dest,
            final Map<String, Object> conf) {
        dest.add(
                toValueMap(
                        key,
                        value));
        conf.remove(key);
    }

    /**
     * Updates the pool.
     *
     * @param index The index.
     * @param pool  The pool.
     * @param dest  The param destination.
     * @param conf  The old conf.
     */
    private static void addUpdatedPool(
            final int index,
            final Pool pool,
            final List<Map<String, Object>> dest,
            final Map<String, Object> conf) {
        addField("pool" + index, pool.getUrl(), dest, conf);
        addField("worker" + index, pool.getUsername(), dest, conf);
        addField("passwd" + index, pool.getPassword(), dest, conf);
    }

    /**
     * Converts the provided old conf and the new pools to new params for the
     * POST.
     *
     * @param conf  The old conf.
     * @param pools The new pools.
     *
     * @return The new params.
     */
    private static List<Map<String, Object>> toParams(
            final Map<String, Object> conf,
            final List<Pool> pools) {
        final List<Map<String, Object>> newParams = new LinkedList<>();
        for (int i = 0; i < pools.size(); i++) {
            addUpdatedPool(
                    i + 1,
                    pools.get(i),
                    newParams,
                    conf);
        }

        // Copy over everything else
        conf
                .entrySet()
                .stream()
                .map(entry ->
                        toValueMap(
                                entry.getKey(),
                                entry.getValue()))
                .forEach(newParams::add);

        return newParams;
    }

    /**
     * Creates a value map.
     *
     * @param key   The key.
     * @param value The value.
     *
     * @return The map.
     */
    private static Map<String, Object> toValueMap(
            final String key,
            final Object value) {
        return ImmutableMap.of(
                "key",
                key,
                "value",
                value);
    }

    /**
     * Extracts the conf from the returned web response.
     *
     * @param conf The raw conf (javascript object).
     *
     * @return The configuration.
     *
     * @throws IOException on failure to obtain the conf.
     */
    private Map<String, Object> extractConf(final String conf)
            throws IOException {
        final String startHaystack = conf.substring(0, conf.indexOf("pool1"));
        final int confStart = startHaystack.lastIndexOf("{");
        final int confEnd = conf.lastIndexOf("}");
        final String jsonConf = conf.substring(confStart, confEnd + 1);
        return this.objectMapper.readValue(
                jsonConf,
                new TypeReference<Map<String, Object>>() {
                });
    }
}
