package mn.foreman.pickaxe.command.asic;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/** An enum representing all of the commands that can be executed. */
public enum RemoteCommand {

    /** Discover a miner. */
    DISCOVER("discover", false),

    /** Scan for miners. */
    SCAN("scan", false),

    /** Scan for miners over a range of IPs. */
    SCAN_RANGES("scan-ranges", false),

    /** Scan for a specific MAC address. */
    TARGETED_SCAN("targeted-scan", false),

    /** Change miner pools. */
    CHANGE_POOLS("change-pools", true),

    /** Reboot miner. */
    REBOOT("reboot", true),

    /** Digest authentication GET. */
    DIGEST("digest", false),

    /** Obtain raw stats from miner. */
    RAW_STATS("raw-stats", false),

    /** Factory reset. */
    FACTORY_RESET("factory-reset", true),

    /** Run a raw read miner query. */
    EVAL("eval", false),

    /** Queries a Whatsminer page. */
    WHATSMINER_GET("whatsminer-get", false),

    /** Change network configuration. */
    NETWORK("network", true),

    /** Terminate agent. */
    TERMINATE("terminate", true),

    /** Change power mode. */
    POWER_MODE("power-mode", true),

    /** Change password. */
    PASSWORD("password", true),

    /** Blink LEDs. */
    BLINK("blink", true),

    /** Queries an Obelisk page. */
    OBILESK_GET("obelisk-get", false),

    /** Firmware upgrading. */
    FIRMWARE_UPGRADE("firmware-upgrade", true);

    /** The known types. */
    private static final Map<String, RemoteCommand> TYPES =
            new ConcurrentHashMap<>();

    static {
        for (final RemoteCommand command : values()) {
            TYPES.put(command.type, command);
        }
    }

    /** Whether or not the command is control. */
    private final boolean isControl;

    /** The type. */
    private final String type;

    /**
     * Constructor.
     *
     * @param type      The type.
     * @param isControl Whether or not control is enabled.
     */
    RemoteCommand(
            final String type,
            final boolean isControl) {
        this.type = type;
        this.isControl = isControl;
    }

    /**
     * Returns the type, if known.
     *
     * @param type The type.
     *
     * @return The known type.
     */
    public static Optional<RemoteCommand> forType(final String type) {
        return Optional.ofNullable(TYPES.get(type));
    }

    /**
     * Returns the type.
     *
     * @return The type.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns whether or not the command is a control command.
     *
     * @return Whether or not the command is a control command.
     */
    public boolean isControl() {
        return this.isControl;
    }
}
