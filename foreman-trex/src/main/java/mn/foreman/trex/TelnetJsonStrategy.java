package mn.foreman.trex;

import mn.foreman.io.Query;
import mn.foreman.model.error.MinerException;
import mn.foreman.trex.json.Summary;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link TelnetJsonStrategy} provides a {@link ApiStrategy} implementation
 * that will query the telnet API running via trex and return a parsed {@link
 * Summary} from the response.
 */
public class TelnetJsonStrategy
        implements ApiStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(TelnetJsonStrategy.class);

    @Override
    public Summary getSummary(
            final String apiIp,
            final int apiPort)
            throws MinerException {
        try {
            return Query.jsonQuery(
                    apiIp,
                    apiPort,
                    "summary",
                    new TypeReference<Summary>() {
                    });
        } catch (final Exception e) {
            LOG.warn("Failed to obtain summary", e);
            throw new MinerException(e);
        }
    }
}
