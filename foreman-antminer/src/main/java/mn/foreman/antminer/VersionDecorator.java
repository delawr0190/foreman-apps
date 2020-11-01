package mn.foreman.antminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ResponseStrategy;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.cgminer.response.CgMinerResponse;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.EmptySiteException;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A miner decorator that will query the version API on cgminer periodically to
 * identify when a miner's type changes, but prevent unnecessary querying during
 * normal metrics queries.
 */
public class VersionDecorator
        implements Miner {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(VersionDecorator.class);

    /** Query the version every 20 minutes. */
    private static final long VERSION_QUERY_INTERVAL =
            TimeUnit.MINUTES.toMillis(20);

    /** The miner for querying antminers. */
    private final CgMiner antminer;

    /** The miner for querying braiins. */
    private final CgMiner braiins;

    /** The current type. */
    private final AtomicReference<AntminerType> type =
            new AtomicReference<>();

    /** The miner for obtaining the version. */
    private final CgMiner version;

    /** When the next version query should be ran. */
    private long nextQueryTime = System.currentTimeMillis();

    /**
     * Constructor.
     *
     * @param ip       The IP.
     * @param port     The port.
     * @param antminer The antminer.
     * @param braiins  The braiins.
     */
    VersionDecorator(
            final String ip,
            final String port,
            final CgMiner antminer,
            final CgMiner braiins) {
        this.antminer = antminer;
        this.braiins = braiins;
        this.version =
                new CgMiner.Builder(new Context(), Collections.emptyList())
                        .setApiIp(ip)
                        .setApiPort(port)
                        .addRequest(
                                new CgMinerRequest.Builder()
                                        .setCommand(CgMinerCommand.VERSION)
                                        .build(),
                                new VersionStrategy())
                        .build();
    }

    @Override
    public int getApiPort() {
        return this.antminer.getApiPort();
    }

    @Override
    public String getIp() {
        return this.antminer.getIp();
    }

    @Override
    public Optional<String> getMacAddress() {
        Optional<String> mac = Optional.empty();

        try {
            if (shouldQueryVersion()) {
                this.version.getStats();
            }

            final AntminerType currentType = this.type.get();
            if (currentType != null) {
                if (currentType.isBraiins()) {
                    mac = this.braiins.getMacAddress();
                } else {
                    mac = this.antminer.getMacAddress();
                }
            }
        } catch (final Exception e) {
            LOG.debug("Failed to obtain MAC", e);
        }

        return mac;
    }

    @Override
    public MinerID getMinerID() {
        return this.antminer.getMinerID();
    }

    @Override
    public MinerStats getStats() throws MinerException {
        if (shouldQueryVersion()) {
            this.version.getStats();
        }

        final AntminerType currentType = this.type.get();
        if (currentType != null) {
            if (currentType.isBraiins()) {
                return this.braiins.getStats();
            } else {
                return this.antminer.getStats();
            }
        } else {
            throw new MinerException("Failed to find antminer type");
        }
    }

    /**
     * Returns whether or not the version should be queried.
     *
     * @return Whether or not the version should be queried.
     */
    private boolean shouldQueryVersion() {
        return ((this.type.get() == null) || (this.nextQueryTime <= System.currentTimeMillis()));
    }

    /** Captures the version and stores it. */
    private class VersionStrategy
            implements ResponseStrategy {

        @Override
        public void processResponse(
                final MinerStats.Builder builder,
                final CgMinerResponse response) {
            try {
                AntminerUtils.toType(response.getValues())
                        .ifPresent(type -> {
                            VersionDecorator.this.type.set(type);
                            VersionDecorator.this.nextQueryTime =
                                    System.currentTimeMillis() + VERSION_QUERY_INTERVAL;
                        });
            } catch (final EmptySiteException ese) {
                // Ignore
            }
        }
    }
}