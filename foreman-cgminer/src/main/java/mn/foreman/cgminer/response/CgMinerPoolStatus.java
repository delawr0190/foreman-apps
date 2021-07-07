package mn.foreman.cgminer.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** An enum representing cgminer pool statuses. */
public enum CgMinerPoolStatus {

    /** The pool is dead. */
    DEAD("Dead"),

    /** The pool is dead (Bitmain typo). */
    DEED("Deed"),

    /** The pool is sick. */
    SICK("Sick"),

    /** The pool didn't start. */
    NO_START("NoStart"),

    /** The pool is starting up. */
    INIT("Initialising"),

    /** The pool is disabled. */
    DISABLED("Disabled"),

    /** The pool is alive. */
    ALIVE("Alive"),

    /** The pool is rejecting. */
    REJECTING("Rejecting"),

    /** Unknown. */
    UNKNOWN("Unknown");

    /** All of the statuses, mapped by string. */
    private static final ConcurrentMap<String, CgMinerPoolStatus> STATUSES =
            new ConcurrentHashMap<>();

    static {
        for (final CgMinerPoolStatus status : values()) {
            STATUSES.put(
                    status.getStatus(),
                    status);
        }
    }

    /** The status. */
    private final String status;

    /**
     * Constructor.
     *
     * @param status The status.
     */
    CgMinerPoolStatus(final String status) {
        this.status = status;
    }

    @JsonCreator
    public static CgMinerPoolStatus forValue(final String status) {
        return STATUSES.getOrDefault(
                status,
                UNKNOWN);
    }

    /**
     * Returns the status.
     *
     * @return The status.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Checks to see if the pool is enabled.
     *
     * @return Whether or not the pool is enabled.
     */
    public boolean isEnabled() {
        return (this != DISABLED);
    }

    /**
     * Checks to see if the pool is up.
     *
     * @return Whether or not the pool is up.
     */
    public boolean isUp() {
        return (this == ALIVE);
    }

    @JsonValue
    public String toValue() {
        return this.status;
    }
}