package mn.foreman.avalon;

import mn.foreman.api.model.Pool;
import mn.foreman.model.AbstractChangePoolsAction;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.AsicAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * An {@link AbstractChangePoolsAction} implementation that will change the
 * pools on an Avalon miner.
 */
public class AvalonChangePoolsAction
        extends AbstractChangePoolsAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AvalonChangePoolsAction.class);

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /** The action to run when performing a reboot. */
    private final AsicAction.CompletableAction rebootAction;

    /**
     * Constructor.
     *
     * @param rebootAction             The action to run when performing a
     *                                 reboot.
     * @param applicationConfiguration The configuration.
     */
    public AvalonChangePoolsAction(
            final AsicAction.CompletableAction rebootAction,
            final ApplicationConfiguration applicationConfiguration) {
        this.rebootAction = rebootAction;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools) {
        final String username = parameters.get("username").toString();
        final String password = parameters.get("password").toString();

        boolean success = false;

        final ApplicationConfiguration.SocketConfig socketConfig =
                this.applicationConfiguration.getWriteSocketTimeout();

        for (int i = 0; i < pools.size(); i++) {
            final Pool pool = pools.get(i);
            final String url = pool.getUrl();
            if (url != null && !url.isEmpty()) {
                success |=
                        AvalonUtils.query(
                                ip,
                                port,
                                String.format(
                                        "ascset|0,setpool,%s,%s,%d,%s,%s,%s",
                                        username,
                                        password,
                                        i,
                                        pool.getUrl(),
                                        pool.getUsername(),
                                        pool.getPassword()),
                                (s, request) ->
                                        request.connected() &&
                                                s != null &&
                                                s.toLowerCase().contains("success"),
                                socketConfig);
            }
        }

        if (!success) {
            // Older firmware didn't require indexes
            final Pool pool = pools.get(0);
            success =
                    AvalonUtils.query(
                            ip,
                            port,
                            String.format(
                                    "ascset|0,setpool,%s,%s,%s,%s,%s",
                                    username,
                                    password,
                                    pool.getUrl(),
                                    pool.getUsername(),
                                    pool.getPassword()),
                            (s, request) ->
                                    request.connected() &&
                                            s != null &&
                                            s.toLowerCase().contains("success"),
                            socketConfig);
        }

        if (success) {
            try {
                success =
                        this.rebootAction.run(
                                ip,
                                port,
                                parameters);
            } catch (final Exception e) {
                LOG.warn("Exception occurred", e);
            }
        }

        return success;
    }
}
