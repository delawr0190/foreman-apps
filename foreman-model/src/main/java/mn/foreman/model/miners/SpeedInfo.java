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
 *     "hashRate": "13.5 TH/s",
 *     "avgHashRate": "13.5 TH/s"
 *   }
 * </pre>
 */
public class SpeedInfo {

    /** The average hash rate. */
    private final String avgHashRate;

    /** The current hash rate. */
    private final String hashRate;

    /**
     * Constructor.
     *
     * @param hashRate    The hash rate.
     * @param avgHashRate The average hash rate.
     */
    private SpeedInfo(
            @JsonProperty("hashRate") final String hashRate,
            @JsonProperty("avgHashRate") final String avgHashRate) {
        Validate.notEmpty(
                hashRate,
                "hashRate cannot be empty");
        Validate.notEmpty(
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
    public String getAvgHashRate() {
        return this.avgHashRate;
    }

    /**
     * Returns the current hash rate.
     *
     * @return The current hash rate.
     */
    public String getHashRate() {
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
        private String avgHashRate = UNDEFINED_STRING;

        /** The hash rate. */
        private String hashRate = UNDEFINED_STRING;

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
         * @return The {@link Builder} instance.
         */
        public Builder setAvgHashRate(final String avgHashRate) {
            this.avgHashRate = avgHashRate;
            return this;
        }

        /**
         * Sets the hash rate.
         *
         * @param hashRate The hash rate.
         *
         * @return The {@link Builder} instance.
         */
        public Builder setHashRate(final String hashRate) {
            this.hashRate = hashRate;
            return this;
        }
    }
}