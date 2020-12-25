package mn.foreman.cheetahminer;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link CheetahminerType} provides a {@link MinerType} implementation that
 * contains all of the known Cheetah types.
 */
public enum CheetahminerType
        implements MinerType {

    /** F1. */
    F1("F1", "cheetahminer-f1"),

    /** F3. */
    F3("F3", "cheetahminer-f3"),

    /** F5M. */
    F5M("F5M", "cheetahminer-f5m"),

    /** F5. */
    F5("F5", "cheetahminer-f5"),

    /** F5I. */
    F5I("F5I", "cheetahminer-f5i"),

    /** F7S. */
    F7S("F7S", "cheetahminer-f7s");

    /** All of the types, by indicator, mapped to their type. */
    private static final Map<String, CheetahminerType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final CheetahminerType asicType : values()) {
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
    CheetahminerType(
            final String model,
            final String slug) {
        this.model = model;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link CheetahminerType}.
     *
     * @param type The type.
     *
     * @return The corresponding {@link CheetahminerType}.
     */
    public static Optional<CheetahminerType> forType(final String type) {
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
