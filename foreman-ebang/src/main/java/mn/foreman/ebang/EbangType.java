package mn.foreman.ebang;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link EbangType} provides a {@link MinerType} implementation that
 * contains all of the known Ebang types.
 */
public enum EbangType
        implements MinerType {

    /** E12+. */
    E12P("e12+", "ebang-ebit-e12+"),

    /** E11++. */
    E11PP("e11++", "ebang-ebit-e11++"),

    /** E12. */
    E12("e12", "ebang-ebit-e12"),

    /** E11+. */
    E11P("e11+", "ebang-ebit-e11+"),

    /** E11. */
    E11("e11", "ebang-ebit-e11"),

    /** E10. */
    E10("e10", "ebang-ebit-e10"),

    /** E9.3. */
    E9P3("e9.3", "ebang-ebit-e9.3"),

    /** E9I. */
    E9I("e9i", "ebang-ebit-e9i"),

    /** E9.2. */
    E9P2("e9.2", "ebang-ebit-e9.2"),

    /** E9+. */
    E9P("e9+", "ebang-ebit-e9+");

    /** All of the types, by indicator, mapped to their type. */
    private static final Map<String, EbangType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final EbangType asicType : values()) {
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
    EbangType(
            final String indicator,
            final String slug) {
        this.indicator = indicator;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link EbangType}.
     *
     * @param type The type.
     *
     * @return The corresponding {@link EbangType}.
     */
    public static Optional<EbangType> forType(final String type) {
        if (type != null && !type.isEmpty()) {
            final String lowerType = type.toLowerCase();
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> lowerType.equals(entry.getKey()))
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
