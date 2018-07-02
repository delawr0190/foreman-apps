package mn.foreman.excavator.method;

/** A model-object implementation of devices-related methods. */
public enum DevicesMethod
        implements Method {

    /** devices.get */
    GET("get");

    /** The command. */
    private final String command;

    /**
     * Constructor.
     *
     * @param command The command.
     */
    DevicesMethod(final String command) {
        this.command = command;
    }

    @Override
    public String toMethod() {
        return String.format(
                "devices%s",
                (!this.command.isEmpty()
                        ? "." + this.command
                        : ""));
    }
}