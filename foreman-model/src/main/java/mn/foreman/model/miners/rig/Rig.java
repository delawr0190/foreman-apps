package mn.foreman.model.miners.rig;

import mn.foreman.model.AbstractBuilder;
import mn.foreman.model.miners.BigDecimalSerializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.*;

/** A {@link Rig} represents a miner that's comprised of {@link Gpu GPUs}. */
public class Rig {

    /** Miscellaneous rig attributes. */
    private final List<Map<String, String>> attributes;

    /** The GPUs. */
    private final List<Gpu> gpus;

    /** The hash rate. */
    @JsonSerialize(using = BigDecimalSerializer.class)
    private final BigDecimal hashRate;

    /**
     * Constructor.
     *
     * @param hashRate   The hash rate.
     * @param gpus       The GPUs.
     * @param attributes Rig attributes.
     */
    private Rig(
            @JsonProperty("hashRate") final BigDecimal hashRate,
            @JsonProperty("gpus") final List<Gpu> gpus,
            @JsonProperty("attributes") final List<Map<String, String>> attributes) {
        Validate.notNull(
                hashRate,
                "Speed cannot be null");
        Validate.notNull(
                gpus,
                "GPUs cannot be null");
        Validate.notNull(
                attributes,
                "attributes cannot be null");
        this.hashRate = hashRate;
        this.gpus = new ArrayList<>(gpus);
        this.attributes = new ArrayList<>(attributes);
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
                            .append(this.hashRate, rig.hashRate)
                            .append(this.gpus, rig.gpus)
                            .append(this.attributes, rig.attributes)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the attributes.
     *
     * @return The attributes.
     */
    public List<Map<String, String>> getAttributes() {
        return Collections.unmodifiableList(this.attributes);
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.hashRate)
                .append(this.gpus)
                .append(this.attributes)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "hashRate=%s, " +
                        "gpus=%s, " +
                        "attributes=%s" +
                        " ]",
                getClass().getSimpleName(),
                this.hashRate,
                this.gpus,
                this.attributes);
    }

    /** A builder for creating {@link Rig Rigs}. */
    public static class Builder
            extends AbstractBuilder<Rig> {

        /** The attributes. */
        private final List<Map<String, String>> attributes = new ArrayList<>();

        /** The {@link Gpu GPUs}. */
        private final List<Gpu> gpus = new LinkedList<>();

        /** The speed info. */
        private BigDecimal hashRate;

        /**
         * Adds a rig attribute.
         *
         * @param key   The key.
         * @param value The value.
         *
         * @return This builder instance.
         */
        public Builder addAttribute(
                final String key,
                final String value) {
            if ((key != null) && (!key.isEmpty()) &&
                    (value != null) && (!value.isEmpty())) {
                this.attributes.add(
                        ImmutableMap.of(
                                "key",
                                key,
                                "value",
                                value));
            }
            return this;
        }

        /**
         * Adds the provided attributes.
         *
         * @param attributes The attributes to add.
         *
         * @return This builder instance.
         */
        public Builder addAttributes(
                final List<Map<String, String>> attributes) {
            if (attributes != null) {
                attributes.forEach(this::addAttributes);
            }
            return this;
        }

        /**
         * Adds the provided attributes.
         *
         * @param attributes The attributes to add.
         *
         * @return This builder instance.
         */
        public Builder addAttributes(final Map<String, String> attributes) {
            if (attributes != null) {
                this.attributes.add(new HashMap<>(attributes));
            }
            return this;
        }

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

        /**
         * Adds the {@link Gpu GPUs}.
         *
         * @param gpus The {@link Gpu GPUs} to add.
         *
         * @return This builder instance.
         */
        public Builder addGpus(final List<Gpu> gpus) {
            gpus.forEach(this::addGpu);
            return this;
        }

        @Override
        public Rig build() {
            return new Rig(
                    this.hashRate,
                    this.gpus,
                    this.attributes);
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
    }
}