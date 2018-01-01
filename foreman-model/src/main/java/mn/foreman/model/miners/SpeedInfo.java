package mn.foreman.model.miners;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

/**
 * The following POJO represents a JSON object with the following format:
 *
 * <pre>
 *   {
 *     "hashRate": 135000000000.00,
 *     "avgHashRate": 135000000000.00,
 *   }
 * </pre>
 */
public class SpeedInfo {

    /** The average hash rate. */
    private final BigDecimal avgHashRate;

    /** The current hash rate. */
    private final BigDecimal hashRate;

    /**
     * Constructor.
     *
     * @param hashRate    The hash rate.
     * @param avgHashRate The average hash rate.
     */
    private SpeedInfo(
            @JsonProperty("hashRate") final BigDecimal hashRate,
            @JsonProperty("avgHashRate") final BigDecimal avgHashRate) {
        Validate.notNull(
                hashRate,
                "hashRate cannot be empty");
        Validate.notNull(
                avgHashRate,
                "avgHashRate cannot be empty");
        this.hashRate = hashRate;
        this.avgHashRate = avgHashRate;
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final SpeedInfo speedInfo = (SpeedInfo) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.hashRate, speedInfo.hashRate)
                            .append(this.avgHashRate, speedInfo.avgHashRate)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the average hash rate.
     *
     * @return The average hash rate.
     */
    public BigDecimal getAvgHashRate() {
        return this.avgHashRate;
    }

    /**
     * Returns the current hash rate.
     *
     * @return The current hash rate.
     */
    public BigDecimal getHashRate() {
        return this.hashRate;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.hashRate)
                .append(this.avgHashRate)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ hashRate=%s, avgHashRate=%s ]",
                getClass().getSimpleName(),
                this.hashRate,
                this.avgHashRate);
    }

    /** A builder for creating {@link SpeedInfo speed infos}. */
    public static class Builder
            extends AbstractBuilder<SpeedInfo> {

        /** The average hash rate. */
        private BigDecimal avgHashRate = UNDEFINED_DECIMAL;

        /** The hash rate. */
        private BigDecimal hashRate = UNDEFINED_DECIMAL;

        @Override
        public SpeedInfo build() {
            return new SpeedInfo(
                    this.hashRate,
                    this.avgHashRate);
        }

        /**
         * Sets the average hash rate.
         *
         * @param avgHashRate The average hash rate.
         *
         * @return The builder instance.
         */
        public Builder setAvgHashRate(final BigDecimal avgHashRate) {
            this.avgHashRate = avgHashRate;
            return this;
        }

        /**
         * Sets the hash rate.
         *
         * @param hashRate The hash rate.
         *
         * @return The builder instance.
         */
        public Builder setHashRate(final BigDecimal hashRate) {
            this.hashRate = hashRate;
            return this;
        }
    }
}