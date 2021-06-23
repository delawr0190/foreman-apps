package mn.foreman.antminer;

import mn.foreman.io.Query;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.AsicAction;

import java.util.Map;

/** A strategy that performs an antminer factory reset. */
public class StockFactoryResetAction
        implements AsicAction.CompletableAction {

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /** The realm. */
    private final String realm;

    /**
     * Constructor.
     *
     * @param realm                    The realm.
     * @param applicationConfiguration The configuration.
     */
    public StockFactoryResetAction(
            final String realm,
            final ApplicationConfiguration applicationConfiguration) {
        this.realm = realm;
        this.applicationConfiguration = applicationConfiguration;
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
                    this.applicationConfiguration.getWriteSocketTimeout());
        } catch (final Exception e) {
            // Ignore
        }
        return true;
    }
}
