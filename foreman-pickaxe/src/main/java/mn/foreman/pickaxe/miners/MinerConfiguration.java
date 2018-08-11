package mn.foreman.pickaxe.miners;

import mn.foreman.model.Miner;

import java.util.List;

/**
 * A {@link MinerConfiguration} provides a mechanism for creating the list of
 * {@link Miner miners} that are to be queried by pickaxe.
 */
public interface MinerConfiguration {

    /**
     * Loads the {@link Miner miners}.
     *
     * @return The miners.
     */
    List<Miner> load();
}
