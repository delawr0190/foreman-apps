package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.RebootStrategy;
import mn.foreman.model.error.MinerException;

import org.apache.http.HttpStatus;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/** A strategy that performs an antminer reboot. */
public class AntminerRebootStrategy
        implements RebootStrategy {

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm The realm.
     */
    public AntminerRebootStrategy(final String realm) {
        this.realm = realm;
    }

    @Override
    public boolean reboot(
            final String ip,
            final int port,
            final Map<String, Object> parameters)
            throws MinerException {
        final AtomicReference<Integer> statusCode =
                new AtomicReference<>();

        try {
            final String username =
                    (String) parameters.getOrDefault("username", "");
            final String password =
                    (String) parameters.getOrDefault("password", "");

            Query.digestGet(
                    ip,
                    port,
                    this.realm,
                    "/cgi-bin/reboot.cgi",
                    username,
                    password,
                    (code, s) -> statusCode.set(code));
        } catch (final Exception e) {
            throw new MinerException(e);
        }

        final Integer code = statusCode.get();
        return code != null && code == HttpStatus.SC_OK;
    }
}
