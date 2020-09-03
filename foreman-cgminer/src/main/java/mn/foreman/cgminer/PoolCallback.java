package mn.foreman.cgminer;

import java.util.Map;

/** A callback mechanism to inform a listener that a pool has been found. */
public interface PoolCallback {

    /**
     * Notifies a listener that a pool has been found.
     *
     * @param poolInfo The found pool.
     */
    void foundPool(Map<String, String> poolInfo);
}
