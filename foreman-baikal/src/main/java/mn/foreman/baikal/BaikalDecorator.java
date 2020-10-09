package mn.foreman.baikal;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.FanInfo;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A decorator for returning not hashing stats when a Baikal isn't responding.
 */
public class BaikalDecorator
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(BaikalDecorator.class);

    /** The baikal API querier. */
    private final CgMiner baikal;

    /** How to find a baikal if the API is unresponsive. */
    private final DetectionStrategy detectionStrategy;

    /** Whether or not a baikal was previously found. */
    private final AtomicBoolean foundBaikal = new AtomicBoolean(false);

    /**
     * The stats to return when a baikal was found, but now it's not
     * responding.
     */
    private final MinerStats notHashingStats;

    /**
     * Constructor.
     *
     * @param baikal            The baikal.
     * @param detectionStrategy The strategy to use for finding baikals.
     */
    BaikalDecorator(
            final CgMiner baikal,
            final DetectionStrategy detectionStrategy) {
        this.baikal = baikal;
        this.detectionStrategy = detectionStrategy;
        this.notHashingStats =
                new MinerStats.Builder()
                        .setApiIp(baikal.getIp())
                        .setApiPort(baikal.getApiPort())
                        .addAsic(
                                new Asic.Builder()
                                        .setHashRate(BigDecimal.ZERO)
                                        .setFanInfo(
                                                new FanInfo.Builder()
                                                        .setCount(0)
                                                        .setSpeedUnits("%")
                                                        .build())
                                        .build())
                        .build();
    }

    @Override
    public int getApiPort() {
        return this.baikal.getApiPort();
    }

    @Override
    public String getIp() {
        return this.baikal.getIp();
    }

    @Override
    public MinerID getMinerID() {
        return this.baikal.getMinerID();
    }

    @Override
    public MinerStats getStats()
            throws MinerException {
        MinerStats stats = null;
        try {
            stats = this.baikal.getStats();
        } catch (final MinerException me) {
            if (this.foundBaikal.get()) {
                LOG.info("Baikal not hashing - returning default stats");
                stats = this.notHashingStats;
            } else {
                LOG.info("Failed to find baikal - see if it's not hashing");
                final Optional<Detection> detection =
                        this.detectionStrategy.detect(
                                this.baikal.getIp(),
                                this.baikal.getApiPort(),
                                Collections.emptyMap());
                if (detection.isPresent()) {
                    this.foundBaikal.set(true);
                    stats = this.notHashingStats;
                }
            }
        }

        if (stats == null) {
            throw new MinerException("Failed to find baikal");
        }

        return stats;
    }
}
