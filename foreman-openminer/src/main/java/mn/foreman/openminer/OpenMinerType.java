package mn.foreman.openminer;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link OpenMinerType} provides a {@link MinerType} implementation that
 * contains all of the known OpenMiner types.
 */
public enum OpenMinerType
        implements MinerType {

    /** Bitfury B8 - uninitialized. */
    B8_UNINITIALIZED(0, "bitfury-b8"),

    /** Bitfury B8. */
    B8_INITIALIZED(6, "bitfury-b8"),

    /** Bitfury Tardis. */
    TARDIS(8, "bitfury-tardis");

    /** All of the types, by boards, mapped to their type. */
    private static final Map<Integer, OpenMinerType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final OpenMinerType asicType : values()) {
            TYPE_MAP.put(asicType.boards, asicType);
        }
    }

    /** The boards. */
    private final int boards;

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param boards The boards.
     * @param slug   The miner slug.
     */
    OpenMinerType(
            final int boards,
            final String slug) {
        this.boards = boards;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link OpenMinerType}.
     *
     * @param boards The boards.
     *
     * @return The corresponding {@link OpenMinerType}.
     */
    public static Optional<OpenMinerType> forBoards(final int boards) {
        return Optional.ofNullable(TYPE_MAP.get(boards));
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
