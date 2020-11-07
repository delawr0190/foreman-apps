package mn.foreman.whatsminer;

import mn.foreman.model.Miner;
import mn.foreman.model.MinerID;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;

import java.util.Optional;

/**
 * A firmware-aware miner is a miner that will try to obtain stats from two
 * different versions of a miner.
 */
public class FirmwareAwareMiner
        implements Miner {

    /** The new firmware. */
    private final Miner newFirmware;

    /** The old firmware. */
    private final Miner oldFirmware;

    /**
     * Constructor.
     *
     * @param oldFirmware The old firmware.
     * @param newFirmware The new firmware.
     */
    FirmwareAwareMiner(
            final Miner oldFirmware,
            final Miner newFirmware) {
        this.oldFirmware = oldFirmware;
        this.newFirmware = newFirmware;
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
        Optional<String> macAddress;
        try {
            macAddress = this.newFirmware.getMacAddress();
            if (!macAddress.isPresent()) {
                macAddress = this.oldFirmware.getMacAddress();
            }
        } catch (final Exception e) {
            macAddress = this.oldFirmware.getMacAddress();
        }
        return macAddress;
    }

    @Override
    public MinerID getMinerID() {
        return this.oldFirmware.getMinerID();
    }

    @Override
    public MinerStats getStats()
            throws MinerException {
        try {
            return this.newFirmware.getStats();
        } catch (final Exception e) {
            return this.oldFirmware.getStats();
        }
    }
}
