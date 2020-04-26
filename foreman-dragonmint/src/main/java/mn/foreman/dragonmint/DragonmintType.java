package mn.foreman.dragonmint;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link DragonmintType} provides a {@link MinerType} implementation that
 * contains all of the known Dragonmint types.
 */
public enum DragonmintType
        implements MinerType {

    /** The Dragonmint T1. */
    DRAGONMINT_T1("DT1", "dragonmint-t1"),

    /** The Dragonmint B52. */
    DRAGONMINT_B52("DB52", "dragonmint-b52");

    /** All of the types, by indicator, mapped to their type. */
    private static final Map<String, DragonmintType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final DragonmintType asicType : values()) {
            TYPE_MAP.put(asicType.indicator, asicType);
        }
    }

    /** The indicator. */
    private final String indicator;

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param indicator The indicator.
     * @param slug      The miner slug.
     */
    DragonmintType(
            final String indicator,
            final String slug) {
        this.indicator = indicator;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link DragonmintType}.
     *
     * @param model The model.
     *
     * @return The corresponding {@link DragonmintType}.
     */
    public static Optional<DragonmintType> forModel(final String model) {
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
