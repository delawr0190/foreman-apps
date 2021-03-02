package mn.foreman.avalon;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractPasswordAction;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An {@link AvalonPasswordAction} provides an {@link AbstractPasswordAction}
 * implementation that will change a password for an Avalon.
 */
public class AvalonPasswordAction
        extends AbstractPasswordAction {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AvalonPasswordAction.class);

    @Override
    protected boolean doChange(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final String oldPassword,
            final String newPassword) {
        final AtomicBoolean success = new AtomicBoolean(false);

        try {
            Query.post(
                    ip,
                    port,
                    "/admin.cgi",
                    Arrays.asList(
                            ImmutableMap.of(
                                    "key",
                                    "passwd",
                                    "value",
                                    newPassword),
                            ImmutableMap.of(
                                    "key",
                                    "confirm",
                                    "value",
                                    newPassword)),
                    (code, s1) -> success.set(true));
        } catch (final Exception e) {
            LOG.warn("Exception occurred", e);
        }

        return success.get();
    }
}