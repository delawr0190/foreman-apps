package mn.foreman.pickaxe.command.asic;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.command.CommandStart;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.pickaxe.cache.StatsCache;
import mn.foreman.pickaxe.command.PostCommandProcessor;
import mn.foreman.pickaxe.run.MetricsSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A {@link PostCommandProcessorImpl} provides a {@link PostCommandProcessor}
 * that will send updated metrics to the dashboard for a miner that has just
 * completed an action.
 */
public class PostCommandProcessorImpl
        implements PostCommandProcessor {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(PostCommandProcessorImpl.class);

    /** The sender for pushing metrics to the dashboard. */
    private final MetricsSender metricsSender;

    /** The miner blacklist. */
    private final Set<MinerID> minerBlacklist;

    /** The miners. */
    private final AtomicReference<List<Miner>> miners;

    /** The stats cache. */
    private final StatsCache statsCache;

    /**
     * Constructor.
     *
     * @param statsCache     The stats cache.
     * @param minerBlacklist The miner blacklist.
     * @param miners         The miners.
     * @param metricsSender  The sender for pushing metrics to the dashboard.
     */
    public PostCommandProcessorImpl(
            final StatsCache statsCache,
            final Set<MinerID> minerBlacklist,
            final AtomicReference<List<Miner>> miners,
            final MetricsSender metricsSender) {
        this.statsCache = statsCache;
        this.minerBlacklist = minerBlacklist;
        this.miners = miners;
        this.metricsSender = metricsSender;
    }

    @Override
    public void completed(final CommandStart start) {
        final Map<String, Object> args = start.args;

        final String ip = args.get("ip").toString();
        final int apiPort = Integer.parseInt(args.get("apiPort").toString());

        final List<Miner> miners = this.miners.get();
        final Optional<Miner> minerOptional =
                miners
                        .stream()
                        .filter(miner -> miner.getIp().equals(ip) && miner.getApiPort() == apiPort)
                        .findFirst();
        if (minerOptional.isPresent()) {
            final Miner miner = minerOptional.get();
            try {
                final MinerStats stats = miner.getStats();
                LOG.info("Sending stats for {}: {}", miner, stats);
                final MinerID minerID = miner.getMinerID();
                this.statsCache.add(
                        minerID,
                        stats);
                this.minerBlacklist.remove(minerID);
                this.metricsSender.sendMetrics(
                        ZonedDateTime.now(),
                        Collections.singletonList(
                                stats));
            } catch (final Exception e) {
                LOG.warn("Exception occurred while querying {}", miner);
            }
        } else {
            LOG.warn("Reboot was completed, but no miner existed at {}:{}",
                    ip,
                    apiPort);
        }
    }
}