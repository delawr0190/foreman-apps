package mn.foreman.cgminer;

import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link ResponseStrategy} implementation that will pass a response through
 * all strategies.
 */
public class MultiResponseStrategy
        implements ResponseStrategy {

    /** The candidates. */
    private final List<ResponseStrategy> candidates;

    /**
     * Constructor.
     *
     * @param candidates The candidates.
     */
    public MultiResponseStrategy(final List<ResponseStrategy> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response) throws MinerException {
        for (final ResponseStrategy responseStrategy : this.candidates) {
            responseStrategy.processResponse(
                    builder,
                    response);
        }
    }
}