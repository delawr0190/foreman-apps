package mn.foreman.lolminer;

import mn.foreman.lolminer.v4.Lolminer;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import org.apache.commons.lang3.Validate;

/**
 * A {@link VersionDecorator} provides a decorator implementation that will
 * query a lolminer, determining the software version and querying the
 * appropriate API handler.
 *
 * <p>This class will correctly identify and query older lolminer API
 * miners (<= 0.4) and newer API miners (>= 0.5).</p>
 */
public class VersionDecorator
        implements Miner {

    /** The V4 miner. */
    private final Lolminer v4Miner;

    /** The V6 miner. */
    private final mn.foreman.lolminer.v6.Lolminer v6Miner;

    /**
     * Constructor.
     *
     * @param v4Miner The V4 miner.
     * @param v6Miner The V6 miner.
     */
    VersionDecorator(
            final Lolminer v4Miner,
            final mn.foreman.lolminer.v6.Lolminer v6Miner) {
        Validate.notNull(
                v4Miner,
                "v4Miner cannot be null");
        Validate.notNull(
                v6Miner,
                "v6Miner cannot be null");
        this.v4Miner = v4Miner;
        this.v6Miner = v6Miner;
    }

    @Override
    public int getApiPort() {
        return this.v4Miner.getApiPort();
    }

    @Override
    public String getIp() {
        return this.v4Miner.getIp();
    }

    @Override
    public MinerID getMinerID() {
        return this.v4Miner.getMinerID();
    }

    @Override
    public MinerStats getStats()
            throws MinerException {
        try {
            return this.v6Miner.getStats();
        } catch (final MinerException me) {
            return this.v4Miner.getStats();
        }
    }
}