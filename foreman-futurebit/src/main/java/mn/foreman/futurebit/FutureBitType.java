package mn.foreman.futurebit;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link FutureBitType} provides a {@link MinerType} implementation that
 * contains all of the known FutureBit types.
 */
public enum FutureBitType
        implements MinerType {

    /** The FutureBit Moonlander 2. */
    FUTUREBIT_MOONLANDER("MLD", "futurebit-moonlander");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, FutureBitType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final FutureBitType asicType : values()) {
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
    FutureBitType(
            final String identifier,
            final String slug) {
        this.identifier = identifier;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link FutureBitType}.
     *
     * @param identifier The ID.
     *
     * @return The corresponding {@link FutureBitType}.
     */
    public static Optional<FutureBitType> forIdentifier(final String identifier) {
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
