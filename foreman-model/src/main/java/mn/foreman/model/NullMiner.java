package mn.foreman.model;

import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import java.util.Optional;

/** A {@link Miner} that does nothing. */
public class NullMiner
        implements Miner {

    @Override
    public int getApiPort() {
        return 0;
    }

    @Override
    public String getIp() {
        return null;
    }

    @Override
    public Optional<String> getMacAddress() {
        return Optional.empty();
    }

    @Override
    public MinerID getMinerID() {
        return null;
    }

    @Override
    public MinerStats getStats() throws MinerException {
        return null;
    }
}
