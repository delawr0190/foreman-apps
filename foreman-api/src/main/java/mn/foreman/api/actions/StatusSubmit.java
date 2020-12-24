package mn.foreman.api.actions;

import com.fasterxml.jackson.annotation.JsonProperty;

/** The action submit statuses. */
public enum StatusSubmit {

    /** Action was accepted. */
    @JsonProperty("okay")
    OKAY;

    /**
     * Returns whether or not the action was accepted.
     *
     * @return Whether or not the action was accepted.
     */
    public boolean isSuccess() {
        return (this == OKAY);
    }
}
