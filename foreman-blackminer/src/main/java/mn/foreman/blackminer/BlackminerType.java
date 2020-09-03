package mn.foreman.blackminer;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link BlackminerType} provides a {@link MinerType} implementation that
 * contains all of the known Blackminer types.
 */
public enum BlackminerType
        implements MinerType {

    /** Blackminer F1. */
    BLACKMINER_F1("Blackminer F1", "blackminer-f1"),

    /** Blackminer F1+. */
    BLACKMINER_F1P("Blackminer F1+", "blackminer-f1+"),

    /** Blackminer F1-single. */
    BLACKMINER_F1_SINGLE("Blackminer F1-single", "blackminer-f1-single"),

    /** Blackminer F1-ULTRA. */
    BLACKMINER_F1_ULTRA("Blackminer F1-ULTRA", "blackminer-f1-ultra"),

    /** Blackminer F1-Mini. */
    BLACKMINER_F1_MINI("Blackminer F1-MINI", "blackminer-f1-mini"),

    /** Blackminer F1-Mini. */
    BLACKMINER_F1_MINIP("Blackminer F1-MINI+", "blackminer-f1-mini+"),

    /** Blackminer F2. */
    BLACKMINER_F2("Blackminer F2", "blackminer-f2");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, BlackminerType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final BlackminerType asicType : values()) {
            TYPE_MAP.put(asicType.name, asicType);
        }
    }

    /** The miner name. */
    private final String name;

    /** The Foreman slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param name The miner name.
     * @param slug The slug.
     */
    BlackminerType(
            final String name,
            final String slug) {
        this.name = name;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link BlackminerType}.
     *
     * @param model The model.
     *
     * @return The corresponding {@link BlackminerType}.
     */
    public static Optional<BlackminerType> forModel(final String model) {
        Optional<BlackminerType> type = Optional.empty();
        if (model != null && !model.isEmpty()) {
            type =
                    TYPE_MAP.entrySet()
                            .stream()
                            .filter(entry -> model.equalsIgnoreCase(entry.getKey()))
                            .map(Map.Entry::getValue)
                            .findFirst();
            if (!type.isPresent()) {
                // See if just an F1 will work - better to keep things working
                if (model.startsWith(BLACKMINER_F1.name)) {
                    type = Optional.of(BLACKMINER_F1);
                }
            }
        }
        return type;
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
