package mn.foreman.claymore;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@link TypeMapping} provides a mapping of slugs to multipliers to help with
 * hash rate scaling of Claymore types.
 */
public class TypeMapping {

    /** The multipliers. */
    private final Map<Pattern, BigDecimal> multiplier;

    /**
     * Constructor.
     *
     * @param builder The builder.
     */
    private TypeMapping(final Builder builder) {
        this.multiplier = new HashMap<>(builder.multipliers);
    }

    @Override
    public boolean equals(final Object other) {
        boolean equals = false;
        if (this == other) {
            equals = true;
        } else if ((other != null) && (getClass() == other.getClass())) {
            final TypeMapping mapping = (TypeMapping) other;
            equals =
                    new EqualsBuilder()
                            .append(this.multiplier, mapping.multiplier)
                            .isEquals();
        }
        return equals;
    }

    /**
     * Returns the multiplier for the slug.
     *
     * @param slug The slug.
     *
     * @return The multiplier, if present.
     */
    public Optional<BigDecimal> getMultiplierFor(final String slug) {
        return this.multiplier
                .entrySet()
                .stream()
                .filter(entry -> {
                    final Pattern pattern = entry.getKey();
                    final Matcher matcher = pattern.matcher(slug);
                    return matcher.matches();
                })
                .findAny()
                .map(Map.Entry::getValue);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.multiplier)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s [ multipliers=%s ]",
                getClass().getSimpleName(),
                this.multiplier);
    }

    /** A builder for creating {@link TypeMapping mappings}. */
    public static class Builder {

        /** The multipliers. */
        private final Map<Pattern, BigDecimal> multipliers = new HashMap<>();

        /**
         * Adds the provided mapping and multiplier.
         *
         * @param pattern    The pattern.
         * @param multiplier The multiplier.
         *
         * @return This builder instance.
         */
        public Builder addMapping(
                final String pattern,
                final BigDecimal multiplier) {
            this.multipliers.put(Pattern.compile(pattern), multiplier);
            return this;
        }

        /**
         * Builds the new mapping.
         *
         * @return The new mapping.
         */
        public TypeMapping build() {
            return new TypeMapping(this);
        }
    }
}
