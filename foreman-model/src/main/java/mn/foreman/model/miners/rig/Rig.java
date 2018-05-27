package mn.foreman.model.miners.rig;

import mn.foreman.model.AbstractBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** A {@link Rig} represents a miner that's comprised of {@link Gpu GPUs}. */
public class Rig {

    /** The GPUs. */
    private final List<Gpu> gpus;

    /** The hash rate. */
    private final BigDecimal hashRate;

    /** The name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name     The name.
     * @param hashRate The hash rate.
     * @param gpus     The GPUs.
     */
    private Rig(
            @JsonProperty("name") final String name,
            @JsonProperty("hashRate") final BigDecimal hashRate,
            @JsonProperty("gpus") final List<Gpu> gpus) {
        Validate.notNull(
                name,
                "Name cannot be null");
        Validate.notEmpty(
                name,
                "Name cannot be empty");
        Validate.notNull(
                hashRate,
                "Speed cannot be null");
        Validate.notNull(
                gpus,
                "GPUs cannot be null");
        this.name = name;
        this.hashRate = hashRate;
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
                            .append(this.hashRate, rig.hashRate)
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
     * Returns the hash rate.
     *
     * @return The hash rate.
     */
    public BigDecimal getHashRate() {
        return this.hashRate;
    }

    /**
     * Returns the name.
     *
     * @return The name.
     */
    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.name)
                .append(this.hashRate)
                .append(this.gpus)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "name=%s, " +
                        "hashRate=%s, " +
                        "gpus=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.name,
                this.hashRate,
                this.gpus);
    }

    /** A builder for creating {@link Rig Rigs}. */
    public static class Builder
            extends AbstractBuilder<Rig> {

        /** The {@link Gpu GPUs}. */
        private final List<Gpu> gpus = new LinkedList<>();

        /** The speed info. */
        private BigDecimal hashRate;

        /** The name. */
        private String name;

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
                    this.hashRate,
                    this.gpus);
        }

        /**
         * Sets the hash rate.
         *
         * @param hashRate The hash rate.
         *
         * @return This builder instance.
         */
        public Builder setHashRate(final BigDecimal hashRate) {
            this.hashRate = hashRate;
            return this;
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
    }
}