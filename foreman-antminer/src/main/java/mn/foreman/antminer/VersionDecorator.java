package mn.foreman.antminer;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.Context;
import mn.foreman.cgminer.ContextKey;
import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /** The context. */
    private final Context context;

    /** The API IP. */
    private final String ip;

    /** The password. */
    private final String password;

    /** The API port. */
    private final int port;

    /** The realm. */
    private final String realm;

    /** The current type. */
    private final AtomicReference<AntminerType> type =
            new AtomicReference<>();

    /** The username. */
    private final String username;

    /** The web port. */
    private final int webPort;

    /** When the next version query should be ran. */
    private long nextQueryTime = System.currentTimeMillis();

    /**
     * Constructor.
     *
     * @param ip       The IP.
     * @param port     The port.
     * @param webPort  The web port.
     * @param realm    The realm.
     * @param username The username.
     * @param password The password.
     * @param context  The context to update.
     * @param antminer The antminer.
     * @param braiins  The braiins.
     */
    VersionDecorator(
            final String ip,
            final String port,
            final String webPort,
            final String realm,
            final String username,
            final String password,
            final Context context,
            final CgMiner antminer,
            final CgMiner braiins) {
        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.webPort = Integer.parseInt(webPort);
        this.realm = realm;
        this.username = username;
        this.password = password;
        this.context = context;
        this.antminer = antminer;
        this.braiins = braiins;
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
                updateType();
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
            updateType();
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

    /** Updates the type. */
    private void updateType() {
        AntminerUtils.getType(
                this.ip,
                this.port,
                this.webPort,
                this.realm,
                this.username,
                this.password,
                (s1, s2, s3) -> {
                    this.context.addSimple(
                            ContextKey.MINER_TYPE,
                            s1);
                    this.context.addSimple(
                            ContextKey.COMPILE_TIME,
                            s2);
                })
                .ifPresent(type -> {
                    this.type.set(type);
                    this.nextQueryTime =
                            System.currentTimeMillis() + VERSION_QUERY_INTERVAL;
                });
    }


}