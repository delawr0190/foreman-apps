package mn.foreman.openminer;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpStatus;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A {@link OpenMinerPasswordAction} provides an {@link AbstractPasswordAction}
 * implementation that will change the password.
 */
public class OpenMinerPasswordAction
        extends AbstractPasswordAction {

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword)
            throws MinerException {
        final String username =
                parameters.getOrDefault("username", "").toString();
        final String password =
                parameters.getOrDefault("password", "").toString();

        // Test hook
        final int realPort =
                port != 8080 && port != 8081
                        ? 80
                        : port;

        final String authToken =
                OpenMinerUtils.getAuthToken(
                        ip,
                        realPort,
                        username,
                        password)
                        .orElseThrow(
                                () -> new MinerException("Failed to login"));

        final AtomicBoolean success = new AtomicBoolean(false);
        try {
            Query.restPost(
                    ip,
                    realPort,
                    "/api/user/current/password",
                    authToken,
                    String.format(
                            "{\"current\":\"%s\",\"new\":\"%s\"}",
                            oldPassword,
                            newPassword),
                    new TypeReference<Map<String, String>>() {
                    },
                    (integer, s) -> success.set(integer == HttpStatus.SC_OK));
        } catch (final Exception e) {
            throw new MinerException("Failed to change password", e);
        }

        return success.get();
    }
}
