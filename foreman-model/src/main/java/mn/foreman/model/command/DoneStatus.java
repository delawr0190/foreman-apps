package mn.foreman.model.command;

import com.fasterxml.jackson.annotation.JsonValue;

/** The status for the {@link CommandDone.Response}. */
public enum DoneStatus {

    /** The success status. */
    SUCCESS("success"),

    /** The failed status. */
    FAILED("failed");

    /** The status message. */
    private final String status;

    /**
     * The status.
     *
     * @param status The message.
     */
    DoneStatus(final String status) {
        this.status = status;
    }

    /**
     * Returns the status.
     *
     * @return The status.
     */
    @JsonValue
    public String getStatus() {
        return this.status;
    }
}
