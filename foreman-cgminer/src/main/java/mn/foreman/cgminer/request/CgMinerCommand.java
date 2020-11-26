package mn.foreman.cgminer.request;

/** A {@link CgMinerCommand} represents an acceptable command for cgminer. */
public enum CgMinerCommand {

    /** The cgminer "pools" command. */
    POOLS("pools"),

    /** The cgminer "stats" command. */
    STATS("stats"),

    /** The cgminer "devs" command. */
    DEVS("devs"),

    /** The cgminer "edevs" command. */
    EDEVS("edevs"),

    /** The cgminer "summary" command. */
    SUMMARY("summary"),

    /** The cgminer "version" command. */
    VERSION("version"),

    /** The bOS temps command. */
    TEMPS("temps"),

    /** The bOS fans command. */
    FANS("fans");

    /** The command. */
    private final String command;

    /** The accepted number of parameters. */
    private final int numParameters;

    /** Whether or not the parameter is required. */
    private final boolean parameterRequired;

    /**
     * Constructor.
     *
     * @param command           The command.
     * @param numParameters     The number of parameters.
     * @param parameterRequired Whether the parameters are required.
     */
    CgMinerCommand(
            final String command,
            final int numParameters,
            final boolean parameterRequired) {
        this.command = command;
        this.numParameters = numParameters;
        this.parameterRequired = parameterRequired;
    }

    /**
     * Constructor.
     *
     * @param command The command.
     */
    CgMinerCommand(final String command) {
        this(command, 0, false);
    }

    /**
     * Returns the command.
     *
     * @return The command.
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * Returns the accepted number of parameters.
     *
     * @return The accepted number of parameters.
     */
    public int getNumParameters() {
        return this.numParameters;
    }

    /**
     * Checks to see if the parameters are required.
     *
     * @return Whether or not the parameters are required.
     */
    public boolean isParameterRequired() {
        return this.parameterRequired;
    }
}
