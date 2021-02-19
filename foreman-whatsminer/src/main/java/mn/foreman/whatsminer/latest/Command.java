package mn.foreman.whatsminer.latest;

/** A command that can be sent to a whatsminer. */
public enum Command {

    /** status. */
    STATUS("status"),

    /** update_pools. */
    UPDATE_POOLS("update_pools"),

    /** reboot. */
    REBOOT("reboot"),

    /** factory_reset. */
    FACTORY_RESET("factory_reset"),

    /** net_config. */
    NETWORK("net_config"),

    /** power_off. */
    POWER_OFF("power_off"),

    /** power_on. */
    POWER_ON("power_on"),

    /** get_token. */
    GET_TOKEN("get_token");

    /** The command. */
    private final String command;

    /**
     * Constructor.
     *
     * @param command The command.
     */
    Command(final String command) {
        this.command = command;
    }

    /**
     * Returns the command.
     *
     * @return The command.
     */
    public String getCommand() {
        return this.command;
    }
}
