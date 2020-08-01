package mn.foreman.model;

import java.util.Map;

/** A {@link RebootStrategy} that doesn't do anything. */
public class NullRebootStrategy
        implements RebootStrategy {

    @Override
    public void reboot(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final Callback callback) {
        callback.failed("Not supported");
    }
}
