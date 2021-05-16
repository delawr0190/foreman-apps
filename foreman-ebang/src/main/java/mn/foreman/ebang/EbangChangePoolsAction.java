package mn.foreman.ebang;

import mn.foreman.api.model.Pool;
import mn.foreman.ebang.json.Result;
import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.util.ParamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A {@link EbangChangePoolsAction} provides an {@link AbstractChangePoolsAction}
 * implementation that will change the pools in use by an ebang device.
 */
public class EbangChangePoolsAction
        extends AbstractChangePoolsAction {

    /** The logger. */
    private static final Logger LOG =
            LoggerFactory.getLogger(EbangChangePoolsAction.class);

    /** The mapper. */
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
     * @param objectMapper       The mapper.
     */
    public EbangChangePoolsAction(
            final int socketTimeout,
            final TimeUnit socketTimeoutUnits,
            final ObjectMapper objectMapper) {
        this.socketTimeout = socketTimeout;
        this.socketTimeoutUnits = socketTimeoutUnits;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools) {
        final List<Map<String, Object>> content = new LinkedList<>();
        addParams(pools.get(0), 1, content);
        addParams(pools.get(1), 2, content);
        addParams(pools.get(2), 3, content);

        final String username =
                (String) parameters.getOrDefault("username", "");
        final String password =
                (String) parameters.getOrDefault("password", "");

        boolean success = false;
        try {
            success =
                    EbangQuery.query(
                            ip,
                            port,
                            username,
                            password,
                            "/Cgminer/CgminerConfig",
                            null,
                            content,
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
        } catch (final Exception e) {
            LOG.warn("Failed to change pools", e);
        }
        return success;
    }

    /**
     * Adds parameters to the params.
     *
     * @param pool   The pool.
     * @param index  The index.
     * @param params The params.
     */
    private static void addParams(
            final Pool pool,
            final int index,
            final List<Map<String, Object>> params) {
        params.add(
                ParamUtils.toParam(
                        "mip" + index,
                        pool.getUrl()));
        params.add(
                ParamUtils.toParam(
                        "mwork" + index,
                        pool.getUsername()));
        params.add(
                ParamUtils.toParam(
                        "mpassword" + index,
                        pool.getPassword()));
    }
}
