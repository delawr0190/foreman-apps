package mn.foreman.util;

import java.util.concurrent.TimeUnit;

/**
 * A common interface to all fae miners that may be used for integration
 * testing.
 */
public interface FakeMinerServer
        extends AutoCloseable {

    /** Starts the server. */
    void start();

    /**
     * Waits until the server is done.
     *
     * @param deadline      The deadline.
     * @param deadlineUnits The deadline (units).
     *
     * @return True if finished before the deadline; false otherwise.
     */
    boolean waitTillDone(
            long deadline,
            TimeUnit deadlineUnits);
}