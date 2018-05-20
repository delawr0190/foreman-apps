package mn.foreman.ccminer;

/** A {@link CcMinerCommand} provides all of the supported ccminer commands. */
public enum CcMinerCommand {

    /** Obtains a summary. */
    SUMMARY("summary"),

    /** Obtains pool metrics. */
    POOL("pool"),

    /** Obtains hardware info. */
    HWINFO("hwinfo");

    /** The command. */
    private final String command;

    /**
     * Constructor.
     *
     * @param command The command.
     */
    CcMinerCommand(final String command) {
        this.command = command;
    }

    /**
     * Returns the command.
     *
     * @return The command
     */
    public String getCommand() {
        return this.command;
    }
}