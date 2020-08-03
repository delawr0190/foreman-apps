package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link AbstractMiner} provides an abstract class representing a remote
 * miner.
 */
public abstract class AbstractMiner
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AbstractMiner.class);

    /** The API IP. */
    protected final String apiIp;

    /** The API port. */
    protected final int apiPort;

    /**
     * Constructor.
     *
     * @param apiIp   The api IP.
     * @param apiPort The api port.
     */
    protected AbstractMiner(
            final String apiIp,
            final int apiPort) {
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.isTrue(
                apiPort > 0,
                "apiPort must be > 0");
        this.apiIp = apiIp;
        this.apiPort = apiPort;
    }

    @Override
    public boolean equals(final Object other) {
        boolean equals = false;
        if (this == other) {
            equals = true;
        } else if ((other != null) && (getClass() == other.getClass())) {
            final AbstractMiner miner = (AbstractMiner) other;
            final EqualsBuilder equalsBuilder =
                    new EqualsBuilder()
                            .append(this.apiIp, miner.apiIp)
                            .append(this.apiPort, miner.apiPort);
            addToEquals(
                    equalsBuilder,
                    miner);
            equals = equalsBuilder.isEquals();
        }
        return equals;
    }

    @Override
    public int getApiPort() {
        return this.apiPort;
    }

    @Override
    public String getIp() {
        return this.apiIp;
    }

    @Override
    public MinerID getMinerID() {
        return new SimpleMinerID(
                this.apiIp,
                this.apiPort);
    }

    @Override
    public MinerStats getStats()
            throws MinerException {
        LOG.debug("Obtaining stats from {}", this);

        final MinerStats.Builder builder =
                new MinerStats.Builder()
                        .setApiIp(this.apiIp)
                        .setApiPort(this.apiPort);

        addStats(builder);

        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder =
                new HashCodeBuilder()
                        .append(this.apiIp)
                        .append(this.apiPort);
        addToHashCode(hashCodeBuilder);
        return hashCodeBuilder.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ apiIp=%s, apiPort=%d%s ]",
                getClass().getSimpleName(),
                this.apiIp,
                this.apiPort,
                addToString());
    }

    /**
     * Adds stats to the provided builder.
     *
     * @param statsBuilder The builder to update.
     *
     * @throws MinerException on failure to get stats.
     */
    protected abstract void addStats(
            MinerStats.Builder statsBuilder)
            throws MinerException;

    /**
     * Adds to the {@link EqualsBuilder}.
     *
     * @param equalsBuilder The builder.
     * @param other         The other.
     */
    protected void addToEquals(
            final EqualsBuilder equalsBuilder,
            final AbstractMiner other) {
        // Do nothing
    }

    /**
     * Adds to the {@link HashCodeBuilder}.
     *
     * @param hashCodeBuilder The builder.
     */
    protected void addToHashCode(final HashCodeBuilder hashCodeBuilder) {
        // Do nothing
    }

    /**
     * Adds additional parameters to {@link #toString()}.
     *
     * @return The additional parameters.
     */
    protected String addToString() {
        return "";
    }
}