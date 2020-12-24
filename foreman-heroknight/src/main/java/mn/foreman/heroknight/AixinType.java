package mn.foreman.heroknight;

import mn.foreman.model.MinerType;

/**
 * A {@link AixinType} provides a {@link MinerType} implementation that contains
 * all of the known Aixin types.
 */
public enum AixinType
        implements MinerType {

    /** An A1. */
    AIXIN_A1("aixin-a1");

    /** The slug. */
    private final String slug;

    /**
     * Constructor.
     *
     * @param slug The slug.
     */
    AixinType(final String slug) {
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
