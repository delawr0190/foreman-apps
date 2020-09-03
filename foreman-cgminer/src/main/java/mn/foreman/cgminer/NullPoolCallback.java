package mn.foreman.cgminer;

import java.util.Map;

/** A null implementation of a {@link PoolCallback}. */
public class NullPoolCallback
        implements PoolCallback {

    @Override
    public void foundPool(final Map<String, String> poolInfo) {
        // Do nothing
    }
}
