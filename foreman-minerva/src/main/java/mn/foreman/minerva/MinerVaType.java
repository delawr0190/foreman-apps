package mn.foreman.minerva;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link MinerVaType} provides a {@link MinerType} implementation that
 * contains all of the known Miner-Va types.
 */
public enum MinerVaType
        implements MinerType {

    /** MV8. */
    MV8("C12", "mv8");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, MinerVaType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final MinerVaType asicType : values()) {
            TYPE_MAP.put(asicType.statName, asicType);
        }
    }

    /** The miner slug. */
    private final String slug;

    /** The miner name. */
    private final String statName;

    /**
     * Constructor.
     *
     * @param statName The miner stat name.
     * @param slug     The slug.
     */
    MinerVaType(
            final String statName,
            final String slug) {
        this.statName = statName;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link MinerVaType}.
     *
     * @param model The model.
     *
     * @return The corresponding {@link MinerVaType}.
     */
    public static Optional<MinerVaType> forModel(final String model) {
        Optional<MinerVaType> type = Optional.empty();
        if (model != null && !model.isEmpty()) {
            type =
                    TYPE_MAP.entrySet()
                            .stream()
                            .filter(entry -> model.equalsIgnoreCase(entry.getKey()))
                            .map(Map.Entry::getValue)
                            .findFirst();
        }
        return type;
    }

    @Override
    public Category getCategory() {
        return Category.ASIC;
    }

    @Override
    public String getSlug() {
        return String.format(
                "minerva-%s",
                this.slug);
    }
}
