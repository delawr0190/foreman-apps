package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.AsicAction;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/** A strategy that performs an antminer factory reset. */
public class StockFactoryResetAction
        implements AsicAction.CompletableAction {

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm The realm.
     */
    public StockFactoryResetAction(final String realm) {
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
                    "/cgi-bin/reset_conf.cgi",
                    username,
                    password,
                    (code, s) -> {
                    },
                    5,
                    TimeUnit.SECONDS);
        } catch (final Exception e) {
            // Ignore
        }
        return true;
    }
}
