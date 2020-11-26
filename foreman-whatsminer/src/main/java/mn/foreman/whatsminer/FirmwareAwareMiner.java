package mn.foreman.whatsminer;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.model.miners.asic.Asic;

import com.google.common.collect.Iterables;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A firmware-aware miner is a miner that will try to obtain stats from two
 * different versions of a miner.
 */
public class FirmwareAwareMiner
        implements Miner {

    /** The firmware. */
    private final List<Miner> firmwares;

    /**
     * Constructor.
     *
     * @param firmwares The firmware to try.
     */
    FirmwareAwareMiner(final Miner... firmwares) {
        this.firmwares = Arrays.asList(firmwares);
    }

    @Override
    public int getApiPort() {
        return Iterables.get(this.firmwares, 0).getApiPort();
    }

    @Override
    public String getIp() {
        return Iterables.get(this.firmwares, 0).getIp();
    }

    @Override
    public Optional<String> getMacAddress() {
        for (final Miner miner : this.firmwares) {
            try {
                final Optional<String> macAddress = miner.getMacAddress();
                if (macAddress.isPresent()) {
                    return macAddress;
                }
            } catch (final Exception e) {
                // Ignore and try the next
            }
        }
        return Optional.empty();
    }

    @Override
    public MinerID getMinerID() {
        return Iterables.get(this.firmwares, 0).getMinerID();
    }

    @Override
    public MinerStats getStats()
            throws MinerException {
        for (final Miner miner : this.firmwares) {
            try {
                final MinerStats stats = miner.getStats();
                final List<Asic> asics = stats.getAsics();
                if (asics != null && !asics.isEmpty()) {
                    if (asics
                            .stream()
                            .anyMatch(asic -> asic.getHashRate().compareTo(BigDecimal.ZERO) > 0)) {
                        // Found good stats
                        return stats;
                    }
                }
            } catch (final Exception e) {
                // Ignore
            }
        }

        throw new MinerException("Miner didn't respond");
    }
}
