package mn.foreman.autominer;

import mn.foreman.model.MinerFactory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link MinerMapping} provides a mapping between am miners and {@link
 * MinerFactory MinerFactories}.
 */
public class MinerMapping {

    /** The mappings. */
    private final Map<String, MinerFactory> mappings;

    /**
     * Constructor.
     *
     * @param builder The reference builder.
     */
    private MinerMapping(final Builder builder) {
        this.mappings = new HashMap<>(builder.mappings);
    }

    @Override
    public boolean equals(final Object other) {
        boolean isEqual = false;
        if (other == this) {
            isEqual = true;
        } else if ((other != null) && (getClass() == other.getClass())) {
            final MinerMapping mapping =
                    (MinerMapping) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.mappings,
                                    mapping.mappings)
                            .isEquals();
        }
        return isEqual;
    }

    /**
     * Returns the factory for the provided miner, assuming one exists.
     *
     * @param amMiner The miner.
     *
     * @return The factory.
     */
    public Optional<MinerFactory> getMiner(final String amMiner) {
        return Optional.ofNullable(this.mappings.get(amMiner));
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.mappings)
                .build();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        this.mappings.forEach((key, value) -> {
            stringBuilder
                    .append(key)
                    .append(" -> ")
                    .append(value)
                    .append(", ");
        });
        return String.format(
                "%s [ mappings=[ %s ] ]",
                getClass().getSimpleName(),
                stringBuilder.toString());
    }

    /** A builder for creating new mappings. */
    public static class Builder {

        /** The mappings. */
        private final Map<String, MinerFactory> mappings =
                new HashMap<>();

        /**
         * Adds a miner/factory mapping.
         *
         * @param amMiner      The miner.
         * @param minerFactory The factory.
         *
         * @return This builder instance.
         */
        public Builder addMapping(
                final String amMiner,
                final MinerFactory minerFactory) {
            this.mappings.put(amMiner, minerFactory);
            return this;
        }

        /**
         * Builds a new mapping.
         *
         * @return The new mapping.
         */
        public MinerMapping build() {
            return new MinerMapping(this);
        }
    }
}
