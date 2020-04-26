package mn.foreman.spondoolies;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link SpondooliesType} provides a {@link MinerType} implementation that
 * contains all of the known Spondoolies types.
 */
public enum SpondooliesType
        implements MinerType {

    /** The SPx36. */
    SPONDOOLIES_SPX36("-spx", "spondoolies-spx36");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, SpondooliesType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final SpondooliesType asicType : values()) {
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
    SpondooliesType(
            final String identifier,
            final String slug) {
        this.identifier = identifier;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link SpondooliesType}.
     *
     * @param identifier The ID.
     *
     * @return The corresponding {@link SpondooliesType}.
     */
    public static Optional<SpondooliesType> forIdentifier(final String identifier) {
        if (identifier != null && !identifier.isEmpty()) {
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> identifier.endsWith(entry.getKey()))
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
