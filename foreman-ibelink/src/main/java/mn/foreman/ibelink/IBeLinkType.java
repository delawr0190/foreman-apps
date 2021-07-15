package mn.foreman.ibelink;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link IBeLinkType} provides a {@link MinerType} implementation that
 * contains all of the known iBeLink types.
 */
public enum IBeLinkType
        implements MinerType {

    /** BM-N1Max. */
    BM_N1_MAX("BM-N1Max", "ibelink-bm-n1max");

    /** The known types. */
    private static final Map<String, IBeLinkType> TYPES =
            new ConcurrentHashMap<>();

    static {
        for (final IBeLinkType type : values()) {
            TYPES.put(type.name, type);
        }
    }

    /** The name. */
    private final String name;

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param name The name.
     * @param slug The slug.
     */
    IBeLinkType(
            final String name,
            final String slug) {
        this.name = name;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link IBeLinkType}.
     *
     * @param name The name.
     *
     * @return The corresponding {@link IBeLinkType}.
     */
    public static Optional<IBeLinkType> forName(final String name) {
        if (name != null && !name.isEmpty()) {
            return TYPES.entrySet()
                    .stream()
                    .filter(entry -> name.startsWith(entry.getKey()))
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
