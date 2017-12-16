package mn.foreman.pickaxe.itest.miner;

import java.io.Closeable;

/**
 * A {@link FakeMiner} provides an API miner interface that closely resembles
 * the actual miner interface that's being mocked.
 */
public interface FakeMiner
        extends Closeable {

    /**
     * Starts the miner.
     *
     * @return Whether or not the miner was started.
     */
    boolean start();

    /** Stops the miner. */
    void stop();
}