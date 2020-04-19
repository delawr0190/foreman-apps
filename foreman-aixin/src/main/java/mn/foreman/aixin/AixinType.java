package mn.foreman.aixin;

import mn.foreman.model.MinerType;

/**
 * A {@link AixinType} provides a {@link MinerType} implementation that contains
 * all of the known Aixin types.
 */
public enum AixinType
        implements MinerType {

    /** An A1. */
    AIXIN_A1(0);

    /** The ID. */
    private final int id;

    /**
     * Constructor.
     *
     * @param id The ID.
     */
    AixinType(final int id) {
        this.id = id;
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
