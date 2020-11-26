package mn.foreman.whatsminer;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A firmware-aware miner is a miner that will try to obtain stats from two
 * different versions of a miner.
 */
public class FirmwareAwareMiner
        implements Miner {

    /** The alt new firmware. */
    private final Miner altNewFirmware;

    /** The firmware. */
    private final List<Miner> firmwares;

    /** The new firmware. */
    private final Miner newFirmware;

    /** The old firmware. */
    private final Miner oldFirmware;

    /**
     * Constructor.
     *
     * @param oldFirmware    The old firmware.
     * @param newFirmware    The new firmware.
     * @param altNewFirmware The alternative new firmware.
     */
    FirmwareAwareMiner(
            final Miner oldFirmware,
            final Miner newFirmware,
            final Miner altNewFirmware) {
        this.oldFirmware = oldFirmware;
        this.newFirmware = newFirmware;
        this.altNewFirmware = altNewFirmware;
        this.firmwares =
                Arrays.asList(
                        altNewFirmware,
                        newFirmware,
                        oldFirmware);
    }

    @Override
    public int getApiPort() {
        return this.oldFirmware.getApiPort();
    }

    @Override
    public String getIp() {
        return this.oldFirmware.getIp();
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
        return this.oldFirmware.getMinerID();
    }

    @Override
    public MinerStats getStats()
            throws MinerException {
        try {
            return this.altNewFirmware.getStats();
        } catch (final Exception e1) {
            try {
                return this.newFirmware.getStats();
            } catch (final Exception e2) {
                return this.oldFirmware.getStats();
            }
        }
    }
}
