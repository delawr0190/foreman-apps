package mn.foreman.avalon;

import mn.foreman.io.Query;
import mn.foreman.model.AsicAction;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

/**
 * A {@link AvalonRebootAction} provides an action implementation that will
 * perform a reboot on an avalon miner.
 */
public class AvalonRebootAction
        implements AsicAction.CompletableAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AvalonRebootAction.class);

    @Override
    public boolean run(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        final String username = args.get("username").toString();
        final String password = args.get("password").toString();

        try {
            Query.restQuery(
                    ip,
                    port,
                    "/reboot.cgi",
                    username,
                    password,
                    Collections.singletonList(
                            ImmutableMap.of(
                                    "key",
                                    "reboot",
                                    "value",
                                    "1")),
                    s -> {
                        // Doesn't return anything
                    });
        } catch (final Exception e) {
            // Ignore - will throw since the miner doesn't return anything
            LOG.warn("Exception occurred", e);
        }

        LOG.info("Miner has rebooted");
        return true;
    }
}
