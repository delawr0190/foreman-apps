package mn.foreman.obelisk;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link ObeliskType} provides a {@link MinerType} implementation that
 * contains all of the known Dragonmint types.
 */
public enum ObeliskType
        implements MinerType {

    /** SC1. */
    SCI1("sc1", ObeliskGen.Gen1, "obelisk-sc1"),

    /** DCR1. */
    DCR1("dcr1", ObeliskGen.Gen1, "obelisk-dcr1"),

    /** SC1 Slim. */
    SC1_SLIM("sc1 slim", ObeliskGen.Gen2, "obelisk-sc1-slim"),

    /** DCR1 Slim. */
    DCR1_SLIM("dcr1 slim", ObeliskGen.Gen2, "obelisk-dcr1-slim"),

    /** An unknown model number. */
    UNKNOWN("obelisk", ObeliskGen.Gen1, "obelisk-sc1");

    /** All of the types, by indicator, mapped to their type. */
    private static final Map<String, ObeliskType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final ObeliskType asicType : values()) {
            TYPE_MAP.put(asicType.indicator, asicType);
        }
    }

    /** The obelisk generation. */
    private final ObeliskGen gen;

    /** The indicator. */
    private final String indicator;

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param indicator The indicator.
     * @param gen       The obelisk generation.
     * @param slug      The miner slug.
     */
    ObeliskType(
            final String indicator,
            final ObeliskGen gen,
            final String slug) {
        this.indicator = indicator;
        this.gen = gen;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link ObeliskType}.
     *
     * @param type The type.
     *
     * @return The corresponding {@link ObeliskType}.
     */
    public static Optional<ObeliskType> forType(final String type) {
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

    /**
     * Returns the generation.
     *
     * @return The generation.
     */
    public ObeliskGen getGen() {
        return this.gen;
    }

    @Override
    public String getSlug() {
        return this.slug;
    }
}
