package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.AsicAction;

import java.util.Map;

/** A strategy that performs an antminer reboot. */
public class StockRebootAction
        implements AsicAction.CompletableAction {

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm The realm.
     */
    public StockRebootAction(final String realm) {
        this.realm = realm;
    }

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> parameters) {
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
                    (code, s) -> {
                    });
        } catch (final Exception e) {
            // Ignore
        }
        return true;
    }
}
