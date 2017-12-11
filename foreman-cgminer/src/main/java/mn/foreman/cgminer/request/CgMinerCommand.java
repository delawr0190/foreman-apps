package mn.foreman.cgminer.request;

/** A {@link CgMinerCommand} represents an acceptable command for cgminer. */
public enum CgMinerCommand {

    /**
     * <pre>
     * CgMinerRequest       Reply Section  Details
     * -------       -------------  -------
     * pools         POOLS          The status of each pool e.g.
     *                              Pool=0,URL=http://pool.com:6311,Status=Alive,...|
     * </pre>
     */
    POOLS("pools"),

    /**
     * <pre>
     * CgMinerRequest       Reply Section  Details
     * -------       -------------  -------
     * devs          DEVS           Each available PGA and ASC with their
     *                              details
     *                              e.g. ASC=0,Accepted=NN,MHSav=NNN,...,Intensity=D|
     *                              Last Share Time=NNN,
     *                                  <- standand long time in sec (or 0 if
     *                                     none) of last accepted share
     *                              Last Share Pool=N,
     *                                  <- pool number (or -1 if none)
     *                              Last Valid Work=NNN,
     *                                  <- standand long time in sec of last
     *                                     work returned that wasn't an HW:
     *                              Will not report PGAs if PGA mining is
     *                              disabled
     *                              Will not report ASCs if ASC mining is
     *                              disabled
     * </pre>
     */
    DEVS("devs");

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
    CgMinerCommand(final String command,
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
