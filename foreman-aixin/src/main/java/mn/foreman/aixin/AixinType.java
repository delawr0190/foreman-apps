package mn.foreman.aixin;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link AixinType} provides a {@link MinerType} implementation that contains
 * all of the known Aixin types.
 */
public enum AixinType
        implements MinerType {

    /** An A1. */
    A1("A1", "aixin-a1");

    /** All of the types, by indicator, mapped to their type. */
    private static final Map<String, AixinType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final AixinType asicType : values()) {
            TYPE_MAP.put(
                    asicType.model.toLowerCase(),
                    asicType);
        }
    }

    /** The indicator. */
    private final String model;

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param model The indicator.
     * @param slug  The miner slug.
     */
    AixinType(
            final String model,
            final String slug) {
        this.model = model;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link AixinType}.
     *
     * @param type The type.
     *
     * @return The corresponding {@link AixinType}.
     */
    public static Optional<AixinType> forType(final String type) {
        if (type != null && !type.isEmpty()) {
            final String typeLower = type.toLowerCase();
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> typeLower.equals(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public Category getCategory() {
        return Category.ASIC;
    }

    @Override
    public String getSlug() {
        return this.slug;
    }
}
