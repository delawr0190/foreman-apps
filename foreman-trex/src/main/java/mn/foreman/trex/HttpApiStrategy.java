package mn.foreman.trex;

import mn.foreman.io.Query;
import mn.foreman.model.error.MinerException;
import mn.foreman.trex.json.Summary;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * An {@link HttpApiStrategy} provides a {@link ApiStrategy} implementation
 * that will query the HTTP API running via trex and return a parsed {@link
 * Summary} from the response.
 */
public class HttpApiStrategy
        implements ApiStrategy {

    @Override
    public Summary getSummary(
            final String apiIp,
            final int apiPort)
            throws MinerException {
        try {
            return Query.restQuery(
                    apiIp,
                    apiPort,
                    "/summary",
                    new TypeReference<Summary>() {
                    });
        } catch (final Exception e) {
            throw new MinerException(e);
        }
    }
}
