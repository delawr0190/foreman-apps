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

    /** The Avalon 741. */
    AVALON_741("Ver[741", 0),

    /** The Avalon 842. */
    AVALON_821("Ver[821", 0),

    /** The Avalon 841. */
    AVALON_841("Ver[841", 0),

    /** The Avalon 851. */
    AVALON_851("Ver[851", 0),

    /** The Avalon 911. */
    AVALON_911("Ver[911", 0),

    /** The Avalon 921. */
    AVALON_921("Ver[921", 0),

    /** The Avalon 1047. */
    AVALON_1047("Ver[1047", 0);

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, AvalonType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final AvalonType asicType : values()) {
            TYPE_MAP.put(asicType.identifier, asicType);
        }
    }

    /** The miner ID. */
    private final int id;

    /** The identifier. */
    private final String identifier;

    /**
     * Constructor.
     *
     * @param identifier The identifier.
     * @param id         The IDs.
     */
    AvalonType(
            final String identifier,
            final int id) {
        this.identifier = identifier;
        this.id = id;
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
    public int getId() {
        return this.id;
    }
}