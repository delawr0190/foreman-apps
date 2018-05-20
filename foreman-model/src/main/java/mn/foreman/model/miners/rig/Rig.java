package mn.foreman.model.miners.rig;

import mn.foreman.model.AbstractBuilder;
import mn.foreman.model.miners.SpeedInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** A {@link Rig} represents a miner that's comprised of {@link Gpu GPUs}. */
public class Rig {

    /** The GPUs. */
    private final List<Gpu> gpus;

    /** The name. */
    private final String name;

    /** The speed information. */
    private final SpeedInfo speedInfo;

    /**
     * Constructor.
     *
     * @param name      The name.
     * @param speedInfo The speed information.
     * @param gpus      The GPUs.
     */
    private Rig(
            @JsonProperty("name") final String name,
            @JsonProperty("speedInfo") final SpeedInfo speedInfo,
            @JsonProperty("gpus") final List<Gpu> gpus) {
        Validate.notNull(
                name,
                "Name cannot be null");
        Validate.notEmpty(
                name,
                "Name cannot be empty");
        Validate.notNull(
                speedInfo,
                "Speed cannot be null");
        Validate.notNull(
                gpus,
                "GPUs cannot be null");
        this.name = name;
        this.speedInfo = speedInfo;
        this.gpus = new ArrayList<>(gpus);
    }

    @Override
    public boolean equals(final Object other) {
        final boolean isEqual;
        if (other == null) {
            isEqual = false;
        } else if (getClass() != other.getClass()) {
            isEqual = false;
        } else {
            final Rig rig = (Rig) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.name, rig.name)
                            .append(this.speedInfo, rig.speedInfo)
                            .append(this.gpus, rig.gpus)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the {@link Gpu GPUs}.
     *
     * @return The GPUs.
     */
    public List<Gpu> getGpus() {
        return this.gpus;
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
     * Returns the speed info.
     *
     * @return The speed info.
     */
    public SpeedInfo getSpeedInfo() {
        return this.speedInfo;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.name)
                .append(this.speedInfo)
                .append(this.gpus)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "name=%s, " +
                        "speedInfo=%s, " +
                        "gpus=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.name,
                this.speedInfo,
                this.gpus);
    }

    /** A builder for creating {@link Rig Rigs}. */
    public static class Builder
            extends AbstractBuilder<Rig> {

        /** The {@link Gpu GPUs}. */
        private final List<Gpu> gpus = new LinkedList<>();

        /** The name. */
        private String name;

        /** The speed info. */
        private SpeedInfo speedInfo;

        /**
         * Adds the {@link Gpu}.
         *
         * @param gpu The {@link Gpu}.
         *
         * @return This builder instance.
         */
        public Builder addGpu(final Gpu gpu) {
            this.gpus.add(gpu);
            return this;
        }

        @Override
        public Rig build() {
            return new Rig(
                    this.name,
                    this.speedInfo,
                    this.gpus);
        }

        /**
         * Sets the name.
         *
         * @param name The name.
         *
         * @return This builder instance.
         */
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the speed info.
         *
         * @param speedInfo The speed info.
         *
         * @return This builder instance.
         */
        public Builder setSpeedInfo(final SpeedInfo speedInfo) {
            this.speedInfo = speedInfo;
            return this;
        }
    }
}