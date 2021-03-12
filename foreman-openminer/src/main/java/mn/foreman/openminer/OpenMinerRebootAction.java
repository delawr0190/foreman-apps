package mn.foreman.openminer;

import mn.foreman.io.Query;
import mn.foreman.model.AsicAction;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpStatus;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/** Reboots an OpenMiner miner. */
public class OpenMinerRebootAction
        implements AsicAction.CompletableAction {

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
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
                    "/api/om/miner/action",
                    authToken,
                    "{\"action\":\"reboot\",\"parameters\":{}}",
                    new TypeReference<Map<String, Object>>() {
                    },
                    5,
                    TimeUnit.SECONDS,
                    (integer, s) -> success.set(integer == HttpStatus.SC_OK));
        } catch (final Exception e) {
            throw new MinerException("Failed to reboot", e);
        }

        return success.get();
    }
}
