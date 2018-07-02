package mn.foreman.excavator.method;

/** A model-object implementation of algorithm-related methods. */
public enum AlgorithmMethod
        implements Method {

    /** algorithm.list. */
    LIST("list");

    /** The command. */
    private final String command;

    /**
     * Constructor.
     *
     * @param command The command.
     */
    AlgorithmMethod(final String command) {
        this.command = command;
    }

    @Override
    public String toMethod() {
        return String.format(
                "algorithm%s",
                (!this.command.isEmpty()
                        ? "." + this.command
                        : ""));
    }
}