package mn.foreman.cgminer.request;

/** A {@link CgMinerCommand} represents an acceptable command for cgminer. */
public enum CgMinerCommand {

    /**
     * The following description was retrieved from the <a href="https://github
     * .com/ckolivas/cgminer/blob/master/API-README">API-README</a>.
     *
     * <pre>
     * CgMinerRequest   Reply Section  Details
     * -------          -------------  -------
     * pools            POOLS          The status of each pool e.g.
     *                                 Pool=0,URL=http://pool.com:6311,Status=Alive,...|
     * </pre>
     */
    POOLS("pools"),

    /**
     * The following description was retrieved from the <a href="https://github
     * .com/ckolivas/cgminer/blob/master/API-README">API-README</a>.
     *
     * <pre>
     * CgMinerRequest   Reply Section  Details
     * -------          -------------  -------
     * stats            STATS          Each device or pool that has 1 or more
     *                                 getworks with a list of stats regarding
     *                                 getwork times The values returned by
     *                                 stats may change in future versions thus
     *                                 would not normally be displayed
     *                                 Device drivers are also able to add
     *                                 stats to the end of the details returned
     * </pre>
     */
    STATS("stats"),

    /**
     * The following description was retrieved from the <a href="https://github
     * .com/ckolivas/cgminer/blob/master/API-README">API-README</a>.
     *
     * <pre>
     * CgMinerRequest   Reply Section  Details
     * -------          -------------  -------
     * version          VERSION        CGMiner=cgminer, version
     *                                 API=API| version
     * </pre>
     */
    VERSION("version");

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
