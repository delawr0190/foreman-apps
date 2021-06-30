package mn.foreman.pickaxe.command.asic;

import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.MinerID;
import mn.foreman.model.cache.StatsCache;
import mn.foreman.pickaxe.command.CommandStrategy;
import mn.foreman.pickaxe.command.PostCommandProcessor;
import mn.foreman.pickaxe.command.StrategyFactory;
import mn.foreman.pickaxe.command.asic.digest.DigestStrategy;
import mn.foreman.pickaxe.command.asic.discover.DiscoverStrategy;
import mn.foreman.pickaxe.command.asic.eval.EvalStrategy;
import mn.foreman.pickaxe.command.asic.obelisk.ObeliskGetStrategy;
import mn.foreman.pickaxe.command.asic.rawstats.RawStatsStrategy;
import mn.foreman.pickaxe.command.asic.scan.MacFilteringStrategy;
import mn.foreman.pickaxe.command.asic.scan.RangesSourceStrategy;
import mn.foreman.pickaxe.command.asic.scan.ScanStrategy;
import mn.foreman.pickaxe.command.asic.scan.StartStopSourceStrategy;
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

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /** Whether or not command and control is enabled. */
    private final boolean isControl;

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
     * @param threadPool          The thread pool.
     * @param blacklist           The blacklist.
     * @param statsCache          The cache.
     * @param configuration       The configuration.
     * @param isControl           Whether or not command and control is
     *                            enabled.
     */
    public AsicStrategyFactory(
            final PostCommandProcessor postRebootProcessor,
            final ScheduledExecutorService threadPool,
            final Set<MinerID> blacklist,
            final StatsCache statsCache,
            final ApplicationConfiguration configuration,
            final boolean isControl) {
        this.postRebootProcessor = postRebootProcessor;
        this.threadPool = threadPool;
        this.blacklist = blacklist;
        this.statsCache = statsCache;
        this.configuration = configuration;
        this.isControl = isControl;
    }

    @Override
    public Optional<CommandStrategy> forType(final String type) {
        final Optional<RemoteCommand> remoteCommand =
                RemoteCommand.forType(type);
        if (remoteCommand.isPresent()) {
            return toStrategy(remoteCommand.get());
        }
        return Optional.empty();
    }

    /**
     * Converts the provided command to a {@link CommandStrategy} that can be
     * invoked.
     *
     * @param remoteCommand The command.
     *
     * @return The strategy to invoke.
     */
    private Optional<CommandStrategy> toStrategy(final RemoteCommand remoteCommand) {
        CommandStrategy commandStrategy = null;
        switch (remoteCommand) {
            case DISCOVER:
                commandStrategy = new DiscoverStrategy();
                break;
            case SCAN_RANGES:
                commandStrategy =
                        new ScanStrategy(
                                this.configuration,
                                new RangesSourceStrategy());
                break;
            case SCAN:
                commandStrategy =
                        new ScanStrategy(
                                this.configuration,
                                new StartStopSourceStrategy());
                break;
            case TARGETED_SCAN:
                commandStrategy =
                        new ScanStrategy(
                                new MacFilteringStrategy(),
                                this.configuration,
                                new StartStopSourceStrategy());
                break;
            case CHANGE_POOLS:
                commandStrategy =
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getChangePoolsAction(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache,
                                                this.configuration));
                break;
            case REBOOT:
                commandStrategy =
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getRebootAction(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache,
                                                this.configuration));
                break;
            case DIGEST:
                commandStrategy = new DigestStrategy();
                break;
            case RAW_STATS:
                commandStrategy =
                        new RawStatsStrategy(
                                this.configuration);
                break;
            case FACTORY_RESET:
                commandStrategy =
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getFactoryResetStrategy(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache,
                                                this.configuration));
                break;
            case EVAL:
                commandStrategy = new EvalStrategy();
                break;
            case WHATSMINER_GET:
                commandStrategy = new WhatsminerGetStrategy(this.configuration);
                break;
            case NETWORK:
                commandStrategy =
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getNetworkStrategy(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache,
                                                this.configuration));
                break;
            case TERMINATE:
                commandStrategy = new TerminateStrategy();
                break;
            case POWER_MODE:
                commandStrategy =
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getPowerModeStrategy(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache,
                                                this.configuration));
                break;
            case PASSWORD:
                commandStrategy =
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getPasswordStrategy(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache,
                                                this.configuration));
                break;
            case BLINK:
                commandStrategy =
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getBlinkStrategy(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache,
                                                this.configuration));
                break;
            case FIRMWARE_UPGRADE:
                commandStrategy =
                        new RebootingCommandStrategy(
                                this.postRebootProcessor,
                                manufacturer ->
                                        manufacturer.getFirmwareUpgradeStrategy(
                                                this.threadPool,
                                                this.blacklist,
                                                this.statsCache,
                                                this.configuration));
                break;
            case OBILESK_GET:
                commandStrategy = new ObeliskGetStrategy();
                break;
            default:
                break;
        }

        return this.isControl || !remoteCommand.isControl()
                ? Optional.ofNullable(commandStrategy)
                : Optional.empty();
    }
}
