package mn.foreman.antminer.response;

import mn.foreman.antminer.AntminerType;
import mn.foreman.antminer.AntminerUtils;
import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.error.EmptySiteException;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * A {@link VersionResponseStrategy} provides a {@link ResponseStrategy}
 * implementation that determines the type of miner running (Antminer or
 * Braiins) and queries it based on the version cgminer response.
 */
public class VersionResponseStrategy
        implements ResponseStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(VersionResponseStrategy.class);

    /** The miner to use for obtaining antminer stats. */
    private final CgMiner antminer;

    /** The miner to use for obtaining braiins stats. */
    private final CgMiner braiins;

    /**
     * Constructor.
     *
     * @param antminer The miner to use for obtaining antminer stats.
     * @param braiins  The miner to use for obtaining braiins stats.
     */
    public VersionResponseStrategy(
            final CgMiner antminer,
            final CgMiner braiins) {
        this.antminer = antminer;
        this.braiins = braiins;
    }

    @Override
    public void processResponse(
            final MinerStats.Builder builder,
            final CgMinerResponse response)
            throws MinerException {
        try {
            final Optional<AntminerType> type =
                    AntminerUtils.toType(
                            response.getValues());
            if (type.isPresent()) {
                final MinerStats minerStats;

                final AntminerType antminerType = type.get();
                if (antminerType.isBraiins()) {
                    minerStats = this.braiins.getStats();
                } else {
                    minerStats = this.antminer.getStats();
                }

                builder.fromStats(minerStats);
            } else {
                LOG.warn("Found an unknown Antminer type");
            }
        } catch (final EmptySiteException ese) {
            throw new MinerException(ese);
        }
    }
}