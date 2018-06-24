package mn.foreman.model.miners;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The following POJO represents a JSON object with the following format:
 *
 * <pre>
 *   {
 *     "name": "nicehash",
 *     "enabled": true,
 *     "status": true,
 *     "priority": 0,
 *     "accepted": 1421,
 *     "rejected": 10,
 *     "stale": 0
 *   }
 * </pre>
 */
public class Pool {

    /** The accepted count. */
    private final long accepted;

    /** Whether or not the pool is enabled. */
    private final Boolean enabled;

    /** The pool name. */
    private final String name;

    /** The pool priority. */
    private final int priority;

    /** The rejected count. */
    private final long rejected;

    /** The stale count. */
    private final long stale;

    /** The pool status (up or not). */
    private final Boolean status;

    /**
     * Constructor.
     *
     * @param name     The pool name.
     * @param enabled  Whether or not the pool is enabled.
     * @param status   Whether or not the pool is up.
     * @param priority The pool priority.
     * @param accepted The accepted count.
     * @param rejected The rejected count.
     * @param stale    The stale count.
     */
    private Pool(
            @JsonProperty("name") final String name,
            @JsonProperty("enabled") final Boolean enabled,
            @JsonProperty("status") final Boolean status,
            @JsonProperty("priority") final int priority,
            @JsonProperty("accepted") final long accepted,
            @JsonProperty("rejected") final long rejected,
            @JsonProperty("stale") final long stale) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notNull(
                enabled,
                "enabled cannot be null");
        Validate.notNull(
                status,
                "status cannot be null");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, priority,
                "priority must be positive");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, accepted,
                "accepted must be positive");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, rejected,
                "rejected must be positive");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, stale,
                "stale must be positive");
        this.name = name;
        this.enabled = enabled;
        this.status = status;
        this.priority = priority;
        this.accepted = accepted;
        this.rejected = rejected;
        this.stale = stale;
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final Pool pool = (Pool) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.name, pool.name)
                            .append(this.enabled, pool.enabled)
                            .append(this.status, pool.status)
                            .append(this.priority, pool.priority)
                            .append(this.accepted, pool.accepted)
                            .append(this.rejected, pool.rejected)
                            .append(this.stale, pool.stale)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the accepted count.
     *
     * @return The accepted count.
     */
    public long getAccepted() {
        return this.accepted;
    }

    /**
     * Returns whether or not the pool is enabled.
     *
     * @return Whether or not the pool is enabled.
     */
    public Boolean getEnabled() {
        return this.enabled;
    }

    /**
     * Returns the pool name.
     *
     * @return The pool name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the pool priority.
     *
     * @return The pool priority.
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Returns the rejected count.
     *
     * @return The rejected count.
     */
    public long getRejected() {
        return this.rejected;
    }

    /**
     * Returns the stale count.
     *
     * @return The stale count.
     */
    public long getStale() {
        return this.stale;
    }

    /**
     * Returns whether or not the pool is up.
     *
     * @return Whether or not the pool is up.
     */
    public Boolean getStatus() {
        return this.status;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.name)
                .append(this.enabled)
                .append(this.status)
                .append(this.priority)
                .append(this.accepted)
                .append(this.rejected)
                .append(this.stale)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "name=%s, " +
                        "enabled=%s, " +
                        "status=%s, " +
                        "priority=%d, " +
                        "accepted=%d, " +
                        "rejected=%d, " +
                        "stale=%d " +
                        "]",
                getClass().getSimpleName(),
                this.name,
                this.enabled,
                this.status,
                this.priority,
                this.accepted,
                this.rejected,
                this.stale);
    }

    /** A builder for creating {@link Pool pools}. */
    public static class Builder
            extends AbstractBuilder<Pool> {

        /** The accepted count. */
        private long accepted = UNDEFINED_LONG;

        /** Whether or not the pool is enabled. */
        private Boolean enabled = UNDEFINED_BOOL;

        /** The pool name. */
        private String name = UNDEFINED_STRING;

        /** The pool priority. */
        private int priority = UNDEFINED_INT;

        /** The rejected count. */
        private long rejected = UNDEFINED_LONG;

        /** The stale count. */
        private long stale = UNDEFINED_LONG;

        /** Whether or not the pool is up. */
        private Boolean status = UNDEFINED_BOOL;

        /**
         * Builds a new {@link Pool}.
         *
         * @return The new {@link Pool}.
         */
        @Override
        public Pool build() {
            return new Pool(
                    this.name,
                    this.enabled,
                    this.status,
                    this.priority,
                    this.accepted,
                    this.rejected,
                    this.stale);
        }

        /**
         * Sets the pool metrics.
         *
         * @param accepted The accepted count.
         * @param rejected The rejected count.
         * @param stale    The stale count.
         *
         * @return The builder instance.
         */
        public Builder setCounts(
                final String accepted,
                final String rejected,
                final String stale) {
            return setCounts(
                    Long.parseLong(accepted),
                    Long.parseLong(rejected),
                    Long.parseLong(stale));
        }

        /**
         * Sets the pool metrics.
         *
         * @param accepted The accepted count.
         * @param rejected The rejected count.
         * @param stale    The stale count.
         *
         * @return The builder instance.
         */
        public Builder setCounts(
                final long accepted,
                final long rejected,
                final long stale) {
            this.accepted = accepted;
            this.rejected = rejected;
            this.stale = stale;
            return this;
        }

        /**
         * Sets the name.
         *
         * @param name The name.
         *
         * @return The builder instance.
         */
        public Builder setName(final String name) {
            if ((name != null) && (!name.isEmpty())) {
                this.name = name;
            }
            return this;
        }

        /**
         * Sets the priority.
         *
         * @param priority The priority.
         *
         * @return The builder instance.
         */
        public Builder setPriority(final String priority) {
            return setPriority(Integer.parseInt(priority));
        }

        /**
         * Sets the priority.
         *
         * @param priority The priority.
         *
         * @return The builder instance.
         */
        public Builder setPriority(final int priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Sets the status.
         *
         * @param enabled The enabled flag.
         * @param status  Whether or not the pool is up.
         *
         * @return The builder instance.
         */
        public Builder setStatus(
                final Boolean enabled,
                final Boolean status) {
            this.enabled = enabled;
            this.status = status;
            return this;
        }
    }
}