package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractPowerModeAction;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/** Sets the Antminer power mode. */
public class StockPowerModeAction
        extends AbstractPowerModeAction {

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm        The realm.
     * @param objectMapper The mapper.
     */
    public StockPowerModeAction(
            final String realm,
            final ObjectMapper objectMapper) {
        this.realm = realm;
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
            if (AntminerUtils.isNewGen(
                    ip,
                    port,
                    this.realm,
                    username,
                    password)) {
                changeNew(
                        ip,
                        port,
                        username,
                        password,
                        mode,
                        status);
            } else {
                throw new MinerException("Older model not supported");
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

    /**
     * Changes the power mode.
     *
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
            final String ip,
            final int port,
            final String username,
            final String password,
            final PowerMode mode,
            final AtomicBoolean status)
            throws Exception {
        final Map<String, Object> conf =
                AntminerUtils.getConf(
                        ip,
                        port,
                        this.realm,
                        "/cgi-bin/get_miner_conf.cgi",
                        username,
                        password);
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
}
