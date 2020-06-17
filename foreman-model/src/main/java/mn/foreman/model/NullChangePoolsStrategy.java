package mn.foreman.model;

import mn.foreman.model.command.DoneStatus;

import java.util.List;
import java.util.Map;

/**
 * A {@link ChangePoolsStrategy} implementation that does nothing and returns a
 * {@link DoneStatus#FAILED}.
 */
public class NullChangePoolsStrategy
        implements ChangePoolsStrategy {

    @Override
    public boolean change(
            final String ip,
            final int port,
            final Map<String, Object> parameters,
            final List<Pool> pools) {
        return false;
    }
}
