package mn.foreman.baikal;

import mn.foreman.model.MinerType;

/**
 * A {@link BaikalType} provides a {@link MinerType} implementation that
 * contains all of the known Baikal types.
 */
public enum BaikalType
        implements MinerType {

    /** A Baikal. */
    GENERIC("baikal-generic");

    /** The miner slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param slug The slug.
     */
    BaikalType(
            final String slug) {
        this.slug = slug;
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
