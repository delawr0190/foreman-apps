package mn.foreman.hyperbit;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link HyperbitType} provides a {@link MinerType} implementation that
 * contains all of the known Hyperbit types.
 */
public enum HyperbitType
        implements MinerType {

    /** The Hyperbit BW L21. */
    BW_L21("ttyS1", "hyperbit-bw-l21");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, HyperbitType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final HyperbitType asicType : values()) {
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
    HyperbitType(
            final String identifier,
            final String slug) {
        this.identifier = identifier;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link HyperbitType}.
     *
     * @param identifier The ID.
     *
     * @return The corresponding {@link HyperbitType}.
     */
    public static Optional<HyperbitType> forIdentifier(final String identifier) {
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
