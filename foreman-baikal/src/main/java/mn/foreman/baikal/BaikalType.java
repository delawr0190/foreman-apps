package mn.foreman.baikal;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link BaikalType} provides a {@link MinerType} implementation that
 * contains all of the known Baikal types.
 */
public enum BaikalType
        implements MinerType {

    /** A Baikal. */
    X("BKLU", "baikal-bk-x"),

    /** The Baikal giant. */
    GIANT("BKLS", "baikal-giantp-a2000");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, BaikalType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final BaikalType asicType : values()) {
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
    BaikalType(
            final String identifier,
            final String slug) {
        this.identifier = identifier;
        this.slug = slug;
    }

    /**
     * Converts the provided model to a {@link BaikalType}.
     *
     * @param identifier The ID.
     *
     * @return The corresponding {@link BaikalType}.
     */
    public static Optional<BaikalType> forIdentifier(final String identifier) {
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

    /**
     * Returns the identifier.
     *
     * @return The identifier.
     */
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getSlug() {
        return this.slug;
    }
}
