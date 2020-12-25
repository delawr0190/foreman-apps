package mn.foreman.honorknight;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/** All of the known honorknight models. */
public enum HonorKnightType
        implements MinerType {

    /** K2. */
    K2("K2", "honorknight-k2"),

    /** K2.1. */
    K2_1("K2.1", "honorknight-k2-1"),

    /** K3. */
    K3("K3", "honorknight-k3"),

    /** K5. */
    K5("K5", "honorknight-k5");

    /** All of the types, by indicator, mapped to their type. */
    private static final Map<String, HonorKnightType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final HonorKnightType asicType : values()) {
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
    HonorKnightType(
            final String model,
            final String slug) {
        this.model = model;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link HonorKnightType}.
     *
     * @param type The type.
     *
     * @return The corresponding {@link HonorKnightType}.
     */
    public static Optional<HonorKnightType> forType(final String type) {
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
    public MinerType.Category getCategory() {
        return MinerType.Category.ASIC;
    }

    @Override
    public String getSlug() {
        return this.slug;
    }
}
