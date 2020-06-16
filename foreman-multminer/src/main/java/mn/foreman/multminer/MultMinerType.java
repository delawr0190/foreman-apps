package mn.foreman.multminer;

import mn.foreman.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link MultMinerType} provides a {@link MinerType} implementation that
 * contains all of the known MultMiner types.
 */
public enum MultMinerType
        implements MinerType {

    /** M1. */
    M1(24, "multminer-m1");

    /** All of the types, by indicator, mapped to their type. */
    private static final Map<Integer, MultMinerType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final MultMinerType asicType : values()) {
            TYPE_MAP.put(asicType.numChips, asicType);
        }
    }

    /** The number of chips. */
    private final int numChips;

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param numChips The number of chips.
     * @param slug     The miner slug.
     */
    MultMinerType(
            final int numChips,
            final String slug) {
        this.numChips = numChips;
        this.slug = slug;
    }

    /**
     * Converts the provided model to an {@link MultMinerType}.
     *
     * @param numChips The number of chips.
     *
     * @return The corresponding {@link MultMinerType}.
     */
    public static Optional<MultMinerType> forNumChips(final int numChips) {
        return Optional.ofNullable(TYPE_MAP.get(numChips));
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
