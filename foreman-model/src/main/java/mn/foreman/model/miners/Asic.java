package mn.foreman.model.miners;

import com.fasterxml.jackson.annotation.JsonProperty;
import mn.foreman.model.AbstractBuilder;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

/**
 * The following POJO represents a JSON object with the following format:
 *
 * <pre>
 *   {
 *     "name": "ASIC 0",
 *     "temperature": 70.1
 *     "enabled": true,
 *     "priority": 0
 *   }
 * </pre>
 */
public class Asic {

    /** The ASIC name. */
    private final String name;

    /** The ASIC temperature. */
    private final BigDecimal temperature;

    /**
     * Constructor.
     *
     * @param name        The name.
     * @param temperature The temperature.
     */
    private Asic(
            @JsonProperty("name") final String name,
            @JsonProperty("temperature") final BigDecimal temperature) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notNull(
                temperature,
                "temperature cannot be null");
        this.name = name;
        this.temperature = temperature;
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final Asic asic = (Asic) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.name, asic.name)
                            .append(this.temperature, asic.temperature)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the name.
     *
     * @return The name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the temperature.
     *
     * @return The temperature.
     */
    public BigDecimal getTemperature() {
        return this.temperature;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.name)
                .append(this.temperature)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ name=%s, temperature=%s ]",
                getClass().getSimpleName(),
                this.name,
                this.temperature);
    }

    /** A builder for creating {@link Asic ASICs}. */
    public static class Builder
            extends AbstractBuilder<Asic> {

        /** The name. */
        private String name = UNDEFINED_STRING;

        /** The temperature. */
        private BigDecimal temperature = UNDEFINED_DECIMAL;

        @Override
        public Asic build() {
            return new Asic(
                    this.name,
                    this.temperature);
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
         * Sets the temperature.
         *
         * @param temperature The temperature.
         *
         * @return The {@link Builder} instance.
         */
        public Builder setTemperature(final BigDecimal temperature) {
            this.temperature = temperature;
            return this;
        }
    }
}