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
 *     "name": "mypool",
 *     "enabled": true,
 *     "priority": 0
 *   }
 * </pre>
 */
public class Pool {

    /** Whether or not the pool is enabled. */
    private final Boolean enabled;

    /** The pool name. */
    private final String name;

    /** The pool priority. */
    private final int priority;

    /**
     * Constructor.
     *
     * @param name     The pool name.
     * @param enabled  Whether or not the pool is enabled.
     * @param priority The pool priority.
     */
    private Pool(
            @JsonProperty("name") final String name,
            @JsonProperty("enabled") final Boolean enabled,
            @JsonProperty("priority") final int priority) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notNull(
                enabled,
                "enabled cannot be null");
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, priority,
                "priority must be positive");
        this.name = name;
        this.enabled = enabled;
        this.priority = priority;
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
                            .append(this.priority, pool.priority)
                            .isEquals();
        }
        return isEqual;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.name)
                .append(this.enabled)
                .append(this.priority)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ name=%s, enabled=%s, priority=%d ]",
                getClass().getSimpleName(),
                this.name,
                this.enabled,
                this.priority);
    }

    /** A builder for creating {@link Pool pools}. */
    public static class Builder
            extends AbstractBuilder<Pool> {

        /** Whether or not the pool is enabled. */
        private Boolean enabled = false;

        /** The pool name. */
        private String name = UNDEFINED_STRING;

        /** The pool priority. */
        private int priority = UNDEFINED_INT;

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
                    this.priority);
        }

        /**
         * Sets the enabled flag.
         *
         * @param enabled The enabled flag.
         *
         * @return The {@link Builder} instance.
         */
        public Builder setEnabled(final Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * Sets the name.
         *
         * @param name The name.
         *
         * @return The {@link Builder} instance.
         */
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the priority.
         *
         * @param priority The priority.
         *
         * @return The {@link Builder} instance.
         */
        public Builder setPriority(final int priority) {
            this.priority = priority;
            return this;
        }
    }
}