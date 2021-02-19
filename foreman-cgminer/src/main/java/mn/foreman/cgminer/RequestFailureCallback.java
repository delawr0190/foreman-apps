package mn.foreman.cgminer;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

/**
 * A failure callback provides one last change to analyze an IP before aborting
 * metrics gathering.
 */
public interface RequestFailureCallback {

    /**
     * Gives one last change to do something before aborting this miner.
     *
     * @param builder        The builder.
     * @param minerException The problem.
     */
    void failed(
            MinerStats.Builder builder,
            MinerException minerException) throws MinerException;
}
