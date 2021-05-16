package mn.foreman.ebang;

import mn.foreman.api.model.Network;
import mn.foreman.ebang.json.Result;
import mn.foreman.io.QueryException;
import mn.foreman.model.AbstractNetworkAction;
import mn.foreman.util.ParamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * An {@link EbangNetworkAction} provides an {@link AbstractNetworkAction}
 * implementation that will change the network settings.
 */
public class EbangNetworkAction
        extends AbstractNetworkAction {

    /** The logger. */
    private static final Logger LOG =
            LoggerFactory.getLogger(EbangNetworkAction.class);

    /** The json mapper. */
    private final ObjectMapper objectMapper;

    /** The socket timeout. */
    private final int socketTimeout;

    /** The socket timeout (units). */
    private final TimeUnit socketTimeoutUnits;

    /**
     * Constructor.
     *
     * @param socketTimeout      The socket timeout.
     * @param socketTimeoutUnits The socket timeout (units).
     * @param objectMapper       The json mapper.
     */
    public EbangNetworkAction(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final ObjectMapper objectMapper) {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Network network) {
        final String username =
                (String) parameters.getOrDefault("username", "");
        final String password =
                (String) parameters.getOrDefault("password", "");

        boolean success = false;
        try {
            if (setToStatic(
                    ip,
                    port,
                    username,
                    password)) {
                success =
                        setNetworkConfig(
                                ip,
                                port,
                                username,
                                password,
                                network);
            }
        } catch (final Exception e) {
            LOG.warn("Failed to apply network configuration", e);
        }
        return success;
    }

    /**
     * Sets the miner's static IP config.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     * @param network  The network.
     *
     * @return Whether or not the operation was successful.
     *
     * @throws QueryException on failure.
     */
    private boolean setNetworkConfig(
            final String ip,
            final int port,
            final String username,
            final String password,
            final Network network) throws QueryException {
        return EbangQuery.query(
                ip,
                port,
                username,
                password,
                "/admininfo/Setadmininfo",
                null,
                Arrays.asList(
                        ParamUtils.toParam(
                                "ip",
                                network.ipAddress),
                        ParamUtils.toParam(
                                "mask",
                                network.netmask),
                        ParamUtils.toParam(
                                "gate",
                                network.gateway),
                        ParamUtils.toParam(
                                "dns",
                                network.dns)),
                (code, body) ->
                        this.objectMapper.readValue(
                                body,
                                Result.class),
                (code, body) -> {
                },
                this.socketTimeout,
                this.socketTimeoutUnits)
                .map(result -> result.status == 1)
                .orElse(false);
    }

    /**
     * Sets the miner to static IPing.
     *
     * @param ip       The ip.
     * @param port     The port.
     * @param username The username.
     * @param password The password.
     *
     * @return Whether or not the operation was successful.
     *
     * @throws QueryException on failure.
     */
    private boolean setToStatic(
            final String ip,
            final int port,
            final String username,
            final String password) throws QueryException {
        return EbangQuery.query(
                ip,
                port,
                username,
                password,
                "/admininfo/SetDHCPEnable",
                null,
                Collections.singletonList(
                        ParamUtils.toParam(
                                "DHCPNC",
                                0)),
                (code, body) ->
                        this.objectMapper.readValue(
                                body,
                                Result.class),
                (code, body) -> {
                },
                this.socketTimeout,
                this.socketTimeoutUnits)
                .map(result -> result.status == 1)
                .orElse(false);
    }
}
