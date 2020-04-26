package mn.foreman.blackminer;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link BlackminerType} provides a {@link MinerType} implementation that
 * contains all of the known Blackminer types.
 */
public enum BlackminerType
        implements MinerType {

    /** The Blackminer F1. */
    BLACKMINER_F1("Blackminer F1", "blackminer-f1");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, BlackminerType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final BlackminerType asicType : values()) {
            TYPE_MAP.put(asicType.name, asicType);
        }
    }

    /** The miner name. */
    private final String name;

    /** The Foreman slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param name The miner name.
     * @param slug The slug.
     */
    BlackminerType(
            final String name,
            final String slug) {
        this.name = name;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link BlackminerType}.
     *
     * @param model The model.
     *
     * @return The corresponding {@link BlackminerType}.
     */
    public static Optional<BlackminerType> forModel(final String model) {
        if (model != null && !model.isEmpty()) {
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> model.startsWith(entry.getKey()))
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
