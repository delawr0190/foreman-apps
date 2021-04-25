package mn.foreman.goldshell;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link GoldshellType} provides a {@link MinerType} implementation that
 * contains all of the known Goldshell types.
 */
public enum GoldshellType
        implements MinerType {

    /** KD2. */
    KD2("Goldshell-KD2", "goldshell-kd2"),

    /** KD5. */
    KD5("Goldshell-KD5", "goldshell-kd5"),

    /** LT5. */
    LT5("Goldshell-LT5", "goldshell-lt5"),

    /** HS3. */
    HS3("Goldshell-HS3", "goldshell-hs3"),

    /** HS3-SE. */
    HS3SE("Goldshell-HS3-SE", "goldshell-hs3se"),

    /** HS5. */
    HS5("Goldshell-HS5", "goldshell-hs5"),

    /** CK5. */
    CK5("Goldshell-CK5", "goldshell-ck5");

    /** All of the types by model. */
    private static final Map<String, GoldshellType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final GoldshellType goldshellType : values()) {
            TYPE_MAP.put(goldshellType.model, goldshellType);
        }
    }

    /** The model. */
    private final String model;

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param model The model.
     * @param slug  The miner slug.
     */
    GoldshellType(
            final String model,
            final String slug) {
        this.model = model;
        this.slug = slug;
    }

    /**
     * Returns the type from the model.
     *
     * @param model The model.
     *
     * @return The type.
     */
    public static Optional<GoldshellType> fromModel(final String model) {
        return Optional.ofNullable(TYPE_MAP.get(model));
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
