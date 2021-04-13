package mn.foreman.pickaxe.command.asic;

import mn.foreman.model.MinerID;
import mn.foreman.model.cache.StatsCache;
import mn.foreman.pickaxe.command.CommandStrategy;
import mn.foreman.pickaxe.command.PostCommandProcessor;
import mn.foreman.pickaxe.command.StrategyFactory;
import mn.foreman.pickaxe.command.asic.digest.DigestStrategy;
import mn.foreman.pickaxe.command.asic.discover.DiscoverStrategy;
import mn.foreman.pickaxe.command.asic.eval.EvalStrategy;
import mn.foreman.pickaxe.command.asic.rawstats.RawStatsStrategy;
import mn.foreman.pickaxe.command.asic.scan.MacFilteringStrategy;
import mn.foreman.pickaxe.command.asic.scan.ScanStrategy;
import mn.foreman.pickaxe.command.asic.terminate.TerminateStrategy;
import mn.foreman.pickaxe.command.asic.whatsminer.WhatsminerGetStrategy;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

/**
 * An {@link AsicStrategyFactory} provides a {@link StrategyFactory}
 * implementation that's capable of producing {@link CommandStrategy strategies}
 * that works with ASICs.
 */
public class AsicStrategyFactory
        implements StrategyFactory {

    /** The blacklist. */
    private final Set<MinerID> blacklist;

    /** The post processor for rebooting. */
    private final PostCommandProcessor postRebootProcessor;

    /** The stats cache. */
    private final StatsCache statsCache;

    /** The thread pool to use for actions. */
    private final ScheduledExecutorService threadPool;

    /**
     * Constructor.
     *
     * @param postRebootProcessor The post processor for rebooting.
     * @param blacklist           The blacklist.
     * @param statsCache          The cache.
     */
    public AsicStrategyFactory(
            final PostCommandProcessor postRebootProcessor,
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache) {
        this.postRebootProcessor = postRebootProcessor;
        this.threadPool = threadPool;
        this.blacklist = blacklist;
        this.statsCache = statsCache;
    }

    @Override
    public Optional<CommandStrategy> forType(final String type) {
        Optional<CommandStrategy> strategy = Optional.empty();
        switch (type) {
            case "discover":
                strategy = Optional.of(new DiscoverStrategy());
                break;
            case "scan":
                strategy = Optional.of(new ScanStrategy());
                break;
            case "targeted-scan":
                strategy = Optional.of(new ScanStrategy(new MacFilteringStrategy()));
                break;
            case "change-pools":
                strategy =
                        Optional.of(
                                new RebootingCommandStrategy(
                                        this.postRebootProcessor,
                                        manufacturer ->
                                                manufacturer.getChangePoolsAction(
                                                        this.threadPool,
                                                        this.blacklist,
                                                        this.statsCache)));
                break;
            case "reboot":
                strategy =
                        Optional.of(
                                new RebootingCommandStrategy(
                                        this.postRebootProcessor,
                                        manufacturer ->
                                                manufacturer.getRebootAction(
                                                        this.threadPool,
                                                        this.blacklist,
                                                        this.statsCache)));
                break;
            case "digest":
                strategy = Optional.of(new DigestStrategy());
                break;
            case "raw-stats":
                strategy = Optional.of(new RawStatsStrategy());
                break;
            case "factory-reset":
                strategy =
                        Optional.of(
                                new RebootingCommandStrategy(
                                        this.postRebootProcessor,
                                        manufacturer ->
                                                manufacturer.getFactoryResetStrategy(
                                                        this.threadPool,
                                                        this.blacklist,
                                                        this.statsCache)));
                break;
            case "eval":
                strategy = Optional.of(new EvalStrategy());
                break;
            case "whatsminer-get":
                strategy = Optional.of(new WhatsminerGetStrategy());
                break;
            case "network":
                strategy = Optional.of(
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getNetworkStrategy(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache)));
                break;
            case "terminate":
                strategy = Optional.of(new TerminateStrategy());
                break;
            case "power-mode":
                strategy = Optional.of(
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getPowerModeStrategy(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache)));
                break;
            case "password":
                strategy = Optional.of(
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getPasswordStrategy(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache)));
                break;
            case "blink":
                strategy = Optional.of(
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getBlinkStrategy(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache)));
                break;
            default:
                break;
        }
        return strategy;
    }
}
