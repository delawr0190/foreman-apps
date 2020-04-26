package mn.foreman.dayun;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link DayunType} provides a {@link MinerType} implementation that
 * contains all of the known Dayun types.
 */
public enum DayunType
        implements MinerType {

    /** The Dayun Zig Z1. */
    DAYUN_ZIG_Z1("lyra2rev20", "dayun-zig-z1");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, DayunType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final DayunType asicType : values()) {
            TYPE_MAP.put(asicType.identifier, asicType);
        }
    }

    /** The identifier. */
    private final String identifier;

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param identifier The identifier.
     * @param slug       The slug.
     */
    DayunType(
            final String identifier,
            final String slug) {
        this.identifier = identifier;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link DayunType}.
     *
     * @param identifier The ID.
     *
     * @return The corresponding {@link DayunType}.
     */
    public static Optional<DayunType> forIdentifier(final String identifier) {
        if (identifier != null && !identifier.isEmpty()) {
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> identifier.startsWith(entry.getKey()))
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
