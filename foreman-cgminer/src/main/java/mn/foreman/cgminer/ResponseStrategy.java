package mn.foreman.cgminer;

import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

/**
 * A {@link ResponseStrategy} provides a mechanism for performing a
 * fork-specific interpretation of a {@link CgMinerResponse}.
 *
 * <p>This class was needed due to no standard API format existing for each
 * cgminer fork.  While the commands have mostly remained the same, they return
 * different fields.</p>
 */
public interface ResponseStrategy {

    /**
     * Processes the provided response, updating the builder as it goes.
     *
     * @param builder  The builder to update.
     * @param response The response.
     *
     * @throws MinerException on failure to process response.
     */
    void processResponse(
            MinerStats.Builder builder,
            CgMinerResponse response)
            throws MinerException;
}