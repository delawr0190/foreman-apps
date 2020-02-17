package mn.foreman.trex;

import mn.foreman.model.error.MinerException;
import mn.foreman.trex.json.Summary;

/**
 * A {@link ApiStrategy} provides a mechanism for obtaining a {@link Summary}
 * from a running trex instance.
 */
public interface ApiStrategy {

    /**
     * Obtains a {@link Summary} from trex.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     *
     * @return The {@link Summary}.
     *
     * @throws MinerException on failure to obtain a valid summary.
     */
    Summary getSummary(
            String apiIp,
            int apiPort)
            throws MinerException;
}
