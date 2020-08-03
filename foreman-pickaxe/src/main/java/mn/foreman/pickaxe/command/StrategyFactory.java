package mn.foreman.pickaxe.command;

import java.util.Optional;

/**
 * A {@link StrategyFactory} provides an interface to all factories that can
 * make {@link CommandStrategy strategies}.
 */
public interface StrategyFactory {

    /**
     * Creates a command for the provided type.
     *
     * @param type The type.
     *
     * @return The new command.
     */
    Optional<CommandStrategy> forType(String type);
}
