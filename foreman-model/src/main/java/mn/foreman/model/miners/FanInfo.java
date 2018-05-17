package mn.foreman.model.miners;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The following POJO represents a JSON object with the following format:
 *
 * <pre>
 * {
 *   "count": 2,
 *   "speeds": [
 *     4080,
 *     4560
 *   ]
 * }
 * </pre>
 */
public class FanInfo {

    /** The number of fans. */
    private final int count;

    /** The fan speeds. */
    private final List<Integer> speeds;

    /**
     * Constructor.
     *
     * @param count  The number of fans.
     * @param speeds The fan speeds.
     */
    private FanInfo(
            @JsonProperty("count") final int count,
            @JsonProperty("speeds") final List<Integer> speeds) {
        Validate.inclusiveBetween(
                0, Integer.MAX_VALUE, count,
                "count must be positive");
        Validate.notNull(
                speeds,
                "speeds cannot be null");
        this.count = count;
        this.speeds = new ArrayList<>(speeds);
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final FanInfo fanInfo = (FanInfo) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.count, fanInfo.count)
                            .append(this.speeds, fanInfo.speeds)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the count.
     *
     * @return The count.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Returns the observed fan speeds.
     *
     * @return The observed fan speeds.
     */
    public List<Integer> getSpeeds() {
        return Collections.unmodifiableList(this.speeds);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.count)
                .append(this.speeds)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ count=%s, speeds=%s ]",
                getClass().getSimpleName(),
                this.count,
                this.speeds);
    }

    /** A builder for creating {@link FanInfo speed infos}. */
    public static class Builder
            extends AbstractBuilder<FanInfo> {

        /** The speeds. */
        private final List<Integer> speeds = new LinkedList<>();

        /** The number of fans. */
        private int count = UNDEFINED_INT;

        /**
         * Adds a speed.
         *
         * @param speed The speed.
         *
         * @return The builder instance.
         */
        public Builder addSpeed(final String speed) {
            if ((speed != null) && !speed.isEmpty()) {
                return addSpeed(Integer.parseInt(speed));
            }
            return this;
        }

        /**
         * Adds a speed.
         *
         * @param speed The speed.
         *
         * @return The builder instance.
         */
        public Builder addSpeed(final int speed) {
            // Ignore fan speeds of 0, as many miners report metrics for fans
            // that aren't being used.  If there's a problem, the reported
            // speeds won't match the count, so an error will display
            if (speed != 0) {
                this.speeds.add(speed);
            }
            return this;
        }

        @Override
        public FanInfo build() {
            return new FanInfo(
                    this.count,
                    this.speeds);
        }

        /**
         * Sets the count.
         *
         * @param count The count.
         *
         * @return The builder instance.
         */
        public Builder setCount(final String count) {
            return setCount(Integer.parseInt(count));
        }

        /**
         * Sets the count.
         *
         * @param count The count.
         *
         * @return The builder instance.
         */
        public Builder setCount(final int count) {
            this.count = count;
            return this;
        }
    }
}