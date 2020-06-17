package mn.foreman.antminer;

import mn.foreman.antminer.json.MinerConf;
import mn.foreman.io.Query;
import mn.foreman.model.ChangePoolsStrategy;
import mn.foreman.model.Pool;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An {@link AntminerChangePoolsStrategy} provides a {@link ChangePoolsStrategy}
 * implementation that will change the pools in use by an antminer device.
 */
public class AntminerChangePoolsStrategy
        implements ChangePoolsStrategy {

    /** The digest auth realm. */
    private static final String ANTMINER_REALM =
            "antMiner Configuration";

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AntminerChangePoolsStrategy.class);

    @Override
    public boolean change(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        if (pools.size() != 3) {
            throw new MinerException("3 pools are required");
        }

        boolean success;

        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");

            final AtomicReference<MinerConf> minerConfRef =
                    new AtomicReference<>();
            Query.digestGet(
                    ip,
                    port,
                    ANTMINER_REALM,
                    "/cgi-bin/get_miner_conf.cgi",
                    username,
                    password,
                    s -> {
                        try {
                            minerConfRef.set(
                                    objectMapper.readValue(
                                            s,
                                            MinerConf.class));
                        } catch (final IOException e) {
                            LOG.warn("Exception occurred while querying", e);
                        }
                    });
            final MinerConf minerConf = minerConfRef.get();
            if (minerConf != null) {
                success =
                        changeConf(
                                minerConf,
                                ip,
                                port,
                                username,
                                password,
                                pools);
            } else {
                throw new MinerException(
                        String.format(
                                "Failed to obtain a response from miner at %s:%d",
                                ip,
                                port));
            }
        } catch (final Exception e) {
            throw new MinerException(e);
        }

        return success;
    }

    /**
     * Creates a parameter.
     *
     * @param key   The parameter key.
     * @param value The parameter value.
     * @param dest  The destination.
     */
    private static void addParam(
            final String key,
            final Object value,
            final List<Map<String, Object>> dest) {
        dest.add(
                ImmutableMap.of(
                        "key",
                        key,
                        "value",
                        value != null
                                ? value
                                : ""));
    }

    /**
     * Creates the pool params for the provided {@link Pool}.
     *
     * @param pool  The pool.
     * @param index The index.
     * @param dest  The destination.
     */
    private static void addPool(
            final Pool pool,
            final int index,
            final List<Map<String, Object>> dest) {
        addParam(
                String.format(
                        "_ant_pool%durl",
                        index),
                pool.getUrl(),
                dest);
        addParam(
                String.format(
                        "_ant_pool%duser",
                        index),
                pool.getUsername(),
                dest);
        addParam(
                String.format(
                        "_ant_pool%dpw",
                        index),
                pool.getPassword(),
                dest);
    }

    /**
     * Changes the configuration.
     *
     * @param minerConf The conf.
     * @param ip        The ip.
     * @param port      The port.
     * @param username  The username.
     * @param password  The password.
     * @param pools     The new pools.
     *
     * @return Whether or not the change was successful.
     *
     * @throws Exception on failure to communicate.
     */
    private static boolean changeConf(
            final MinerConf minerConf,
            final String ip,
            final int port,
            final String username,
            final String password,
            final List<Pool> pools)
            throws Exception {
        final List<Map<String, Object>> content = new LinkedList<>();
        for (int i = 1; i <= pools.size(); i++) {
            addPool(
                    pools.get(i - 1),
                    i,
                    content);
        }
        addParam(
                "_ant_nobeeper",
                minerConf.noBeeper,
                content);
        addParam(
                "_ant_notempoverctrl",
                minerConf.noTempOverCtrl,
                content);
        addParam(
                "_ant_fan_customize_switch",
                minerConf.customizeFanSwitch,
                content);
        addParam(
                "_ant_fan_customize_value",
                minerConf.customizeFanValue,
                content);
        addParam(
                "_ant_freq",
                minerConf.freq,
                content);

        final AtomicReference<String> response = new AtomicReference<>();
        Query.digestPost(
                ip,
                port,
                ANTMINER_REALM,
                "/cgi-bin/set_miner_conf.cgi",
                username,
                password,
                content,
                response::set);

        return "ok".equalsIgnoreCase(response.get());
    }
}
