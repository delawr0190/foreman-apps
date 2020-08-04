package mn.foreman.pickaxe.command.asic;

import mn.foreman.pickaxe.command.CommandStrategy;
import mn.foreman.pickaxe.command.PostCommandProcessor;
import mn.foreman.pickaxe.command.StrategyFactory;
import mn.foreman.pickaxe.command.asic.discover.DiscoverStrategy;
import mn.foreman.pickaxe.command.asic.scan.ScanStrategy;

import java.util.Optional;

/**
 * An {@link AsicStrategyFactory} provides a {@link StrategyFactory}
 * implementation that's capable of producing {@link CommandStrategy strategies}
 * that works with ASICs.
 */
public class AsicStrategyFactory
        implements StrategyFactory {

    /** The post processor for rebooting. */
    private final PostCommandProcessor postRebootProcessor;

    /**
     * Constructor.
     *
     * @param postRebootProcessor The post processor for rebooting.
     */
    public AsicStrategyFactory(final PostCommandProcessor postRebootProcessor) {
        this.postRebootProcessor = postRebootProcessor;
    }

    @Override
    public Optional<CommandStrategy> forType(final String type) {
        Optional<CommandStrategy> strategy = Optional.empty();
        switch (type) {
            case "discover":
                strategy =
                        Optional.of(
                                new DiscoverStrategy());
                break;
            case "scan":
                strategy =
                        Optional.of(
                                new ScanStrategy());
                break;
            case "change-pools":
                strategy =
                        Optional.of(
                                new RebootingCommandStrategy(
                                        this.postRebootProcessor,
                                        Manufacturer::getChangePoolsAction));
                break;
            case "reboot":
                strategy =
                        Optional.of(
                                new RebootingCommandStrategy(
                                        this.postRebootProcessor,
                                        Manufacturer::getRebootAction));
                break;
            default:
                break;
        }
        return strategy;
    }
}
