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
 *     "avgHashRate": 13674000000000.52,
 *     "avgHashRate5s": 13800000000000.49,
 *   }
 * </pre>
 */
public class SpeedInfo {

    /** The average hash rate. */
    private final BigDecimal avgHashRate;

    /** The 5 second average hash rate. */
    private final BigDecimal avgHashRate5s;

    /**
     * Constructor.
     *
     * @param avgHashRate   The average hash rate.
     * @param avgHashRate5s The 5 second average hash rate.
     */
    private SpeedInfo(
            @JsonProperty("avgHashRate") final BigDecimal avgHashRate,
            @JsonProperty("avgHashRate5s") final BigDecimal avgHashRate5s) {
        Validate.notNull(
                avgHashRate,
                "avgHashRate cannot be empty");
        Validate.notNull(
                avgHashRate5s,
                "avgHashRate5s cannot be empty");
        this.avgHashRate = avgHashRate;
        this.avgHashRate5s = avgHashRate5s;
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
                            .append(this.avgHashRate, speedInfo.avgHashRate)
                            .append(this.avgHashRate5s, speedInfo.avgHashRate5s)
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
     * Returns the 5 second average hash rate.
     *
     * @return The 5 second average hash rate.
     */
    public BigDecimal getAvgHashRate5s() {
        return this.avgHashRate5s;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.avgHashRate)
                .append(this.avgHashRate5s)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ avgHashRate=%s, avgHashRate5s=%s ]",
                getClass().getSimpleName(),
                this.avgHashRate,
                this.avgHashRate5s);
    }

    /** A builder for creating {@link SpeedInfo speed infos}. */
    public static class Builder
            extends AbstractBuilder<SpeedInfo> {

        /** The average hash rate. */
        private BigDecimal avgHashRate = UNDEFINED_DECIMAL;

        /** The 5 second average hash rate. */
        private BigDecimal avgHashRate5s = UNDEFINED_DECIMAL;

        @Override
        public SpeedInfo build() {
            return new SpeedInfo(
                    this.avgHashRate,
                    this.avgHashRate5s);
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
         * Sets the 5 second average hash rate.
         *
         * @param avgHashRate5s The 5 second average hash rate.
         *
         * @return The builder instance.
         */
        public Builder setAvgHashRate5s(final BigDecimal avgHashRate5s) {
            this.avgHashRate5s = avgHashRate5s;
            return this;
        }
    }
}