package mn.foreman.openminer;

import mn.foreman.io.Query;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.error.NotAuthenticatedException;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpStatus;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/** Runs a firmware upgrade against an openminer. */
public class OpenMinerFirmwareUpgradeAction
        implements AsicAction.CompletableAction {

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Constructor.
     *
     * @param applicationConfiguration The configuration.
     */
    public OpenMinerFirmwareUpgradeAction(
            final ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws NotAuthenticatedException, MinerException {
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
                        password,
                        this.applicationConfiguration)
                        .orElseThrow(
                                () -> new MinerException("Failed to login"));

        final AtomicBoolean success = new AtomicBoolean(false);
        try {
            Query.restPost(
                    ip,
                    realPort,
                    "/api/om/miner/action",
                    authToken,
                    "{action: \"upgrade\", parameters: {}}",
                    new TypeReference<Map<String, Object>>() {
                    },
                    this.applicationConfiguration.getWriteSocketTimeout(),
                    (integer, s) -> success.set(integer == HttpStatus.SC_OK));
        } catch (final Exception e) {
            throw new MinerException("Failed to upgrade", e);
        }

        return success.get();
    }
}