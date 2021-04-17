package mn.foreman.epic;

import mn.foreman.model.MinerType;

import java.util.Optional;

/**
 * An {@link EpicType} provides a {@link MinerType} implementation that contains
 * all of the known epic types.
 */
public enum EpicType
        implements MinerType {

    /** SC200. */
    SC200("epic-sc200");

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param slug The miner slug.
     */
    EpicType(final String slug) {
        this.slug = slug;
    }

    /**
     * Returns the {@link EpicType}.
     *
     * @return The corresponding {@link EpicType}.
     */
    public static Optional<EpicType> forType() {
        return Optional.of(SC200);
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
