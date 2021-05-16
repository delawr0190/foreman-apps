package mn.foreman.obelisk;

import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/** Changes the password of an obelisk. */
public class ObeliskPasswordAction
        extends AbstractPasswordAction {

    /** The mapper for JSON. */
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
    public ObeliskPasswordAction(
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
            final String oldPassword,
            final String newPassword)
            throws MinerException {
        boolean result;
        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");
            result =
                    ObeliskQuery.runSessionQuery(
                            ip,
                            port,
                            "/api/action/changePassword",
                            true,
                            username,
                            password,
                            this.socketTimeout,
                            this.socketTimeoutUnits,
                            Collections.emptyList(),
                            (code, body) -> null,
                            this.objectMapper.writeValueAsString(
                                    ImmutableMap.of(
                                            "username",
                                            "admin",
                                            "oldPassword",
                                            oldPassword,
                                            "newPassword",
                                            newPassword)),
                            o -> {
                            },
                            new HashMap<>());
        } catch (final Exception e) {
            throw new MinerException(e);
        }
        return result;
    }
}