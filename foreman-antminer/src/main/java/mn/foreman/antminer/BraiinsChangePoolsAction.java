package mn.foreman.antminer;

import mn.foreman.api.model.Pool;
import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An {@link AbstractChangePoolsAction} implementation that will change the
 * pools on an Antminer running bOS.
 */
public class BraiinsChangePoolsAction
        extends AbstractChangePoolsAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(BraiinsChangePoolsAction.class);

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Constructor.
     *
     * @param applicationConfiguration The configuration.
     */
    public BraiinsChangePoolsAction(final ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools)
            throws MinerException {
        final AtomicBoolean success = new AtomicBoolean(false);

        final String username = parameters.get("username").toString();
        final String password = parameters.get("password").toString();

        final ObjectMapper objectMapper = new ObjectMapper();

        BraiinsUtils.query(
                ip,
                port,
                username,
                password,
                "/cgi-bin/luci/admin/miner/cfg_data/",
                true,
                Collections.emptyList(),
                null,
                (statusCode, data) ->
                        processGetConfig(
                                statusCode,
                                data,
                                ip,
                                port,
                                username,
                                password,
                                pools,
                                objectMapper,
                                success),
                this.applicationConfiguration.getWriteSocketTimeout());

        return success.get();
    }

    /**
     * Converts the provided pools to the pools as json.
     *
     * @param pools The new pools.
     *
     * @return The new json group.
     */
    private static List<Map<String, Object>> toGroup(final List<Pool> pools) {
        final Map<String, Object> group = new HashMap<>();
        group.put("name", "Foreman Group");

        final List<Map<String, String>> newPools = new LinkedList<>();
        pools
                .stream()
                .filter(pool -> pool.getUrl() != null && !pool.getUrl().isEmpty())
                .map(pool ->
                        ImmutableMap.of(
                                "url",
                                pool.getUrl(),
                                "user",
                                pool.getUsername(),
                                "password",
                                pool.getPassword()))
                .forEach(newPools::add);
        group.put("pool", newPools);

        return Collections.singletonList(group);
    }

    /**
     * Processes a post-cfg_data call.
     *
     * @param statusCode   The status code.
     * @param data         The data.
     * @param ip           The ip.
     * @param port         The port.
     * @param username     The username.
     * @param password     The password.
     * @param pools        The pools.
     * @param objectMapper The mapper for the json response.
     * @param success      The change pools success flag.
     */
    @SuppressWarnings("unchecked")
    private void processGetConfig(
            final int statusCode,
            final String data,
            final String ip,
            final int port,
            final String username,
            final String password,
            final List<Pool> pools,
            final ObjectMapper objectMapper,
            final AtomicBoolean success) {
        try {
            if (statusCode == HttpStatus.SC_OK) {
                final Map<String, Object> config =
                        new HashMap<>(objectMapper.readValue(
                                data,
                                new TypeReference<Map<String, Object>>() {
                                }));
                LOG.info("Current bOS config: {}", config);
                config.remove("status");

                final Map<String, Object> newData =
                        (Map<String, Object>) config.get("data");
                newData.remove("format");
                newData.put("group", toGroup(pools));

                final String json =
                        objectMapper.writeValueAsString(config);
                LOG.info("New bOS config: {}", json);

                BraiinsUtils.query(
                        ip,
                        port,
                        username,
                        password,
                        "/cgi-bin/luci/admin/miner/cfg_save/",
                        false,
                        Collections.emptyList(),
                        json,
                        (newStatusCode, newNewData) ->
                                processSaveConfig(
                                        newStatusCode,
                                        ip,
                                        port,
                                        username,
                                        password,
                                        success),
                        this.applicationConfiguration.getWriteSocketTimeout());
            }
        } catch (final Exception e) {
            LOG.warn("Exception occurred while parsing config", e);
        }
    }

    /**
     * Processes a post-cfg_save action.
     *
     * @param statusCode The status of the save.
     * @param ip         The ip.
     * @param port       The port.
     * @param username   The username.
     * @param password   The password.
     * @param success    The change pools save action.
     */
    private void processSaveConfig(
            final int statusCode,
            final String ip,
            final int port,
            final String username,
            final String password,
            final AtomicBoolean success) {
        try {
            if (statusCode == HttpStatus.SC_OK) {
                BraiinsUtils.query(
                        ip,
                        port,
                        username,
                        password,
                        "/cgi-bin/luci/admin/miner/cfg_apply/",
                        false,
                        Collections.emptyList(),
                        null,
                        (newStatusCode, newNewData) ->
                                success.set(newStatusCode == HttpStatus.SC_OK),
                        this.applicationConfiguration.getWriteSocketTimeout());
            }
        } catch (final Exception e) {
            LOG.warn("Exception occurred while parsing config", e);
        }
    }
}
