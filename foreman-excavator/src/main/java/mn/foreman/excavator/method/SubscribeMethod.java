package mn.foreman.excavator.method;

/** A model-object implementation of subscribe-related methods. */
public enum SubscribeMethod
        implements Method {

    /** subscribe.info */
    INFO("info");

    /** The command. */
    private final String command;

    /**
     * Constructor.
     *
     * @param command The command.
     */
    SubscribeMethod(final String command) {
        this.command = command;
    }

    @Override
    public String toMethod() {
        return String.format(
                "subscribe%s",
                (!this.command.isEmpty()
                        ? "." + this.command
                        : ""));
    }
}