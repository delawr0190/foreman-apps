package mn.foreman.avalon;

import mn.foreman.io.Query;
import mn.foreman.model.AbstractPasswordAction;
import mn.foreman.model.ApplicationConfiguration;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpStatus;
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

    /** The configuration. */
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Constructor.
     *
     * @param applicationConfiguration The configuration.
     */
    public AvalonPasswordAction(final ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

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
                    (code, s1) -> success.set(code == HttpStatus.SC_OK),
                    this.applicationConfiguration.getWriteSocketTimeout());
        } catch (final Exception e) {
            LOG.warn("Exception occurred", e);
        }

        return success.get();
    }
}