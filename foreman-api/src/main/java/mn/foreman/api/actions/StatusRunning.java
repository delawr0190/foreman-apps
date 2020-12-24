package mn.foreman.api.actions;

import com.fasterxml.jackson.annotation.JsonProperty;

/** The action statuses. */
public enum StatusRunning {

    /** Action is pending. */
    @JsonProperty("pending")
    PENDING,

    /** Action is currently running. */
    @JsonProperty("in-progress")
    IN_PROGRESS,

    /** Action completed successfully. */
    @JsonProperty("complete")
    COMPLETE,

    /** Action failed. */
    @JsonProperty("failed")
    FAILED;

    /**
     * Returns whether or not the action is done.
     *
     * @return Whether or not the action is done.
     */
    public boolean isDone() {
        return (this != PENDING && this != IN_PROGRESS);
    }

    /**
     * Returns whether or not the action is done.
     *
     * @return Whether or not the action is done.
     */
    public boolean isSuccess() {
        return (this == COMPLETE);
    }
}
