package mn.foreman.avalon;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link AvalonType} provides a {@link MinerType} implementation that
 * contains all of the known Avalon types.
 */
public enum AvalonType
        implements MinerType {

    /** Avalon 741. */
    AVALON_741("Ver[741", "avalonminer-741"),

    /** Avalon 842. */
    AVALON_821("Ver[821", "avalonminer-821"),

    /** Avalon 841. */
    AVALON_841("Ver[841", "avalonminer-841"),

    /** Avalon 851. */
    AVALON_851("Ver[851", "avalonminer-851"),

    /** Avalon 911. */
    AVALON_911("Ver[911", "avalonminer-911"),

    /** Avalon 921. */
    AVALON_921("Ver[921", "avalonminer-921"),

    /** Avalon 1026. */
    AVALON_1026("Ver[1026", "avalonminer-1026"),

    /** Avalon 1047. */
    AVALON_1047("Ver[1047", "avalonminer-1047"),

    /** Avalon 1066. */
    AVALON_1066("Ver[1066", "avalonminer-1066");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, AvalonType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final AvalonType asicType : values()) {
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
    AvalonType(
            final String identifier,
            final String slug) {
        this.identifier = identifier;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link AvalonType}.
     *
     * @param identifier The ID.
     *
     * @return The corresponding {@link AvalonType}.
     */
    public static Optional<AvalonType> forIdentifier(final String identifier) {
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