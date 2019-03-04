package mn.foreman.trex;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A {@link TrexFacade} provides a facade around the discovery of the
 * appropriate trex api to query and extracts metrics from that running
 * instance.
 */
public class TrexFacade
        implements Miner {

    /** The ccminer API. */
    private final TrexCcminer ccminer;

    /** The http API. */
    private final TrexHttp http;

    /**
     * Constructor.
     *
     * @param http    The http API.
     * @param ccminer The ccminer API.
     */
    TrexFacade(
            final TrexHttp http,
            final TrexCcminer ccminer) {
        this.http = http;
        this.ccminer = ccminer;
    }

    @Override
    public boolean equals(final Object other) {
        boolean equals = false;
        if (this == other) {
            equals = true;
        } else if ((other != null) && (getClass() == other.getClass())) {
            final TrexFacade miner = (TrexFacade) other;
            equals =
                    new EqualsBuilder()
                            .append(this.http, miner.http)
                            .append(this.ccminer, miner.ccminer)
                            .isEquals();
        }
        return equals;
    }

    @Override
    public MinerID getMinerID() {
        return this.http.getMinerID();
    }

    @Override
    public MinerStats getStats()
            throws MinerException {
        MinerStats minerStats;
        try {
            minerStats = this.http.getStats();
        } catch (final MinerException me) {
            // Fall back to ccminer API
            minerStats = this.ccminer.getStats();
        }
        return minerStats;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.http)
                .append(this.ccminer)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ http=%s, ccminer=%s ]",
                getClass().getSimpleName(),
                this.http,
                this.ccminer);
    }
}