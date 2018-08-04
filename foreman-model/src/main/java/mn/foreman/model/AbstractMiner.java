package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import org.apache.commons.lang3.Validate;
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

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name    The name.
     * @param apiIp   The api IP.
     * @param apiPort The api port.
     */
    protected AbstractMiner(
            final String name,
            final String apiIp,
            final int apiPort) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notEmpty(
                apiIp,
                "apiIp cannot be empty");
        Validate.isTrue(
                apiPort > 0,
                "apiPort must be > 0");
        this.name = name;
        this.apiIp = apiIp;
        this.apiPort = apiPort;
    }

    @Override
    public MinerStats getStats()
            throws MinerException {
        LOG.debug("Obtaining stats from {}-{}:{}",
                this.name,
                this.apiIp,
                this.apiPort);

        final MinerStats.Builder builder =
                new MinerStats.Builder()
                        .setApiIp(this.apiIp)
                        .setApiPort(this.apiPort)
                        .setName(this.name);

        addStats(builder);

        return builder.build();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ name=%s, apiIp=%s, apiPort=%d%s ]",
                getClass().getSimpleName(),
                this.name,
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
     * Adds additional parameters to {@link #toString()}.
     *
     * @return The additional parameters.
     */
    protected String addToString() {
        return "";
    }
}