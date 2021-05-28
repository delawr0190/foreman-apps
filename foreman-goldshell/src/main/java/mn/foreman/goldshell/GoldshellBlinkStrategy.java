package mn.foreman.goldshell;

import mn.foreman.goldshell.json.Setting;
import mn.foreman.model.ApplicationConfiguration;
import mn.foreman.model.BlinkStrategy;
import mn.foreman.model.error.MinerException;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

/** A {@link BlinkStrategy} for blinking LEDs on a goldshell. */
public class GoldshellBlinkStrategy
        implements BlinkStrategy {

    /** The configuration. */
    private final ApplicationConfiguration configuration;

    /**
     * Constructor.
     *
     * @param configuration The configuration.
     */
    public GoldshellBlinkStrategy(
            final ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean startBlink(
            final String ip,
            final int port,
            final Map<String, Object> parameters) throws MinerException {
        return runLedOp(
                ip,
                port,
                true);
    }

    @Override
    public boolean stopBlink(
            final String ip,
            final int port,
            final Map<String, Object> parameters) throws MinerException {
        return runLedOp(
                ip,
                port,
                false);
    }

    /**
     * Changes the LED status.
     *
     * @param ip   The ip.
     * @param port The port.
     * @param on   Whether or not it's lit.
     *
     * @return Whether or not the command was successful.
     *
     * @throws MinerException on failure.
     */
    private boolean runLedOp(
            final String ip,
            final int port,
            final boolean on) throws MinerException {
        final Setting setting =
                GoldshellQuery.runGet(
                        ip,
                        port,
                        "/mcb/setting",
                        null,
                        new TypeReference<Setting>() {
                        },
                        this.configuration)
                        .orElseThrow(() -> new MinerException("Failed to obtain settings"));
        setting.ledControl = on;
        return GoldshellQuery.runPut(
                ip,
                port,
                "/mcb/setting",
                setting,
                this.configuration);
    }
}