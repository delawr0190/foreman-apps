package mn.foreman.pickaxe.command;

import mn.foreman.pickaxe.command.discover.DiscoverStrategy;
import mn.foreman.pickaxe.command.scan.ScanStrategy;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** All of the known commands. */
public enum Command {

    /** Discover an unknown miner. */
    DISCOVER(
            "discover",
            new DiscoverStrategy()),

    /** Scan for miners. */
    SCAN(
            "scan",
            new ScanStrategy());

    /** All of the known commands. */
    private static final ConcurrentMap<String, Command> TYPES =
            new ConcurrentHashMap<>();

    static {
        for (final Command command : values()) {
            TYPES.put(
                    command.name,
                    command);
        }
    }

    /** The name. */
    private final String name;

    /** The command strategy. */
    private final CommandStrategy strategy;

    /**
     * Constructor.
     *
     * @param name     The name.
     * @param strategy The strategy.
     */
    Command(
            final String name,
            final CommandStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    /**
     * Returns the command for hte provided name.
     *
     * @param name The name.
     *
     * @return The command.
     */
    public static Optional<Command> forName(final String name) {
        return Optional.ofNullable(TYPES.get(name));
    }

    /**
     * Returns the name.
     *
     * @return The name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the strategy.
     *
     * @return The strategy.
     */
    public CommandStrategy getStrategy() {
        return this.strategy;
    }
}