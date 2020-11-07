package mn.foreman.whatsminer;

import mn.foreman.model.Detection;
import mn.foreman.model.DetectionStrategy;

import java.util.Map;
import java.util.Optional;

/** A detection strategy for scanning across firmwares. */
public class FirmwareAwareDetectionStrategy
        implements DetectionStrategy {

    /** The new firmware. */
    private final DetectionStrategy newFirmware;

    /** The old firmware. */
    private final DetectionStrategy oldFirmware;

    /**
     * Constructor.
     *
     * @param oldFirmware The old firmware.
     * @param newFirmware The new firmware.
     */
    public FirmwareAwareDetectionStrategy(
            final DetectionStrategy oldFirmware,
            final DetectionStrategy newFirmware) {
        this.oldFirmware = oldFirmware;
        this.newFirmware = newFirmware;
    }

    @Override
    public Optional<Detection> detect(
            final String ip,
            final int port,
            final Map<String, Object> args) {
        Optional<Detection> detection;
        try {
            detection =
                    this.oldFirmware.detect(
                            ip,
                            port,
                            args);
            if (!detection.isPresent()) {
                detection =
                        this.newFirmware.detect(
                                ip,
                                port,
                                args);
            }
        } catch (final Exception e) {
            detection =
                    this.newFirmware.detect(
                            ip,
                            port,
                            args);
        }
        return detection;
    }
}
