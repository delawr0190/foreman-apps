package mn.foreman.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A {@link SimpleMinerID} represents a unique identifier for a {@link Miner}.
 */
public class SimpleMinerID
        implements MinerID {

    /** The API IP. */
    private final String apiIp;

    /** The API port. */
    private final int apiPort;

    /**
     * Constructor.
     *
     * @param apiIp   The API IP.
     * @param apiPort The API port.
     */
    public SimpleMinerID(
            final String apiIp,
            final int apiPort) {
        this.apiIp = apiIp;
        this.apiPort = apiPort;
    }

    @Override
    public boolean equals(final Object other) {
        boolean isEqual = false;
        if (other == this) {
            isEqual = true;
        } else if ((other != null) && (getClass() == other.getClass())) {
            final SimpleMinerID minerID = (SimpleMinerID) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.apiIp,
                                    minerID.apiIp)
                            .append(this.apiPort,
                                    minerID.apiPort)
                            .isEquals();
        }
        return isEqual;
    }

    @Override
    public String getApiIp() {
        return this.apiIp;
    }

    @Override
    public int getApiPort() {
        return this.apiPort;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.apiIp)
                .append(this.apiPort)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ apiIp=%s, apiPort=%s ]",
                getClass().getSimpleName(),
                this.apiIp,
                this.apiPort);
    }
}