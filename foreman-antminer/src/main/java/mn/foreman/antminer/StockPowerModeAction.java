package mn.foreman.antminer;

import mn.foreman.api.model.Pool;
import mn.foreman.io.Query;
import mn.foreman.model.AbstractPowerModeAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.util.ParamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/** Sets the Antminer power mode. */
public class StockPowerModeAction
        extends AbstractPowerModeAction {

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The props. */
    private final List<ConfValue> props;

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm        The realm.
     * @param props        The properties.
     * @param objectMapper The mapper.
     */
    public StockPowerModeAction(
            final String realm,
            final List<ConfValue> props,
            final ObjectMapper objectMapper) {
        this.realm = realm;
        this.props = props;
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final PowerMode mode) throws MinerException {
        final AtomicBoolean status = new AtomicBoolean();

        final String username =
                parameters.getOrDefault("username", "root").toString();
        final String password =
                parameters.getOrDefault("password", "root").toString();

        try {
            final Map<String, Object> conf =
                    AntminerUtils.getConf(
                            ip,
                            port,
                            this.realm,
                            "/cgi-bin/get_miner_conf.cgi",
                            username,
                            password);
            if (AntminerUtils.isNewGen(conf)) {
                changeNew(
                        conf,
                        ip,
                        port,
                        username,
                        password,
                        mode,
                        status);
            } else {
                changeOld(
                        conf,
                        parameters,
                        ip,
                        port,
                        username,
                        password,
                        mode,
                        status);
            }
        } catch (final Exception e) {
            throw new MinerException(
                    String.format(
                            "Failed to obtain a response from miner at %s:%d",
                            ip,
                            port));
        }
        return status.get();
    }

    @SuppressWarnings("unchecked")
    private static List<Pool> toPools(final Map<String, Object> conf) {
        final List<Map<String, String>> pools =
                (List<Map<String, String>>) conf.get("pools");
        final List<Pool> newPools = new ArrayList<>(pools.size());
        for (final Map<String, String> pool : pools) {
            newPools.add(
                    Pool
                            .builder()
                            .url(pool.get("url"))
                            .username(pool.get("user"))
                            .password(pool.get("pass"))
                            .build());
        }
        return newPools;
    }

    /**
     * Changes the power mode.
     *
     * @param conf     The conf.
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     * @param mode     The mode.
     * @param status   The status.
     *
     * @throws Exception on failure.
     */
    private void changeNew(
            final Map<String, Object> conf,
            final String ip,
            final int port,
            final String username,
            final String password,
            final PowerMode mode,
            final AtomicBoolean status)
            throws Exception {
        if (conf.containsKey("bitmain-fan-ctrl")) {
            // Obtained a good conf
            final Map<String, Object> newConf = new HashMap<>();
            newConf.put(
                    "bitmain-fan-ctrl",
                    conf.get("bitmain-fan-ctrl"));
            newConf.put(
                    "bitmain-fan-pwm",
                    conf.get("bitmain-fan-pwm"));
            newConf.put(
                    "miner-mode",
                    mode == PowerMode.SLEEPING
                            ? 1
                            : 0);
            newConf.put(
                    "freq-level",
                    conf.get("bitmain-freq-level"));
            newConf.put(
                    "pools",
                    conf.get("pools"));
            Query.digestPost(
                    ip,
                    port,
                    this.realm,
                    "/cgi-bin/set_miner_conf.cgi",
                    username,
                    password,
                    null,
                    this.objectMapper.writeValueAsString(newConf),
                    (integer, s) -> status.set(s != null && s.toLowerCase().contains("ok")));
        }
    }

    /**
     * Changes the power mode.
     *
     * @param conf     The conf.
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     * @param mode     The mode.
     * @param status   The status.
     *
     * @throws Exception on failure.
     */
    private void changeOld(
            final Map<String, Object> conf,
            final Map<String, Object> parameters,
            final String ip,
            final int port,
            final String username,
            final String password,
            final PowerMode mode,
            final AtomicBoolean status)
            throws Exception {
        if (conf.containsKey("bitmain-work-mode")) {
            final List<Pool> pools = toPools(conf);

            final List<Map<String, Object>> content = new LinkedList<>();
            this.props.forEach(
                    confValue ->
                            confValue.getAndSet(
                                    parameters,
                                    conf,
                                    pools,
                                    content));
            ParamUtils.addParam(
                    "_ant_work_mode",
                    mode == PowerMode.SLEEPING
                            ? 254
                            : 0,
                    content);

            Query.digestPost(
                    ip,
                    port,
                    this.realm,
                    "/cgi-bin/set_miner_conf.cgi",
                    username,
                    password,
                    content,
                    null,
                    (integer, s) -> {
                    });
            status.set(true);
        }
    }
}
