package mn.foreman.model;

import java.util.Map;

/** An {@link AsicAction} that doesn't do anything. */
public class NullAsicAction
        implements AsicAction {

    @Override
    public void runAction(
            final String ip,
            final int port,
            final Map<String, Object> args,
            final CompletionCallback callback) {
        callback.failed("Not supported");
    }
}
