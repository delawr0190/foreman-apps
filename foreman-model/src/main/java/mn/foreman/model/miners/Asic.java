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
 *   {
 *     "name": "Antminer S9",
 *     "speedInfo": {
 *       "avgHashRate": 13674000000000.52,
 *       "avgHashRate5s": 13674000000000.52
 *     },
 *     "fans": {
 *       "num": 2,
 *       "speeds": [
 *         4080,
 *         4560
 *       ]
 *     },
 *     "temps": [
 *       56,
 *       52,
 *       54,
 *       71,
 *       67,
 *       69
 *     ],
 *     "hasErrors": false
 *   }
 * </pre>
 */
public class Asic {

    /** The fan readings. */
    private final FanInfo fans;

    /** Whether or not errors were reported. */
    private final Boolean hasErrors;

    /** The ASIC name. */
    private final String name;

    private final SpeedInfo speedInfo;

    /** The temp sensor readings. */
    private final List<Integer> temps;

    /**
     * Constructor.
     *
     * @param name      The name.
     * @param speedInfo The speed information.
     * @param fans      The fan information.
     * @param temps     The temperatures.
     * @param hasErrors Whether or not errors were observed.
     */
    private Asic(
            @JsonProperty("name") final String name,
            @JsonProperty("speedInfo") final SpeedInfo speedInfo,
            @JsonProperty("fans") final FanInfo fans,
            @JsonProperty("temps") final List<Integer> temps,
            @JsonProperty("hasErrors") final Boolean hasErrors) {
        Validate.notEmpty(
                name,
                "name cannot be empty");
        Validate.notNull(
                speedInfo,
                "speedInfo cannot be null");
        Validate.notNull(
                fans,
                "fans cannot be null");
        Validate.notNull(
                temps,
                "temps cannot be null");
        Validate.notEmpty(
                temps,
                "temps cannot be empty");
        Validate.notNull(
                hasErrors,
                "hasErrors cannot be null");
        this.name = name;
        this.speedInfo = speedInfo;
        this.fans = fans;
        this.temps = new ArrayList<>(temps);
        this.hasErrors = hasErrors;
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
                            .append(this.speedInfo, asic.speedInfo)
                            .append(this.fans, asic.fans)
                            .append(this.temps, asic.temps)
                            .append(this.hasErrors, asic.hasErrors)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the {@link FanInfo}.
     *
     * @return The {@link FanInfo}.
     */
    public FanInfo getFans() {
        return this.fans;
    }

    /**
     * Returns whether or not errors were observed.
     *
     * @return Whether or not errors were observed.
     */
    public Boolean getHasErrors() {
        return this.hasErrors;
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
     * Returns the {@link SpeedInfo}.
     *
     * @return The {@link SpeedInfo}.
     */
    public SpeedInfo getSpeedInfo() {
        return this.speedInfo;
    }

    /**
     * Returns the temps.
     *
     * @return The temps.
     */
    public List<Integer> getTemps() {
        return Collections.unmodifiableList(this.temps);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.name)
                .append(this.speedInfo)
                .append(this.fans)
                .append(this.temps)
                .append(this.hasErrors)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "name=%s, " +
                        "speedInfo=%s, " +
                        "fans=%s, " +
                        "temps=%s, " +
                        "hasErrors=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.name,
                this.speedInfo,
                this.fans,
                this.temps,
                this.hasErrors);
    }

    /** A builder for creating {@link Asic ASICs}. */
    public static class Builder
            extends AbstractBuilder<Asic> {

        /** The temperatures. */
        private final List<Integer> temps = new LinkedList<>();

        /** The fan information. */
        private FanInfo fanInfo;

        /** Whether or not errors were observed. */
        private Boolean hasErrors = UNDEFINED_BOOL;

        /** The name. */
        private String name = UNDEFINED_STRING;

        /** The {@link SpeedInfo}. */
        private SpeedInfo speedInfo;

        /**
         * Adds a new temperature reading.
         *
         * @param temp The new temperature reading.
         *
         * @return The builder instance.
         */
        public Builder addTemp(final String temp) {
            return addTemp(Integer.parseInt(temp));
        }

        /**
         * Adds a new temperature reading.
         *
         * @param temp The new temperature reading.
         *
         * @return The builder instance.
         */
        public Builder addTemp(final int temp) {
            // Ignore temps of 0, as many miners report metrics for sensors
            // that aren't being used.  If there's a problem, no sensors
            // will display a temperature and an error will display
            if (temp != 0) {
                this.temps.add(temp);
            }
            return this;
        }

        @Override
        public Asic build() {
            return new Asic(
                    this.name,
                    this.speedInfo,
                    this.fanInfo,
                    this.temps,
                    this.hasErrors);
        }

        /**
         * Sets whether or not errors were observed.
         *
         * @param hasErrors Whether or not errors were observed.
         *
         * @return The builder instance.
         */
        public Builder hasErrors(final Boolean hasErrors) {
            this.hasErrors = hasErrors;
            return this;
        }

        /**
         * Sets the fan information.
         *
         * @param fanInfo The fan info.
         *
         * @return The builder instance.
         */
        public Builder setFanInfo(final FanInfo fanInfo) {
            this.fanInfo = fanInfo;
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
            this.name = name;
            return this;
        }

        /**
         * Sets the speed information.
         *
         * @param speedInfo The speed info.
         *
         * @return The builder instance.
         */
        public Builder setSpeedInfo(final SpeedInfo speedInfo) {
            this.speedInfo = speedInfo;
            return this;
        }
    }
}