package mn.foreman.dragonmint;

import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A {@link DragonmintPasswordAction} provides an {@link AbstractPasswordAction}
 * implementation that will change the password.
 */
public class DragonmintPasswordAction
        extends AbstractPasswordAction {

    /** The mapper for creating JSON. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword)
            throws MinerException {
        final List<Map<String, Object>> content = new LinkedList<>();
        addParam(
                "user",
                "admin",
                content);
        addParam(
                "currentPassword",
                oldPassword,
                content);
        addParam(
                "newPassword",
                newPassword,
                content);
        return DragonmintQuery.runQuery(
                ip,
                port,
                "/api/updatePassword",
                parameters,
                content);
    }

    /**
     * Adds parameters to the params.
     *
     * @param key    The key.
     * @param value  The value.
     * @param params The params.
     */
    private static void addParam(
            final String key,
            final String value,
            final List<Map<String, Object>> params) {
        params.add(
                ImmutableMap.of(
                        "key",
                        key,
                        "value",
                        value));
    }
}
