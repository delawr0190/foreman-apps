package mn.foreman.pickaxe.command.asic.reboot;

import mn.foreman.model.Miner;
import mn.foreman.model.command.CommandStart;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.pickaxe.cache.StatsCache;
import mn.foreman.pickaxe.command.PostCommandProcessor;
import mn.foreman.pickaxe.run.MetricsSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A {@link PostRebootProcessor} provides a {@link PostCommandProcessor} that
 * will send updated metrics to the dashboard for a miner that has just
 * completed rebooting.
 */
public class PostRebootProcessor
        implements PostCommandProcessor {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(PostRebootProcessor.class);

    /** The sender for pushing metrics to the dashboard. */
    private final MetricsSender metricsSender;

    /** The miners. */
    private final AtomicReference<List<Miner>> miners;

    /** The stats cache. */
    private final StatsCache statsCache;

    /**
     * Constructor.
     *
     * @param statsCache    The stats cache.
     * @param miners        The miners.
     * @param metricsSender The sender for pushing metrics to the dashboard.
     */
    public PostRebootProcessor(
            final StatsCache statsCache,
            final AtomicReference<List<Miner>> miners,
            final MetricsSender metricsSender) {
        this.statsCache = statsCache;
        this.miners = miners;
        this.metricsSender = metricsSender;
    }

    @Override
    public void completed(final CommandStart start) {
        final Map<String, Object> args = start.args;

        final String ip = (String) args.get("ip");
        final int apiPort = (Integer) args.get("apiPort");

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
                this.statsCache.add(
                        miner.getMinerID(),
                        stats);
                this.metricsSender.sendMetrics(
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